/*
 * @(#)KaUserRegisterListener.java	2017年6月29日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.mq.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javaclub.sword.core.Entry;
import com.github.javaclub.sword.domain.ContextParam;
import com.github.javaclub.sword.domain.SerializableObject;
import com.github.javaclub.mq.client.customer.message.SubMessage;
import #{packagePrefix}#.mq.TmqDispatcher;
import #{packagePrefix}#.mq.TmqProcessor;
import #{packagePrefix}#.mq.listener.processor.ReceiveLongProcessor;
import #{packagePrefix}#.mq.listener.processor.UserSyncProcessor;

/**
 * KaUserRegisterListener 用户注册处理
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: KaUserRegisterListener.java 2017年6月29日 10:17:42 Exp $
 */
@Component
public class TestDummyListener extends TmqDispatcher<Long> {
	
	@Autowired
	private ReceiveLongProcessor receiveLongProcessor;
	
	@Autowired
	private UserSyncProcessor userSyncProcessor;
	

	@Override
	public ContextParam<Long> parse(SubMessage subMessage) {
		Long userId = SerializableObject.deserialize(subMessage.getBody());
		ContextParam<Long> contextParam = new ContextParam<>(userId);
		return contextParam;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public Entry<String, TmqProcessor<Long>>[] dispatchedProcessor() {
		return new Entry[] {
			new Entry("receiveLong", receiveLongProcessor),
			new Entry("syncRegUser", userSyncProcessor),
		};
	}


}
