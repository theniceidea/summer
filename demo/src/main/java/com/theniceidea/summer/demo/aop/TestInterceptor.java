package com.theniceidea.summer.demo.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class TestInterceptor {

    @Around("@within(com.theniceidea.summer.base.SummerService)||@annotation(com.theniceidea.summer.base.SummerService)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        return point.proceed();
    }
}
