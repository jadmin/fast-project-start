//package #{packagePrefix}#.web.filter;
//
//import java.util.List;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.lang.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.google.common.base.Splitter;
//import com.shandiangou.AppConstants.Member;
//import com.github.javaclub.sword.config.ConfigFactory;
//import com.github.javaclub.sword.core.Strings;
//import com.github.javaclub.sword.spring.SpringContextUtil;
//import com.github.javaclub.sword.util.WebUtil;
//import com.shandiangou.member.session.SdgServletResponse;
//import com.shandiangou.member.session.SdgSession;
//import com.shandiangou.member.session.SdgSessionFilter;
//
///**
// * AppSessionFilter
// *
// * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
// * @version $Id: AppSessionFilter.java 2017年6月28日 9:33:11 Exp $
// */
//public class AppSessionFilter extends SdgSessionFilter {
//	
//	static final Logger log = LoggerFactory.getLogger(AppSessionFilter.class);
//	
//	static final ConcurrentHashMap<String, Pattern> loginCheckPatternMap = new ConcurrentHashMap<>();
//	
//	protected void sendNeedLoginResponse(SdgSession sdgSession) {
//
//		HttpServletRequest request = sdgSession.getRequest();
//		
//		String serletPath = request.getServletPath();
//		String queryString = request.getQueryString();
//		String path = request.getRequestURL().toString();
//		if (StringUtils.isNotBlank(queryString)) {
//			path += "?" + queryString;
//		}
//
//		// 根据content type 判断返回类型
//		String contentType = request.getHeader("Content-Type");
//		boolean flag = false;
//		if (StringUtils.isNotBlank(contentType)) {
//			List<String> values = Splitter.on(";").trimResults().splitToList(contentType);
//			if ("application/json".equals(values.get(0)) || "application/jsonp".equals(values.get(0))) {
//				flag = true;
//			}
//		}
//
//		// json请求
//		boolean jsonFlag = "XMLHttpRequest".equalsIgnoreCase(request.getHeader("x-requested-with"));
//		
//		ConfigFactory kaConfigFactory = SpringContextUtil.getBean("kaConfigFactory");
//		// 如果未登陆是否需要JSON提示信息
//		String needJsonEchoUrl = kaConfigFactory.get("nologin.need.json.urls");
//		boolean isSetNeedJson = isSetNeedJson(serletPath, needJsonEchoUrl);
//
//		if (flag || jsonFlag || isSetNeedJson) { // 未登录给出json提示信息
//			super.sendNeedLoginResponse(sdgSession);
//			return;
//		}
//
//		SdgServletResponse response = (SdgServletResponse) sdgSession.getResponse();
//		String url = this.getLoginUrl(request) + "?redirectUrl=" + path;
//
//		try {
//			response.sendRedirect(url);
//			response.commit();
//		} catch (Exception e) {
//			log.error("AppSessionFilter", e);
//		}
//	}
//	
//	boolean isSetNeedJson(String resource, String setting) {
//		List<String> loginCheckList = Splitter.on(";").trimResults().splitToList(setting);
//        if (loginCheckList != null && loginCheckList.contains(resource)) {
//            return true;
//        }
//        if (loginCheckList != null) {
//            Pattern patternUrl = null;
//            Matcher matcher = null;
//
//            for (String strUrl : loginCheckList) {
//                if (!strUrl.contains("*")) {
//                    continue;
//                }
//                patternUrl = loginCheckPatternMap.get(strUrl);
//
//                if (patternUrl == null) {
//                    patternUrl = Pattern.compile(strUrl);
//                    loginCheckPatternMap.putIfAbsent(strUrl, patternUrl);
//                }
//
//                matcher = patternUrl.matcher(resource);
//                if (matcher.find()) {
//                    return true;
//                }
//            }
//        }
//        
//        return false;
//    }
//	
//	String getLoginUrl(HttpServletRequest request) {
//		String host = WebUtil.getRequestHost(request);
//		String context = Member.LOGIN_CONTEXT;
//		if(host.contains("51xianqu")) {
//			context = Member.CRM_LOGIN;
//		} else if(host.contains("52shangou")) {
//			context = Member.KAWEB_LOGIN;
//		}
//		return Strings.concat(host, context);
//	}
//	
//	String[] getBlankUrl() {
//		return new String[] {
//			"api/points/get.do",
//			"api/points/list.do",
//		};
//	}
//
//}
