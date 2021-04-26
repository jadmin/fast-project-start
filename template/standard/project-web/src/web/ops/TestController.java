/*
 * @(#)TestController.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.web.ops;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javaclub.sword.web.HttpController;
import com.github.javaclub.sword.web.HttpResult;
import ${packagePrefix}.common.AppConstants.RedisKeys;
import ${packagePrefix}.support.RedisCacheManager;

import lombok.extern.slf4j.Slf4j;

/**
 * TestController
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: TestController.java ${currentTime} Exp $
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController extends HttpController {
	
	@Autowired
	private RedisCacheManager redisCacheManager;

	@RequestMapping(value = "/saveKey")
    public HttpResult<Boolean> saveKey(String key, String value) {
        try {
        	String rKey = RedisKeys.formatKey("tkak_%s", key);
        	redisCacheManager.set(rKey, value, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error("saveKey", e);
        }
        return HttpResult.success();
    }

    
}
