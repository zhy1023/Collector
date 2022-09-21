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
public abstract class ParentAspect {

/*    @Before("PointcutAspect.callFunctionPointcut()")
    public void beforeCall(JoinPoint joinPoint) {
        Log.i(ConstValue.TAG, " ... [ParentAspect] beforeCall ... Signature:"+joinPoint.getSignature() + "  Location:" + joinPoint.getSourceLocation());
    }*/

/*    @After("PointcutAspect.callFunctionPointcut()")
    public void afterCall(JoinPoint joinPoint) {
        Log.i(ConstValue.TAG, " ... [ParentAspect] afterCall ... Signature:"+joinPoint.getSignature() + "  Location:" + joinPoint.getSourceLocation());
    }*/

/*    @Around("PointcutAspect.callFunctionPointcut()")
    public Object aroundCall(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.i(ConstValue.TAG, " ... [ParentAspect] aroundCall ... Signature:"+joinPoint.getSignature() + "  Location:" + joinPoint.getSourceLocation());
        Object object = joinPoint.proceed();
        Log.i(ConstValue.TAG, "return: "+object.toString());
        return object;
    }*/

    @Before("PointcutAspect.executionFunctionPointcut()")
    public void beforeExecution(JoinPoint joinPoint) {
        Log.i(ConstValue.TAG, " ... [ParentAspect] beforeExecution ... Signature:"+joinPoint.getSignature() + "  Location:" + joinPoint.getSourceLocation());
    }

    @Around("PointcutAspect.executionFunctionPointcut()")
    public Object aroundExecution(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.i(ConstValue.TAG, " ... [ParentAspect] aroundExecution ... Signature:"+joinPoint.getSignature() + "  Location:" + joinPoint.getSourceLocation());
        Object object = joinPoint.proceed();
        Log.i(ConstValue.TAG, "return: "+object.toString());
        return object;
    }

    @After("PointcutAspect.executionFunctionPointcut()")
    public void afterExecution(JoinPoint joinPoint) {
        Log.i(ConstValue.TAG, " ... [ParentAspect] afterExecution ... Signature:"+joinPoint.getSignature() + "  Location:" + joinPoint.getSourceLocation());
    }

    @AfterReturning(value = "PointcutAspect.executionFunctionPointcut()", returning = "returnVal")
    public void afterReturningExecution(JoinPoint joinPoint, Object returnVal) {
        Log.i(ConstValue.TAG, " ... [ParentAspect] afterReturningExecution ... Signature:"+joinPoint.getSignature() + "  Location:" + joinPoint.getSourceLocation() + " returnVal："+returnVal.toString());
    }

    @AfterThrowing(value = "PointcutAspect.executionFunctionPointcut()", throwing = "e")
    public void afterThrowingExecution(JoinPoint joinPoint, Throwable e) {
        Log.i(ConstValue.TAG, " ... [ParentAspect] afterThrowingExecution ... Signature:"+joinPoint.getSignature() + "  Location:" + joinPoint.getSourceLocation() + " exception:"+e.toString());
    }

/*    @Before("PointcutAspect.executionFunctionPointcut()")
    public void beforeExecution2(JoinPoint joinPoint) {
        Log.i(ConstValue.TAG, " ... [ParentAspect] beforeExecution 22 ... Signature:"+joinPoint.getSignature() + "  Location:" + joinPoint.getSourceLocation());
    }*/

    /*@Around("PointcutAspect.executionFunctionPointcut()")
    public Object aroundExecution2(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.i(ConstValue.TAG, " ... [ParentAspect] aroundExecution 22 ... Signature:"+joinPoint.getSignature() + "  Location:" + joinPoint.getSourceLocation());
        Object object = joinPoint.proceed();
        Log.i(ConstValue.TAG, "return: "+object.toString());
        return object;
    }*/

/*    @After("PointcutAspect.executionFunctionPointcut()")
    public void afterExecution2(JoinPoint joinPoint) {
        Log.i(ConstValue.TAG, " ... [ParentAspect] afterExecution 22 ... Signature:"+joinPoint.getSignature() + "  Location:" + joinPoint.getSourceLocation());
    }

    @AfterReturning(value = "PointcutAspect.executionFunctionPointcut()", returning = "returnVal")
    public void afterReturningExecution2(JoinPoint joinPoint, Object returnVal) {
        Log.i(ConstValue.TAG, " ... [ParentAspect] afterReturningExecution 22 ... Signature:"+joinPoint.getSignature() + "  Location:" + joinPoint.getSourceLocation() + " returnVal："+returnVal.toString());
    }

    @AfterThrowing(value = "PointcutAspect.executionFunctionPointcut()", throwing = "e")
    public void afterThrowingExecution2(JoinPoint joinPoint, Throwable e) {
        Log.i(ConstValue.TAG, " ... [ParentAspect] afterThrowingExecution 22 ... Signature:"+joinPoint.getSignature() + "  Location:" + joinPoint.getSourceLocation() + " exception:"+e.toString());
    }*/
}
