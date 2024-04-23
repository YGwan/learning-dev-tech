package org.example.logging.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class LogAspect2 {

    @Pointcut("execution(* org.example.service..*(..))")
    private void logServicePoint(){}

    @Around("logServicePoint()")
    public Object doLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("aop test v2");
        log.info("aop logging test v2");
        return joinPoint.proceed();
    }
}
