/*
 * @(#)RedisMessageReceiveListener.java	2019年6月25日
 *
 * Copyright (c) 2019. All Rights Reserved.
 *
 */

package ${packagePrefix}.config.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import ${packagePrefix}.config.redis.handler.RedisNotifyMessageHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * RedisMessageReceiveListener
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: RedisMessageReceiveListener.java 2019年6月25日 11:26:40 Exp $
 */
@Component
@Slf4j
public class RedisMessageReceiveListener implements MessageListener {
	
	@Autowired
    private RedisTemplate<String, Object> redisTemplate;
	
	@Override
	public void onMessage(Message message, byte[] pattern) {
		RedisSerializer<?> serializer = redisTemplate.getValueSerializer();
		String channel = redisTemplate.getStringSerializer().deserialize(message.getChannel());
		Object messageBody = serializer.deserialize(message.getBody());
        log.info("Redis message received: pattern={}，channel={}, messageBody={}", new String(pattern), channel, messageBody);
        
        RedisNotifyMessageHandler handler = RedisNotifyHandlerFactory.route(channel);
        if(null == handler) {
        	log.warn("No RedisNotifyMessageHandler for topic={}", channel);
        	return;
        }
        
        handler.handle(messageBody);
	}

}
