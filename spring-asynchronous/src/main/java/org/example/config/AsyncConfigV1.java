package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Profile("v1")
@Configuration
@EnableAsync
public class AsyncConfigV1 {

    private static final int CORE_POOL_SIZE = 2;

    private static final int MAX_POOL_SIZE = 4;

    private static final int queueCapacity = 20;

    @Bean("TaskExecutorTest1")
    public Executor taskExecutorTest1() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("TaskExecutorTest1-");
        executor.initialize();
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }

    @Bean("TaskExecutorTest2")
    public Executor taskExecutorTest2() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("TaskExecutorTest2-");
        executor.initialize();
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
