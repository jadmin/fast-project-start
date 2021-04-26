/*
 * @(#)OpenApiAspect.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.aop;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.javaclub.sword.core.BizObjects;
import com.github.javaclub.sword.core.Numbers;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.util.WebUtil;
import com.google.common.collect.Maps;
import ${packagePrefix}.common.AppConstants.OpenApi;

/**
 * OpenApiAspect
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: OpenApiAspect.java ${currentTime} Exp $
 */
@Aspect
@Component
public class OpenApiAspect {
	
	@Pointcut("@annotation(${packagePrefix}.common.annotation.OpenApiAuth)")
    public void apiAspect() {
    }

    @Around("apiAspect()")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        
        Object result = null;
		try {
			Map<String, String> reqParams = Maps.newHashMap();
			Enumeration<?> params = request.getParameterNames();
			while (params.hasMoreElements()) {
				String param = (String) params.nextElement();
				String value = request.getParameter(param);
				reqParams.put(param, value);
			}
			String appKey = reqParams.get(OpenApi.PARAM_APPKEY);
			String reqSign = reqParams.get(OpenApi.PARAM_SIGN);
			String timestamp = reqParams.get(OpenApi.PARAM_TIMESTAMP);
			BizObjects.requireTrue(!Strings.isBlank(appKey), "请求错误，缺少应用参数：appKey");
			BizObjects.requireTrue(!Strings.isBlank(reqSign), "请求错误，缺少签名参数：sign");
			BizObjects.requireTrue(!Strings.isBlank(timestamp), "请求错误，缺少时间参数：timestamp");

			boolean hasAppKey = OpenApi.APPKEY_SECRETS.containsKey(appKey);
			BizObjects.requireTrue(hasAppKey, "请求错误，appKey不存在或已失效");

			long currentTimestamp = System.currentTimeMillis();
			long time = Numbers.parseLong(timestamp);
			BizObjects.requireTrue(currentTimestamp >= time, "请求错误，参数校验异常");
			boolean check = (currentTimestamp - time) < 60 * 1000L; // 时间差在60s以内
			BizObjects.requireTrue(check, "请求错误，请求已失效");

			// 验签
			String secret = OpenApi.APPKEY_SECRETS.get(appKey);
			String srvSign = WebUtil.sign(reqParams, Arrays.asList(new String[] { OpenApi.PARAM_SIGN }), secret);
			BizObjects.requireTrue(Strings.equals(reqSign, srvSign), "请求错误，非法的签名");

			// 执行具体业务逻辑
			result = point.proceed();
			
		} catch (Throwable e) {
			throw e;
		}
        
        return result;
    }

}
