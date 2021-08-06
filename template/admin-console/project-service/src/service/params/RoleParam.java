/*
 * @(#)RoleParam.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.service.params;

import java.util.List;

import ${packagePrefix}.dataobject.RoleDO;

import lombok.Data;

/**
 * RoleParam
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: RoleParam.java ${currentTime} Exp $
 */
@Data
public class RoleParam {

	private RoleDO role;
	
	private List<AuthMenuParam> menuParams;
	
	private String creator;
	private String modifier;
	
}
