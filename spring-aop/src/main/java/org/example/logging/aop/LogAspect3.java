package org.example.logging.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class LogAspect3 {

    @Pointcut("execution(* org.example.service..*(..))")
    private void logServicePoint() {
    }

    @Before("logServicePoint()")
    public void doLogging() {
        System.out.println("aop test v3");
        log.info("aop logging test v3");
    }
}
