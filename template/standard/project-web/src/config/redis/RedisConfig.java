/*
 * @(#)RedisConfig.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.config.redis;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javaclub.sword.core.Strings;

import lombok.extern.slf4j.Slf4j;

/**
 * RedisConfig
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: RedisConfig.java ${currentTime} Exp $
 */
@Configuration
@EnableCaching
@Slf4j
public class RedisConfig extends CachingConfigurerSupport {
	
	@Value("${redis.notify.message.topics:'redisPub'}")
	private String redisNotifyTopics;

	@Bean("redisTemplate")
	@Primary
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {

		RedisTemplate<String, Object> template = new RedisTemplate<>();
		// 配置连接工厂
		template.setConnectionFactory(factory);
		
		// 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
		Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);

		ObjectMapper om = new ObjectMapper();
		om.setSerializationInclusion(Include.NON_NULL); // 不序列化null值
		// 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
		om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		// 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
		om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		jacksonSeial.setObjectMapper(om);
		
		// 设置key序列化模式
		template.setKeySerializer(new StringRedisSerializer());
		template.setHashKeySerializer(new StringRedisSerializer());

		// 值采用json序列化
		template.setValueSerializer(jacksonSeial);
		template.setHashValueSerializer(jacksonSeial);
		
		template.afterPropertiesSet();

		return template;
	}
	
	@Bean("stringRedisTemplate")
	public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) throws Exception {
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}
	
	@Bean
    public MessageListenerAdapter messageListenerAdapter(MessageListener messageListener) {
        return new MessageListenerAdapter(messageListener);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory,
            MessageListenerAdapter messageListenerAdapter) {

        List<Topic> collection = new ArrayList<Topic>();
        
        // 普通订阅，订阅具体的频道
        String[] redisTopics = Strings.splitAndTrim(redisNotifyTopics, ",");
        if(null != redisTopics && redisTopics.length > 0) {
        	for (String topic : redisTopics) {
        		collection.add(new ChannelTopic(topic));
			}
        }

        RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(redisConnectionFactory);
        redisMessageListenerContainer.addMessageListener(messageListenerAdapter, collection);
        return redisMessageListenerContainer;
    }

}
