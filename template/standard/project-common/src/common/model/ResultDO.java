/*
 * @(#)ResultDO.java	2020-12-29
 *
 * Copyright (c) 2020. All Rights Reserved.
 *
 */

package ${packagePrefix}.common.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;


@Data
@ToString
@ApiModel(description = "返回结果")
public class ResultDO<T> implements Serializable {

	private static final long serialVersionUID = -172911448358060648L;

    /**
     * 接口请求id
     */
    @ApiModelProperty(value = "接口请求id")
    private Long requestId = System.currentTimeMillis();
    /**
     * 接口请求的traceId
     */
    @ApiModelProperty(value = "接口请求的traceId")
    private String traceId;

    /**
     * 状态(true:成功,false:失败)
     */
    @ApiModelProperty(value = "状态(true:成功,false:失败)")
    private boolean status = false;

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
     * 记录总（分页时有用）
     */
    @ApiModelProperty(value = "记录总")
    private long totalRecordSize;


    /**
     * 是否还有下一页
     */
    @ApiModelProperty(value = "是否还有下一页")
    private boolean isHasNext;

    /**
     * 响应时间
     */
    @ApiModelProperty(value = "响应时间")
    private long timestamp = System.currentTimeMillis();

    /**
     * 业务数据
     */
    @ApiModelProperty(value = "业务数据")
    private T entry;
    
    public ResultDO() {
    }

    public ResultDO(String responseCode, String message) {
        this.responseCode = responseCode;
        this.message = message;
    }

    public ResultDO failure(String errorMsg) {
        this.status = false;
        this.message = errorMsg;
        return this;
    }

    public ResultDO failure(String errorMsg, Exception e) {
        this.status = false;
        this.message = errorMsg;
        this.exception = e != null ? e.toString() : "";
        return this;
    }

    public ResultDO failure(String errorMsg, int responseCode) {
        this.status = false;
        this.responseCode = responseCode + "";
        this.message = errorMsg;
        return this;
    }

    public ResultDO failure(String errorMsg, int responseCode, Exception e) {
        this.status = false;
        this.responseCode = responseCode + "";
        this.message = errorMsg;
        this.exception = e != null ? e.toString() : "";
        return this;
    }

    public ResultDO success() {
        this.status = true;
        return this;
    }

    public ResultDO success(T entry, long totalRecordSize) {
        this.status = true;
        this.entry = entry;
        this.totalRecordSize = totalRecordSize;
        return this;
    }

    public ResultDO success(T entry) {
        this.status = true;
        this.entry = entry;
        return this;
    }

    public ResultDO success(int responseCode, String message) {
        this.status = true;
        this.responseCode = responseCode + "";
        this.message = message;
        return this;
    }

    public ResultDO success(int responseCode, String message, T entry) {
        this.status = true;
        this.responseCode = responseCode + "";
        this.message = message;
        this.entry = entry;
        return this;
    }
}
