/*
 * @(#)JedisUtil.java	2018年3月1日
 *
 * Copyright (c) 2018. All Rights Reserved.
 *
 */

package #{packagePrefix}#.utils;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;

/**
 * JedisUtil
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: JedisUtil.java 2018年3月1日 15:53:50 Exp $
 */
public class JedisUtil {
	
	static Logger log = LoggerFactory.getLogger(JedisUtil.class);

	/**
     * 用于redis排它锁
     * 调用方通过响应值来判断：1）true，抢占成功，进入业务逻辑处理，2）false，抢占失败，可能是重复提交产生，可以提示错误
     * <p>示例代码：
     * <blockquote><pre>
     *     String lockKey = "xxx-key-32344";
     *     boolean isDuplicatedInvoke = false;
     *     try (Jedis jedis = jedisConnectionFactory.getConnection().getNativeConnection()) {
     *         boolean getLock = JedisUtils.tryLock(jedis, lockKey, 5, TimeUnit.MINUTES);
     *         if (!getLock) {
     *             isDuplicatedInvoke = true;
     *             return getFailedSdgResult("重复请求，工单：" + workOrderId);
     *         }
     *         // ...
     *         // your biz code
     *         // ...
     *     } finally {
     *         if (!isDuplicatedInvoke) {
     *             redisTemplate.delete(lockKey);
     *         }
     *     }
     * </pre></blockquote>
     *
     * @param jedis    可以通过 try(Jedis jedis = jedisConnectionFactory.getConnection().getNativeConnection()) 获取
     * @param redisKey redis key
     * @param time     超时时间
     * @param timeUnit 超时时间单位
     * @return 获取锁是否成功
     */
	public static boolean tryLock(Jedis jedis, String redisKey, long time, TimeUnit timeUnit) {
        String setRet = jedis.set(redisKey, "1", "NX", "EX", timeUnit.toSeconds(time));
        if (setRet == null) {
        	log.info("[tryLock], setRet is null, redisKey={}", redisKey);
        }
        return setRet != null;
    }
	
	
}
