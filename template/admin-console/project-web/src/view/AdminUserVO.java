/*
 * @(#)AdminUserVO.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.view;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * AdminUserVO
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: AdminUserVO.java ${currentTime} Exp $
 */
public class AdminUserVO extends AdminUserView {

	public AdminUserVO() {
		
	}
	
	public String getOsInfo() {
		UserAgent userAgent = UserAgent.parseUserAgentString(getLoginUA());
		// 获取操作系统对象
		OperatingSystem operatingSystem = userAgent.getOperatingSystem();
		return operatingSystem.getName();
	}
	
	public String getBrowserInfo() {
		UserAgent userAgent = UserAgent.parseUserAgentString(getLoginUA());
		// 获取浏览器对象
		Browser browser = userAgent.getBrowser();
		return browser.getName();
	}

}
