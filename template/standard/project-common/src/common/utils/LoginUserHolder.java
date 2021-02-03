/*
 * @(#)LoginUserHolder.java	2020-8-20
 *
 * Copyright (c) 2020. All Rights Reserved.
 *
 */

package ${packagePrefix}.common.utils;

/**
 * LoginUserHolder
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: LoginUserHolder.java 2020-8-20 14:24:05 Exp $
 */
public class LoginUserHolder {
	
	private static ThreadLocal<Object> CURRENT_THREAD_USER_VALUE = new InheritableThreadLocal<>();

    public static void set(Object user) {
        if (user == null) {
            return;
        }
        CURRENT_THREAD_USER_VALUE.set(user);
    }

    public static void remove() {
        CURRENT_THREAD_USER_VALUE.remove();
    }

    public static <T> T get() {
        return (T) CURRENT_THREAD_USER_VALUE.get();
    }

}
