/* Automatic generated by CrudCodeGenerator wirtten by Gerald Chen
 *
 * @(#)MenuModuleService.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.service;

import java.util.List;

import ${packagePrefix}.dataobject.MenuModuleDO;
import ${packagePrefix}.query.MenuModuleQuery;
import com.github.javaclub.sword.domain.QueryResult;
import com.github.javaclub.sword.domain.ResultDO;
import com.github.javaclub.sword.domain.dto.BatchOperationDTO;

/**
 * MenuModuleService
 *
 * @version $Id: MenuModuleService.java ${currentTime} Exp $
 */
public interface MenuModuleService {

	/**
	 * 创建或更新业务实体（MenuModuleDO）
	 * 
	 * @param menuModuleDO 业务实体
	 * @return 操作结果
	 */
	ResultDO<Boolean> save(MenuModuleDO menuModuleDO);

	/**
	 * 创建业务实体（MenuModuleDO）
	 * 
	 * @param menuModuleDO 业务实体
	 * @return 创建结果
	 */
	ResultDO<Boolean> create(MenuModuleDO menuModuleDO);
	
	/**
	 * 批量创建业务实体（MenuModuleDO）
	 * 
	 * @param list 业务实体列表集合
	 * @return 批量处理结果
	 */
	BatchOperationDTO<MenuModuleDO> createBatch(List<MenuModuleDO> list);
	
	/**
	 * 更新业务实体（MenuModuleDO）
	 * 
	 * @param menuModuleDO 业务实体
	 * @return 更新结果
	 */
	ResultDO<Boolean> update(MenuModuleDO menuModuleDO);
	
	/**
	 * 根据主键删除单个业务实体（MenuModuleDO）
	 * 
	 * @param id 业务实体的主键ID
	 * @return 删除结果
	 */
	ResultDO<Boolean> deleteById(Long id);
	
	/**
	 * 根据主键查询单个业务实体（MenuModuleDO）
	 * 
	 * @param id 业务实体的主键ID
	 * @return 查询结果
	 */
	ResultDO<MenuModuleDO> getById(Long id);
	
	MenuModuleDO selectById(Long id);
	
	MenuModuleDO selectOne(MenuModuleQuery query);

	/**
	 * 根据一组查询条件统计总数
	 * 
	 * @param query 查询条件
	 * @return 满足查询条件的记录总数
	 */
	int count(MenuModuleQuery query);
	
	/**
	 * 根据一组查询条件查询业务实体列表
	 * 
	 * @param query 查询条件
	 * @return 业务实体列表集合
	 */
	List<MenuModuleDO> findList(MenuModuleQuery query);
	
	List<MenuModuleDO> findByParentId(Long pid);
	
	Long queryMaxSort(MenuModuleQuery query);
	
	/**
	 * 根据一组查询条件查询业务实体列表，同时返回总记录数
	 * 
	 * @param query 查询条件
	 * @return 查询结果（含总记录数）
	 */
	QueryResult<MenuModuleDO> findListWithCount(MenuModuleQuery query);
	
	List<MenuModuleDO> findUserMenu(Long userId);
}