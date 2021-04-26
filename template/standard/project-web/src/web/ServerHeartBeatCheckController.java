package ${packagePrefix}.web;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.javaclub.sword.web.HttpResult;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.annotations.ApiIgnore;

/**
 * ServerHeartBeatCheckController Web应用启动心跳自检
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: ServerHeartBeatCheckController.java ${currentTime} Exp $
 */
@ApiIgnore
@Controller
@Slf4j
public class ServerHeartBeatCheckController implements ApplicationListener<ContextRefreshedEvent> {
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		ApplicationContext ac = event.getApplicationContext();
		if(null != ac && null == ac.getParent()) {
			log.info("====== WebApplication started successfully ======");
		}
	}
	
	@RequestMapping(value = { "", "/", "/index" })
	@ResponseBody
	public HttpResult<String> indexPage() {
		return HttpResult.success("ok");
	}
	
	@RequestMapping(value = { "/check", "/webStatus" })
	@ResponseBody
	public HttpResult<Boolean> check() {
		return HttpResult.success();
	}

	

}
