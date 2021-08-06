package ${packagePrefix}.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;


public class BeanFactory {
	
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);
    private static BeanFactory instance = new BeanFactory();
    private ApplicationContext ctx;

    private BeanFactory() {
    }

    public static BeanFactory getInstance() {
        return instance;
    }

    public void setCtx(ApplicationContext ctx) {
        this.ctx = ctx;
    }
    
    public <T> T getBean(Class<T> clazz) {
    	 if (ctx == null) {
             int i = 0;
             while (i++ < 10 && ctx == null) {
                 try {
                     Thread.sleep(3000L); // 等待初始化
                     logger.warn("Waiting bean " + clazz + " init...");
                 } catch (InterruptedException e) {
                 }
             }
         }
        return ctx.getBean(clazz);  
    }

    public Object getBean(String name) {
        if (ctx == null) {
            int i = 0;
            while (i++ < 10 && ctx == null) {
                try {
                    Thread.sleep(3000L); // 等待初始化
                    logger.warn("Waiting bean " + name + " init...");
                } catch (InterruptedException e) {
                }
            }
        }
        return ctx.getBean(name);
    }
}
