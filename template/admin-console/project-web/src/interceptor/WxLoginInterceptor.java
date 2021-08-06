package ${packagePrefix}.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.github.javaclub.sword.BizException;
import com.github.javaclub.sword.core.Strings;
import ${packagePrefix}.common.enums.ErrorEnum;
import ${packagePrefix}.common.utils.LoginUserHolder;

import lombok.extern.slf4j.Slf4j;


/**
 * 小程序登录鉴权拦截
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: WxLoginInterceptor.java ${currentTime} Exp $
 */
@Component
@Slf4j
public class WxLoginInterceptor implements HandlerInterceptor {
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    	// head请求直接返回
		if (request.getMethod().equals("HEAD")) {
			return false;
		}
		
//		User wxUser = this.checkCurrentLoginUser(request);
//		if(null != wxUser) {
//			LoginUserHolder.set(wxUser);
//		}
		
        return true;
    }
    
    @Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
    	LoginUserHolder.remove();
	}

//	protected User checkCurrentLoginUser(HttpServletRequest request) throws Exception {
//    	String token = request.getHeader("token");
//    	if(Strings.isBlank(token)) {
//    		throw new BizException(ErrorEnum.LOGIN_INVALID.getCode(), "请登录后再试！");
//    	}
//    	
//    	LoginTokenModel tkModel = DomainUtils.checkWxLoginToken(token);
//        Long wxUserId = tkModel.getWxUserId();
//    	
//        String tokenUserKey = RedisKeys.formatKey(RedisKeys.WX_MP_TOEKN_USER, wxUserId);
//    	String wxUserJSON = redisTemplate.opsForValue().get(tokenUserKey);
//    	if(Strings.isBlank(wxUserJSON)) {
//    		throw new BizException(ErrorEnum.LOGIN_INVALID.getCode(), "登录已失效, 请登录后重试！");
//    	}
//    	
//    	User user = JSONObject.parseObject(wxUserJSON, User.class);
//    	ValidateUtils.requireTrue(null != user && null != user.getId() && 0 < user.getId(), ErrorEnum.LOGIN_INVALID, "登录状态异常, 请重新登录！");
//		
//		return user;
//	}

}
