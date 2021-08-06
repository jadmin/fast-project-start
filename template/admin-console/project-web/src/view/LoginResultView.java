/*
 * @(#)LoginResultView.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.view;

import lombok.Data;

/**
 * LoginResultView
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: LoginResultView.java ${currentTime} Exp $
 */
@Data
public class LoginResultView {

	private Boolean loginSucess = false;
	
	private Boolean openSecondVerify;
	
	private Integer secondVerifyType;
	
	private Long userId;
}
