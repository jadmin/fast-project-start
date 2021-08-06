package ${packagePrefix}.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import ${packagePrefix}.common.AppConstants;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@Slf4j
public class ThreadPoolConfig {

    @Bean("threadTaskExecutor")
    @Primary
    public ThreadPoolTaskExecutor threadTaskExecutor() {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        
        int queueCapacity = 3000;
        pool.setCorePoolSize(5);
        pool.setMaxPoolSize(10);
        pool.setQueueCapacity(queueCapacity);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        
        pool.setThreadNamePrefix(AppConstants.APP_NAME);
        RejectedExecutionHandler handler = new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                log.error("thread pool is full, corePoolSize:{}, maxPoolSize:{}, queueCapacity:{},"
                    + " Can not handle more:{}", pool.getCorePoolSize(), pool.getMaxPoolSize(), queueCapacity, r);
            }
        };
        pool.setRejectedExecutionHandler(handler);
        
        return pool;
    }
}
