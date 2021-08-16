/*
 * @(#)WebSkins.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.utils;

import java.util.Date;

import com.github.javaclub.sword.core.BizObjects;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.util.DateUtil;

/**
 * WebSkins
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: WebSkins.java ${currentTime} Exp $
 */
public class WebSkins {

	public static final String PREFERED_SKIN = "green";
	
	protected static final String[] SKIN_ALLOWED = new String[] {
			// 黑夜
			"blue",  // 0x 分
			"black", // 1x 分
			"purple", // 2x 分
			"yellow", // 3x 分
			"red",   // 4x 分
			"green", // 5x 分
			// 白天
			"blue-light", // 0x 分
			"black-light", // 1x 分
			"purple-light", // 2x 分
			"yellow-light", // 3x 分
			"red-light",    // 4x 分
			"green-light"  // 5x 分
	};
	
	public static String getRandomSkin() {
		try {
			int[] times = DateUtil.getTime(new Date());
			int hour = times[4];
			int minute = times[5];
			int index = minute/10;
			// 黑夜
			if((hour >=0 && hour <=6) || (hour >= 19 && hour <= 23)) {
				return SKIN_ALLOWED[index];
			}
			// 白天
			return SKIN_ALLOWED[index + 6];
		} catch (Exception e) {
		}
		
		return getPreferedSkin();
	}
	
	public static String getSkinActually(String setSkin) {
		if(Strings.isBlank(setSkin)) {
			return getPreferedSkin();
		}
		boolean has = BizObjects.contains(SKIN_ALLOWED, setSkin);
		if(!has) {
			return getRandomSkin();
		}
		return setSkin;
	}
	
	public static String getPreferedSkin() {
		return PREFERED_SKIN;
	}

}
