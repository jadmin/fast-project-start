/* Automatic generated by CrudCodeGenerator wirtten by Gerald Chen
 *
 * @(#)RoleMenuDAO.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.javaclub.sword.domain.StandardDAO;
import ${packagePrefix}.dataobject.RoleMenuDO;

/**
 * RoleMenuDAO
 *
 * @version $Id: RoleMenuDAO.java ${currentTime} Exp $
 */
public interface RoleMenuDAO extends StandardDAO<RoleMenuDO, Long> {

	void deleteByRoleId(@Param("roleId") Long roleId);
	
	List<Long> findRoleMenuNode(@Param("roleId") Long roleId);
	
}

