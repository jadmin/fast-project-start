/*
 * @(#)BlackWhiteListDO.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.dataobject;

import com.github.javaclub.sword.domain.BaseDO;
import ${packagePrefix}.common.enums.BlackWhiteEnum;

/**
 * BlackWhiteListDO
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: BlackWhiteListDO.java ${currentTime} Exp $
 */
public class BlackWhiteListDO extends BaseDO {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 修改人
	 */
	private String modifier;

	/**
	 * 业务标识码
	 */
	private String bizCode;

	/**
	 * 名单身份标识(uid/mobile等)
	 */
	private String identity;

	/**
	 * 名单类型：1-黑名单 2-白名单
	 */
	private Integer type;

	/**
	 * 备注
	 */
	private String comment;

	/**
	 * 关联ID
	 */
	private String relationId;

	/**
	 * 创建人ID
	 */
	private Long creatorId;

	/**
	 * 过期类型：1-1天 2-3天 3-10天 4-1个月 5-3个月 6-永久 7-自定义过期时间
	 */
	private Integer expireType;

	/**
	 * 过期时间
	 */
	private java.util.Date expireTime;

	/**
	 * 逻辑删除：0-未删除 1-删除
	 */
	private Integer isDeleted;

	public BlackWhiteListDO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getRelationId() {
		return relationId;
	}

	public void setRelationId(String relationId) {
		this.relationId = relationId;
	}

	public Long getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}

	public Integer getExpireType() {
		return expireType;
	}

	public void setExpireType(Integer expireType) {
		this.expireType = expireType;
	}

	public java.util.Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(java.util.Date expireTime) {
		this.expireTime = expireTime;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}
	
	public String bwlGroupCacheKey() {
		return BlackWhiteEnum.groupCacheKey(bizCode, type);
	}

}
