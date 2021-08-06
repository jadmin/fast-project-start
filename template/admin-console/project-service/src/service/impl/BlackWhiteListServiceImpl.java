/* Automatic generated by CrudCodeGenerator wirtten by Gerald Chen
 *
 * @(#)BlackWhiteListServiceImpl.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.javaclub.sword.core.BizObjects;
import com.github.javaclub.sword.domain.QueryResult;
import com.github.javaclub.sword.domain.ResultDO;
import com.github.javaclub.sword.domain.dto.BatchOperationDTO;
import com.github.javaclub.sword.domain.enumtype.BasicMessage;
import com.google.common.collect.Lists;
import ${packagePrefix}.dao.BlackWhiteListDAO;
import ${packagePrefix}.dataobject.BlackWhiteListDO;
import ${packagePrefix}.query.BlackWhiteListQuery;
import ${packagePrefix}.service.BlackWhiteListService;

/**
 * BlackWhiteListServiceImpl
 *
 * @version $Id: BlackWhiteListServiceImpl.java ${currentTime} Exp $
 */
@Service("blackWhiteListService")
public class BlackWhiteListServiceImpl implements BlackWhiteListService {
	
	static final Logger log = LoggerFactory.getLogger(BlackWhiteListServiceImpl.class);
	
	@Autowired
	private BlackWhiteListDAO blackWhiteListDAO;

	/**
	 * 创建时的业务校验
	 * 
	 * @param blackWhiteListDO 业务实体
	 */
	void doCreateCheck(BlackWhiteListDO blackWhiteListDO) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * 更新时的业务校验
	 * 
	 * @param blackWhiteListDO 业务实体
	 */
	void doUpdateCheck(BlackWhiteListDO blackWhiteListDO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ResultDO<Boolean> save(BlackWhiteListDO blackWhiteListDO) {
		if(null == blackWhiteListDO.getId()) {
			return create(blackWhiteListDO);
		}
		BlackWhiteListDO db = blackWhiteListDAO.getById(blackWhiteListDO.getId());
		if(null == db) {
			return ResultDO.failure(BasicMessage.NOT_FOUND);
		}
		return update(blackWhiteListDO);
	}

	@Override
	public ResultDO<Boolean> create(BlackWhiteListDO blackWhiteListDO) {
		ResultDO<Boolean> result = ResultDO.failure();
		try {
			this.doCreateCheck(blackWhiteListDO);
			int num = blackWhiteListDAO.insert(blackWhiteListDO);
			result = ResultDO.result(num > 0, "创建记录失败");
		} catch (Exception e) {
			log.error("", e);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public ResultDO<Long> createReturnId(BlackWhiteListDO blackWhiteListDO) {
		ResultDO<Long> result = ResultDO.failure();
		try {
			this.doCreateCheck(blackWhiteListDO);
			int num = blackWhiteListDAO.insert(blackWhiteListDO);
			if (num > 0) {
				result = ResultDO.success();
				result.setEntry(blackWhiteListDO.getId());
				return result;
			}
			result.setMessage("创建记录失败");
		} catch (Exception e) {
			log.error("", e);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public BatchOperationDTO<BlackWhiteListDO> createBatch(List<BlackWhiteListDO> list) {
		BatchOperationDTO<BlackWhiteListDO> bod = new BatchOperationDTO<BlackWhiteListDO>();
		List<BlackWhiteListDO> toAdd = Lists.newArrayList();
		for (BlackWhiteListDO item : list) {
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
				blackWhiteListDAO.insertBatch(toAdd);
				bod.setSuccessList(toAdd);
			}
		} catch (Exception e) {
			bod.setMessage(e.getMessage());
			log.error("BlackWhiteListService createBatch", e);
		}
		
		return bod;
	}

	@Override
	public ResultDO<Boolean> update(BlackWhiteListDO blackWhiteListDO) {
		ResultDO<Boolean> result = ResultDO.failure();
		try {
			BizObjects.requireNotNull(blackWhiteListDO.getId(), "主键ID不能为空");
			this.doUpdateCheck(blackWhiteListDO);
			int num = blackWhiteListDAO.update(blackWhiteListDO);
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
			int num = blackWhiteListDAO.deleteById(id);
			result = ResultDO.result(num > 0, "没有记录被删除");
		} catch (Exception e) {
			log.error("", e);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@Override
	public ResultDO<BlackWhiteListDO> getById(Long id) {
		ResultDO<BlackWhiteListDO> result = ResultDO.failure();
		try {
			BlackWhiteListDO dbRecord = blackWhiteListDAO.getById(id);
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
	public BlackWhiteListDO selectById(Long id) {
		return blackWhiteListDAO.getById(id);
	}
	
	@Override
	public BlackWhiteListDO selectOne(BlackWhiteListQuery query) {
		List<BlackWhiteListDO> list = blackWhiteListDAO.queryList(query);
		if(null != list && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public int count(BlackWhiteListQuery query) {
		return blackWhiteListDAO.count(query);
	}

	@Override
	public List<BlackWhiteListDO> findList(BlackWhiteListQuery query) {
		return blackWhiteListDAO.queryList(query);
	}

	@Override
	public QueryResult<BlackWhiteListDO> findListWithCount(BlackWhiteListQuery query) {
		QueryResult<BlackWhiteListDO> queryResult = new QueryResult<BlackWhiteListDO>();
		try {
			List<BlackWhiteListDO> list = blackWhiteListDAO.queryList(query);
			int count = blackWhiteListDAO.count(query);
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
	public int deleteByExpire() {
		return blackWhiteListDAO.deleteByExpire();
	}
}
