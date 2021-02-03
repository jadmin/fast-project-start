/*
 * @(#)BaseDTO.java	2021-1-13
 *
 * Copyright (c) 2021. All Rights Reserved.
 *
 */

package ${packagePrefix}.common.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel("基础字段")
public class BaseVO implements Serializable {
	
	private static final long serialVersionUID = -8328543154708946515L;
	
	private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
