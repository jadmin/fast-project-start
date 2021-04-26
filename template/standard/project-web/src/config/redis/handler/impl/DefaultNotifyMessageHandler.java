/*
 * @(#)DefaultNotifyMessageHandler.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.config.redis.handler.impl;

import org.springframework.stereotype.Component;

import ${packagePrefix}.config.redis.handler.RedisNotifyMessageHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * DefaultNotifyMessageHandler
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: DefaultNotifyMessageHandler.java ${datetime} Exp $
 */
@Component
@Slf4j
public class DefaultNotifyMessageHandler implements RedisNotifyMessageHandler {

	@Override
	public void handle(Object notifyMessage) {
		
		// do Nothing

	}

}
