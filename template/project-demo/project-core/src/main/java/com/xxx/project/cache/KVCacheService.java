/*
 * @(#)KVCacheService.java	2017年5月31日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.cache;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.TypeReference;

public interface KVCacheService {
	
	boolean put(String key, Object object);
	boolean put(String key, Object object, long expire);
	Object get(String key);
	
	/**
	 * 防并发加锁
	 *
	 * @param key     加锁key
	 * @param seconds 秒
	 * @return
	 */
	boolean lock(String key, int seconds);
	
	/**
	 * 释放锁
	 *
	 * @param key  加锁key
	 * @return
	 */
	boolean unlock(String key);

    /**
     * 检查key 是否存在
     * 
     * @param key
     * @return
     */
    public boolean exists(String key);

    public void setObject(String key, Object object);

    public boolean expire(final String key, final long seconds);

    /**
     * @param key
     * @param object
     * @param expire
     *            单位秒
     */
    public void setObject(String key, Object object, long expire);
    
    /**
     * 原子锁方式放入对象。<b>因为setNX不能设置expire时间，设置超时时间是二次提交，可能会有不成功的问题</b>
     * @param key
     * @param obj
     * @param expire
     * @return
     */
    public Boolean setObjectIfNotExists(String key, Object obj, long expire);

    public <T> T getObject(String key, Class<T> clazz);

    /**
     * @description:从redis中获取对象时，对集合类的json解析的支持
     * @param key
     * @param typeReference
     * @return_type:T
     * @author: caikunxiao
     * @time:2015年8月11日 上午10:31:53
     */
    public <T> T getObject(final String key, TypeReference<T> typeReference);

    public long inc(final String key);

    public long inc(final String key, final long value);

    public long getLong(final String key);

    public void setLong(final String key, final long object);

    public long decr(final String key);

    public long decr(final String key, final long value);

    public void setString(String key, String object);

    public String getString(String key);

    public void del(String key);

    /**
     * 批量获取
     * 
     * @param list
     * @return
     */
    public Map<String, Long> batchGetLong(final List<String> list);
    
    
    /**
     * set 
     * @param key
     * @param objects
     * @return
     */
    public Long sadd(String key, Object... objects);
    
    /**
     * 获取set所有对象
     * @param key
     * @param clazz
     * @return
     */
    public <T> List<T> getSmembers(String key, Class<T> clazz);
    
    /**
     * 
     * @param key
     * @param clazz
     * @return
     */
    public <T> T sPop(String key, Class<T> clazz);
    
    
    public <T> Long sRem(String key, List<T> objects);
    
    public void del( List<String> keys);

    /**
     * 分布式锁
     * 
     * @param key
     * @param expire
     * @return
     */
    Boolean setIfNotExists(String key, final long expire);

    /**
     * redis pub message
     * 
     * @param channel
     * @param message
     */
    public void pubMessage(String channel, Object message);
}
