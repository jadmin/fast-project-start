/*
 * @(#)BaseDTO.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.common.model;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * BaseDTO
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: BaseDTO.java ${currentTime} Exp $
 */
@Data
@ApiModel("基础字段")
public class BaseDTO implements Serializable {
	
    private static final long serialVersionUID = -2578018627934424045L;
    private Long id;
    private Date gmtCreate;
    private Date gmtModified;

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
