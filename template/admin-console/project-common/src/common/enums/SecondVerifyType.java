/*
 * @(#)SecondVerifyType.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.common.enums;

import java.util.Objects;

/**
 * 二次验证类型
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: SecondVerifyType.java ${currentTime} Exp $
 */
public enum SecondVerifyType {
	
	SMS_CODE(1, "手机短信验证码"),
    GOOGLE_CODE(2, "GOOGLE动态验证码"),

    ;
	
    private final int value;
    private final String desc;

    SecondVerifyType(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

    public static SecondVerifyType fromValue(int value) {
        for (SecondVerifyType type : SecondVerifyType.values()) {
            if (Objects.equals(type.getValue(), value)) {
                return type;
            }
        }
        return null;
    }
}
