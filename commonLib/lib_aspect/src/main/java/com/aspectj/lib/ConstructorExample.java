package com.aspectj.lib;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author shhe
 * @Date 2020/8/22 下午3:07
 * @Description:
 */
@Aspect
public class ConstructorExample {

    public static final String TAG = "ConstructorExample";

    /**
     * 构造函数调用
     */
    public static final String CONSTRUCTOR_CALL = "call(com.aspectj.libmodule.imp.FundamentalOperationsImpl.new(..))";

    /**
     * 构造函数执行
     */
    public static final String CONSTRUCTOR_EXECUTION = "execution(com.aspectj.libmodule.imp.FundamentalOperationsImpl.new(..))";

    /**
     * 对象初始化
     * 从一个父构造器返回，到当前构造器执行完成的部分（不包括调用父构造器的部分）
     */
    public static final String INITIALIZATION = "initialization(com.aspectj.libmodule.imp.FundamentalOperationsImpl.new(..))";

    /**
     * 类初始化
     * 类加载，包括静态成员初始化部分
     */
    public static final String STATIC_INITIALIZATION = "staticinitialization(com.aspectj.libmodule.imp.FundamentalOperationsImpl)";


    /**
     * 对象预初始化
     * 在构造器中的对象初始化之前：从当前构造器调用，到父构造器调用结束为止
     */
    public static final String PRE_INITIALIZATION = "preinitialization(com.aspectj.libmodule.imp.FundamentalOperationsImpl.new(..))";

    @Pointcut(CONSTRUCTOR_CALL)
    public void constructorCall() {

    }

    @Pointcut(CONSTRUCTOR_EXECUTION)
    public void constructorExecution() {

    }

    @Pointcut(STATIC_INITIALIZATION)
    public void classInitialization() {

    }

    @Pointcut(INITIALIZATION)
    public void objectInitialization() {

    }

    @Pointcut(PRE_INITIALIZATION)
    public void preInitializationClass() {

    }

    @After(value = "constructorCall()")
    public void afterConstructorCall(JoinPoint joinPoint) {
        Log.i(TAG, "afterConstructorCall : " + joinPoint.getSignature() + " Location:"+ joinPoint.getSourceLocation() + " this: "+joinPoint.getThis() + " target:" + joinPoint.getTarget());
    }

    @Before("constructorExecution()")
    public void beforeConstructorExecution(JoinPoint joinPoint) {
        Log.i(TAG, "beforeConstructorExecution : " + joinPoint.getSignature() + " Location:"+ joinPoint.getSourceLocation() + " this: "+joinPoint.getThis() + " target:" + joinPoint.getTarget());
    }

    @Before("classInitialization()")
    public void beforeClassInitialization(JoinPoint joinPoint) {
        Log.i(TAG, "beforeClassInitialization : " + joinPoint.getSignature() + " Location:"+ joinPoint.getSourceLocation() + " this: "+joinPoint.getThis() + " target:" + joinPoint.getTarget());
    }

    @Before("objectInitialization()")
    public void beforeObjectInitialization(JoinPoint joinPoint) {
        Log.i(TAG, "beforeObjectInitialization : " + joinPoint.getSignature() + " Location:"+ joinPoint.getSourceLocation() + " this: "+joinPoint.getThis() + " target:" + joinPoint.getTarget());
    }

    @Before("preInitializationClass()")
    public void beforePreInitialization(JoinPoint joinPoint) {
        Log.i(TAG, "beforePreInitialization : " + joinPoint.getSignature() + " Location:"+ joinPoint.getSourceLocation() + " this: "+joinPoint.getThis() + " target:" + joinPoint.getTarget());
    }

    @Pointcut("within(com.aspectj.lib.ConstructorExample) && adviceexecution() && if()")
    public static boolean adviceexEcutionOfThisAspect(JoinPoint joinPoint) {
        return !joinPoint.getSignature().toString().contains("beforeAdviceexecution") && !joinPoint.getSignature().toString().contains("adviceexEcutionOfThisAspect");
    }

    @Before("adviceexEcutionOfThisAspect(joinPoint)")
    public void beforeAdviceexecution(JoinPoint joinPoint) {
        Log.i(TAG, "beforeAdviceexecution : " + joinPoint.getSignature() + " Location:"+ joinPoint.getSourceLocation() + " this: "+joinPoint.getThis() + " target:" + joinPoint.getTarget());
    }
}
