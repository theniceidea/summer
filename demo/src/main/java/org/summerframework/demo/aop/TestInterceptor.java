package org.summerframework.demo1.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

//@Component
//@Aspect
public class TestInterceptor {

    @Around("@within(SummerService)||@annotation(SummerService)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        return point.proceed();
    }
}
