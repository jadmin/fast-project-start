/*
 * @(#)TmqRecieverImpl.java	2017年5月22日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.mq.impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.javaclub.sword.BizException;
import com.github.javaclub.sword.core.B;
import com.github.javaclub.sword.core.Entry;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.mq.client.customer.Customer;
import com.github.javaclub.mq.client.customer.message.MessageListener;
import #{packagePrefix}#.mq.TmqReciever;
import #{packagePrefix}#.mq.listener.TestDummyListener;

/**
 * TmqRecieverImpl
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: TmqRecieverImpl.java 2017年5月22日 9:32:30 Exp $
 */
@Component
public class TmqRecieverImpl implements TmqReciever, InitializingBean {
	
	static final Logger log = LoggerFactory.getLogger(TmqRecieverImpl.class);
	
	@Value("#{APP_PROP['config.server.env']}")
    private String dev;
	
    @Value("#{APP_PROP['tmq.server.consumer.appId']}")
    private int appID;
    
    @Value("#{APP_PROP['tmq.server.consumer.appKey']}")
    private String appkey;
    
    @Autowired
    private TestDummyListener testDummyListener;
    
	private Customer customer = null;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.init();
	}

	@Override
	public void init() {
		try {
			log.warn("[ignore] env=" + dev + ",appID=" + appID + ",appkey=" + appkey + " TmqReciever init start ...");
			if(null == customer) {
				customer = new Customer(appID, appkey, dev);
			}
			if(null == customer) {
				throw new BizException("====== TmqReciever init failed ======");
			}
			customer.init();
			
			Entry<String, MessageListener>[] subscribeTopics = this.subscribeTopics();
			for (Entry<String, MessageListener> entry : subscribeTopics) {
				this.register(entry.getKey(), entry.getValue());
			}
			log.warn("[ignore] env=" + dev + ",appID=" + appID + ",appkey=" + appkey + " TmqReciever init successfully ...");
		} catch (Exception e) {
			log.error("TmqRecieverImpl init Exception", e);
			throw e;
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Entry<String, MessageListener>[] subscribeTopics() {
		return new Entry[] {
			
			new Entry("test | dummy", testDummyListener),
			
		};
	}

	void register(String topic, MessageListener listener) {
		String[] array = Strings.splitAndTrim(topic, "|");
		if(B.isEmpty(array)) {
			return;
		}
		if(2 == array.length) { // 子主题消息
			customer.subscribe(array[0].trim(), array[1].trim(), listener);
		} else {
			customer.subscribe(topic.trim(), listener);
		}
	}
	
	public static void main(String[] args) {
		String text = "wx_market_msg | user_del_card";
		String[] array = Strings.splitAndTrim(text, "|");
		System.out.println(Arrays.asList(array));
	}

}
