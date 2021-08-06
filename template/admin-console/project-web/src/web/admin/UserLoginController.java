/*
 * @(#)UserController.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.web.admin;

import static com.github.javaclub.sword.core.Strings.isNotBlank;
import static ${packagePrefix}.common.AppConstants.RedisKeys.formatKey;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javaclub.sword.BizException;
import com.github.javaclub.sword.algorithm.crypt.Base64;
import com.github.javaclub.sword.core.BizObjects;
import com.github.javaclub.sword.core.Entry;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.domain.PairValue;
import com.github.javaclub.sword.util.WebUtil;
import ${packagePrefix}.common.annotation.WithApiResult;
import ${packagePrefix}.common.constants.RedisKeyConstants;
import ${packagePrefix}.common.enums.AdminUserStatus;
import ${packagePrefix}.common.enums.ErrorEnum;
import ${packagePrefix}.common.enums.LoginTypeEnum;
import ${packagePrefix}.common.enums.SecondVerifyType;
import ${packagePrefix}.common.utils.ValidateUtils;
import ${packagePrefix}.component.authenticator.AuthenticationInfo;
import ${packagePrefix}.component.authenticator.AuthenticatorContext;
import ${packagePrefix}.component.authenticator.LdapAuthenticator;
import ${packagePrefix}.convert.UserConvertor;
import ${packagePrefix}.dataobject.LoginUserDO;
import ${packagePrefix}.param.UserLoginParam;
import ${packagePrefix}.service.LoginUserService;
import ${packagePrefix}.service.params.UserLoginConfirmParam;
import ${packagePrefix}.service.params.UserModelParam;
import ${packagePrefix}.service.params.result.LoginCofirmResult;
import ${packagePrefix}.service.utils.BizUtils;
import ${packagePrefix}.view.AdminUserView;
import ${packagePrefix}.view.LoginResultView;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * UserControllUserController href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: UserController.java ${currentTime} Exp $
 */
@RestController
@RequestMapping("/admin/user")
@WithApiResult
@ApiIgnore
@Slf4j
public class UserLoginController extends AdminControllerBase {
	
	@Autowired
	private LoginUserService loginUserService;
	@Autowired
	private LdapAuthenticator ldapAuthenticator;
	
	@Autowired
    private JavaMailSender javaMailSender;
    
	@Autowired
    private MailProperties mailProperties;
	
	@PostMapping(value = "/login")
    public LoginResultView login(@RequestBody UserLoginParam param) throws Exception {
    	ValidateUtils.requireTrue(null != param, ErrorEnum.WRONG_ARGS);
    	ValidateUtils.requireNotEmpty(param.getUsername(), ErrorEnum.PARAM_REQUIRE_NOT_EMPTY, "用户名不能为空");
    	ValidateUtils.requireNotEmpty(param.getPassword(), ErrorEnum.PARAM_REQUIRE_NOT_EMPTY, "登录密码不能为空");
    	
    	String headCrsfToken = request.getHeader("token");
    	ValidateUtils.requireNotEmpty(param.getPassword(), ErrorEnum.PARAM_REQUIRE_NOT_EMPTY, "登录校验失败！");
    	String key = formatKey(RedisKeyConstants.ADMIN_USER_CRSF_TOKEN, headCrsfToken);
    	String svrValue = (String) redisTemplate.opsForValue().get(key);
    	ValidateUtils.requireTrue(isNotBlank(svrValue) && svrValue.equals(headCrsfToken), ErrorEnum.PARAM_REQUIRE_NOT_EMPTY, "登录校验失败，请重试！");
    	
    	String tokenKey = BizUtils.generateToken();
    	String decryptInput = Base64.decode(param.getPassword());
    	if(!decryptInput.endsWith(tokenKey)) {
    		throw new BizException(ErrorEnum.LOGIN_INVALID.getCode(), "验证失败, 请重新登录！");
    	}
    	String rawPassword = decryptInput.replace(tokenKey, "");
    	
    	LoginUserDO loginUser = null;
		try {
			Integer loginType = param.getLoginType();
			if(Objects.equals(loginType, LoginTypeEnum.LDAP.getValue())) { // LDAP登陆
				AuthenticatorContext authenticatorContext = new AuthenticatorContext();
				authenticatorContext.setUsername(param.getUsername());
				authenticatorContext.setPassword(rawPassword);
				AuthenticationInfo authenticateInfo = ldapAuthenticator.authenticate(authenticatorContext);
				if (Objects.nonNull(authenticateInfo)) {
					loginUser = UserConvertor.INSTANCE.convertToLoginUserDO(authenticateInfo);
					loginUser.addAttribute(LoginUserDO.ATTR_OPEN_SECOND_VERIFY, authenticateInfo.getOpenSecondVerify());
					loginUser.addAttribute(LoginUserDO.ATTR_SECOND_VERIFY_TYPE, authenticateInfo.getSecondVerifyType());
				}
			} else { // 普通用户名和密码登入
				loginUser = loginUserService.checkLoginUser(param.getUsername(), rawPassword);
			}
		} catch (Exception ex) {
			log.error("", ex);
			throw new BizException(ErrorEnum.SYSTEM_ERROR.getCode(), "登录失败, 请稍后重试！");
		} finally {
			if (null != loginUser) {
				try {
					redisTemplate.delete(key);
				} catch (Exception e) {
				}
			}
		}
    	if(null == loginUser) {
    		throw new BizException(ErrorEnum.LOGIN_INVALID.getCode(), "登录失败, 请检查用户名和密码！");
    	}
    	if(Objects.equals(AdminUserStatus.NO_ACTIVATED.getValue(), loginUser.getStatus())) {
    		throw new BizException(ErrorEnum.LOGIN_INVALID.getCode(), "账号尚未激活, 请联系系统管理员！");
    	}
    	if(Objects.equals(AdminUserStatus.DISABLED.getValue(), loginUser.getStatus())) {
    		throw new BizException(ErrorEnum.LOGIN_FAILED.getCode(), "当前用户已被禁用！");
    	}
    	
    	LoginResultView view = new LoginResultView();
    	view.setLoginSucess(true);
    	view.setOpenSecondVerify(loginUser.isOpenSecondVerify());
    	view.setSecondVerifyType(loginUser.getSecondVerifyType());
    	view.setUserId(loginUser.getId());
    	String loginIp = WebUtil.getIpAddr(request);
    	String loginUA = request.getHeader("User-Agent");
    	loginUser.addAttribute(LoginUserDO.ATTR_LOGIN_IP, loginIp);
    	loginUser.addAttribute(LoginUserDO.ATTR_LOGIN_UA, loginUA);
    	if(!loginUser.isOpenSecondVerify() || null == loginUser.getSecondVerifyType()) {
    		try {
        		initCurrentLoginUser(loginUser, param.isRememberMe());
    		} catch (Exception e) {
    			log.error("", e);
    			throw new BizException(ErrorEnum.SYSTEM_ERROR.getCode(), "登录失败, 请稍后重试！");
    		}
    	} else if (loginUser.isOpenSecondVerify() && null != loginUser.getSecondVerifyType()
    			&& BizObjects.contains(new Integer[] {
    					SecondVerifyType.SMS_CODE.getValue(), 
    					SecondVerifyType.GOOGLE_CODE.getValue() 
    				},  loginUser.getSecondVerifyType())) {
    		
    		UserModelParam ump = new UserModelParam();
    		ump.setLoginTime(System.currentTimeMillis());
    		ump.setRememberMe(param.isRememberMe());
    		ump.setUserid(loginUser.getId());
    		ump.setUsername(loginUser.getUsername());
    		ump.setLoginIp(loginIp);
    		ump.setLoginUA(loginUA);
    		if (Objects.equals(SecondVerifyType.SMS_CODE.getValue(), loginUser.getSecondVerifyType())) {
    			String code = Strings.random(6, "1234567890");
    			String cfKey = formatKey(RedisKeyConstants.ADMIN_USER_LOGIN_CONFIRM_CODE, loginUser.getUsername());
    			redisTemplate.opsForValue().set(cfKey, code, 3*60, TimeUnit.SECONDS);
    			// TODO 发送短信或邮件
    			log.warn("{} 登录验证码 {}", loginUser.getUsername(), code);
    			
//    			SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
//    	        passwordResetEmail.setFrom(mailProperties.getUsername());
//    	        passwordResetEmail.setTo(loginUser.getEmail());
//    	        passwordResetEmail.setSubject("【后台管理系统】登录验证码");
//
//    	        String format = format("账户：{} 当前登录验证码为: {} , 该验证码60秒内有效, 如非本人操作请忽略",
//    	        		loginUser.getUsername(), code);
//    	        passwordResetEmail.setText(format);
//    	        javaMailSender.send(passwordResetEmail);
    		}
    		
			String cfKey = formatKey(RedisKeyConstants.ADMIN_USER_LOGIN_CONFIRM, loginUser.getUsername());
			redisTemplate.opsForValue().set(cfKey, ump, 3*60, TimeUnit.SECONDS);
    	}
    	
    	return view;
    }
	
	/**
	 * 用户登录二次验证
	 * 
	 * <li> type=1 短信验证码
	 * <li> type=2 Google动态身份验证码
	 */
	@PostMapping(value = "/loginConfirm")
    public Boolean loginConfirm(@RequestBody UserLoginConfirmParam param) throws Exception {
    	ValidateUtils.requireTrue(null != param, ErrorEnum.WRONG_ARGS);
    	ValidateUtils.requireNotNullGtZero(param.getUid(), ErrorEnum.WRONG_ARGS, "请求参数错误");
    	ValidateUtils.requireNotNullGtZero(param.getType(), ErrorEnum.WRONG_ARGS, "请求参数错误");
    	ValidateUtils.requireNotEmpty(param.getCode(), ErrorEnum.PARAM_REQUIRE_NOT_EMPTY, "验证码不能为空");
    	
    	boolean isNum = Strings.isNumeric(param.getCode());
    	ValidateUtils.requireTrue(isNum, ErrorEnum.WRONG_ARGS, "验证码错误");
    	
    	param.setLoginIp(WebUtil.getIpAddr(request));
    	param.setLoginUA(request.getHeader("User-Agent"));
    	LoginCofirmResult confirmResult = null;
		try {
			confirmResult = loginUserService.confirmLoginUser(param);
		} catch (Exception ex) {
			log.error("", ex);
			String msg = ex.getMessage().length() > 25 ? "登录失败, 请稍后重试！" : ex.getMessage();
			throw new BizException(ErrorEnum.SYSTEM_ERROR.getCode(), msg);
		}
    	if(null == confirmResult || null == confirmResult.getLoginUser()) {
    		throw new BizException(ErrorEnum.LOGIN_INVALID.getCode(), "登录已失效, 请重新登录！");
    	}
    	LoginUserDO loginUser = confirmResult.getLoginUser();
    	loginUser.addAttribute(LoginUserDO.ATTR_LOGIN_IP, param.getLoginIp());
    	loginUser.addAttribute(LoginUserDO.ATTR_LOGIN_UA, param.getLoginUA());
    	if(Objects.equals(AdminUserStatus.NO_ACTIVATED.getValue(), loginUser.getStatus())) {
    		throw new BizException(ErrorEnum.LOGIN_INVALID.getCode(), "账号尚未激活, 请联系系统管理员！");
    	}
    	if(Objects.equals(AdminUserStatus.DISABLED.getValue(), loginUser.getStatus())) {
    		throw new BizException(ErrorEnum.LOGIN_FAILED.getCode(), "当前用户已被禁用！");
    	}
    	
    	try {
    		initCurrentLoginUser(loginUser, confirmResult.isRememberMe());
		} catch (Exception e) {
			log.error("", e);
			throw new BizException(ErrorEnum.SYSTEM_ERROR.getCode(), "登录失败, 请稍后重试！");
		}
    	
    	return true;
    }
	
	@GetMapping(value = "/getTokenKey")
	public PairValue<String, String> getTokenKey() {
		String tokenKey = BizUtils.generateToken();
		String crsfToken = crsfToken();
		
		return new PairValue<String, String>(tokenKey, crsfToken);
	}
    
    @GetMapping(value = "/logout")
    public Boolean userLogout() throws Exception {
    	return clearCurrentLoginUser(true);
    }
    
    @GetMapping(value = "/clean")
    public Boolean userCookieLogout() throws Exception {
    	return clearCurrentLoginUser(false);
    }
    
    @GetMapping(value = "/profile")
    public AdminUserView userProfile(HttpServletRequest request) throws Exception {
    	return checkCurrentLoginUser();
    }
    
    @GetMapping(value = "/pingStatus")
	public Entry<Boolean, String> pingStatus() {
		String loginIp = WebUtil.getIpAddr(request);
    	String loginUA = request.getHeader("User-Agent");
		
		AdminUserView adminUser = null;
		try {
			adminUser = this.checkCurrentLoginUser();
		} catch (Exception e) {
			return Entry.create(false, "当前登录状态已经失效！");
		}
		
		if(null != adminUser) {
			if(!Objects.equals(loginIp, adminUser.getLoginIp())) {
				return Entry.create(false, "当前网络环境有变更，为确保安全请重新登录！");
			}
			if(!Objects.equals(loginUA, adminUser.getLoginUA())) {
				return Entry.create(false, "当前账号已在其他终端登录，为确保安全请重新登录！");
			}
			return Entry.create(true, "OK");
		}
		
        return Entry.create(true, "OK");
	}
    

}
