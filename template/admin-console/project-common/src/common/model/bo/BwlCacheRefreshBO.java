/*
 * @(#)BwlCacheRefreshBO.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.common.model.bo;

import java.io.Serializable;

import lombok.Data;

/**
 * BwlCacheRefreshBO
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: BwlCacheRefreshBO.java ${currentTime} Exp $
 */
@Data
public class BwlCacheRefreshBO implements Serializable {
	
	private static final long serialVersionUID = -1153936286436827908L;
	
	/**
     * 业务代码
     */
    private String bizCode;

    /**
     * 名单类型
     */
    private Integer bwlType;

    /**
     * 操作类型
     * 
     * ADD: 新增名单
     * BATCH_ADD: 批量新增名单
     * DELETE: 删除名单
     * RELOAD_ALL: 重新加载所有缓存
     * 
     * @see ${packagePrefix}.common.AppConstants.BlackWhiteList.ACTION_TYPE_*
     */
    private String actionType;

    /**
     * 名单值 
     */
    private String identity;


}
