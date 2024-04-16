package org.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

@Aspect
public class TransactionAspect {

    private final PlatformTransactionManager transactionManager;

    public TransactionAspect(PlatformTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Around("@annotation(org.example.annotation.MyTransactional)")
    public Object doTransactional(ProceedingJoinPoint joinPoint) throws Throwable {
        TransactionStatus transaction = transactionManager.getTransaction(new DefaultTransactionDefinition());

        try {
            Object result = joinPoint.proceed();
            transactionManager.commit(transaction);
            return result;

        } catch (Throwable throwable) {
            transactionManager.rollback(transaction);
            throw throwable;
        }
    }
}
