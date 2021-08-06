/* Automatic generated by CrudCodeGenerator wirtten by Gerald Chen
 *
 * @(#)MenuModuleServiceImpl.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.service.impl;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.javaclub.sword.BizException;
import com.github.javaclub.sword.core.BizObjects;
import com.github.javaclub.sword.core.Collections;
import ${packagePrefix}.dao.MenuModuleDAO;
import ${packagePrefix}.dao.UserRoleDAO;
import ${packagePrefix}.dataobject.MenuModuleDO;
import ${packagePrefix}.query.MenuModuleQuery;
import ${packagePrefix}.service.MenuModuleService;
import com.github.javaclub.sword.domain.QueryResult;
import com.github.javaclub.sword.domain.ResultDO;
import com.github.javaclub.sword.domain.dto.BatchOperationDTO;
import com.github.javaclub.sword.domain.enumtype.BasicMessage;
import com.google.common.collect.Lists;

/**
 * MenuModuleServiceImpl
 *
 * @version $Id: MenuModuleServiceImpl.java ${currentTime} Exp $
 */
@Service("menuModuleService")
public class MenuModuleServiceImpl implements MenuModuleService {
	
	static final Logger log = LoggerFactory.getLogger(MenuModuleServiceImpl.class);
	
	@Autowired
	private MenuModuleDAO menuModuleDAO;
	
	@Autowired
	private UserRoleDAO userRoleDAO;

	/**
	 * 创建时的业务校验
	 * 
	 * @param menuModuleDO 业务实体
	 */
	void doCreateCheck(MenuModuleDO menuModuleDO) {
		doParentNodeCheck(menuModuleDO);
	}
	
	/**
	 * 更新时的业务校验
	 * 
	 * @param menuModuleDO 业务实体
	 */
	void doUpdateCheck(MenuModuleDO menuModuleDO) {
		doParentNodeCheck(menuModuleDO);
	}
	
	void doParentNodeCheck(MenuModuleDO menuModuleDO) {
		Long pId = menuModuleDO.getParentId();
		if(null == pId || 0 >= pId) {
			return;
		}
		MenuModuleDO parentNode = this.selectById(pId);
		BizObjects.requireNotNull(parentNode, "父节点不存在或已被删除");
		if (Objects.equals(3, parentNode.getType())) {
			throw new BizException("当前父节点下不允许添加子节点");
		}
		if (Objects.equals(2, parentNode.getType()) && 
				Objects.equals(1, menuModuleDO.getType())) {
			throw new BizException("模块页面节点下不允许添加菜单目录节点");
		}
	}

	@Override
	public ResultDO<Boolean> save(MenuModuleDO menuModuleDO) {
		if(null == menuModuleDO.getId()) {
			return create(menuModuleDO);
		}
		MenuModuleDO db = menuModuleDAO.getById(menuModuleDO.getId());
		if(null == db) {
			return ResultDO.failure(BasicMessage.NOT_FOUND);
		}
		return update(menuModuleDO);
	}

	@Override
	public ResultDO<Boolean> create(MenuModuleDO menuModuleDO) {
		ResultDO<Boolean> result = ResultDO.failure();
		try {
			this.doCreateCheck(menuModuleDO);
			int num = menuModuleDAO.insert(menuModuleDO);
			result = ResultDO.result(num > 0, "创建记录失败");
		} catch (Exception e) {
			log.error("", e);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public BatchOperationDTO<MenuModuleDO> createBatch(List<MenuModuleDO> list) {
		BatchOperationDTO<MenuModuleDO> bod = new BatchOperationDTO<MenuModuleDO>();
		List<MenuModuleDO> toAdd = Lists.newArrayList();
		for (MenuModuleDO item : list) {
			try {
				this.doCreateCheck(item);
			} catch (Exception e) {
				bod.addFailure(item);
				continue;
			}
			toAdd.add(item);
		}
		
		try {
			if(toAdd.size() > 0) {
				menuModuleDAO.insertBatch(toAdd);
				bod.setSuccessList(toAdd);
			}
		} catch (Exception e) {
			bod.setMessage(e.getMessage());
			log.error("MenuModuleService createBatch", e);
		}
		
		return bod;
	}

	@Override
	public ResultDO<Boolean> update(MenuModuleDO menuModuleDO) {
		ResultDO<Boolean> result = ResultDO.failure();
		try {
			BizObjects.requireNotNull(menuModuleDO.getId(), "主键ID不能为空");
			this.doUpdateCheck(menuModuleDO);
			int num = menuModuleDAO.update(menuModuleDO);
			result = ResultDO.result(num > 0, "没有记录被更新");
		} catch (Exception e) {
			log.error("", e);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public ResultDO<Boolean> deleteById(Long id) {
		ResultDO<Boolean> result = ResultDO.failure();
		try {
			int num = menuModuleDAO.deleteById(id);
			result = ResultDO.result(num > 0, "没有记录被删除");
		} catch (Exception e) {
			log.error("", e);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public ResultDO<MenuModuleDO> getById(Long id) {
		ResultDO<MenuModuleDO> result = ResultDO.failure();
		try {
			MenuModuleDO dbRecord = menuModuleDAO.getById(id);
			if(null != dbRecord) {
				result = ResultDO.success(dbRecord);
			}
		} catch (Exception e) {
			log.error("", e);
			result.setMessage(e.getMessage());
		}
		
		return result;
	}
	
	@Override
	public MenuModuleDO selectById(Long id) {
		return menuModuleDAO.getById(id);
	}
	
	@Override
	public MenuModuleDO selectOne(MenuModuleQuery query) {
		List<MenuModuleDO> list = menuModuleDAO.queryList(query);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int count(MenuModuleQuery query) {
		return menuModuleDAO.count(query);
	}

	@Override
	public List<MenuModuleDO> findList(MenuModuleQuery query) {
		return menuModuleDAO.queryList(query);
	}

	@Override
	public List<MenuModuleDO> findByParentId(Long pid) {
		MenuModuleQuery query = new MenuModuleQuery();
		query.setParentId(pid);
		query.setPageable(1, 500);
		return menuModuleDAO.queryList(query);
	}
	

	@Override
	public Long queryMaxSort(MenuModuleQuery query) {
		Object sort = menuModuleDAO.queryMaxSort(query);
		String strVal = Objects.toString(sort, "0");
		return Long.valueOf(strVal);
	}

	@Override
	public QueryResult<MenuModuleDO> findListWithCount(MenuModuleQuery query) {
		QueryResult<MenuModuleDO> queryResult = new QueryResult<MenuModuleDO>();
		try {
			List<MenuModuleDO> list = menuModuleDAO.queryList(query);
			int count = menuModuleDAO.count(query);
			queryResult.setTotalCount(count);
			queryResult.setEntry(list);
			queryResult.setSuccess(true);
		} catch (Exception e) {
			log.error("", e);
			queryResult.setSuccess(false);
			queryResult.setMessage(e.getMessage());
		}
		return queryResult;
	}
	
	@Override
	public List<MenuModuleDO> findUserMenu(Long userId) {
		List<Long> roleIds = userRoleDAO.queryUserRoles(userId);
		if(Collections.isEmpty(roleIds)) {
			return Lists.newArrayList();
		}
		return menuModuleDAO.findRoleMenu(roleIds);
	}

}