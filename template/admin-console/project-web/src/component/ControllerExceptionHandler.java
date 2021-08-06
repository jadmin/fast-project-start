/*
 * @(#)ControllerExceptionHandler.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.component;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.github.javaclub.sword.BizException;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.util.WebUtil;
import com.github.javaclub.sword.web.ExceptionResponse;
import com.github.javaclub.sword.web.HttpResult;
import ${packagePrefix}.common.enums.ErrorEnum;
import ${packagePrefix}.common.exception.BusinessException;
import ${packagePrefix}.common.model.ResultDO;
import ${packagePrefix}.common.utils.TraceIdUtil;
import ${packagePrefix}.common.utils.Utils;
import ${packagePrefix}.support.SystemConfigs;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;
import lombok.extern.slf4j.Slf4j;

/**
 * 全局异常处理
 */
@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

	@Autowired
	private SystemConfigs systemConfigs;

	@ExceptionHandler(value = Throwable.class)
	public ModelAndView handleThrowable(Throwable ex, HttpServletRequest req, HttpServletResponse resp) {
		String errorMsg = "服务异常，请稍后再试！";
		if (ex instanceof BizException && Strings.isNotBlank(ex.getMessage())) {
			errorMsg = ex.getMessage();
		}
		ExceptionResponse response = new ExceptionResponse(ex, req, resp, 500L, HttpResult.failure(500, errorMsg),
				errorMsg);
		return response(response);
	}
	
	public ModelAndView response(ExceptionResponse ex) {
		log.error("System error " + ex.getErrorMsg(), ex.getThrowable());
		if (WebUtil.isAjaxRequest(ex.getReq())) {
			try {
				this.buildAjaxEntity(ex);
				WebUtil.response(ex.getResp(), ex.getObj(), true, ex.getReq().getParameter("callback"), "UTF-8");
			} catch (Exception e) {
				log.error("handleException", e);
			}
			return null;
		}
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error");
		modelAndView.addObject("version", systemConfigs.getAppVersion());
		modelAndView.addObject("skin", "skin-" + systemConfigs.getSkin());
		modelAndView.addObject("code", ex.getCode());
		modelAndView.addObject("errorMsg", ex.getErrorMsg());
		this.initFreemarkerStaticUtils(modelAndView);
		if (null != ex.getReq()) {
			modelAndView.addObject("servletPath", ex.getReq().getServletPath());
			modelAndView.addObject("appPath", ex.getReq().getContextPath());
			modelAndView.addObject("homePath", ex.getReq().getContextPath() + "/console");
		}

		return modelAndView;
	}

	void initFreemarkerStaticUtils(ModelAndView modelAndView) {
		try {
			BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
			TemplateHashModel staticModels = wrapper.getStaticModels();
//			TemplateHashModel optionValueTypeStatics =
//			        (TemplateHashModel) staticModels.get(OptionValueType.class.getName());
//			modelAndView.addObject("OptionValueType", optionValueTypeStatics);

			TemplateHashModel jsonUtilStatics = (TemplateHashModel) staticModels.get(Utils.class.getName());
			modelAndView.addObject("Utils", jsonUtilStatics);
		} catch (TemplateModelException e) {
			log.error("initFreemarkerStaticUtils error", e);
		}
	}

	public ResultDO buildAjaxEntity(ExceptionResponse exResp) {
		Throwable ex = exResp.getThrowable();
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
		exResp.setObj(result);

		return result;
	}
}
