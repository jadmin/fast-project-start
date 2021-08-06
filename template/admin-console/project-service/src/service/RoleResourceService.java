/* Automatic generated by CrudCodeGenerator wirtten by Gerald Chen
 *
 * @(#)RoleResourceService.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.service;

import java.util.List;

import ${packagePrefix}.dataobject.RoleResourceDO;
import ${packagePrefix}.query.RoleResourceQuery;
import com.github.javaclub.sword.domain.QueryResult;
import com.github.javaclub.sword.domain.ResultDO;
import com.github.javaclub.sword.domain.dto.BatchOperationDTO;

/**
 * RoleResourceService
 *
 * @version $Id: RoleResourceService.java ${currentTime} Exp $
 */
public interface RoleResourceService {

	/**
	 * 创建或更新业务实体（RoleResourceDO）
	 * 
	 * @param roleResourceDO 业务实体
	 * @return 操作结果
	 */
	ResultDO<Boolean> save(RoleResourceDO roleResourceDO);

	/**
	 * 创建业务实体（RoleResourceDO）
	 * 
	 * @param roleResourceDO 业务实体
	 * @return 创建结果
	 */
	ResultDO<Boolean> create(RoleResourceDO roleResourceDO);
	
	/**
	 * 批量创建业务实体（RoleResourceDO）
	 * 
	 * @param list 业务实体列表集合
	 * @return 批量处理结果
	 */
	BatchOperationDTO<RoleResourceDO> createBatch(List<RoleResourceDO> list);
	
	/**
	 * 更新业务实体（RoleResourceDO）
	 * 
	 * @param roleResourceDO 业务实体
	 * @return 更新结果
	 */
	ResultDO<Boolean> update(RoleResourceDO roleResourceDO);
	
	/**
	 * 根据主键删除单个业务实体（RoleResourceDO）
	 * 
	 * @param id 业务实体的主键ID
	 * @return 删除结果
	 */
	ResultDO<Boolean> deleteById(Long id);
	
	/**
	 * 根据主键查询单个业务实体（RoleResourceDO）
	 * 
	 * @param id 业务实体的主键ID
	 * @return 查询结果
	 */
	ResultDO<RoleResourceDO> getById(Long id);
	
	RoleResourceDO selectById(Long id);
	
	RoleResourceDO selectOne(RoleResourceQuery query);

	/**
	 * 根据一组查询条件统计总数
	 * 
	 * @param query 查询条件
	 * @return 满足查询条件的记录总数
	 */
	int count(RoleResourceQuery query);
	
	/**
	 * 根据一组查询条件查询业务实体列表
	 * 
	 * @param query 查询条件
	 * @return 业务实体列表集合
	 */
	List<RoleResourceDO> findList(RoleResourceQuery query);
	
	/**
	 * 根据一组查询条件查询业务实体列表，同时返回总记录数
	 * 
	 * @param query 查询条件
	 * @return 查询结果（含总记录数）
	 */
	QueryResult<RoleResourceDO> findListWithCount(RoleResourceQuery query);
}
