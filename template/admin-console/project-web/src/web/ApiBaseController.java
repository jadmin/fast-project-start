/*
 * @(#)ApiBaseController.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.web.HttpController;
import ${packagePrefix}.support.SystemConfigs;

/**
 * ApiBaseController
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: ApiBaseController.java ${currentTime} Exp $
 */
public class ApiBaseController extends HttpController {
	
	@Autowired
	protected SystemConfigs systemConfigs;

	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;
	
	protected String parseEnvForGoogleAuth() {
		String appName = systemConfigs.getAppName().toUpperCase();
        String env = systemConfigs.getEnv();
        if ("gray".equals(env) || "prod".equals(env)) {
            return appName + "-GRAY_PROD"; // 预发和线上
        }
        return appName + "-" + Strings.upperCase(env);
	}

}
