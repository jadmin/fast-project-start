/*
 * @(#)RedisNotifyHandlerFactory.java	2018年12月11日
 *
 * Copyright (c) 2018. All Rights Reserved.
 *
 */

package ${packagePrefix}.config.redis;

import java.util.Map;

import com.github.javaclub.sword.core.Strings;
import com.google.common.collect.Maps;
import ${packagePrefix}.config.redis.handler.RedisNotifyMessageHandler;
import ${packagePrefix}.config.redis.handler.impl.DefaultNotifyMessageHandler;
import ${packagePrefix}.support.BeanFactory;

/**
 * RedisNotifyHandlerFactory
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: RedisNotifyHandlerFactory.java 2018年12月11日 14:47:07 Exp $
 */
public class RedisNotifyHandlerFactory {

	private static Map<String, Class<?>> maps = Maps.newHashMap();
	private static Map<String, String> actionAndBeanNameMap = Maps.newHashMap();

	static {

	}

	public static void register(String code, Class<?> clazz) {
		maps.put(code, clazz);
	}
	
	public static void registerCompatible(String code, Class<?> clazz, String beanName) {
		if(null != clazz) {
			maps.put(code, clazz);
		}
		if(Strings.isNotBlank(beanName)) {
			actionAndBeanNameMap.put(code, beanName);
		}
	}

	public static Class<?> get(String code) {
		return maps.get(code);
	}
	
	public static RedisNotifyMessageHandler route(String code) {
		Class<?> clazz = get(code);
		if(null == clazz) {
			if(null != actionAndBeanNameMap.get(code)) {
				RedisNotifyMessageHandler builder = (RedisNotifyMessageHandler) BeanFactory.getInstance().getBean(actionAndBeanNameMap.get(code));
				if(null != builder) {
					return builder;
				}
			}
			clazz = DefaultNotifyMessageHandler.class;
		}
		return (RedisNotifyMessageHandler) BeanFactory.getInstance().getBean(clazz);
	}
	
}
