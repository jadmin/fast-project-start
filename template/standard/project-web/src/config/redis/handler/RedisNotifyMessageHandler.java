/*
 * @(#)RedisNotifyMessageHandler.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.config.redis.handler;

/**
 * RedisNotifyMessageHandler
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: RedisNotifyMessageHandler.java ${currentTime} Exp $
 */
public interface RedisNotifyMessageHandler {

	void handle(Object notifyMessage);
}
