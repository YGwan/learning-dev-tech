package org.example.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.example.prefix.TaskExecutorData.*;

@ActiveProfiles("v1")
@SpringBootTest
class AsyncV1TestServiceTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    public AsyncTestService asyncTestService;

    @DisplayName("스프링 컨테이너 빈에서 내가 등록한 TaskExecutor가 있는지 확인")
    @Test
    void taskExecutorNameCheckInSpringBeanContainer() {
        var beanNames = applicationContext.getBeanDefinitionNames();

        List<String> beanNameAboutTaskExecutorList = new ArrayList<>();

        for (String beanName : beanNames) {
            if (beanName.contains("TaskExecutor")) {
                beanNameAboutTaskExecutorList.add(beanName);
            }
        }

        Assertions.assertThat(beanNameAboutTaskExecutorList).hasSize(2)
                .anyMatch(s -> s.contains(TASK_EXECUTOR_TEST_1_BEAN_NAME))
                .anyMatch(s -> s.contains(TASK_EXECUTOR_TEST_2_BEAN_NAME));
    }

    @DisplayName("비동기 처리 시 @Async 어노테이션에 아무것도 없을 때")
    @Test
    void test() throws ExecutionException, InterruptedException {
        var response = asyncTestService.callOnlyAsync();

        Assertions.assertThat(response.get()).contains(TASK_EXECUTOR_DEFAULT_BEAN_NAME);
    }

    @DisplayName("비동기 처리 시 @Async 어노테이션 value에 TaskExecutorTest1 있을때")
    @Test
    void test1() throws ExecutionException, InterruptedException {
        var response = asyncTestService.callAsyncWithTaskExecutorTest1();
        Assertions.assertThat(response.get()).contains(TASK_EXECUTOR_TEST_1_PREFIX);

    }

    @DisplayName("비동기 처리 시 @Async 어노테이션 value에 TaskExecutorTest2 있을때")
    @Test
    void test2() throws ExecutionException, InterruptedException {
        var response = asyncTestService.callAsyncWithTaskExecutorTest2();
        Assertions.assertThat(response.get()).contains(TASK_EXECUTOR_TEST_2_PREFIX);
    }
}