/*
 * @(#)AppConstants.java	2020-12-30
 *
 * Copyright (c) 2020. All Rights Reserved.
 *
 */

package ${packagePrefix}.common;

import com.github.javaclub.sword.core.Strings;

/**
 * AppConstants
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: AppConstants.java 2020-12-30 11:27:07 Exp $
 */
public interface AppConstants {
	
	public static final String SEP_COLON = ":";

	public class RedisKeys {
		
		public static final String APP_REDIS_NS = "${appName}";
		
		public static final String formatKey(String format, Object... params) {
            String key = String.format(format, params);
            return Strings.concat(APP_REDIS_NS, SEP_COLON, key);
        }
	}
	
	
}
