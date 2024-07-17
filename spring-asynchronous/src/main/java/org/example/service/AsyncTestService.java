package org.example.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncTestService {

    @Async
    public void callAsyncTest() {
        System.out.println("test  : " + Thread.currentThread().getName());
    }

    @Async("TaskExecutorTest1")
    public void callAsyncTest1() {
        System.out.println("test 1 : " + Thread.currentThread().getName());
    }

    @Async("TaskExecutorTest2")
    public void callAsyncTest2() {
        System.out.println("test 2 : " + Thread.currentThread().getName());
    }
}
