/* Automatic generated by CrudCodeGenerator wirtten by Gerald Chen
 *
 * @(#)ResourceDO.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.dataobject;

import com.github.javaclub.sword.domain.BaseDO;

/**
 * ResourceDO
 *
 * @version $Id: ResourceDO.java ${currentTime} Exp $
 */
public class ResourceDO extends BaseDO {

	private static final long serialVersionUID = 1624627240644L;
	
	/**
	 * 主键id
	 */
	private Long id;

	/**
	 * 资源名称
	 */
	private String name;

	/**
	 * 资源代码标识
	 */
	private String code;

	/**
	 * 访问uri
	 */
	private String uri;

	/**
	 * 描述说明
	 */
	private String description;

	/**
	 * 创建人
	 */
	private String creator;

	/**
	 * 修改人
	 */
	private String modifier;
	
	/**
	 * @see com.xsoft.demo.common.enums.ResourceType
	 */
	private Integer type;


	public ResourceDO() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	
}

