/* Automatic generated by CrudCodeGenerator wirtten by Gerald Chen
 *
 * @(#)LoginUserService.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.service;

import java.util.List;

import com.github.javaclub.sword.domain.QueryResult;
import com.github.javaclub.sword.domain.ResultDO;
import com.github.javaclub.sword.domain.dto.BatchOperationDTO;
import ${packagePrefix}.dataobject.LoginUserDO;
import ${packagePrefix}.query.LoginUserQuery;
import ${packagePrefix}.service.params.LoginUserUpdateParam;
import ${packagePrefix}.service.params.UserLoginConfirmParam;
import ${packagePrefix}.service.params.result.LoginCofirmResult;

/**
 * LoginUserService
 *
 * @version $Id: LoginUserService.java ${currentTime} Exp $
 */
public interface LoginUserService {
	
	LoginUserDO checkLoginUser(String username, String rawPassword);
	
	LoginCofirmResult confirmLoginUser(UserLoginConfirmParam param);
	
	boolean resetPassword(LoginUserDO user);

	/**
	 * 创建或更新业务实体（LoginUserDO）
	 * 
	 * @param loginUserDO 业务实体
	 * @return 操作结果
	 */
	ResultDO<Boolean> save(LoginUserDO loginUserDO);

	/**
	 * 创建业务实体（LoginUserDO）
	 * 
	 * @param loginUserDO 业务实体
	 * @return 创建结果
	 */
	ResultDO<Boolean> create(LoginUserDO loginUserDO);
	
	/**
	 * 批量创建业务实体（LoginUserDO）
	 * 
	 * @param list 业务实体列表集合
	 * @return 批量处理结果
	 */
	BatchOperationDTO<LoginUserDO> createBatch(List<LoginUserDO> list);
	
	/**
	 * 更新业务实体（LoginUserDO）
	 * 
	 * @param loginUserDO 业务实体
	 * @return 更新结果
	 */
	ResultDO<Boolean> update(LoginUserDO loginUserDO);
	
	/**
	 * 根据主键删除单个业务实体（LoginUserDO）
	 * 
	 * @param id 业务实体的主键ID
	 * @return 删除结果
	 */
	ResultDO<Boolean> deleteById(Long id);
	
	/**
	 * 根据主键查询单个业务实体（LoginUserDO）
	 * 
	 * @param id 业务实体的主键ID
	 * @return 查询结果
	 */
	ResultDO<LoginUserDO> getById(Long id);
	
	LoginUserDO selectById(Long id);
	
	LoginUserDO getByUsername(String username);

	/**
	 * 根据一组查询条件统计总数
	 * 
	 * @param query 查询条件
	 * @return 满足查询条件的记录总数
	 */
	int count(LoginUserQuery query);
	
	/**
	 * 根据一组查询条件查询业务实体列表
	 * 
	 * @param query 查询条件
	 * @return 业务实体列表集合
	 */
	List<LoginUserDO> findList(LoginUserQuery query);
	
	/**
	 * 根据一组查询条件查询业务实体列表，同时返回总记录数
	 * 
	 * @param query 查询条件
	 * @return 查询结果（含总记录数）
	 */
	QueryResult<LoginUserDO> findListWithCount(LoginUserQuery query);
	
	ResultDO<Boolean> updateUserProfileDetail(LoginUserUpdateParam loginUserUpdateParam);

	ResultDO<Boolean> resetSecondAuth(Long userId);

	LoginUserDO matchUserByQueryKey(String val);
        

	
}
