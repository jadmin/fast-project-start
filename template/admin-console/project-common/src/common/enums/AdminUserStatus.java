/*
 * @(#)AdminUserStatus.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.common.enums;


/**
 * AdminUserStatus
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: AdminUserStatus.java ${currentTime} Exp $
 */
public enum AdminUserStatus {
    
    NO_ACTIVATED(0, "待激活"),
    NORMAL(1, "正常状态"),
    DISABLED(2, "已禁用"),

    ;

    private final int value;
    private final String desc;

    AdminUserStatus(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }
}
