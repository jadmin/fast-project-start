/*
 * @(#)AppIdKeyManager.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ${packagePrefix}.support.RedisCacheManager;

/**
 * AppIdKeyManager
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: AppIdKeyManager.java ${currentTime} Exp $
 */
@Component
public class AppIdKeyManager {
	
	@Autowired
	private RedisCacheManager redisCacheManager;

	public String getAppKey(Integer appId) {
		// TODO Auto-generated method stub
		return "d3d1f52452be41aaa1b68e33d59d0188";
	}

	
}
