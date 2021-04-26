/*
 * @(#)WebApplication.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix};

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

import ${packagePrefix}.support.BeanFactory;

import lombok.extern.slf4j.Slf4j;


/**
 * WebApplication
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: WebApplication.java ${currentTime} Exp $
 */
@SpringBootApplication(exclude = {
})
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages = { "${groupId}" })
@Slf4j
@EnableScheduling
public class WebApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(WebApplication.class, args);
        BeanFactory.getInstance().setCtx(applicationContext);
        log.info("启动成功, ENV = {}", applicationContext.getEnvironment());
	}
	
}

