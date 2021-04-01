/*
 * @(#)AppConstants.java	2020-12-30
 *
 * Copyright (c) 2020. All Rights Reserved.
 *
 */

package ${packagePrefix}.common;

import java.util.Map;

import com.github.javaclub.sword.core.Maps;
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
	
	public class OpenApi {
		
		public static final String PARAM_APPKEY = "appKey";
		public static final String PARAM_TIMESTAMP = "timestamp"; // 当前时间戳(毫秒)参数
		public static final String PARAM_SIGN = "sign"; // 请求签名
		
		public static final long TIMESTAMP_CHECK = 60L; // 时间差限制在60秒内才有效
		
		public static final Map<String, String> APPKEY_SECRETS = Maps.createStringMap(
			"a3e21b6da1586158", "55ac68c0cbb2b61837ee366bae60dceb"
		);
	}
	
	
}
