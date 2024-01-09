package org.algotithmcontestdatacollect.crawlerreceiver.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@EnableAsync
@Configuration
public class ThreadPoolConfig {

    @Bean("taskExecutor")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors()/2); // 核心线程数设置为机器的核心数
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors()); // 最大线程数设置为核心数的两倍
        executor.setQueueCapacity(100); // 队列容量，根据需求进行调整
        executor.setThreadNamePrefix("MyThread-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.setKeepAliveSeconds(60); // 非核心线程空闲时的存活时间
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60); // 在关闭时等待任务完成的时间
        executor.initialize();
        return executor;
    }

}

