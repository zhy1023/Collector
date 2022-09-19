package com.aspectj.lib;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author shhe
 * @Date 2020/8/25 下午4:53
 * @Description:
 */
@Aspect
public class AtArgs {

    public static final String TAG = "AtArgs";

    @Pointcut("@args(com.aspectj.annotation.ClassAnnotation)")
    private void annotationAboveParam() {

    }

    @Pointcut("@args(com.aspectj.annotation.ClassAnnotation2)")
    private void annotationBelowParam() {

    }

    @Before("annotationAboveParam()")
    public void beforeFunction(JoinPoint joinPoint) {
        Log.i(TAG, "beforeFunction1 sign:"+joinPoint.getSignature()+" location: "+joinPoint.getSourceLocation());
    }

    @Before("annotationBelowParam()")
    public void beforeFunction2(JoinPoint joinPoint) {
        Log.i(TAG, "beforeFunction2 sign:"+joinPoint.getSignature()+" location: "+joinPoint.getSourceLocation());
    }
}
