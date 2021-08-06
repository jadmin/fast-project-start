/*
 * @(#)BwlEnumDO.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.dataobject;

import com.github.javaclub.sword.domain.BaseDO;

/**
 * BwlEnumDO
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: BwlEnumDO.java ${currentTime} Exp $
 */
public class BwlEnumDO extends BaseDO {

	private static final long serialVersionUID = 1L;

	/**
	 * 自增主键
	 */
	private Integer id;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 修改人
	 */
	private String modifier;

	/**
	 * 业务代码
	 */
	private String bizCode;

	/**
	 * 描述
	 */
	private String bizDesc;

	/**
	 * 逻辑删除：0-未删除 1-删除
	 */
	private Integer isDeleted;

	public BwlEnumDO() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public String getBizCode() {
		return bizCode;
	}

	public void setBizCode(String bizCode) {
		this.bizCode = bizCode;
	}

	public String getBizDesc() {
		return bizDesc;
	}

	public void setBizDesc(String bizDesc) {
		this.bizDesc = bizDesc;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

}
