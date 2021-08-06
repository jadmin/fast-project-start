package ${packagePrefix}.interceptor.admin;


import static com.github.javaclub.sword.core.Strings.isNotBlank;
import static ${packagePrefix}.common.AppConstants.RedisKeys.formatKey;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.github.javaclub.sword.algorithm.crypt.MD5;
import com.github.javaclub.sword.core.BizObjects;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.util.WebUtil;
import com.google.common.collect.Lists;
import ${packagePrefix}.common.constants.RedisKeyConstants;
import ${packagePrefix}.common.enums.ErrorEnum;
import ${packagePrefix}.common.utils.LoginUserHolder;
import ${packagePrefix}.common.utils.ValidateUtils;
import ${packagePrefix}.config.BaseConfig;
import ${packagePrefix}.dataobject.MenuModuleDO;
import ${packagePrefix}.param.MenuTreeNode;
import ${packagePrefix}.service.MenuModuleService;
import ${packagePrefix}.service.utils.BizUtils;
import ${packagePrefix}.utils.TreeNodeUtil;
import ${packagePrefix}.view.AdminUserView;


@Component
public class LoginInterceptor extends BaseConfig implements HandlerInterceptor {
	
	@Autowired
	private MenuModuleService menuModuleService;
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	// head请求直接返回
		if (request.getMethod().equals("HEAD")) {
			return false;
		}
		boolean isAjax = WebUtil.isAjaxRequest(request);
		String loginIp = WebUtil.getIpAddr(request);
    	String loginUA = request.getHeader("User-Agent");
		
		AdminUserView adminUser = (AdminUserView) LoginUserHolder.get();
		if (null != adminUser) {
			return safetyCheck(request, response, adminUser, loginIp, loginUA, isAjax);
		}
		
		try {
			adminUser = this.checkCurrentLoginUser(request);
		} catch (Exception e) {
			if(!isAjax)  {
				redirectLoginUrl(request, response);
			}
			throw e;
		}
		
		if(null != adminUser) {
			LoginUserHolder.set(adminUser);
			return safetyCheck(request, response, adminUser, loginIp, loginUA, isAjax);
		}
		
        return false;
    }

	
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    	if(null == modelAndView) {
    		return;
    	}
    	AdminUserView adminUser = (AdminUserView) LoginUserHolder.get();
    	modelAndView.addObject("isLogined", null != adminUser);
    	if(null != adminUser) {
    		modelAndView.addObject("adminUser", adminUser);
    		modelAndView.addObject("userid", adminUser.getUserId());
    		modelAndView.addObject("username", adminUser.getUsername());
    		modelAndView.addObject("name", adminUser.getName());
    		modelAndView.addObject("mobile", adminUser.getMobile());
    		modelAndView.addObject("email", adminUser.getEmail());
    		
    		List<MenuModuleDO> menus = menuModuleService.findUserMenu(adminUser.getUserId());
    		if(BizObjects.length(menus) > 0) {
    			List<MenuTreeNode> list = Lists.newArrayList();
    			for (MenuModuleDO menu : menus) {
    				list.add(new MenuTreeNode(menu));
				}
    			List<MenuTreeNode> graph = TreeNodeUtil.assembleTree(list);
    			modelAndView.addObject("menus", graph);
    		} else {
    			modelAndView.addObject("menus", Lists.newArrayList());
    		}
    	}
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    	LoginUserHolder.remove();
    }
    
    protected void redirectLoginUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String url = Strings.concat(request.getContextPath(), "/console/login");
		response.sendRedirect(url);
	}
    
    protected boolean safetyCheck(HttpServletRequest request, HttpServletResponse response, AdminUserView adminUser,
			String loginIp, String loginUA, boolean isAjax) throws Exception {
    	if (null == adminUser) {
    		return false;
    	}
		if(!Objects.equals(loginIp, adminUser.getLoginIp()) && !isAjax) {
			redirectLoginUrl(request, response);
			return false;
		}
		if(!Objects.equals(loginUA, adminUser.getLoginUA()) && !isAjax) {
			redirectLoginUrl(request, response);
			return false;
		}
		return true;
	}
    
    protected AdminUserView checkCurrentLoginUser(HttpServletRequest request) {
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
		String userKey = formatKey(RedisKeyConstants.ADMIN_USER, userId);
		AdminUserView adminUser = (AdminUserView) redisTemplate.opsForValue().get(userKey);
		ValidateUtils.requireTrue(null != adminUser, ErrorEnum.LOGIN_INVALID, "登录已失效, 请登录后重试！");
		
		return adminUser;
	}

}
