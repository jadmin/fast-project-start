/* Automatic generated by CrudCodeGenerator wirtten by Gerald Chen
 *
 * @(#)UserRoleDAO.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.github.javaclub.sword.domain.StandardDAO;
import ${packagePrefix}.dataobject.RoleUserDO;
import ${packagePrefix}.dataobject.UserRoleDO;
import ${packagePrefix}.dataobject.UserRoleExtDO;
import ${packagePrefix}.query.RoleUserQuery;

/**
 * UserRoleDAO
 *
 * @version $Id: UserRoleDAO.java ${currentTime} Exp $
 */
public interface UserRoleDAO extends StandardDAO<UserRoleDO, Long> {
	
	int deleteByUserId(@Param("userId") Long userId);
	
	List<Long> queryUserRoles(@Param("userId") Long userId);
	
	List<RoleUserDO> queryRoleUserList(RoleUserQuery query);
	
	int countRoleUserList(RoleUserQuery query);
	
	int prolongExpiredTime(UserRoleDO urd);
	
	List<UserRoleExtDO> queryUserRoleList(RoleUserQuery query);
	
	int countUserRoleList(RoleUserQuery query);
	
	UserRoleDO getByUserIdAndRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId);
}
