/*
 * @(#)ConfigUtil.java	2017年11月14日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.utils;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javaclub.sword.config.ConfigFactory;
import com.github.javaclub.sword.core.ValueWrap;
import com.github.javaclub.sword.spring.SpringContextUtil;

/**
 * ConfigUtil
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: ConfigUtil.java 2017年11月14日 15:55:02 Exp $
 */
public class ConfigUtil {
	
	static final Logger log = LoggerFactory.getLogger(ConfigUtil.class);
	
	static final String GLOBAL_CONFIG_KEY = "global.config.values";
	static final String OPS_TOOL_DATA_KEY = "ops.tool.data";
	static final String ONE_SHOP_OF_ORGANIZE = "one_shop_of_organize";
	
	static ConfigFactory kaConfigFactory;
	
	static {
		kaConfigFactory = (ConfigFactory) SpringContextUtil.getBean("kaConfigFactory");
	}
	
	/**
	 * 获取独立key配置项的值
	 */
	public static String getConfigValue(String key) {
		return kaConfigFactory.getConfigValue(key);
	}

	/**
	 * 获取全局配置项
	 */
	public static ValueWrap getGlobalConfigValue(String key) {
		Map<String, ValueWrap> map = kaConfigFactory.getConfigAsWrappedValueMap(GLOBAL_CONFIG_KEY);
		return (null == map ? null : map.get(key));
	}
	
	/**
	 * 获取开发运维配置项
	 */
	public static ValueWrap getOpsDataValue(String key) {
		Map<String, ValueWrap> map = kaConfigFactory.getConfigAsWrappedValueMap(OPS_TOOL_DATA_KEY);
		return (null == map ? null : map.get(key));
	}
	
	
}
