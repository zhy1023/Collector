package com.aspectj.lib.extend;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * @author shhe
 * @Date 2020/8/25 上午11:14
 * @Description:
 */
@Aspect
public class AdviceTest {

    public static final String TAG = "AdviceTest";

    /**
     * 引用命名切点
     */
    @Before("NamePointcut.inPackageMethodExecute()")
    public void inPackageMethod(JoinPoint joinPoint) {
        Log.i(TAG, "inPackageMethod: " + joinPoint.getSignature() + " location: "+joinPoint.getSourceLocation());
    }

    /**
     * 引用命名切点
     */
    @Before("NamePointcut.inPackageAndSubPacMethodExecute()")
    public void inPackageAndSubPMethod(JoinPoint joinPoint) {
        Log.i(TAG, "inPackageAndSubPMethod: " + joinPoint.getSignature() + " location: "+joinPoint.getSourceLocation());
    }

    @Before("NamePointcut.inPackageMethodExecute1()")
    public void inPackageMethod1(JoinPoint joinPoint) {
        Log.i(TAG, "inPackageMethod 111: " + joinPoint.getSignature() + " location: "+joinPoint.getSourceLocation());
    }

    @Before("NamePointcut.inPackageMethodExecute2()")
    public void inPackageMethod2(JoinPoint joinPoint) {
        Log.i(TAG, "inPackageMethod 222: " + joinPoint.getSignature() + " location: "+joinPoint.getSourceLocation());
    }

    /**
     * 引用命名切点
     */
    @Before("NamePointcut.inPackageAndSubPacMethodExecute1()")
    public void inPackageAndSubPMethod1(JoinPoint joinPoint) {
        Log.i(TAG, "inPackageAndSubPMethod 111: " + joinPoint.getSignature() + " location: "+joinPoint.getSourceLocation());
    }

    /**
     * 引用命名切点
     */
    @Before("NamePointcut.inPackageAndSubPacMethodExecute2()")
    public void inPackageAndSubPMethod2(JoinPoint joinPoint) {
        Log.i(TAG, "inPackageAndSubPMethod 222: " + joinPoint.getSignature() + " location: "+joinPoint.getSourceLocation());
    }

    /**
     * 引用命名切点
     */
    @Before("NamePointcut.inPackageAndSubPacMethodExecute3()")
    public void inPackageAndSubPMethod3(JoinPoint joinPoint) {
        Log.i(TAG, "inPackageAndSubPacMethodExecute 333: " + joinPoint.getSignature() + " location: "+joinPoint.getSourceLocation());
    }
}
