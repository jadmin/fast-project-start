/* Automatic generated by CrudCodeGenerator wirtten by Gerald Chen
 *
 * @(#)BasicComponentQuery.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.query;


import java.util.List;

import com.github.javaclub.sword.domain.DomainEntityQuery;

/**
 * BasicComponentQuery
 *
 * @version $Id: BasicComponentQuery.java ${currentTime} Exp $
 */
public class BasicComponentQuery extends DomainEntityQuery {

	private static final long serialVersionUID = 1621587400487L;
	
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
	
	private Integer status;
	private String creator;
	private String widgetNameLike;
	
	private Integer widgetType;

	public BasicComponentQuery() {
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
	
	public Integer getStatus() {
		return this.status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCreator() {
		return this.creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getWidgetNameLike() {
		return this.widgetNameLike;
	}
	public void setWidgetNameLike(String widgetNameLike) {
		this.widgetNameLike = widgetNameLike;
	}

	public Integer getWidgetType() {
		return widgetType;
	}

	public void setWidgetType(Integer widgetType) {
		this.widgetType = widgetType;
	}
	
	
}

