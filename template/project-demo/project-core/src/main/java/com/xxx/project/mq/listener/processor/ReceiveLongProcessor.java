/*
 * @(#)PushCouponAfterUserRegisterProcessor.java	2017年11月17日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.mq.listener.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.javaclub.sword.domain.ContextParam;
import #{packagePrefix}#.mq.TmqProcessor;

/**
 * 用户注册后给用户发券
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: PushCouponAfterUserRegisterProcessor.java 2017年11月17日 10:03:55 Exp $
 */
@Component
public class ReceiveLongProcessor implements TmqProcessor<Long> {
	
	static final Logger log = LoggerFactory.getLogger(ReceiveLongProcessor.class);

	@Override
	public boolean execute(ContextParam<Long> context) {
		Long longValue = context.getTarget();
		log.warn("ReceiveLong => {}", longValue);
		return null != longValue && longValue > 0;
	}
	

}
