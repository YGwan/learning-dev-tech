package org.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

import static org.example.prefix.TaskExecutorData.TASK_EXECUTOR_TEST_V2_PREFIX;
import static org.example.prefix.TaskExecutorData.getThreadPoolTaskExecutor;

@Profile("v2")
@EnableAsync
@Configuration
public class AsyncConfigV2 implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() {
        return getThreadPoolTaskExecutor(TASK_EXECUTOR_TEST_V2_PREFIX);
    }
}