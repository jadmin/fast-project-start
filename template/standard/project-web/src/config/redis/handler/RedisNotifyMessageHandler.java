/*
 * @(#)RedisNotifyMessageHandler.java	2020-10-11
 *
 * Copyright (c) 2020. All Rights Reserved.
 *
 */

package ${packagePrefix}.config.redis.handler;

/**
 * RedisNotifyMessageHandler
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: RedisNotifyMessageHandler.java 2020-10-11 17:02:39 Exp $
 */
public interface RedisNotifyMessageHandler {

	void handle(Object notifyMessage);
}
