/*
 * @(#)TmqReciever.java	2017年5月22日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.mq;

import com.github.javaclub.sword.core.Entry;
import com.github.javaclub.mq.client.customer.message.MessageListener;

/**
 * TmqReciever 消息订阅统一处理
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: TmqReciever.java 2017年5月22日 9:25:39 Exp $
 */
public interface TmqReciever {

	/**
	 * 订阅初始化
	 */
	void init();
	
	/**
	 * 订阅哪些消息
	 */
	Entry<String, MessageListener>[] subscribeTopics();
}
