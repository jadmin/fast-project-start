/*
 * @(#)UserModelParam.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.service.params;

import java.io.Serializable;

import lombok.Data;

/**
 * UserModelParam
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: UserModelParam.java ${currentTime} Exp $
 */
@Data
public class UserModelParam implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private boolean rememberMe;
	private Long loginTime;
	
	private Long userid;
	private String username;
	
	private String loginUA;
	private String loginIp;

}
