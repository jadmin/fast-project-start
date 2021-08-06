/*
 * @(#)TestController.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.web.ops;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javaclub.sword.algorithm.crypt.Blowfish;
import com.github.javaclub.sword.core.Maps;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.web.HttpResult;
import ${packagePrefix}.common.AppConstants.RedisKeys;
import ${packagePrefix}.common.enums.SecondVerifyType;
import ${packagePrefix}.dataobject.LoginUserDO;
import ${packagePrefix}.service.LoginUserService;
import ${packagePrefix}.service.utils.GoogleAuthenticatorUtils;
import ${packagePrefix}.support.RedisCacheManager;
import ${packagePrefix}.web.ApiBaseController;

import lombok.extern.slf4j.Slf4j;

/**
 * TestController
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: TestController.java ${currentTime} Exp $
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController extends ApiBaseController {
	
	@Autowired
	private RedisCacheManager redisCacheManager;
	
	@Autowired
	private LoginUserService loginUserService;

	@RequestMapping(value = "/saveKey")
    public HttpResult<Boolean> saveKey(String key, String value) {
        try {
        	String rKey = RedisKeys.formatKey("tkak_%s", key);
        	redisCacheManager.set(rKey, value, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            log.error("saveKey", e);
        }
        return HttpResult.success();
    }
	
	@GetMapping("/generateUserAuthKey")
	public Map<String, Object> generateUserAuthKey(String username, Boolean open) throws Exception {
		Map<String, Object> map = com.google.common.collect.Maps.newHashMap();
		LoginUserDO user = loginUserService.getByUsername(username);
		if(null != user) {
			String secretKey = user.getAuthKey();
			if(Strings.isBlank(secretKey)) {
				secretKey = Blowfish.encrypt(GoogleAuthenticatorUtils.generateSecretKey());
				user.setAuthKey(secretKey);
			}
			String googleAuthKey = Blowfish.decrypt(secretKey);
			user.addAttribute(LoginUserDO.ATTR_OPEN_SECOND_VERIFY, open);
			if(open) {
				user.addAttribute(LoginUserDO.ATTR_SECOND_VERIFY_TYPE, SecondVerifyType.GOOGLE_CODE.getValue());
			} else {
				user.removeAttribute(LoginUserDO.ATTR_SECOND_VERIFY_TYPE);
			}
			String issuer = this.parseEnvForGoogleAuth();
			String qrcodeUrl = GoogleAuthenticatorUtils.formatQrcodeUrl(issuer, username, googleAuthKey);
			
			map.put("googleAuthKey", googleAuthKey);
			map.put("qrcodeUrl", qrcodeUrl);
			
			loginUserService.update(user);
		}
		
		return map;
	}
	
	@GetMapping("/requestTest")
	public Object requestTest() {
		Object value1 = request.getParameterValues("name");
		Object value2 = request.getParameter("name");
		
		return Maps.generateMap("value1", value1, "value2", value2);
	}
	
	@GetMapping("/redisScan")
	public Object redisScan(String prefix, int count) {
		Set<String> keys = redisCacheManager.matchKeysWithScan(prefix + "*", count);
		return keys;
	}
    
}

