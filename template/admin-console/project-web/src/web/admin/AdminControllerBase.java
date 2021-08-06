/*
 * @(#)AdminControllerBase.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.web.admin;

import static com.github.javaclub.sword.core.Strings.isNotBlank;
import static ${packagePrefix}.common.AppConstants.RedisKeys.formatKey;
import static ${packagePrefix}.common.constants.RedisKeyConstants.ADMIN_USER;
import static ${packagePrefix}.common.constants.RedisKeyConstants.ADMIN_USER_CRSF_TOKEN;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.javaclub.sword.BizException;
import com.github.javaclub.sword.algorithm.crypt.MD5;
import com.github.javaclub.sword.core.BizObjects;
import com.github.javaclub.sword.core.Collections;
import com.github.javaclub.sword.core.Entry;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.util.UuidUtil;
import com.google.common.collect.Lists;
import ${packagePrefix}.common.AppConstants.UserRoles;
import ${packagePrefix}.common.enums.ErrorEnum;
import ${packagePrefix}.common.utils.LoginUserHolder;
import ${packagePrefix}.common.utils.ValidateUtils;
import ${packagePrefix}.convert.UserConvertor;
import ${packagePrefix}.dataobject.BwlEnumDO;
import ${packagePrefix}.dataobject.LoginUserDO;
import ${packagePrefix}.dataobject.RoleDO;
import ${packagePrefix}.query.BwlEnumQuery;
import ${packagePrefix}.service.BwlEnumService;
import ${packagePrefix}.service.MenuModuleService;
import ${packagePrefix}.service.ResourceService;
import ${packagePrefix}.service.RoleService;
import ${packagePrefix}.service.utils.BizUtils;
import ${packagePrefix}.support.RedisCacheManager;
import ${packagePrefix}.view.AdminUserView;
import ${packagePrefix}.web.ApiBaseController;

import lombok.extern.slf4j.Slf4j;

/**
 * AdminControllerBase
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: AdminControllerBase.java ${currentTime} Exp $
 */
@Slf4j
public class AdminControllerBase extends ApiBaseController {
	
	@Autowired
	protected RedisCacheManager redisCacheManager;
	
	@Autowired
	protected BwlEnumService bwlEnumService;
	
	@Autowired
	protected MenuModuleService menuModuleService;
	
	@Autowired
	protected RoleService roleService;
	
	@Autowired
	protected ResourceService resourceService;
	
	protected String crsfToken() {
		String token = UuidUtil.randomUUID();
		String key = formatKey(ADMIN_USER_CRSF_TOKEN, token);
		redisTemplate.opsForValue().set(key, token, 10L, TimeUnit.MINUTES);
		return token;
	}
	
	protected void initCurrentLoginUser(LoginUserDO loginUser, boolean rememberMe) {
		int expre = 1 * 60 * 60;
		if (rememberMe) {
			expre = 48 * 60 * 60;
		}
		String timestamp = String.valueOf(System.currentTimeMillis());
		Cookie lgt = new Cookie("_tid", timestamp);
		lgt.setPath("/");
		lgt.setMaxAge(expre);
		response.addCookie(lgt);

		Cookie tk = new Cookie("_tk", MD5.getMd5(timestamp + BizUtils.AUTH_SALT));
		tk.setPath("/");
		tk.setMaxAge(expre);
		response.addCookie(tk);
		
		Cookie ckCookie = new Cookie("_ck", systemConfigs.getCookieKey());
		ckCookie.setPath("/");
		ckCookie.setMaxAge(expre);
		response.addCookie(ckCookie);

		Cookie useridCookie = new Cookie("_uid", String.valueOf(loginUser.getId()));
		useridCookie.setPath("/");
		useridCookie.setMaxAge(expre);
		response.addCookie(useridCookie);
		
		AdminUserView adminUser = UserConvertor.INSTANCE.build(loginUser);
		String userKey = formatKey(ADMIN_USER, loginUser.getId());
		redisTemplate.opsForValue().set(userKey, adminUser, Long.valueOf(expre + 10), TimeUnit.SECONDS);

		// 清理当前登录提交的crsfToken
		String reqToken = request.getHeader("token");
		String key = formatKey(ADMIN_USER_CRSF_TOKEN, reqToken);
		redisTemplate.delete(key);
	}

	protected boolean clearCurrentLoginUser(boolean forceRemove) {
		try {
			AdminUserView adminUser = (AdminUserView) LoginUserHolder.get();
			if(null != adminUser && forceRemove) {
				Long userId = adminUser.getUserId();
				String userKey = formatKey(ADMIN_USER, userId);
				redisTemplate.delete(userKey);
			}
			
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (int i = 0; i < cookies.length; i++) {
					if(BizObjects.contains(new String[] {"_tid", "_tk", "_ck", "_uid"}, cookies[i].getName())) {
						BizUtils.clearCookie(cookies[i], response, "/");
					}
				}
			}
			
			return true;
			
		} catch (Exception e) {
			log.error("", e);
		}
		
		return false;
	}
	
	protected AdminUserView checkCurrentLoginUser() {
		String tid = "", tk = "", uid = "", ck = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if ("_tid".equals(cookies[i].getName())) {
					tid = cookies[i].getValue();
				}
				if ("_tk".equals(cookies[i].getName())) {
					tk = cookies[i].getValue();
				}
				if ("_ck".equals(cookies[i].getName())) {
					ck = cookies[i].getValue();
				}
				if ("_uid".equals(cookies[i].getName())) {
					uid = cookies[i].getValue();
				}
			}
		}
		ValidateUtils.requireTrue(isNotBlank(tid) && isNotBlank(tk) && isNotBlank(uid), ErrorEnum.LOGIN_INVALID, "登录已失效, 请登录后重试！");
		ValidateUtils.requireTrue(Long.valueOf(tid) < System.currentTimeMillis(), ErrorEnum.LOGIN_INVALID, "登录状态异常, 请登录后重试！");
		
		boolean timestampCheck = Objects.equals(tk, MD5.getMd5(tid + BizUtils.AUTH_SALT));
		ValidateUtils.requireTrue(timestampCheck, ErrorEnum.LOGIN_INVALID, "登录状态异常, 请登录后重试！");
		
		boolean envCheck = Objects.equals(ck, systemConfigs.getCookieKey());
		ValidateUtils.requireTrue(envCheck, ErrorEnum.LOGIN_INVALID, "登录状态异常, 请登录后重试！");
		
		Long userId = Long.valueOf(uid);
		String userKey = formatKey(ADMIN_USER, userId);
		AdminUserView adminUser = (AdminUserView) redisTemplate.opsForValue().get(userKey);
		ValidateUtils.requireTrue(null != adminUser, ErrorEnum.LOGIN_INVALID, "登录已失效, 请登录后重试！");
		
		return adminUser;
	}
	
	protected AdminUserView getCurrentLoginUser() throws Exception {
		
		AdminUserView user = null;
		try {
			user = checkCurrentLoginUser();
		} catch (BizException e) { // 跳到登录页
			String url = Strings.concat(request.getContextPath(), "/console/login");
			response.sendRedirect(url);
		}
		
		return user;
	}
	
	protected boolean isSuperAdmin() {
		return hasAuthRole(UserRoles.SUPER_ADMIN);
	}
	
	protected boolean hasAuthRole(String roleCode) {
		if (Strings.isBlank(roleCode)) {
			return false;
		}
		try {
			AdminUserView adminUser = getCurrentLoginUser();
			if(null != adminUser) {
				List<RoleDO> list = roleService.findUserRoleList(adminUser.getUserId());
				if(Collections.isNotEmpty(list)) {
					List<RoleDO> filterList = list.stream().filter(a -> Objects.equals(roleCode, a.getRoleCode())).collect(Collectors.toList());
					return Collections.isNotEmpty(filterList);
				}
			}
		} catch (Exception e) {
		}
		
		return false;
	}
	
	protected Long getLoginUserId() throws Exception {
		AdminUserView adminUser = getCurrentLoginUser();
		return null == adminUser ? null : adminUser.getUserId();
	}
	
	protected String getLoginUsername() throws Exception {
		AdminUserView adminUser = getCurrentLoginUser();
		return null == adminUser ? null : adminUser.getName();
	}
	
	protected List<Entry<String, String>> buildBizEnums() {
        List<Entry<String, String>> groupList = Lists.newArrayList();
        BwlEnumQuery query = new BwlEnumQuery();
        query.setPageSize(1);
        query.setPageSize(500);
        try {
            List<BwlEnumDO> list = bwlEnumService.findList(query);
            if(BizObjects.length(list) > 0) {
                for (BwlEnumDO bizEnumDO : list) {
                    groupList.add(Entry.create(bizEnumDO.getBizCode(), bizEnumDO.getBizDesc()));
                }
            }
        } catch (Exception e) {
            log.error("查询名单类型列表出错", e);
        }
        return groupList;
    }
}
