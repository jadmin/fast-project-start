package #{packagePrefix}#.web.controller.status;

import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.util.concurrent.Monitor;
import com.github.javaclub.sword.web.HttpResult;


/**
 * ServerStartCheckController Web应用启动自检
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: ServerStartCheckController.java 2017年5月17日 11:27:50 Exp $
 */
@Controller
public class ServerStartCheckController {
	
	static final Logger log = LoggerFactory.getLogger(ServerStartCheckController.class);
	
	static volatile AtomicBoolean inited = new AtomicBoolean(false);
	
	
	private Monitor monitor = new Monitor();
	private Monitor.Guard noInit = new Monitor.Guard(monitor) {
        @Override
        public boolean isSatisfied() {
            return inited.get() == false;
        }
    };
	
    @RequestMapping(value = {"/index", "check", "webStatus"})
    @ResponseBody
    public HttpResult check() {
    	if(monitor.enterIf(noInit)) {
    		try {
				if(!inited.get()) { // 避免控制台不停的打印
					inited.set(true);
					log.warn("============ Web server started successfully ============");
				}
			} finally {
				monitor.leave();
			}
    	}
    	
        return HttpResult.success();
    }
    
}
