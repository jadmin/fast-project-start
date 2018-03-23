/*
 * @(#)KaAssociateAfterUserRegisterProcessor.java	2017年11月17日
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
 * 用户注册后，同步处理
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: KaAssociateAfterUserRegisterProcessor.java 2017年11月17日 10:09:48 Exp $
 */
@Component
public class UserSyncProcessor implements TmqProcessor<Long> {
	
	private static final Logger log = LoggerFactory.getLogger(UserSyncProcessor.class);

	@Override
	public boolean execute(ContextParam<Long> context) { // 必须保证强幂等性
		Long userId = (Long) context.getTarget();
		boolean success = this.syncRegUser(userId);
		return success;
	}

	boolean syncRegUser(Long userId) {
		log.warn("syncRegUser => {}", userId);
		return true;
	}

}
