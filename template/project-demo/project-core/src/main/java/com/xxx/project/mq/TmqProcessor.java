/*
 * @(#)TmqProcessor.java	2017年11月16日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.mq;

import com.github.javaclub.sword.domain.ContextParam;

/**
 * TmqProcessor
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: TmqProcessor.java 2017年11月16日 18:37:36 Exp $
 */
public interface TmqProcessor<T> {
	
	/**
	 * 消息处理器执行(须保证本次操作的幂等性)
	 */
	boolean execute(ContextParam<T> context);
}
