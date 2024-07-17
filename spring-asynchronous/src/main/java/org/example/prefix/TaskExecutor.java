package org.example.prefix;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

public class TaskExecutor {

    public static final String TASK_EXECUTOR_TEST_1_BEAN_NAME = "TaskExecutorTest1";
    public static final String TASK_EXECUTOR_TEST_2_BEAN_NAME = "TaskExecutorTest2";

    public static final String TASK_EXECUTOR_TEST_1_PREFIX = "TaskExecutorTest1-";
    public static final String TASK_EXECUTOR_TEST_2_PREFIX = "TaskExecutorTest2-";
    public static final String TASK_EXECUTOR_TEST_V2_PREFIX = "TaskExecutorTest3-";

    private static final int CORE_POOL_SIZE = 2;
    private static final int MAX_POOL_SIZE = 4;
    private static final int queueCapacity = 20;

    public static Executor getThreadPoolTaskExecutor(String threadPrefix) {
        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadPrefix);
        executor.initialize();
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return executor;
    }
}
