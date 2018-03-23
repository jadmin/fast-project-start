/*
 * @(#)GlobalSwitchController.java	2017年5月24日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.web.controller.ops;

import java.util.Objects;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.javaclub.sword.web.BasicAuth;
import com.github.javaclub.sword.web.HttpResult;
import #{packagePrefix}#.domain.Constants;
import #{packagePrefix}#.web.controller.BaseController;

/**
 * GlobalSwitchController
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: GlobalSwitchController.java 2017年5月24日 10:32:26 Exp $
 */
@Controller
@RequestMapping("/ops/switch")
public class GlobalSwitchController extends BaseController {
	
	/**
	 * 默认访问，直接设置成默认值，除非强制设置成非系统预设值
	 * </p>
	 * 主要测试时用到
	 */
	@RequestMapping(value = "/setAuthSwitch")
	@ResponseBody
	@BasicAuth
	public HttpResult setAuthSwitch(Boolean auth, String pwd) {
		Constants.Switcher.AUTH = true;
		if(null != auth && !Objects.equals(Boolean.TRUE, auth)
				&& matchedOpsKey(pwd)) {
			Constants.Switcher.AUTH = auth;
		}
		
		return HttpResult.success(Constants.Switcher.AUTH);
	}
	
}
