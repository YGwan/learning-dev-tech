package org.example.logging.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class LogAspect1 {

    @Around("execution(* org.example.service..*(..))")
    public Object doLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("aop test v1");
        log.info("aop logging test v1");
        return joinPoint.proceed();
    }
}
