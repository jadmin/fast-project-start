/*
 * @(#)AdminUserView.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.view;

import lombok.Data;

/**
 * AdminUserView
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: AdminUserView.java ${currentTime} Exp $
 */
@Data
public class AdminUserView {
	
	private Long userId;

	private String username;
	
	private String name;
	
	private String email;
	
	private String mobile;
	
	private Integer status;
	
	private String loginIp;
	private String loginUA;
}
