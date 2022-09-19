package com.aspectj.lib;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author shhe
 * @Date 2020/8/10 下午5:41
 * @Description:
 */
@Aspect
public class AdviceCategory {

    public static final String TAG = "AdviceCategory";

    @Pointcut("execution(* com.aspectj.practice.MainActivity.onCreate(..))")
//    @Pointcut("execution(* com.aspectj.practice.framework.interfaces.User.add(..))")
    public void onCreatePointcut() {

    }

    @Before(value = "onCreatePointcut()")
    public void beforeOnCreate(JoinPoint joinPoint) {
        Log.i(TAG, "beforeOnCreate "+joinPoint.getSignature().getDeclaringType().getSimpleName());
    }

    @Pointcut("execution(* com.aspectj.practice.framework.interfaces.User.add(..))")
    public void addOfUserPointcut() {

    }

    @AfterReturning(value = "addOfUserPointcut()")
    public void addOfUserWithoutReturn(JoinPoint joinPoint) {
        Log.i(TAG, ".. addOfUserWithoutReturn .. "+joinPoint.getTarget().toString());
    }

    @AfterReturning(value = "addOfUserPointcut()", returning = "returnVal")
    public void addOfUserWithReturn(JoinPoint joinPoint, Object returnVal) {
        Log.i(TAG, ".. addOfUserWithReturn .. "+joinPoint.getTarget().toString() + " return:"+returnVal);
    }

    @AfterThrowing(value = "addOfUserPointcut()", throwing = "e")
    public void addOfUserWithReturnException(JoinPoint joinPoint, Throwable e) {
        Log.i(TAG, ".. addOfUserWithReturnException .. "+joinPoint.getTarget().toString() + " e:"+e.toString());
    }

    /**
     * 无论什么情况下都会执行
     * @param joinPoint
     */
    @After(value = "addOfUserPointcut()")
    public void addOfUserAfter(JoinPoint joinPoint) {
        Log.i(TAG, ".. addOfUserAfter .. "+joinPoint.getTarget().toString());
    }

/*    @Around(value = "addOfUserPointcut()")
    public Object addOfUserAround(ProceedingJoinPoint joinPoint) {
        Object object = new Integer(2);
        try {
            object = joinPoint.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return object;
    }*/
}
