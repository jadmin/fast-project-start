/*
 * @(#)BlackWhiteEnum.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.common.enums;

import java.util.Objects;

import com.github.javaclub.sword.core.Strings;
import ${packagePrefix}.common.AppConstants.BlackWhiteList;

/**
 * BlackWhiteEnum
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: BlackWhiteEnum.java ${currentTime} Exp $
 */
public enum BlackWhiteEnum {

	FORBIDDEN_IP("forbiddenIp", "高风险禁用IP", true),
	
	APP_ID_KEY("appIdKey", "应用appKey配置", false),
	INTERNAL_MOBILE_WHITELIST("wlInternalMobile", "内部手机号白名单", false),
	
	;

	private String code;
	private String desc;
	private boolean black;

	private BlackWhiteEnum(String code, String desc, boolean black) {
		this.code = code;
		this.desc = desc;
		this.black = black;
	}

	public String getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	public boolean isBlack() {
		return black;
	}

	public static BlackWhiteEnum fromCode(String code) {
		for (BlackWhiteEnum param : BlackWhiteEnum.values()) {
            if (Objects.equals(param.getCode(), code)) {
                return param;
            }
        }
        return null;
	}
	
	public static String getBlackWhiteBizDesc(String code) {
		BlackWhiteEnum bwe = fromCode(code);
		return null == bwe ? null : bwe.getDesc();
	}
	
	public static String groupCacheKey(String bizCode, int type) {
        return Strings.concat(BlackWhiteList.GROUP_CACHEKEY_PRE, bizCode, "#", type);
    }

}
