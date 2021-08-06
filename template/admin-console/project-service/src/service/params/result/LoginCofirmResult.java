/*
 * @(#)LoginCofirmResult.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.service.params.result;

import java.io.Serializable;

import ${packagePrefix}.dataobject.LoginUserDO;

import lombok.Data;

/**
 * LoginCofirmResult
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: LoginCofirmResult.java ${currentTime} Exp $
 */
@Data
public class LoginCofirmResult implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private LoginUserDO loginUser;
	
	private boolean rememberMe;

}
