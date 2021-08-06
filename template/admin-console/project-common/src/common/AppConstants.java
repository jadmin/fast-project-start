/*
 * @(#)AppConstants.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.common;

import java.util.Date;
import java.util.Map;

import com.github.javaclub.sword.core.Maps;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.util.DateUtil;
import ${packagePrefix}.common.AppConstants.RedisKeys;

/**
 * AppConstants
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: AppConstants.java ${currentTime} Exp $
 */
public interface AppConstants {
	
	public static final String APP_NAME = "${appName}";
	public static final String SEP_COLON = ":";

	public class RedisKeys {
		
		public static final String APP_REDIS_NS = "${appName}";
		
		public static final String formatKey(String format, Object... params) {
            String key = String.format(format, params);
            return Strings.concat(APP_REDIS_NS, SEP_COLON, key);
        }
	}
	
	public static class BlackWhiteList {
		
		public static final int BLACK_LIST = 1;
        public static final int WHITE_LIST = 2;
        public static final int CONFIG_LIST = 3;
		
        public static final String GROUP_CACHEKEY_PRE = RedisKeys.APP_REDIS_NS + ":bwl:";

        public static final int EXPIRE_TYPE_1D = 1; // 过期时间为1天
        public static final int EXPIRE_TYPE_3D = 2; // 过期时间为3天
        public static final int EXPIRE_TYPE_10D = 3; // 过期时间为10天
        public static final int EXPIRE_TYPE_1M = 4; // 过期时间为1个月
        public static final int EXPIRE_TYPE_3M = 5; // 过期时间为3个月
        public static final int EXPIRE_TYPE_FOREVER = 6; // 过期时间为永久
        public static final int EXPIRE_TYPE_CUSTOM = 7; // 过期时间自定义

        // 过期时间为永久的失效时间
        static final Date EXPIRE_LONG_TIME = DateUtil.toDate("2099-12-31 23:59:59");
        
        public static final String ACTION_TYPE_ADD = "ADD"; // 新增名单
		public static final String ACTION_TYPE_BATCH_ADD = "BATCH_ADD"; // 批量新增名单
		public static final String ACTION_TYPE_DELETE = "DELETE"; // 删除名单
		public static final String ACTION_TYPE_RELOAD_ALL = "RELOAD_ALL"; // 重新加载所有缓存

        public static Date getMaxExpireTime() {
            return EXPIRE_LONG_TIME;
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
	
	public class UserRoles {
		
		public static final String SUPER_ADMIN = "superAdmin";
		
	}
	
	
}
