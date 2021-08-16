/*
 * @(#)GlobalInterceptor.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.interceptor.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import ${packagePrefix}.utils.WebSkins;

import ${packagePrefix}.common.utils.LoginUserHolder;
import ${packagePrefix}.common.utils.Utils;
import ${packagePrefix}.config.BaseConfig;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;
import lombok.extern.slf4j.Slf4j;

/**
 * GlobalInterceptor
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: GlobalInterceptor.java ${currentTime} Exp $
 */
@Component
@Slf4j
public class GlobalInterceptor extends BaseConfig implements HandlerInterceptor {
	

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object arg2) throws Exception {
		// head请求直接返回
		if (req.getMethod().equals("HEAD")) {
			return false;
		}
		
        return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView)
			throws Exception {
		if(null != modelAndView) {
			modelAndView.addObject("isLogined", null != LoginUserHolder.get());
			modelAndView.addObject("version", systemConfigs.getAppVersion());
			modelAndView.addObject("skin", "skin-" + WebSkins.getSkinActually(systemConfigs.getSkin()));
			initFreemarkerStaticUtils(modelAndView);
			if(null != request) {
				modelAndView.addObject("servletPath", request.getServletPath());
				modelAndView.addObject("appPath", request.getContextPath());
				modelAndView.addObject("homePath", request.getContextPath() + "/console");
			}
		}
		
	}

	void initFreemarkerStaticUtils(ModelAndView modelAndView) {
		try {
			BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
			TemplateHashModel staticModels = wrapper.getStaticModels();
//			TemplateHashModel optionValueTypeStatics =
//			        (TemplateHashModel) staticModels.get(OptionValueType.class.getName());
//			modelAndView.addObject("OptionValueType", optionValueTypeStatics);

			TemplateHashModel jsonUtilStatics =
			        (TemplateHashModel) staticModels.get(Utils.class.getName());
			modelAndView.addObject("Utils", jsonUtilStatics);
		} catch (TemplateModelException e) {
			log.error("initFreemarkerStaticUtils error", e);
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}
	
}
