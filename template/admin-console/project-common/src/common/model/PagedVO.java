/*
 * @(#)PagedVO.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.common.model;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel("页面返回")
public class PagedVO<T> implements Serializable {

	private static final long serialVersionUID = 224884595222222755L;

	@ApiModelProperty(value = "当前页")
    private Long pageNum = 1L;

    @ApiModelProperty(value = "页面大小")
    private Long pageSize = 20L;

    @ApiModelProperty(value = "总记录数")
    private Long totalRecord = 0L;

    @ApiModelProperty(value = "返回结果集")
    private List<T> list;

    public Long getTotalPage() {
        return (totalRecord - 1) / pageSize + 1;
    }
    
    public void setTotalPage(Long total) {
    	// do nothing
    }
}
