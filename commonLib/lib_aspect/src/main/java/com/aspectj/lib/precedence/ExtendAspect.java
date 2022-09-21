package com.aspectj.lib.precedence;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author shhe
 * @Date 2020/8/26 上午9:32
 * @Description:
 */
@Aspect
public class ExtendAspect extends ParentAspect {

    @Before("PointcutAspect.executionFunctionPointcut()")
    public void beforeExecutionExtend(JoinPoint joinPoint) {
        Log.i(ConstValue.TAG, " ... [ExtendAspect] beforeExecution ... Signature:"+joinPoint.getSignature() + "  Location:" + joinPoint.getSourceLocation());
    }

    @Around("PointcutAspect.executionFunctionPointcut()")
    public Object aroundExecutionExtend(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.i(ConstValue.TAG, " ... [ExtendAspect] aroundExecution ... Signature:"+joinPoint.getSignature() + "  Location:" + joinPoint.getSourceLocation());
        Object object = joinPoint.proceed();
        Log.i(ConstValue.TAG, "return: "+object.toString());
        return object;
    }

    @After("PointcutAspect.executionFunctionPointcut()")
    public void afterExecutionExtend(JoinPoint joinPoint) {
        Log.i(ConstValue.TAG, " ... [ExtendAspect] afterExecution ... Signature:"+joinPoint.getSignature() + "  Location:" + joinPoint.getSourceLocation());
    }

    @AfterReturning(value = "PointcutAspect.executionFunctionPointcut()", returning = "returnVal")
    public void afterReturningExecutionExtend(JoinPoint joinPoint, Object returnVal) {
        Log.i(ConstValue.TAG, " ... [ExtendAspect] afterReturningExecution ... Signature:"+joinPoint.getSignature() + "  Location:" + joinPoint.getSourceLocation() + " returnVal："+returnVal.toString());
    }

    @AfterThrowing(value = "PointcutAspect.executionFunctionPointcut()", throwing = "e")
    public void afterThrowingExecutionExtend(JoinPoint joinPoint, Throwable e) {
        Log.i(ConstValue.TAG, " ... [ExtendAspect] afterThrowingExecution ... Signature:"+joinPoint.getSignature() + "  Location:" + joinPoint.getSourceLocation() + " exception:"+e.toString());
    }
}
