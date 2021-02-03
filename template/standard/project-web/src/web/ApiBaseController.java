/*
 * @(#)BaseController.java	2017年5月19日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package ${packagePrefix}.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.github.javaclub.sword.web.HttpController;

import lombok.extern.slf4j.Slf4j;

/**
 * BaseController
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: BaseController.java 2017年5月19日 11:47:05 Exp $
 */
@Slf4j
public class ApiBaseController extends HttpController {

	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;
	

}
