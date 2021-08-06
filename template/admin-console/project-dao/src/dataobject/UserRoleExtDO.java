/*
 * @(#)UserRoleExtDO.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.dataobject;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * UserRoleExtDO
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: UserRoleExtDO.java ${currentTime} Exp $
 */
@Data
public class UserRoleExtDO implements Serializable {

	private static final long serialVersionUID = -6526611351851964902L;

	/**
	 * auth_role_user表主键id
	 */
	private Long id;


	/**
	 * 角色名称
	 */
	private Long userId;

	/**
	 * 角色ID
	 */
	private Long roleId;
	
	/**
	 * 角色代码 
	 */
	private String roleCode;
	
	/**
	 * 角色名称
	 */
	private String roleName;
	
	private Date expiredTime;

}
