/*
 * @(#)IpWatcher.java	${date}
 *
 * Copyright (c) ${year} - 2099. All Rights Reserved.
 *
 */

package ${packagePrefix}.test;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.javaclub.monitor.component.alarm.AlarmMonitor;
import com.github.javaclub.sword.core.Maps;
import com.github.javaclub.sword.core.Numbers;
import com.github.javaclub.sword.core.Strings;
import com.github.javaclub.sword.domain.enumtype.TimeRangePeriod;
import com.github.javaclub.sword.util.Utils;

import lombok.extern.slf4j.Slf4j;

/**
 * IpWatcher
 *
 * @author <a href="mailto:gerald.chen.hz@gmail.com">Gerald Chen</a>
 * @version $Id: IpWatcher.java ${currentTime} Exp $
 */
@Component
@Slf4j
public class IpWatcher {
	
	private ScheduledExecutorService executorService;
	
	private static final ThreadGroup threadGroup = new ThreadGroup("IpWatcher");

	private static volatile String homeIp = "";
	
	@Autowired
    private AlarmMonitor alarmMonitor;
	
	public IpWatcher() {
		executorService = Executors.newScheduledThreadPool(3, new ThreadFactory() {
			
			@Override
			public Thread newThread(Runnable runnable) {
				Thread thread = new Thread(threadGroup, runnable, Strings.fixed(11));
				thread.setDaemon(true);
				if (thread.getPriority() != Thread.NORM_PRIORITY) {
					thread.setPriority(Thread.NORM_PRIORITY);
				}
				return thread;
			}
		});
	}
	
	@PostConstruct
	public void init() {
		
		executorService.scheduleAtFixedRate(new Runnable() {
			
			@Override
			public void run() {
				try {
					String ip = Utils.getNetworkIp();
					if(!Objects.equals(ip, homeIp)) {
						homeIp = ip;
						log.warn("当前IP地址为：" + homeIp);
						// 钉钉通知
		                alarmMonitor.sendDingTalk("WARN", new Date(), Maps.createStringMap(
		                        "调用服务", "IP实时探测",
		                        "调用参数", "NONE",
		                        "IP地址", homeIp
		                ));
					}
				} catch (Exception e) {
					alarmMonitor.sendDingTalk("ERROR", new Date(), Maps.createStringMap(
	                        "调用服务", "IP实时探测",
	                        "调用参数", "NONE",
	                        "异常信息", Strings.lessText(e.getMessage(), 60);
	                ));
					try {
						Thread.sleep(Numbers.random(3000L, 30000L));
					} catch (InterruptedException ex) {
					}
				}
				
			}
		}, 3000L, TimeRangePeriod.LAST_HOUR.getTimeDiff()/2, TimeUnit.MILLISECONDS); // 半小时执行1次
	}

}
