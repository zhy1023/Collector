package com.aspectj.lib.extend;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author shhe
 * @Date 2020/8/25 下午2:27
 * @Description:
 * 切面可以进行扩展
 */
@Aspect
public class AspectExtend extends BaseAspect {

    public static final String TAG = "AspectExtend";

    /**
     * 通过注解方法 inPackage 对改切点进行命名，方法的可见范围为private，表明改切点只能在本切面中使用
     * 匹配包com..imp中的所有类的所有方法
     */
    @Pointcut("within(com..imp.*)")
    private void inPackage() {

    }

    @Override @Pointcut("execution(* multi2(..))")
    protected void methodExecute() {
    }

    /**
     * 注解方法 inPackageMethodExecute 对该切点命名，改切点引用命名切点inPackage及methodExecute定义的切点
     */
    @Pointcut("inPackage() && methodExecute()")
    public void inPackageMethodExecute() {

    }

    /**
     * 注解方法 inPackageMethodExecute 对该切点命名，改切点引用命名切点inPackage及methodExecute定义的切点
     */
    @Pointcut("inPackage() && BaseAspect.methodExecute()")
    public void inPackageMethodExecute2() {

    }

    @Override @Pointcut("execution(* multi(..))")
    protected void multiExecute() {

    }

    /**
     * 引用命名切点
     */
    @Before("inPackageMethodExecute()")
    public void inPackageMethod(JoinPoint joinPoint) {
        Log.i(TAG, "inPackageMethod: " + joinPoint.getSignature() + " location: "+joinPoint.getSourceLocation());
    }

    /**
     * 引用命名切点
     */
    @Before("inPackageMethodExecute2()")
    public void inPackageMethod2(JoinPoint joinPoint) {
        Log.i(TAG, "inPackageMethod2: " + joinPoint.getSignature() + " location: "+joinPoint.getSourceLocation());
    }

    /**
     * 引用命名切点
     */
    @Before("multiExecute()")
    public void inPackageMethod3(JoinPoint joinPoint) {
        Log.i(TAG, "inPackageMethod3: " + joinPoint.getSignature() + " location: "+joinPoint.getSourceLocation());
    }
}
