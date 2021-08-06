/*
 * @(#)BlackWhiteMessageHandler.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.config.redis.handler.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import ${packagePrefix}.common.AppConstants.BlackWhiteList;
import ${packagePrefix}.common.AppConstants.RedisKeys;
import ${packagePrefix}.common.constants.RedisKeyConstants;
import ${packagePrefix}.common.model.bo.BwlCacheRefreshBO;
import ${packagePrefix}.config.redis.handler.RedisNotifyMessageHandler;
import ${packagePrefix}.manager.impl.BlackWhiteCacheManager;
import ${packagePrefix}.support.RedisCacheManager;

import lombok.extern.slf4j.Slf4j;

/**
 * BlackWhiteMessageHandler
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: BlackWhiteMessageHandler.java ${currentTime} Exp $
 */
@Component
@Slf4j
public class BlackWhiteMessageHandler implements RedisNotifyMessageHandler {
	
	@Autowired
	private BlackWhiteCacheManager blackWhiteCacheManager;
	@Autowired
	private RedisCacheManager redisCacheManager;

	@Override
	public void handle(Object notifyMessage) {
		try {
			String cacheKey = null;
			BwlCacheRefreshBO bwlFreshBO = JSONObject.parseObject(notifyMessage.toString(), BwlCacheRefreshBO.class);
			switch (bwlFreshBO.getActionType()) {
			    case BlackWhiteList.ACTION_TYPE_ADD:
			        cacheKey = blackWhiteCacheManager.cacheKey(bwlFreshBO.getBizCode(),bwlFreshBO.getBwlType());
			        blackWhiteCacheManager.addCacheElement(cacheKey, bwlFreshBO.getIdentity());
			        break;
			    case BlackWhiteList.ACTION_TYPE_BATCH_ADD:
			        blackWhiteCacheManager.rebuildOneGroupCache(bwlFreshBO.getBizCode());
			        break;
			    case BlackWhiteList.ACTION_TYPE_DELETE:
			        cacheKey = blackWhiteCacheManager.cacheKey(bwlFreshBO.getBizCode(),bwlFreshBO.getBwlType());
			        blackWhiteCacheManager.deleteCacheElement(cacheKey, bwlFreshBO.getIdentity());
			        break;
			    case BlackWhiteList.ACTION_TYPE_RELOAD_ALL:
			        String lockKey = RedisKeys.formatKey(RedisKeyConstants.BWL_CAHCE_REFRESH_RELOAD_ALL, null);
			        boolean aquiredLock = redisCacheManager.weakLock(lockKey, "true", 10L);
			        if(!aquiredLock) {
			        	log.error("获取 {} 锁失败，不重新加载缓存", lockKey);
			        	return;
			        }
			        blackWhiteCacheManager.rebuildCache();
			        break;
			    default:
			}
		} catch (Exception e) {
			log.error("", e);
		}

	}

}
