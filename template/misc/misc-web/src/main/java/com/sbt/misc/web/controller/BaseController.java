/*
 * @(#)BaseController.java	2017年5月19日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package com.sbt.misc.web.controller;

import static com.sbt.sword.core.BizObjects.requireNotNull;
import static com.sbt.sword.core.BizObjects.requireTrue;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.sbt.sso.SessionUtil;
import com.sbt.sword.Constants;
import com.sbt.sword.algorithm.crypt.Blowfish;
import com.sbt.sword.config.ConfigFactory;
import com.sbt.sword.core.BizObjects;
import com.sbt.sword.core.Strings;
import com.sbt.sword.core.ValueWrap;
import com.sbt.sword.domain.ResultDO;
import com.sbt.sword.web.HttpController;

/**
 * BaseController
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: BaseController.java 2017年5月19日 11:47:05 Exp $
 */
public class BaseController extends HttpController {
	
//	@Value("#{APP_PROP['auth.domain.url']}")
//	protected String domainUrl;
//	
//	@Resource
//	protected ConfigFactory authConfigFactory;
	
	
	@Autowired
	protected ThreadPoolTaskExecutor threadTaskExecutor;
	
	protected String redirectTo(String url) {
		return Strings.concat("redirect:", url);
	}
	
	/**
	 * 校验登录状态
	 */
	protected Long checkLogin() {
		Long loginUserId = this.getCurrentLoginUserId();
		requireTrue(null != loginUserId && loginUserId > 0, "尚未登录，请确认您当前是否有权限操作");
		return loginUserId;
	}
	
	/**
	 * 经过一层简单的授权校验
	 */
	protected boolean matchedOpsKey(String opsKey) {
		String encryptText = Blowfish.encrypt(Constants.DEF_ORGINAL_TEXT, opsKey);
		return BizObjects.contains(Constants.DEF_ENCRYPT_TEXT, encryptText);
	}
	
	protected Long getCurrentLoginUserId() {
		return SessionUtil.getUserId();
	}

/*
	
	protected UserAccountDO getLoginUserAccount() {
		if(SessionUtil.isLogin()) {
			Long userId = SessionUtil.getUserId();
			ResultDO<UserAccountDO> userR = userAccountService.getById(userId);
			if(null != userR.getEntry()) {
				return userR.getEntry();
			}
		}
		
		return null;
	}
	
	protected String getUserInfo(UserAccountDO user) {
		if(null != user) {
			String showname = Strings.isNotBlank(user.getName()) ? user.getName() : user.getNickname();
			if(Strings.isBlank(showname) && Strings.isNotBlank(user.getUsername())) {
				showname = user.getUsername();
			}
			return showname;
		}
		return "";
	}
	
	protected String getUserInfo() {
		if(SessionUtil.isLogin()) {
			UserAccountDO uad = getLoginUserAccount();
			return getUserInfo(uad);
		}
		return "";
	}
	
	protected ValueWrap getGlobalConfigValue(String key) {
		Map<String, ValueWrap> map = authConfigFactory.getConfigAsWrappedValueMap(ConfigCenterKeys.GLOBAL_CONFIG_VALUES.getCode());
		return (null == map ? null : map.get(key));
	}
	
	protected ValueWrap getOpsDataConfigValue(String key) {
		Map<String, ValueWrap> map = authConfigFactory.getConfigAsWrappedValueMap(ConfigCenterKeys.OPS_TOOL_DATA.getCode());
		return (null == map ? null : map.get(key));
	}
	
	protected CookieEntity getCookie() {
		SSOToken token = SSOHelper.getToken(request);
		if(null == token || Strings.isBlank(token.getData())) {
			return null;
		}
		return JSON.parseObject(token.getData(), CookieEntity.class);
	}

*/
	
}
