/*
 * @(#)ControllerExceptionHandler.java	2020-12-30
 *
 * Copyright (c) 2020. All Rights Reserved.
 *
 */

package ${packagePrefix}.component;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.javaclub.sword.BizException;
import ${packagePrefix}.common.enums.ErrorEnum;
import ${packagePrefix}.common.exception.BusinessException;
import ${packagePrefix}.common.model.ResultDO;
import ${packagePrefix}.common.utils.TraceIdUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler
    @ResponseBody
    public ResultDO handleException(HttpServletRequest request, Exception ex) {
        log.error("System error", ex);
        ResultDO result = null;
        if (ex instanceof IllegalArgumentException) {
            result = new ResultDO(ErrorEnum.PARAM_ERROR.getErrorCode(), ex.getMessage());
        } else if (ex instanceof BusinessException) {
        	BusinessException bizEx = (BusinessException) ex;
        	result = new ResultDO(bizEx.getResponseCode(), ex.getMessage());
        } else if (ex instanceof BizException) {
            BizException bizEx = (BizException) ex;
            result = new ResultDO(String.valueOf(bizEx.getCode()), ex.getMessage());
        } else if (ex instanceof ConstraintViolationException) {
            result = new ResultDO(ErrorEnum.PARAM_ERROR.getErrorCode(), ex.toString());
        } else if (ex instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) ex;
            BindingResult bindingResult = validException.getBindingResult();
            List<ObjectError> allErrors = bindingResult.getAllErrors();

            StringBuilder builder = new StringBuilder();
            allErrors.forEach(each -> {
                builder.append(each.getDefaultMessage()).append(";");
            });
            result = new ResultDO(ErrorEnum.PARAM_ERROR.getErrorCode(), builder.toString());
        } else if (ex instanceof BindException) {
            BindException validException = (BindException) ex;
            BindingResult bindingResult = validException.getBindingResult();
            List<ObjectError> allErrors = bindingResult.getAllErrors();

            StringBuilder builder = new StringBuilder();
            allErrors.forEach(each -> {
                builder.append(each.getDefaultMessage()).append(";");
            });
            result = new ResultDO(ErrorEnum.PARAM_ERROR.getErrorCode(), builder.toString());
        } else {
            result = new ResultDO(ErrorEnum.SYSTEM_ERROR.getErrorCode(), ErrorEnum.SYSTEM_ERROR.getMsg());

        }
        result.setTraceId(TraceIdUtil.getTraceId());
        return result;
    }
}
