/*
 * @(#)LoginUserHolder.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.common.utils;

/**
 * LoginUserHolder
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: LoginUserHolder.java ${currentTime} Exp $
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
