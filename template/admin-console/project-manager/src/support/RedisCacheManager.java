/*
 * @(#)RedisCacheManager.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

import com.github.javaclub.sword.component.SpringDataRedisCache;
import com.github.javaclub.sword.core.Strings;
import com.google.common.collect.Maps;
import ${packagePrefix}.common.model.RedisInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * RedisCacheManager
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: RedisCacheManager.java ${currentTime} Exp $
 */
@Component
@Slf4j
public class RedisCacheManager extends SpringDataRedisCache {
	
	@Autowired
	private RedisConnectionFactory redisConnectionFactory;
	
	/**
	 * Redis详细信息
	 */
	public List<RedisInfo> getRedisInfo() throws Exception {
		Properties info = redisConnectionFactory.getConnection().info();
		List<RedisInfo> infoList = new ArrayList<RedisInfo>();
		RedisInfo redisInfo = null;
		for (Map.Entry<Object, Object> entry : info.entrySet()) {
			if(null == entry.getKey()) {
				continue;
			}
			redisInfo = new RedisInfo();
			redisInfo.setKey(Strings.anyToString(entry.getKey(), true));
			redisInfo.setValue(Strings.anyToString(entry.getValue(), true));
			infoList.add(redisInfo);
		}
		return infoList;
	}
	
	/**
	 * Redis key的总数量
	 */
	public Map<String, Object> getKeysSize() throws Exception {
		Long dbSize = redisConnectionFactory.getConnection().dbSize();
		Map<String, Object> map = new HashMap<>();
		map.put("create_time", System.currentTimeMillis());
		map.put("dbSize", dbSize);

		log.info("--getKeysSize--: " + map.toString());
		return map;
	}

	/**
	 * Redis内存占用情况
	 */
	public Map<String, Object> getMemoryInfo() throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		Properties info = redisConnectionFactory.getConnection().info();
		for (Map.Entry<Object, Object> entry : info.entrySet()) {
			String key = Strings.anyToString(entry.getKey(), true);
			if ("used_memory".equals(key)) {
				map.put("used_memory", entry.getValue());
				map.put("create_time", System.currentTimeMillis());
			}
		}
		log.info("--getMemoryInfo--: " + map.toString());
		return map;
	}

}
