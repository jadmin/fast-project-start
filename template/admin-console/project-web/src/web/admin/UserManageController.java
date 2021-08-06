/*
 * @(#)UserManageController.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.web.admin;

import static ${packagePrefix}.common.AppConstants.RedisKeys.formatKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.javaclub.sword.BizException;
import com.github.javaclub.sword.algorithm.crypt.Base64;
import com.github.javaclub.sword.algorithm.crypt.Blowfish;
import com.github.javaclub.sword.core.BizObjects;
import com.github.javaclub.sword.core.Collections;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.domain.QueryResult;
import com.github.javaclub.sword.domain.ResultDO;
import com.github.javaclub.sword.domain.SimplePageQuery;
import com.github.javaclub.sword.spring.BeanCopierUtils;
import com.github.javaclub.sword.util.JSONStringUtil;
import com.github.javaclub.sword.util.WebUtil;
import com.github.javaclub.sword.web.HttpResult;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import ${packagePrefix}.common.constants.RedisKeyConstants;
import ${packagePrefix}.common.enums.AdminUserStatus;
import ${packagePrefix}.common.enums.ErrorEnum;
import ${packagePrefix}.common.enums.SecondVerifyType;
import ${packagePrefix}.convert.UserConvertor;
import ${packagePrefix}.dataobject.LoginUserDO;
import ${packagePrefix}.param.LoginUserUpdatePasswordParam;
import ${packagePrefix}.query.LoginUserQuery;
import ${packagePrefix}.service.LoginUserService;
import ${packagePrefix}.service.params.LoginUserUpdateParam;
import ${packagePrefix}.service.utils.BizUtils;
import ${packagePrefix}.service.utils.GoogleAuthenticatorUtils;
import ${packagePrefix}.view.AdminUserVO;
import ${packagePrefix}.view.AdminUserView;
import ${packagePrefix}.view.ProfileDetailVO;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * UserManageController
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: UserManageController.java ${currentTime} Exp $
 */
@RestController
@RequestMapping("/admin/usermanage")
@ApiIgnore
@Slf4j
public class UserManageController extends AdminControllerBase {
	
	@Autowired
	private LoginUserService loginUserService;
	
	@GetMapping("/onlineUserList")
	public Map<String, Object> onlineUserList(HttpServletRequest request, 
												Model model) throws Exception {
		Map<String, Object> map = Maps.newHashMap();
		LoginUserQuery query = WebUtil.parseObject(request, LoginUserQuery.class);
		QueryResult<AdminUserView> qr = redisCacheManager.scanMgetByPage(RedisKeyConstants.getAdminUserKeyPrefix() + "*", 
																5000, 
																new SimplePageQuery(query.getPageNo(), query.getPageSize()));
		if (null == qr || Collections.isEmpty(qr.getEntry())) {
			map.put("total", 0);
			map.put("rows", Lists.newArrayList());
			return map;
		}
		
		List<AdminUserVO> list = BeanCopierUtils.copyListProperties(qr.getEntry(), AdminUserVO.class);
		
        map.put("rows", list);
        map.put("total", qr.getTotalCount());
        
        return map;
	}
	
	@GetMapping("/userList")
	public Map<String, Object> userTableData(HttpServletRequest request, 
												Model model) throws Exception {
		LoginUserQuery query = WebUtil.parseObject(request, LoginUserQuery.class);
        int count = loginUserService.count(query);
        List<LoginUserDO> list = loginUserService.findList(query);
        Map<String, Object> map = Maps.newHashMap();
        map.put("rows", null == list ? new ArrayList<LoginUserDO>() : list);
        map.put("total", count);
        return map;
	}
	
	@PostMapping("/saveRequest")
    public HttpResult<Boolean> save(HttpServletRequest request) throws Exception {
		LoginUserDO bwd = WebUtil.parseObject(request, LoginUserDO.class);
        try {
            BizObjects.requireNotEmpty(bwd.getUsername(), "用户名不能为空");
            if (null == bwd.getId() || 0 >= bwd.getId()) {
            	BizObjects.requireNotEmpty(bwd.getPassword(), "密码不能为空");
            }
            BizObjects.requireNotNull(bwd.getName(), "姓名不能为空");
            BizObjects.requireNotNull(bwd.getMobile(), "手机号不能为空");
            BizObjects.requireNotNull(bwd.getEmail(), "邮件地址不能为空");
        } catch (BizException e) {
            return HttpResult.failure(e.getMessage());
        }
        bwd.setPassword(BizUtils.generatePasswordMD5(bwd.getPassword()));
        if (null != bwd.getId() && 0 < bwd.getId()) {
        	LoginUserDO dbuser = loginUserService.selectById(bwd.getId());
        	bwd.setPassword(null);
        	Map<String, Object> map = dbuser.getAttributesMap();
        	if(null != bwd.getAttributesMap()) {
        		map.putAll(bwd.getAttributesMap());
        	}
        	bwd.setAttributes(JSONStringUtil.toJSONString(map));
        }
        ResultDO<Boolean> r = loginUserService.save(bwd);
        if(!r.isSuccess()) {
        	return HttpResult.failure(r.getMessage());
        }
        return HttpResult.success(r.getEntry());
    }

    
    @PostMapping("/doDisable")
    public HttpResult<Boolean> doDisable(Long id) throws Exception {
    	LoginUserDO user = null;
    	try {
            BizObjects.requireNotNull(id, "id不能为空");
            user = loginUserService.selectById(id);
            BizObjects.requireNotNull(user, "当前请求不合法");
            user.setStatus(AdminUserStatus.DISABLED.getValue());
            loginUserService.update(user);
        } catch (BizException e) {
            return HttpResult.failure(e.getMessage());
        }
    	
    	return HttpResult.success(true);
    }
    
    @PostMapping("/doEnable")
    public HttpResult<Boolean> doEnable(Long id) throws Exception {
    	LoginUserDO user = null;
    	try {
            BizObjects.requireNotNull(id, "id不能为空");
            user = loginUserService.selectById(id);
            BizObjects.requireNotNull(user, "当前请求不合法");
            user.setStatus(AdminUserStatus.NORMAL.getValue());
            loginUserService.update(user);
        } catch (BizException e) {
            return HttpResult.failure(e.getMessage());
        }
    	
    	return HttpResult.success(true);
    }
    
    @PostMapping("/doOffline")
    public HttpResult<Boolean> doOffline(Long id) throws Exception {
    	LoginUserDO user = null;
    	try {
            BizObjects.requireNotNull(id, "id不能为空");
            user = loginUserService.selectById(id);
            BizObjects.requireNotNull(user, "当前请求不合法");
        } catch (BizException e) {
            return HttpResult.failure(e.getMessage());
        }
    	
    	Long userId = Long.valueOf(id);
        String userKey = formatKey(RedisKeyConstants.ADMIN_USER, userId);
        redisTemplate.delete(userKey);
        
        return HttpResult.success(true);
    }
    
    @GetMapping("/queryUser")
    public HttpResult<ProfileDetailVO> queryUser(String q) throws Exception {
    	LoginUserDO user = null;
    	try {
            BizObjects.requireNotEmpty(q, "查询关键字不能为空");
            String val = q.trim();
            if(Strings.isNumeric(q)) {
            	user = loginUserService.selectById(Long.valueOf(val));
            }
            if(null == user) {
            	user = loginUserService.matchUserByQueryKey(val);
            }
            
        } catch (BizException e) {
            return HttpResult.failure(e.getMessage());
        }
    	
    	if(null == user) {
    		return HttpResult.failure("无法匹配相关用户信息");
    	}
    	
    	ProfileDetailVO profileDetailVO = UserConvertor.INSTANCE.convertToProfileDetailVO(user);
    	return HttpResult.success(profileDetailVO);
    }
    
    @GetMapping(value = "/currentUser/detail")
    public HttpResult<ProfileDetailVO> getCurrentUserProfileDetail() throws Exception {
        AdminUserView currentLoginUser = getCurrentLoginUser();
        LoginUserDO userDO = loginUserService.selectById(currentLoginUser.getUserId());
        ProfileDetailVO profileDetailVO = UserConvertor.INSTANCE.convertToProfileDetailVO(userDO);
        if (!Strings.isNullOrEmpty(userDO.getAuthKey())) {
        	String actuallyGoogleKey = Blowfish.decrypt(userDO.getAuthKey());
            profileDetailVO.setAuthKey(actuallyGoogleKey);
            String env = parseEnvForGoogleAuth();
            String qrcodeUrl = GoogleAuthenticatorUtils.formatQrcodeUrl(env, userDO.getUsername(), actuallyGoogleKey);
            profileDetailVO.setQrcodeUrl(qrcodeUrl);
        }
        return HttpResult.success(profileDetailVO);
    }
    
    @GetMapping(value = "/currentUser/getUserProfileQrcode")
    public HttpResult<String> getUserProfileQrcode(String qrcodeUrl) throws Exception {
    	String imageBase64 = "";
        try {
			imageBase64 = WebUtil.fetchUrlImageAsBase64(qrcodeUrl);
		} catch (Exception e) {
		}
        return HttpResult.success(imageBase64);
    }

    @PostMapping(value = "/currentUser/update")
    public HttpResult<Boolean> updateCurrentUserProfile(@RequestBody LoginUserUpdateParam loginUserUpdateParam) throws Exception {
        AdminUserView currentLoginUser = getCurrentLoginUser();
        loginUserUpdateParam.setId(currentLoginUser.getUserId());
        ResultDO<Boolean> rd = loginUserService.updateUserProfileDetail(loginUserUpdateParam);
        if(!rd.isSuccess()) {
        	return HttpResult.failure(rd.getCode(), rd.getMessage());
        }
        return HttpResult.success();
    }

    @GetMapping("/currentUser/generateUserAuthKey")
    public HttpResult<Map<String, Object>> generateUserAuthKey(String username, Boolean open) throws Exception {
        Map<String, Object> map = com.google.common.collect.Maps.newHashMap();
        LoginUserDO user = loginUserService.getByUsername(username);
        if (null != user) {
            String secretKey = user.getAuthKey();
            if (Strings.isBlank(secretKey)) {
                secretKey = Blowfish.encrypt(GoogleAuthenticatorUtils.generateSecretKey());
                user.setAuthKey(secretKey);
            }
            String googleAuthKey = Blowfish.decrypt(secretKey);
            user.addAttribute(LoginUserDO.ATTR_OPEN_SECOND_VERIFY, open);
            if (open) {
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

        return HttpResult.success(map);
    }

    @PostMapping(value = "/currentUser/updatePassword")
    public HttpResult<Boolean> updatePassword(@RequestBody @Validated LoginUserUpdatePasswordParam reqParam) throws Exception {
        String oldPassword = reqParam.getOldPassword();
        String password = reqParam.getPassword();
        String confirmPassword = reqParam.getConfirmPassword();
        if (Strings.isNullOrEmpty(oldPassword) || Strings.isNullOrEmpty(password) || Strings.isNullOrEmpty(confirmPassword)) {
            return HttpResult.failure(ErrorEnum.PARAM_ERROR.getCode(), "旧密码、新密码和确认密码均不能为空");
        }
        if (!Objects.equals(password, confirmPassword)) {
            return HttpResult.failure(ErrorEnum.PARAM_ERROR.getCode(), "确认密码不一致");
        }
        AdminUserView currentLoginUser = getCurrentLoginUser();
        // 查询原密码是否正确
        LoginUserDO loginUserDO = loginUserService.checkLoginUser(currentLoginUser.getUsername(), Base64.decode(oldPassword));
        if (Objects.isNull(loginUserDO)) {
            log.warn("Old password is not correct by loginUserUpdatePasswordParam: {}", reqParam);
            return HttpResult.failure(ErrorEnum.OLD_PASSWORD_NOT_CORRECT.getCode(), ErrorEnum.OLD_PASSWORD_NOT_CORRECT.getMsg());
        }
        String entryPassword = Base64.decode(password);
        LoginUserDO userDO = new LoginUserDO();
        userDO.setPassword(BizUtils.generatePasswordMD5(entryPassword));
        userDO.setId(currentLoginUser.getUserId());
        boolean updateR = loginUserService.resetPassword(userDO);
        if (!updateR) {
            return HttpResult.failure(ErrorEnum.USER_PASSWORD_UPDATE_FAIL.getCode(), ErrorEnum.USER_PASSWORD_UPDATE_FAIL.getMsg());
        }
        return HttpResult.success();
    }

}
