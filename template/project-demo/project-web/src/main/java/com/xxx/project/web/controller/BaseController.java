/*
 * @(#)BaseController.java	2017年5月19日
 *
 * Copyright (c) 2017. All Rights Reserved.
 *
 */

package #{packagePrefix}#.web.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;

import com.github.javaclub.sword.Constants;
import com.github.javaclub.sword.algorithm.crypt.Encryptor;
import com.github.javaclub.sword.config.ConfigFactory;
import com.github.javaclub.sword.core.B;
import com.github.javaclub.sword.core.ValueWrap;

/**
 * BaseController
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: BaseController.java 2017年5月19日 11:47:05 Exp $
 */
public class BaseController {
	
	static final String GLOBAL_CONFIG_KEY = "global.config.values";
	static final String OPS_TOOL_DATA_KEY = "ops.tool.data";
	
	@Value("#{APP_PROP['kamember.domain.url']}")
	protected String domainUrl;
	
	@Resource
	protected ConfigFactory kaConfigFactory;
	@Autowired
	protected JdbcTemplate jdbcTemplate; // 通用Spring jdbc模版
	
	
	/**
	 * 经过一层简单的授权校验
	 */
	protected boolean matchedOpsKey(String opsKey) {
		String encryptText = Encryptor.encrypt(Constants.DEF_ORGINAL_TEXT, opsKey);
		return B.contains(Constants.DEF_ENCRYPT_TEXT, encryptText);
	}
	
//	protected UserDO getLoginUserDO() {
//		if(SdgSessionUtil.isLogin()) {
//			Long userId = SdgSessionUtil.getUserId();
//			UserDO userDO = userService.getUserByUserId(String.valueOf(userId));
//			if(null != userDO) {
//				return userDO;
//			}
//		}
//		
//		return null;
//	}
//	
//	protected String getUserInfo(UserDO user) {
//		if(null != user) {
//			return StringUtils.isNotBlank(user.getRealName()) ? user.getRealName() : user.getUserNick();
//		}
//		return "";
//	}
//	
//	protected String getUserInfo() {
//		if(SdgSessionUtil.isLogin()) {
//			Long userId = SdgSessionUtil.getUserId();
//			UserDO userDO = userService.getUserByUserId(String.valueOf(userId));
//			if(null != userDO) {
//				return StringUtils.isNotBlank(userDO.getRealName()) ? userDO.getRealName() : userDO.getUserNick();
//			}
//		} 
//		return "";
//	}
	
	protected ValueWrap getGlobalConfigValue(String key) {
		Map<String, ValueWrap> map = kaConfigFactory.getConfigAsWrappedValueMap(GLOBAL_CONFIG_KEY);
		return (null == map ? null : map.get(key));
	}
	
	protected ValueWrap getOpsDataConfigValue(String key) {
		Map<String, ValueWrap> map = kaConfigFactory.getConfigAsWrappedValueMap(OPS_TOOL_DATA_KEY);
		return (null == map ? null : map.get(key));
	}
	
}
