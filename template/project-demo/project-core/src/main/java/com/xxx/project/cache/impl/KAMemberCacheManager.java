/*
 * @(#)KAMemberCacheManager.java	2017年5月31日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.cache.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.TypeReference;
import com.github.javaclub.sword.cache.CacheManager;
import #{packagePrefix}#.cache.KVCacheService;

/**
 * KAMemberCacheManager
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: KAMemberCacheManager.java 2017年5月31日 11:41:49 Exp $
 */
public class KAMemberCacheManager implements CacheManager<String, Object> {
	
	static final Logger log = LoggerFactory.getLogger(KAMemberCacheManager.class);
	
	@Autowired
	private KVCacheService kVCacheService;
	

	@Override
	public boolean lock(String key, int seconds) {
		return kVCacheService.lock(key, seconds);
	}

	@Override
	public boolean unlock(String key) {
		return kVCacheService.unlock(key);
	}

	@Override
	public boolean put(String key, Object value) {
		return kVCacheService.put(key, value);
	}

	@Override
	public boolean put(String key, Object value, long expireTime) {
		return kVCacheService.put(key, value, expireTime);
	}

	@Override
	public Object get(String key) {
		return kVCacheService.get(key);
	}

	@Override
	public boolean setNX(String key, Object value, long expireTime) {
		return kVCacheService.setIfNotExists(key, expireTime);
	}

	@Override
	public boolean remove(String key) {
		boolean flag = false;
		try {
			kVCacheService.del(key);
			flag = true;
		} catch (Exception e) {
			log.error("", e);
		}
		
		return flag;
	}

	@Override
	public long incr(String key) {
		return kVCacheService.inc(key);
	}

	@Override
	public long incr(String key, long value) {
		return kVCacheService.inc(key, value);
	}

	@Override
	public long decr(String key) {
		return kVCacheService.decr(key);
	}

	@Override
	public long decr(String key, long value) {
		return kVCacheService.decr(key, value);
	}

	@Override
	public boolean expire(String key, long seconds) {
		return kVCacheService.expire(key, seconds);
	}

	@Override
	public boolean exists(String key) {
		return kVCacheService.exists(key);
	}
	
	/**
     * @param key
     * @param object
     * @param expire
     *            单位秒
     */
    public void setObject(String key, Object object, long expire){
    	
    }
    
    /**
     * 原子锁方式放入对象。<b>因为setNX不能设置expire时间，设置超时时间是二次提交，可能会有不成功的问题</b>
     * @param key
     * @param obj
     * @param expire
     * @return
     */
    public Boolean setObjectIfNotExists(String key, Object obj, long expire){
    	return kVCacheService.setIfNotExists(key, expire);
    }

    public <T> T getObject(String key, Class<T> clazz){
    	return kVCacheService.getObject(key, clazz);
    }

   /**
    * 从redis中获取对象时，对集合类的json解析的支持
    * @author liwentao
    * @param key
    * @param typeReference
    * @return
    *2017年11月1日
    */
    public <T> T getObject(final String key, TypeReference<T> typeReference){
    	return kVCacheService.getObject(key, typeReference);
    }

}
