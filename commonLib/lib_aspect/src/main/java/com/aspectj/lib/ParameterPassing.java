package com.aspectj.lib;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;

/**
 * @author shhe
 * @Date 2020/8/10 下午8:07
 * @Description:
 */
@Aspect
public class ParameterPassing {

    public static final String TAG = "ParameterPassing";

    @Pointcut("execution(* com.aspectj.practice.framework.interfaces.User.add(..))")
    public void addOfUserPointcut() {

    }

    @Before(value = "addOfUserPointcut()")
    public void BeforeAdd(JoinPoint joinPoint) {
        String[] paramNames = ((CodeSignature) joinPoint
                .getSignature()).getParameterNames();
        StringBuilder stringBuilder = new StringBuilder();
        Object[] paramValues = joinPoint.getArgs();
        for(int i=0;i<paramValues.length;i++){
            stringBuilder.append(paramNames[i]);
            stringBuilder.append(": ");
            stringBuilder.append(paramValues[i]);
        }

        Log.i(TAG, "BeforeAdd: "+joinPoint.getSourceLocation() + " this: " +joinPoint.getThis() + " target: " + joinPoint.getTarget() + " params: "+ stringBuilder.toString());
    }

    @Pointcut("execution(* com.aspectj.practice.framework.interfaces.User.add(..)) && args(userId, ..)")
    public void addOfUserPointcut1(int userId) {

    }

    @Before(value = "addOfUserPointcut1(userId)")
    public void BeforeAdd1(JoinPoint joinPoint, int userId) {
        Log.i(TAG, "BeforeAdd1: " + joinPoint.getSourceLocation() + " userId:"+userId);
    }

    public static final String CALL_USER_ADD = "call(* com.aspectj.practice.framework.interfaces.User.add(..)) && args(userId, ..)";

    @Pointcut(CALL_USER_ADD)
    public void addOfUserPointcut2(int userId) {

    }

    @Before(value = "addOfUserPointcut2(userId)")
    public void callAdd(JoinPoint joinPoint, int userId) {
        Log.i(TAG, "callAdd: this:" +joinPoint.getThis() + " target: " + joinPoint.getTarget() + " " + joinPoint.getSourceLocation() + " userId:"+userId);
    }

    public static final String CALL_USER_ADD_WITH_IF = "call(* com.aspectj.practice.framework.interfaces.User.add(..)) && args(userId, ..) && if()";

    /**
     * if 表达式 只能是if()、if(true)或者if(false),而且@Pointcut方法必须是 public static boolean，方法体内
     * 就是if 表达式的内容，可以使用暴露的参数、静态属性、JoinPoint、StaticPart、JoinPoint.EnclosingStaticPart
     * @param joinPoint
     * @param staticPart
     * @param userId
     * @return
     */
    @Pointcut(CALL_USER_ADD_WITH_IF)
    public static boolean addOfUserWithIfPointcut(JoinPoint joinPoint, JoinPoint.EnclosingStaticPart staticPart, int userId) {
        return userId > 0 && joinPoint.getSignature().getName().startsWith("add") &&
                staticPart.getSignature().getName().startsWith("onClick");
    }

    @Before(value = "addOfUserWithIfPointcut(joinPoint, staticPart, userId)")
    public void callAddWithIf(JoinPoint joinPoint, JoinPoint.EnclosingStaticPart staticPart, JoinPoint.StaticPart staticPart1, int userId) {
        Log.i(TAG, "callAddWithIf: this:" +joinPoint.getThis() + " target: " + joinPoint.getTarget() + " " + joinPoint.getSourceLocation() + " userId:"+userId);
    }

    public static final String CALL_USER_SUB = "call(* com.aspectj.practice.framework.interfaces.User.sub(..)) && args(value)";

    @Pointcut(CALL_USER_SUB)
    public void subOfUserPointcut(Long value) {

    }

    /**
     * 范型参数的使用
     * @param joinPoint
     * @param value
     */
    @Before("subOfUserPointcut(value)")
    public void callSubOfUser(JoinPoint joinPoint, Long value) {
        Log.i(TAG, "callSubOfUser: "+joinPoint.getSourceLocation() + " this: " +joinPoint.getThis() + " target: " + joinPoint.getTarget() + " params: "+ value);
    }
}
