/*
 * @(#)TmqPublisherImpl.java	2017年9月14日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.mq.impl;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.util.ByteUtil;
import #{packagePrefix}#.mq.TmqPublisher;
import com.github.javaclub.mq.client.producer.Producer;
import com.github.javaclub.mq.client.producer.SendResult;
import com.github.javaclub.mq.client.producer.message.PubMessage;

/**
 * TmqPublisherImpl
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: TmqPublisherImpl.java 2017年9月14日 14:57:05 Exp $
 */
@Component
public class TmqPublisherImpl implements TmqPublisher {
	
	static final Logger log = LoggerFactory.getLogger("sendMqLogger");
	static final Logger appLog = LoggerFactory.getLogger(TmqPublisherImpl.class);

    @Value("#{APP_PROP['config.server.env']}")
    private String dev;

    @Value("#{APP_PROP['tmq.server.publisher.appId']}")
    private int appId;

    @Value("#{APP_PROP['tmq.server.publisher.appKey']}")
    private String appKey;

    private Producer producer;

    @PostConstruct
    public void init() {
        try {
        	appLog.warn("[ignore] env=" + dev + ",appId=" + appId + ",appkey=" + appKey + " TmqPublisher init start ...");
        	
            producer = new Producer(appId, appKey, dev);
            producer.init();
            
            appLog.warn("[ignore] env=" + dev + ",appId=" + appId + ",appkey=" + appKey + " TmqPublisher init successfully ...");
            
        } catch (Exception e) {
        	appLog.error("TmqPublisher init fail", e);
        }
    }

	@Override
	public SendResult sendStringMessage(String topic, String subTopic, String msgBody) {
		try {
			
			log.info("投递消息：topic={} | {}\t body={}\t 消息开始投递", topic, subTopic, msgBody);
			
			PubMessage pubMessage = new PubMessage();
			pubMessage.setKey(getMsgKeyPre(topic, subTopic) + String.valueOf(System.currentTimeMillis()));
			pubMessage.setBody(msgBody.getBytes("UTF-8"));
			pubMessage.setTopic(topic);
			if(Strings.isNotBlank(subTopic)) {
				pubMessage.setSubTopic(subTopic);
			}

			// 发送消息
			SendResult result = producer.sendMessage(pubMessage);
			
			log.info("投递消息：topic={} | {}\t body={}\t 消息投递结果：{}", topic, subTopic, msgBody, result.toString());

			return result;
			
		} catch (Exception e) {
			String msg = String.format("发送消息异常:[body=%s,topic=%s]", msgBody, topic + "|" + subTopic);
			log.error("", e);
			throw new RuntimeException(msg);
		}
	}

	@Override
	public SendResult sendObjectMessage(String topic, String subTopic, Serializable msgObj) {
		try {
			
			log.info("投递消息：topic={} | {}\t bodyObject={}\t 消息开始投递", topic, subTopic, msgObj);
			
			PubMessage pubMessage = new PubMessage();
			pubMessage.setKey(getMsgKeyPre(topic, subTopic) + String.valueOf(System.currentTimeMillis()));
			pubMessage.setBody(ByteUtil.object2ByteArray(msgObj));
			pubMessage.setTopic(topic);
			if(Strings.isNotBlank(subTopic)) {
				pubMessage.setSubTopic(subTopic);
			}

			// 发送消息
			SendResult result = producer.sendMessage(pubMessage);
			
			log.info("投递消息：topic={} | {}\t bodyObject={}\t 消息投递结果：{}", topic, subTopic, msgObj, result.toString());

			return result;
			
		} catch (Exception e) {
			String msg = String.format("发送消息异常:[body=%s,topic=%s]", msgObj, topic + "|" + subTopic);
			log.error("", e);
			throw new RuntimeException(msg);
		}
	}
	
	String getMsgKeyPre(String topic, String subTopic) {
		if(Strings.isNotBlank(subTopic)) {
			return Strings.concat(topic, "-", subTopic + "-");
		}
		return Strings.concat(topic, "-");
	}
	

}
