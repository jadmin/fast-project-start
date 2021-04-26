/*
 * @(#)BaseController.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
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
 * @version $Id: BaseController.java ${currentTime} Exp $
 */
@Slf4j
public class ApiBaseController extends HttpController {

	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;
	

}
