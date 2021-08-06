package ${packagePrefix}.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.github.javaclub.monitor.util.SmsUtils;
import ${packagePrefix}.interceptor.admin.GlobalInterceptor;
import ${packagePrefix}.interceptor.admin.LoginInterceptor;
import ${packagePrefix}.interceptor.admin.PermissionInterceptor;

@Configuration
@ComponentScan(basePackages = { "com.github.javaclub" })
public class ApplicationConfiguration extends BaseConfig implements WebMvcConfigurer {
	
	@Autowired
	private GlobalInterceptor globalInterceptor;
	
	@Autowired
	private LoginInterceptor loginInterceptor;
	
	@Autowired
	private PermissionInterceptor permissionInterceptor;
	
	@PostConstruct
	public void init() {
		String smsApiEndpoint = ""; // TODO 设置短信发送http接口 URL
		SmsUtils.setInvokeURL(smsApiEndpoint); 
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/**").addResourceLocations("classpath:/static/");
		registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
		registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
        // 管理后台
        registry.addInterceptor(globalInterceptor)
        		.addPathPatterns("/error")
        		.addPathPatterns("/console/**");
        
        String[] noLoginUris = urisAccessWithNoLogin();
        
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/console/**", "/admin/**")
                .addPathPatterns("/error")
                .excludePathPatterns(noLoginUris);
        
        registry.addInterceptor(permissionInterceptor)
        		.addPathPatterns("/console/**", "/admin/**")
        		.excludePathPatterns(noLoginUris);
        		
    }
	
}
