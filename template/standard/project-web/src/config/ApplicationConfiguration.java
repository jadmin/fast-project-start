package ${packagePrefix}.config;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.github.javaclub.monitor.util.SmsUtils;

@Configuration
@ComponentScan(basePackages = { "com.github.javaclub" })
public class ApplicationConfiguration {
	
	@PostConstruct
	public void init() {
		String smsApiEndpoint = ""; // TODO 设置短信发送http接口 URL
		SmsUtils.setInvokeURL(smsApiEndpoint); 
	}
	
}
