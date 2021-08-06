/*
 * @(#)UserLoginConfirmParam.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.service.params;

import lombok.Data;

/**
 * UserLoginConfirmParam
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: UserLoginConfirmParam.java ${currentTime} Exp $
 */
@Data
public class UserLoginConfirmParam {

	/**
	 * 用户ID 
	 */
	private Long uid;
	
	/**
	 * 验证码 
	 */
	private String code;
	
	/**
	 * 验证类型：1-短信验证码  2-Google身份验证动态码 
	 */
	private Integer type;
	
	private String loginUA;
	private String loginIp;
}
