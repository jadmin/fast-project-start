/*
 * @(#)RedisKeyConstants.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.common.constants;

import com.github.javaclub.sword.core.Strings;
import ${packagePrefix}.common.AppConstants.RedisKeys;

/**
 * RedisKeyConstants
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: RedisKeyConstants.java ${currentTime} Exp $
 */
public class RedisKeyConstants {

	public static final String ADMIN_USER = "adminuser:uid_{}";
	public static final String ADMIN_USER_CRSF_TOKEN = "adminuser:crsf_{}";
	public static final String ADMIN_USER_LOGIN_CONFIRM = "adminuser:loginconfirm:uname_{}";
	public static final String ADMIN_USER_LOGIN_CONFIRM_CODE = "adminuser:loginconfirm_code:uname_{}";
	public static final String FORGET_PASSWORD_SMS_CACHE_KEY = "adminuser:password:resetsms:u_{}";
	
	public static final String BWL_CAHCE_REFRESH_TOKEN_WEB_ACTION = "token:bwlrefresh_webaction";
	public static final String BWL_CAHCE_REFRESH_RELOAD_ALL = "token:bwlrefresh_reload_all";
	
	public static String generateBwlRefreshTokenKey(long currentTimeSecs) {
		String format = RedisKeys.APP_REDIS_NS + ":bwl_refresh_token.{}";
		return Strings.format(format, currentTimeSecs);
    }
	
	public static String getAdminUserKeyPrefix() {
		return Strings.concat(RedisKeys.APP_REDIS_NS, ":", "adminuser:uid");
	}
}


