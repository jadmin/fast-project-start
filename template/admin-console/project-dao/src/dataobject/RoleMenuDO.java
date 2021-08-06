/* Automatic generated by CrudCodeGenerator wirtten by Gerald Chen
 *
 * @(#)RoleMenuDO.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.dataobject;

import com.github.javaclub.sword.domain.BaseDO;

/**
 * RoleMenuDO
 *
 * @version $Id: RoleMenuDO.java ${currentTime} Exp $
 */
public class RoleMenuDO extends BaseDO {

	private static final long serialVersionUID = 1625563961718L;
	
	/**
	 * 主键id
	 */
	private Long id;

	/**
	 * 角色ID
	 */
	private Long roleId;

	/**
	 * 菜单ID
	 */
	private Long menuId;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 修改人
	 */
	private String modifier;



	public RoleMenuDO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}


	
}
