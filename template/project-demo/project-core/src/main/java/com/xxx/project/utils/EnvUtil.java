/*
 * @(#)EnvUtil.java	2017年11月13日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.utils;

import com.github.javaclub.sword.util.AppProperties;

/**
 * EnvUtil
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: EnvUtil.java 2017年11月13日 19:24:27 Exp $
 */
public class EnvUtil {
	
	public static final String ENV_DAILY = "daily";
	public static final String ENV_GRAY = "gray";
	public static final String ENV_ONLINE = "online";

	/**
	 * 获取当前环境
	 */
	public static String getEnv() {
		return AppProperties.getString("config.env");
	}
	
	/**
	 * 日常环境
	 */
	public static boolean isDaily() {
		return ENV_DAILY.equals(getEnv());
	}
	
	/**
	 * 灰度环境
	 */
	public static boolean isGray() {
		return ENV_GRAY.equals(getEnv());
	}
	
	/**
	 * 线上环境
	 */
	public static boolean isOnline() {
		return ENV_ONLINE.equals(getEnv());
	}
}
