/*
 * @(#)ZooPlayerService.java	2017年7月18日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.api;

import com.github.javaclub.sword.domain.ResultDO;

/**
 * ZooPlayerService 测试用
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: ZooPlayerService.java 2017年7月18日 9:41:05 Exp $
 */
public interface ZooPlayerService {
	
	ResultDO<Long> create(String txt);
	
	boolean operate();
	
	String get();
}
