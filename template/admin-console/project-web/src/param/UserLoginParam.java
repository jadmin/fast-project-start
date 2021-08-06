/*
 * @(#)UserLoginParam.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.param;

import lombok.Data;

/**
 * UserLoginParam
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: UserLoginParam.java ${currentTime} Exp $
 */
@Data
public class UserLoginParam {

	private String username;
	
	private String password;
	
	private boolean rememberMe;
	
	/**
     * 登陆类型
     *
     * @see ${packagePrefix}.common.enums.LoginTypeEnum
     */
    private Integer loginType;
}
