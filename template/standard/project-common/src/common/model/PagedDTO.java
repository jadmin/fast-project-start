/*
 * @(#)PagedDTO.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.common.model;

import java.io.Serializable;
import java.util.List;

import ${packagePrefix}.common.utils.TraceIdUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("页面返回")
public class PagedDTO<T> implements Serializable {
	
    private static final long serialVersionUID = -3884577158754259731L;

    @ApiModelProperty(value = "当前页")
    private Long pageNum = 1L;

    @ApiModelProperty(value = "页面大小")
    private Long pageSize = 20L;

    @ApiModelProperty(value = "总记录数")
    private Long totalRecord = 0L;

    @ApiModelProperty(value = "返回结果集")
    private List<T> list;
    /**
     * 错误提示
     */
    @ApiModelProperty(value = "错误提示")
    private String message;

    /**
     * 异常信息（不提示给用户） 便于抓包排查问题，特别是未知系统异常，一般在debug模式下启用
     */
    @ApiModelProperty(value = "异常信息")
    private String exception;

    /**
     * 响应码
     */
    @ApiModelProperty(value = "响应码")
    private String responseCode;
    /**
     * 接口请求的traceId
     */
    @ApiModelProperty(value = "接口请求的traceId")
    private String traceId = TraceIdUtil.getTraceId();
    
    public Long getTotalPage() {
        return (totalRecord - 1) / pageSize + 1;
    }

    public void setTotalPage(Long total) {
    	// do nothing
    }

}
