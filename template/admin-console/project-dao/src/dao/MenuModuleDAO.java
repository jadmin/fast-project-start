/* Automatic generated by CrudCodeGenerator wirtten by Gerald Chen
 *
 * @(#)MenuModuleDAO.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.javaclub.sword.domain.StandardDAO;

import ${packagePrefix}.dataobject.MenuModuleDO;
import ${packagePrefix}.query.MenuModuleQuery;

/**
 * MenuModuleDAO
 *
 * @version $Id: MenuModuleDAO.java ${currentTime} Exp $
 */
public interface MenuModuleDAO extends StandardDAO<MenuModuleDO, Long> {

	Object queryMaxSort(MenuModuleQuery query);
	
	List<MenuModuleDO> findRoleMenu(@Param("roleIdList") List<Long> roleIdList);
	
	List<MenuModuleDO> queryUserModuleResource(@Param("roleIdList") List<Long> roleIdList);
}
