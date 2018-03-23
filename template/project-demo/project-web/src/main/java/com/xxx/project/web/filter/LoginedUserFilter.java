///*
// * @(#)LoginedUserFilter.java	2017年7月31日
// *
// * Copyright (c) 2017. All Rights Reserved.
// *
// */
//
//package #{packagePrefix}#.web.filter;
//
//import java.io.IOException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.github.javaclub.sword.cache.CacheManager;
//import com.github.javaclub.sword.core.Strings;
//import com.github.javaclub.sword.spring.SpringContextUtil;
//import com.shandiangou.member.session.SdgSessionUtil;
//import #{packagePrefix}#.domain.enumtype.TmqTopic;
//import #{packagePrefix}#.mq.TmqPublisher;
//
///**
// * LoginedUserFilter 已登录的用户但是没有映射关系(ka_user_mapping)
// *
// * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
// * @version $Id: LoginedUserFilter.java 2017年7月31日 11:29:59 Exp $
// */
//public class LoginedUserFilter implements Filter {
//	
//	static Logger log = LoggerFactory.getLogger(LoginedUserFilter.class);
//	
//	protected ServletContext servletContext;
//	protected FilterConfig filterConfig;
//	
//	protected static TmqPublisher tmqPublisher;
//	protected static CacheManager<String, Object> kAMemberCacheManager;
//
//	@Override
//	public void init(FilterConfig filterConfig) throws ServletException {
//		this.filterConfig = filterConfig;
//        this.servletContext = filterConfig.getServletContext();
//        this.initApi();
//	}
//
//	@Override
//	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//			throws IOException, ServletException {
//		this.initApi();
//		if(SdgSessionUtil.isLogin()) {
//			Long userId = SdgSessionUtil.getUserId();
//			try {
//				// TODO：do something
//			} catch (Exception e) {
//				log.error("LoginedUserFilter.bindKaUser", e);
//			}
//			if(null != userId && userId > 0) {
//				String key = Strings.concat("ulogin_", userId);
//				Object o = kAMemberCacheManager.get(key);
//				if(null == o) {
//					String msgBody = Strings.createMapJson("userAccountId", userId).getJsonObject().toJSONString();
//					tmqPublisher.sendStringMessage(TmqTopic.KA_USER_LOGINED.getTopic(), 
//													TmqTopic.KA_USER_LOGINED.getSubTopic(), 
//													msgBody);
//					kAMemberCacheManager.put(key, System.currentTimeMillis(), 30*60L); // 30分钟内不再重复发送"用户已登录"
//				}
//			}
//		}
//		chain.doFilter(request, response); // 此句放在最后不可少
//	}
//
//	public void setServletContext(ServletContext servletContext) {
//		this.servletContext = servletContext;
//	}
//
//	@Override
//	public void destroy() {
//
//	}
//	
//	void initApi() {
//		if(null == tmqPublisher) {
//			tmqPublisher = SpringContextUtil.getBean(TmqPublisher.class);
//		}
//		if(null == kAMemberCacheManager) {
//			kAMemberCacheManager = SpringContextUtil.getBean("kAMemberCacheManager");
//		}
//	}
//
//}
