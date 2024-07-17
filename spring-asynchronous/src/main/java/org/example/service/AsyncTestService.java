package org.example.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class AsyncTestService {

    @Async
    public CompletableFuture<String> callOnlyAsync() {
        return CompletableFuture.completedFuture(Thread.currentThread().getName());
    }

    @Async("TaskExecutorTest1")
    public CompletableFuture<String> callAsyncWithTaskExecutorTest1() {
        return CompletableFuture.completedFuture(Thread.currentThread().getName());
    }

    @Async("TaskExecutorTest2")
    public CompletableFuture<String> callAsyncWithTaskExecutorTest2() {
        return CompletableFuture.completedFuture(Thread.currentThread().getName());
    }
}
