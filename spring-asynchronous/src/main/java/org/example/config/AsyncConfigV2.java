package org.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Profile("v2")
@EnableAsync
@Configuration
public class AsyncConfigV2 implements AsyncConfigurer {

    private static final int CORE_POOL_SIZE = 2;

    private static final int MAX_POOL_SIZE = 4;

    private static final int queueCapacity = 20;

    @Override
    public Executor getAsyncExecutor() {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("TaskExecutorTest3-");
        executor.initialize();
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}