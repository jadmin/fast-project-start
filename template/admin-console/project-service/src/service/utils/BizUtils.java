/*
 * @(#)BizUtils.java	${date}
 *
 * Copyright (c) ${year}. All Rights Reserved.
 *
 */

package ${packagePrefix}.service.utils;

import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.github.javaclub.sword.algorithm.crypt.MD5;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.util.DateUtil;

/**
 * BizUtils
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: BizUtils.java ${currentTime} Exp $
 */
public class BizUtils {

	public static final String AUTH_SALT = "$^%dsDehGFiR8&6@g3H";
	
	public static void main(String[] args) {
		// zym   1a5c7c9349a8838c716485acfb986b86
		// String pwd = "xc818"; // 041b42a7e586f8136d55354e75287319
		String pwd = "xinc818"; // 90c88a740ce87dbe08cecd245d3ddc23
		
		String md5Pwd = generatePasswordMD5(pwd);
		
		System.out.println(md5Pwd);
	}

	public static String generatePasswordMD5(String passwordRaw) {
		String md5Pwd = MD5.getMd5(passwordRaw + AUTH_SALT);
		return md5Pwd;
	}
	
	public static String generateToken() {
		String formatOf24Hour = DateUtil.getFormat(new Date(), "yyyy-MM-dd HH");
		Calendar cal = Calendar.getInstance();
		int minutes = cal.get(Calendar.MINUTE);
		String rawKey = Strings.concat(formatOf24Hour, "-", minutes <= 30 ? "HOUR_HALF_1" : "HOUR_HALF_2");
		String tokenKey = MD5.getMd5(rawKey + AUTH_SALT);
		return tokenKey;
	}
	
	public static void clearCookie(Cookie cookie, HttpServletResponse response, String path) {
		cookie.setValue("");
		cookie.setPath(path);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
	
}
