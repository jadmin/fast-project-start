package com.sbt.misc;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.sbt.sso.common.util.Utils;
import com.sbt.sso.web.SSOConfigListener;
import com.sbt.sso.web.filter.SSOFilter;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
//	@Bean
//	public ServletContextInitializer initializer() {
//	    return new ServletContextInitializer() {
//	        @Override
//	        public void onStartup(ServletContext servletContext) throws ServletException {
//	        		// 加载context-param启动参数ssoConfigLocation
//	            servletContext.setInitParameter("ssoConfigLocation", "classpath:sso.properties");
//	        }
//	    };
//	}
//	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Bean
//    public ServletListenerRegistrationBean requestContextListener() {
//        ServletListenerRegistrationBean listener = new ServletListenerRegistrationBean();
//        listener.setListener(new RequestContextListener());
//        return listener;
//    }
//	
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Bean
//    public ServletListenerRegistrationBean ssoConfigListener() {
//        ServletListenerRegistrationBean listener = new ServletListenerRegistrationBean();
//        listener.setListener(new SSOConfigListener());
//        return listener;
//    }
//	
//	@Bean  
//    public FilterRegistrationBean<Filter> characterEncodingFilter() {
//		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();  
//        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
//        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
//        filterRegistrationBean.setFilter(characterEncodingFilter);
//        characterEncodingFilter.setEncoding("UTF-8");  
//        characterEncodingFilter.setForceEncoding(true);
//        filterRegistrationBean.addUrlPatterns("/*");
//        return filterRegistrationBean;  
//    }
//	
//	@Bean  
//    public FilterRegistrationBean filterRegistrationBean() {  
//		SSOFilter filter = new SSOFilter();
//        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();  
//        filterRegistrationBean.setFilter(filter);
//        String devEnableLoginCheckStr = configManager.getSwitchMap().get("env.dev.loginCheck.enable");
//        boolean isNeedLoginCheck = !envUtil.isDev() 
//        								|| (envUtil.isDev() && Utils.equals("true", devEnableLoginCheckStr));
//        if(isNeedLoginCheck) {
//    			filterRegistrationBean.setEnabled(true);
//	        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
//	        
//	        filterRegistrationBean.addInitParameter("over.url", 
//						"/js/*;/css/*;/images/*;/fonts/*;/static/*;"
//						+ "/logout.do;/internal/*;"
//						+ "/oklogin;/timeout.html;/proxylogin.html;"
//	        	);
//	        filterRegistrationBean.addInitParameter("nologin.echoJsonUrls", "/msgbox/*");
//			
//	        filterRegistrationBean.addUrlPatterns("/msg/*");
//	        filterRegistrationBean.addUrlPatterns("/admin/*");
//	        filterRegistrationBean.addUrlPatterns("/msgbox/*");
//			filterRegistrationBean.addUrlPatterns("/template/*");
//			filterRegistrationBean.addUrlPatterns("/tool/*");
//			filterRegistrationBean.addUrlPatterns("/test/*");
//			filterRegistrationBean.addUrlPatterns("/switch/*");
//			filterRegistrationBean.addUrlPatterns("/log/*");
//        } else {
//        		filterRegistrationBean.setEnabled(false);
//        }
//        return filterRegistrationBean;  
//    }

}
