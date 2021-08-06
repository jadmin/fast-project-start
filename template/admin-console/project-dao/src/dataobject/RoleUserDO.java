/*
 * @(#)RoleUserDO.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.dataobject;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * RoleUserDO
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: RoleUserDO.java ${currentTime} Exp $
 */
@Data
public class RoleUserDO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public RoleUserDO() {
	}
	
	private Long id;
	
	private Long userId;
	
	private String username;
	
	private String name;
	
	private String mobile;
	
	private Long roleId;
	
	private Date expiredTime;
	
	private String creator;
	
	private Date gmtCreate;
	private Date gmtModify;

}
