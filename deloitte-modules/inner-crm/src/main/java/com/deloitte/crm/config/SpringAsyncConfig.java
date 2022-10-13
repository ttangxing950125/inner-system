package com.deloitte.crm.config;

import cn.hutool.core.thread.ExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 线程池
 */
@Configuration
@EnableAsync
public class SpringAsyncConfig {


    /**
     * 单例线程池
     * @return
     */
    @Bean("singleThreadPoll")
    public ExecutorService singleThreadPoll(){

        return ExecutorBuilder.create()
                .setCorePoolSize(2)
                .setMaxPoolSize(5)
                .setWorkQueue(new LinkedBlockingQueue<>(Integer.MAX_VALUE))
                .build();
    }

    /**
     * 导入文件使用的线程池
     * @return
     */
    @Bean("taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {



        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//        int i = Runtime.getRuntime().availableProcessors();
        // 设置核心线程数
        executor.setCorePoolSize(16);
        // 设置最大线程数
        executor.setMaxPoolSize(64);
        //配置队列大小
        executor.setQueueCapacity(Integer.MAX_VALUE);
        // 设置线程活跃时间（秒）
        executor.setKeepAliveSeconds(60);
        // 设置默认线程名称
        executor.setThreadNamePrefix("template-thread");
        // 等待所有任务结束后再关闭线程池
        executor.setWaitForTasksToCompleteOnShutdown(true);
        //执行初始化
        executor.initialize();


        return executor;
    }
}