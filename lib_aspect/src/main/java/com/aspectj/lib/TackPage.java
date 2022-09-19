package com.aspectj.lib;

import android.os.Bundle;
import android.util.Log;

import com.aspectj.annotation.Page;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * @author shhe
 * @Date 2020/8/13 下午4:23
 * @Description: 页面统计
 */
@Aspect
public class TackPage {

    public static final String TAG = "TackPage";

    public static final String ON_CREATE = "execution(protected void onCreate(..)) && args(bundle) && target(androidx.appcompat.app.AppCompatActivity+)";

    public static final String ON_CREATE2 = "execution(protected void onCreate(..)) && args(bundle) && target(androidx.appcompat.app.AppCompatActivity+) && @target(com.aspectj.annotation.Page)";

    public static final String ON_CREATE3 = "call(protected void onCreate(..)) && args(bundle) && target(androidx.appcompat.app.AppCompatActivity+)";

    public static final String ON_RESUME = "execution(protected void onResume()) && target(androidx.appcompat.app.AppCompatActivity+)";

    public static final String ON_PAUSE = "execution(protected void onPause()) && target(androidx.appcompat.app.AppCompatActivity+)";

    /**
     * @target 匹配任意标注了注解 @M 的目标类
     */
    public static final String WITH_TARGET = "call(* *(..)) && @target(com.aspectj.annotation.Page)";

    /**
     * 匹配 AppCompatActivity 类及其子类的 onCreate 方法的执行
     */
    @Pointcut(ON_CREATE2)
    public void enterPagePointcut(Bundle bundle) {

    }

/*    @Pointcut(ON_CREATE3)
    public void enterPagePointcut3(Bundle bundle) {

    }

    @Around(value = "enterPagePointcut3(bundle)")
    public void enterPage3(ProceedingJoinPoint joinPoint, Bundle bundle) {
        Log.i(TAG, "... enterPage3 ... "+joinPoint.getSignature().getName()+" "+joinPoint.getSourceLocation());
    }*/

    @Before(value = "enterPagePointcut(bundle)")
    public void enterPage(JoinPoint joinPoint, Bundle bundle) {
        Log.i(TAG, "... enterPage ... "+joinPoint.getSignature().getName()+" "+joinPoint.getSourceLocation());
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Page page = method.getAnnotation(Page.class);
        if (page == null) {
            page = joinPoint.getTarget().getClass().getAnnotation(Page.class);
            if (page != null) {
                Log.i(TAG, "enterPage: "+page.name());
            }
        }
    }

    @Pointcut(WITH_TARGET)
    public void ComputeOptionCall() {

    }

    @Before(value = "ComputeOptionCall()")
    public void methodCall(JoinPoint joinPoint) {
        Log.i(TAG, "... methodCall ... "+joinPoint.getSignature().getName()+" "+joinPoint.getSourceLocation());
    }
}
