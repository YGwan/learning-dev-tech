package org.example.logging.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class LogAspect4 {

    @Around("@annotation(org.example.logging.annotation.MyLogging)")
    public Object doLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("aop test v4");
        log.info("aop logging test v4");
        return joinPoint.proceed();
    }
}
