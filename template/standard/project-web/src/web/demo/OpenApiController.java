/*
 * @(#)OpenApiController.java	2021-3-31
 *
 * Copyright (c) 2021. All Rights Reserved.
 *
 */

package ${packagePrefix}.web.demo;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javaclub.sword.core.BizObjects;
import com.github.javaclub.sword.core.Numbers;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.util.WebUtil;
import com.github.javaclub.sword.web.HttpResult;
import com.google.common.collect.Maps;
import com.xsoft.xyz.common.AppConstants.OpenApi;

import ${packagePrefix}.common.AppConstants.OpenApi;
import ${packagePrefix}.common.annotation.OpenApiAuth;
import ${packagePrefix}.web.ApiBaseController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * OpenApiController
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: OpenApiController.java 2021-3-31 17:20:34 Exp $
 */
@Slf4j
@RestController
@RequestMapping("/openapi")
@Api(tags = "openApi签名演示")
public class OpenApiController extends ApiBaseController {

	static final long INVOKE_TIMESTAMP_CHECK = 60L; // 时间差在60秒内

	public static void main(String[] args) {
		String appKey = "a3e21b6da1586158";
		String appSecret = "55ac68c0cbb2b61837ee366bae60dceb";
		
		// 请求参数：appKey/timestamp/sign / username/demoKey/testParam

		Map<String, String> reqParams = Maps.newHashMap();
		reqParams.put("appKey", appKey);
		reqParams.put("sign", "");
		reqParams.put("timestamp", String.valueOf(System.currentTimeMillis()));
		reqParams.put("username", "Tom");
		reqParams.put("demoKey", "JtYNSMAxM3w1SVFJp");
		reqParams.put("testParam", "TM6cAP7JtYNSMAxM3w1SVFJpBtwu2F");

		String sign = WebUtil.sign(reqParams, Arrays.asList(new String[] { "sign" }), appSecret);
		reqParams.put("sign", sign);

		for (Map.Entry<String, String> elem : reqParams.entrySet()) {
			System.out.println(Strings.format("{} = {}", elem.getKey(), elem.getValue()));
		}
	}

	@ApiOperation(value = "openapi接收外部调用请求")
	@RequestMapping(value = "/invoke")
	public HttpResult<Object> invokeOpenApi() {

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
		BizObjects.requireTrue(hasAppKey, "请求错误，appKey已失效");

		long currentTimestamp = System.currentTimeMillis();
		long time = Numbers.parseLong(timestamp);
		BizObjects.requireTrue(currentTimestamp >= time, "请求错误，参数校验异常");
		boolean check = (currentTimestamp - time) < OpenApi.TIMESTAMP_CHECK * 1000L; // 时间差在60s以内
		BizObjects.requireTrue(check, "请求错误，请求已失效");

		// 验签
		String secret = OpenApi.APPKEY_SECRETS.get(appKey);
		String srvSign = WebUtil.sign(reqParams, Arrays.asList(new String[] { OpenApi.PARAM_SIGN }), secret);
		BizObjects.requireTrue(Strings.equals(reqSign, srvSign), "请求错误，非法的签名");

		// TODO 执行具体业务逻辑

		Map<String, Object> rMap = Maps.newHashMap();
		rMap.put("reqParams", reqParams);
		rMap.put("srvSecret", secret);
		rMap.put("srvSign", srvSign);

		return HttpResult.success(rMap);
	}
	
	@ApiOperation(value = "openapi接收外部调用请求(注解验签)")
	@RequestMapping(value = "/invokeV2")
	@OpenApiAuth
	public HttpResult<Object> invokeOpenApiV2(String username, String demoKey, String testParam) {

		// 拦截器会做统一验签, 这里执行具体业务逻辑
		log.info("username={}, demoKey={}, testParam={}", username, demoKey, testParam);

		return HttpResult.success("OK");
	}

}
