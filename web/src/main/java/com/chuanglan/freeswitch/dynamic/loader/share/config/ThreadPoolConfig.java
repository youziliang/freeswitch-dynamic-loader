package com.chuanglan.freeswitch.dynamic.loader.share.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class ThreadPoolConfig {

    @Bean("messageHandleExecutor")
    public TaskExecutor fileTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 设置核心线程数
        executor.setCorePoolSize(8);
        // 设置队列容量（超出核心线程数，不等待，创建新线程处理任务）
        executor.setQueueCapacity(8);
        // 设置最大线程数
        executor.setMaxPoolSize(8);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(30);
        // 设置核心线程超时是否关闭
        executor.setAllowCoreThreadTimeOut(false);
        // 设置默认线程名称
        executor.setThreadNamePrefix("message-");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        return executor;
    }

}
