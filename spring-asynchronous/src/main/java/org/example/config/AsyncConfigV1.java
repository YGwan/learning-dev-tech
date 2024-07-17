package org.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

import static org.example.prefix.TaskExecutorData.*;

@Profile("v1")
@Configuration
@EnableAsync
public class AsyncConfigV1 {

    @Bean(TASK_EXECUTOR_TEST_1_BEAN_NAME)
    public Executor taskExecutorTest1() {
        return getThreadPoolTaskExecutor(TASK_EXECUTOR_TEST_1_PREFIX);
    }

    @Bean(TASK_EXECUTOR_TEST_2_BEAN_NAME)
    public Executor taskExecutorTest2() {
        return getThreadPoolTaskExecutor(TASK_EXECUTOR_TEST_2_PREFIX);
    }
}
