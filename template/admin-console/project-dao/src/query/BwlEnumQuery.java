/*
 * @(#)BwlEnumQuery.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.query;

import java.util.List;

import com.github.javaclub.sword.domain.DomainEntityQuery;

/**
 * BwlEnumQuery
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: BwlEnumQuery.java ${currentTime} Exp $
 */
public class BwlEnumQuery extends DomainEntityQuery {

	private static final long serialVersionUID = 1L;

	/**
	 * 根据主键查询
	 */
	private Long id;

	/**
	 * 根据主键ID列表批量查询
	 */
	private List<Long> ids;

	/**
	 * 最小ID过滤条件
	 */
	private Long minId;

	/**
	 * 最大ID过滤条件
	 */
	private Long maxId;

	private String bizCode;
	private Integer isDeleted;
	private java.util.List<String> bizCodeInList;
	private String bizDescLike;
	private String creatorLike;
	private String modifierLike;

	public BwlEnumQuery() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Long getMinId() {
		return minId;
	}

	public void setMinId(Long minId) {
		this.minId = minId;
	}

	public Long getMaxId() {
		return maxId;
	}

	public void setMaxId(Long maxId) {
		this.maxId = maxId;
	}

	public String getBizCode() {
		return this.bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public Integer getIsDeleted() {
		return this.isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public java.util.List<String> getBizCodeInList() {
		return this.bizCodeInList;
	}

	public void setBizCodeInList(java.util.List<String> bizCodeInList) {
		this.bizCodeInList = bizCodeInList;
	}

	public String getBizDescLike() {
		return this.bizDescLike;
	}

	public void setBizDescLike(String bizDescLike) {
		this.bizDescLike = bizDescLike;
	}

	public String getCreatorLike() {
		return this.creatorLike;
	}

	public void setCreatorLike(String creatorLike) {
		this.creatorLike = creatorLike;
	}

	public String getModifierLike() {
		return this.modifierLike;
	}

	public void setModifierLike(String modifierLike) {
		this.modifierLike = modifierLike;
	}

}
