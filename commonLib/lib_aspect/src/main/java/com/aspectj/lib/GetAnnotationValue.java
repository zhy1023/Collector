package com.aspectj.lib;

import android.util.Log;

import com.aspectj.annotation.MethodAnnotation;
import com.aspectj.annotation.ParamAnnotation;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author shhe
 * @Date 2020/8/19 下午4:35
 * @Description:
 */
@Aspect
public class GetAnnotationValue {

    public static final String TAG = "GetAnnotationValue";

    @Pointcut("target(com.aspectj.practice.framework.interfaces.User) && execution(* *(..))")
    public void withOperatorPointcut() {

    }

    @After(value = "withOperatorPointcut()")
    public void afterWithOperator(JoinPoint joinPoint) {
        Log.i(TAG, "afterWithOperator: "+joinPoint.getSourceLocation());
    }

    /**
     * 匹配任意实现了接口User的目标对象的方法并且方法使用了注解 MethodAnnotation
     */
    @Pointcut("target(com.aspectj.practice.framework.interfaces.User+) && execution(@com.aspectj.annotation.MethodAnnotation * *(..)) && @annotation(annotation)")
    public void withOperatorPointcut1(MethodAnnotation annotation) {

    }

    @After(value = "withOperatorPointcut1(annotation)")
    public void afterWithOperator1(MethodAnnotation annotation) {
        Log.i(TAG, "afterWithOperator1: "+annotation.note());
    }
}
