/*
 * @(#)TmqPublisher.java	2017年9月14日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.mq;

import java.io.Serializable;

import com.github.javaclub.mq.client.producer.SendResult;

/**
 * TmqPublisher 消息发布统一处理
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: TmqPublisher.java 2017年9月14日 14:46:46 Exp $
 */
public interface TmqPublisher {

	/**
	 * 发送字符消息
	 *
	 * @param topic		主题
	 * @param subTopic  子主题
	 * @param msgBody   消息原生字符串
	 * @return
	 */
	SendResult sendStringMessage(String topic, String subTopic, String msgBody);
	
	/**
	 * 发送序列化对象消息
	 *
	 * @param topic		主题
	 * @param subTopic	子主题
	 * @param msgObj    消息序列化对象
	 * @return
	 */
	SendResult sendObjectMessage(String topic, String subTopic, Serializable msgObj);
}
