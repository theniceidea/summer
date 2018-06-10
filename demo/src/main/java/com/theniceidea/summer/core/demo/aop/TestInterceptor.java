package com.theniceidea.summer.core.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class TestInterceptor {

    @Around("@within(SummerService)||@annotation(SummerService)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        return point.proceed();
    }
}
