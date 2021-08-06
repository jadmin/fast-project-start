package ${packagePrefix}.interceptor.admin;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.util.WebUtil;
import com.github.javaclub.sword.web.HttpResult;
import ${packagePrefix}.common.utils.LoginUserHolder;
import ${packagePrefix}.config.BaseConfig;
import ${packagePrefix}.view.AdminUserView;


@Component
public class PermissionInterceptor extends BaseConfig implements HandlerInterceptor {
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	// head请求直接返回
		if (request.getMethod().equals("HEAD")) {
			return false;
		}
		boolean isAjax = WebUtil.isAjaxRequest(request);
		
		AdminUserView adminUser = (AdminUserView) LoginUserHolder.get();
		if (null == adminUser) {
			redirectLoginUrl(request, response);
			return false;
		}
		String currentRequestPath = request.getServletPath();
		Long userId = adminUser.getUserId();
		List<String> uriList = getUserAuthorizedUris(userId);
		boolean hasPermit = WebUtil.matchUrl(uriList, currentRequestPath);
		if (hasPermit) {
			return true;
		}
		
		if (isAjax) {
			HttpResult<Boolean> echo = HttpResult.failure("Unauthorized Access");
			WebUtil.response(response, echo, true, null, "UTF-8");
			return false;
		}
		
		// 页面访问
		request.setAttribute("errorMsg", "请确认当前访问资源是否存在，是否已经获得对应权限！");
		request.getRequestDispatcher("/error").forward(request, response); 
		
        return false;
    }

	
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
    
    protected void redirectLoginUrl(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	String url = Strings.concat(request.getContextPath(), "/console/login");
		response.sendRedirect(url);
	}

}
