/*
 * @(#)BaseConfig.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.github.javaclub.sword.core.Collections;
import com.google.common.collect.Lists;
import ${packagePrefix}.dataobject.ResourceDO;
import ${packagePrefix}.service.ResourceService;
import ${packagePrefix}.support.SystemConfigs;

/**
 * BaseConfig
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: BaseConfig.java ${currentTime} Exp $
 */
@Component
public class BaseConfig {
	
	@Autowired
	protected SystemConfigs systemConfigs;
	
	@Autowired
	protected ResourceService resourceService;
	
	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;
	
	protected String[] urisAccessWithNoLogin() {
		String[] array = systemConfigs.getNoLoginUris();
		return array;
	}
	
	protected List<String> getUserAuthorizedUris(Long userId) {
		List<ResourceDO> list = resourceService.findUserResourceList(userId);
		if(Collections.isEmpty(list)) {
			return Lists.newArrayList();
		}
		return list.stream().map(ResourceDO::getUri).collect(Collectors.toList());
	}

}
