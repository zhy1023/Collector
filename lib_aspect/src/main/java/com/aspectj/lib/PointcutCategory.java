package com.aspectj.lib;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.aspectj.annotation.AspectLog;
import com.aspectj.practice.framework.interfaces.FundamentalOperations;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.ConstructorSignature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * @author shhe
 * @Date 2020/8/10 下午3:21
 * @Description:
 */
@Aspect
public class PointcutCategory {

    public static final String TAG = "PointcutCategory";

    @Around("execution(* *(..))")
    public Object weaveAllMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.i(TAG, "weaveAllMethod "+ joinPoint.getSignature());
        long startNanoTime = System.nanoTime();

        Object returnObject = joinPoint.proceed();

        //纳秒，1毫秒=1纳秒*1000*1000
        long stopNanoTime = System.nanoTime();

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Log.i(TAG, String.format(Locale.CHINA, "Method:<%s> cost=%s ns", method.toGenericString(), String.valueOf(stopNanoTime - startNanoTime)));

        return returnObject;
    }

/*    @Pointcut("execution(* com.aspectj.practice.MainActivity.onCreate(..))")
    public void onCreatePointcut() {

    }

    @After(value = "onCreatePointcut()")
    public void afterOnCreate() {
        Log.i(TAG, "... com.aspectj.practice.MainActivity.onCreate ...");
    }*/

    /**
     * 匹配任意返回值，任意名称，任意参数的公共方法
     */
    @Pointcut("execution(public * *(..))")
    public void publicMethodPointcut() {

    }

/*    @After(value = "publicMethodPointcut()")
    public void afterPublicMethod() {
        Log.i(TAG, "... afterPublicMethod ...");
    }*/

    /**
     * 匹配 com.aspectj.practice 包及其子包中的所有方法
     */
    @Pointcut("within(com.aspectj.practice..*)")
    public void packagePointcut() {

    }

    /**
     * 匹配MainActivity类及其子类的所有方法
     */
    @Pointcut("within(com.aspectj.practice.MainActivity+)")
    public void subclassPointcut() {

    }

    /**
     * 匹配类 MainActivity 的所有方法
     */
    @Pointcut("within(com.aspectj.practice.SubMainActivity)")
    public void classPointcut() {

    }

    /**
     * 匹配所有实现 User 的类的所有方法
     */
    @Pointcut("within(com.aspectj.practice.framework.interfaces.User+)")
    public void interfaceIpm() {

    }

//    @After(value = "subclassPointcut()")
//    @After(value = "classPointcut()")
//    @After(value = "interfaceIpm()")
    public void afterSubClass() {

    }

    /**
     * 匹配 test 开头，任意返回值的方法
     */
    @Pointcut("execution(* test*())")
    public void withSetPrefixPointcut() {

    }

    /**
     * 匹配 userImp 的所有方法
     */
    @Pointcut("execution(* com.aspectj.practice.userImp.*(..))")
    public void allMethodPointcut() {

    }

    /**
     * 匹配 userImp 的所有私有方法
     */
    @Pointcut("execution(private * com.aspectj.practice.userImp.*(..))")
    public void allPrivateMethodPointcut() {

    }

    /**
     * 匹配 userImp 的所有公有方法
     */
    @Pointcut("execution(public * com.aspectj.practice.userImp.*(int, ..))")
    public void allPublicMethodWithIntPointcut() {

    }

//    @After(value = "allMethodPointcut()")
//    @After(value = "allPrivateMethodPointcut()")
//    @After(value = "allPublicMethodWithIntPointcut()")
    public void userImpAllMethod() {

    }

    @Pointcut("this(com.aspectj.practice.framework.interfaces.User)")
    public void thisPointcut() {

    }

    @After(value = "thisPointcut()")
    public void thisAfter(JoinPoint joinPoint) {
        Log.i(TAG, "thisAfter ..... "+joinPoint.getStaticPart().getSignature() + " "+joinPoint.getSourceLocation());
    }

    /**
     * target 用于匹配当前目标对象类型的执行方法
     */
    @Pointcut("target(com.aspectj.practice.framework.interfaces.User)")
    public void targetPointcut() {

    }

    @After(value = "targetPointcut()")
    public void targetAfter(JoinPoint joinPoint) {
        Log.i(TAG, "targetAfter location: "+joinPoint.getSourceLocation() + joinPoint.getSignature());
    }

    /**
     * @within 匹配标注了注解 ClassAnnotation 的类及其子孙的所有方法
     */
    @Pointcut("@within(com.aspectj.annotation.ClassAnnotation)")
    public void withAnnotationClassPointcut() {

    }

    @After(value = "withAnnotationClassPointcut()")
    public void afterWithAnnotationClass(JoinPoint joinPoint) {
        Log.i(TAG, "afterWithAnnotationClass location: "+joinPoint.getSourceLocation() + joinPoint.getSignature());
    }

    /**
     * 匹配使用了注解 MethodAnnotation 的方法
     */
    @Pointcut("@annotation(com.aspectj.annotation.MethodAnnotation)")
    public void withAnnotationMethodPointcut() {

    }

    /**
     * 匹配使用了注解 MethodAnnotation 的方法
     */
    @Pointcut("execution(@com.aspectj.annotation.MethodAnnotation * *(..))")
    public void withAnnotationMethodPointcut2() {

    }

//    @After(value = "withAnnotationMethodPointcut()")
    @After(value = "withAnnotationMethodPointcut2()")
    public void afterWithAnnotationMethod(JoinPoint joinPoint) {
        Log.i(TAG, "afterWithAnnotationMethod location: "+joinPoint.getSourceLocation() + joinPoint.getSignature());
    }

    /**
     * 匹配任意实现了接口User的目标对象的方法并且方法使用了注解 MethodAnnotation
     */
    @Pointcut("target(com.aspectj.practice.framework.interfaces.User) && @annotation(com.aspectj.annotation.MethodAnnotation)")
    public void withOperatorPointcut() {

    }

    @After(value = "withOperatorPointcut()")
    public void afterWithOperator(JoinPoint joinPoint) {
        Log.i(TAG, "afterWithOperator location: "+joinPoint.getSourceLocation() + joinPoint.getSignature());
    }

    /**
     * 匹配任意实现了接口User的目标对象的方法并且方法名称为 add
     */
    @Pointcut("target(com.aspectj.practice.framework.interfaces.User) && execution(* com.aspectj.practice.framework.interfaces.User.add(..))")
    public void withOperatorAddNamePointcut() {

    }

    @After(value = "withOperatorAddNamePointcut()")
    public void afterWithOperatorAdd(JoinPoint joinPoint) {
        Log.i(TAG, "afterWithOperatorAdd location: "+joinPoint.getSourceLocation() + joinPoint.getSignature());
    }

    @Pointcut("call(* com.aspectj.practice.framework.interfaces.User.*(..))")
    public void methodCallPointcut() {

    }

    @Before(value = "methodCallPointcut()")
    public void beforeMethodCall(JoinPoint joinPoint) {
        Log.i(TAG, "beforeMethodCall: "+ joinPoint.getSignature().getName());
    }


    /**
     * 匹配 MemberDto 的属性 userName 的赋值
     */
    @Pointcut("set(java.lang.String com.aspectj.practice.dto.MemberDto.userName)")
    public void fieldSetPointCut() {

    }

    @After(value = "fieldSetPointCut()")
    public void afterFieldSet() {
        Log.i(TAG, "afterFieldSet ..");
    }

    /**
     * 匹配 MemberDto 的属性 userName的获取
     */
    @Pointcut("get(java.lang.String com.aspectj.practice.dto.MemberDto.userName)")
    public void fieldGetPointCut() {

    }

    @After(value = "fieldGetPointCut()")
    public void afterFieldGet() {
        Log.i(TAG, "afterFieldGet ..");
    }

    /**
     * 匹配 MemberDto 构造函数
     */
    @Pointcut("call(com.aspectj.practice.dto.MemberDto.new(..))")
    public void constructorCallPointcut() {

    }

    @After(value = "constructorCallPointcut()")
    public void afterConstructorCall(JoinPoint joinPoint) {
        ConstructorSignature signature = (ConstructorSignature) joinPoint.getSignature();
        Constructor constructor = signature.getConstructor();
        Log.i(TAG, "afterConstructorCall "+constructor.getName());
    }


    /**
     * 匹配包 com.aspectj.practice下面所有类型为 IllegalStateException 或者 IllegalArgumentException 的异常
     */
//    @Pointcut("handler(java.lang.Exception)")
    @Pointcut("(handler(java.lang.IllegalStateException) || handler(java.lang.IllegalArgumentException)) && within(com.aspectj.practice.*)")
    public void handlerExceptionPointcut() {

    }

    @Before(value = "handlerExceptionPointcut()")
    public void handlerException(JoinPoint joinPoint) {
        Log.i(TAG, "...  handlerException ... "+joinPoint.getSignature() + " "+joinPoint.getSourceLocation());
    }

    /**
     * 匹配包 com.aspectj.practice下面所有类型为 IllegalStateException 或者 IllegalArgumentException 的异常
     */
//    @Pointcut("handler(java.lang.Exception)")
    @Pointcut("(handler(java.lang.IllegalStateException) || handler(java.lang.IllegalArgumentException)) && within(com.aspectj.practice.*) && args(e)")
    public void handlerExceptionPointcutWithArgs() {

    }

//    @Before(value = "handlerExceptionPointcutWithArgs()")
    @Before(value = "handler(java.lang.IllegalStateException) && within(com.aspectj.practice.*) && args(e)")
    public void handlerExceptionWithArgs(JoinPoint joinPoint, Exception e) {
        Log.i(TAG, "...  handlerExceptionWithArgs ... "+joinPoint.getSignature() + " "+joinPoint.getSourceLocation());
        e.printStackTrace();
    }

    @Pointcut("call(* com.aspectj.practice..*.*(..)) && args(userId)")
    public void methodArgsPointcut(int userId) {

    }

    /**
     * 匹配包 com.aspectj.practice 下面，含有一个参数的函数的调用
     * @param joinPoint
     * @param userId
     */
//    @Before(value = "call(* com.aspectj.practice.framework.interfaces.User.add(..)) && args(userId)")
    @Before(value = "methodArgsPointcut(userId)")
//    @Before(value = "call(* com.aspectj.practice..*.*(..)) && args(userId)")
    public void addMethodWithArgs(JoinPoint joinPoint, int userId) {
        Log.i(TAG, ".. beforeAddMethodWithArgs ... " + joinPoint.getSignature().getName()
                + " " + joinPoint.getSourceLocation() + " userId:" + userId);
    }

    @Pointcut("call(* com.aspectj.practice..*.*(..))")
    public void methodArgsPointcut2() {

    }

    @Before(value = "methodArgsPointcut2()")
    public void beforeMethodWithArgs2(JoinPoint joinPoint) {
        if (((CodeSignature)(joinPoint.getSignature())).getParameterTypes().length > 0) {
            Class parameterType = ((CodeSignature) (joinPoint.getSignature())).getParameterTypes()[0];
            Object paramValue = joinPoint.getArgs()[0];
            Log.i(TAG, "beforeMethodWithArgs2 class: " + joinPoint.getSignature().getDeclaringType() + " method: " + joinPoint.getSignature().getName()
                    + " parameterType: " + parameterType + " paramValue:" + paramValue);
        }
    }


    /**
     * 匹配 AppCompatActivity 类及其子类的所有 protected 并且有一个参数为 Bundle 的 方法
     */
    @Pointcut("execution(protected * *(..)) && target(androidx.appcompat.app.AppCompatActivity+) && args(bundle)")
    public void targetPointcut2(Bundle bundle) {

    }

    @After(value = "targetPointcut2(bundle)")
//    @After(value = "execution(protected * *(..)) && target(androidx.appcompat.app.AppCompatActivity+) && args(bundle)")
    public void targetMethodExecute(JoinPoint joinPoint, Bundle bundle) {
        Log.i(TAG, "targetMethodExecute "+joinPoint.getSignature().getName());
    }

    // @AspectLog 修饰的方法的执行
    @Pointcut("execution(@com.aspectj.annotation.AspectLog * *(..)) && @annotation(aspectLog)")
    public void method(AspectLog aspectLog) {

    }

    // @AspectLog 修饰的方法的执行
    @Pointcut("execution(@com.aspectj.annotation.AspectLog * *(..))")
    public void method2() {

    }

    // @AspectLog 修饰的构造函数的执行
    @Pointcut("execution(@com.aspectj.annotation.AspectLog *.new(..)) && @annotation(aspectLog)")
    public void constructor(AspectLog aspectLog) {

    }

    // @AspectLog 修饰的构造函数的执行
    @Pointcut("execution(@com.aspectj.annotation.AspectLog *.new(..))")
    public void constructor2() {

    }

    @Around("method(aspectLog) || constructor(aspectLog)")
    public Object logAndExecute(ProceedingJoinPoint joinPoint, AspectLog aspectLog) throws Throwable {
        String note = aspectLog.note();//得到注解checkString的返回值
        Log.i(TAG, "logAndExecute "+note+ " "+joinPoint.getSourceLocation());
        Object result = joinPoint.proceed(); // 调用原来的方法
        long stopNanos = System.nanoTime();
        return result;
    }

    //另外一种写法
    @Around("method2() || constructor2()")
    public Object logAndExecute2(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = ((MethodSignature) joinPoint.getSignature());
        AspectLog aspectLog = methodSignature.getMethod().getAnnotation(AspectLog.class);
        String note = aspectLog.note();
        Log.i(TAG, "logAndExecute2 "+note+ " "+joinPoint.getSourceLocation());
        Object result = joinPoint.proceed();
        return result;
    }

    /**
     * 匹配切点代理的对象 AppCompatActivity 并且函数只有一个 int 参数的函数
     * @param activity
     * @param param
     */
    @Pointcut("this(activity) && args(param)")
    public void thisAndCallMethod(AppCompatActivity activity, int param) {

    }

    @After(value = "thisAndCallMethod(activity, param)")
    public void callMethodThis(JoinPoint joinPoint, AppCompatActivity activity, int param) {
        Log.i(TAG, "... callMethodThis ... "+joinPoint.getSignature().getName()+" "+joinPoint.getSourceLocation());
    }

    /**
     * 匹配目标对象 FundamentalOperations 并且函数第一个参数是 int 的函数的执行
     * @param operations
     * @param param
     */
    @Pointcut("target(operations) && execution(* *(..)) && args(param, ..)")
    public void targetAndCallMethod(FundamentalOperations operations, int param) {

    }

    @After(value = "targetAndCallMethod(operations, param)")
    public void targetCallMethod(JoinPoint joinPoint, FundamentalOperations operations, int param) {
        Log.i(TAG, "... targetCallMethod ... "+joinPoint.getSignature().getName()+" "+joinPoint.getSourceLocation());
    }

    @Pointcut("withincode(* com.aspectj.practice.framework.interfaces.User(..)) || withincode(* com.aspectj.practice.MainActivity.onCreate(..))")
    public void withinCodePointcut() {

    }

    @Before(value = "withinCodePointcut()")
    public void withinCodeMethod(JoinPoint joinPoint) {
        Log.i(TAG, "...  withinCodeMethod ... "+joinPoint.getSignature() + " "+joinPoint.getSourceLocation());
    }

    /**
     * 匹配在 FundamentalOperations类的对象的sub函数调用，并且第一个参数为 int 的调用中
     * @param operations
     * @param param
     */
    @Pointcut("cflow(call(* sub(..))) && target(operations) && args(param, ..)")
//    @Pointcut("cflow(execution(* *(..))) && target(operations) && args(param, ..)")
    public void targetCflow(FundamentalOperations operations, int param) {

    }

    @After(value = "targetCflow(operations, param)")
    public void targetCflowMethod(JoinPoint joinPoint, FundamentalOperations operations, int param) {
        Log.i(TAG, "... targetCflowMethod ... "+joinPoint.getSignature().getName()+" "+joinPoint.getSourceLocation());
    }

    @Pointcut("cflowbelow(call(* *(..))) && target(operations) && args(param, ..)")
    public void targetCflowbelow(FundamentalOperations operations, int param) {

    }

    @After(value = "targetCflowbelow(operations, param)")
    public void targetCflowbelowMethod(JoinPoint joinPoint, FundamentalOperations operations, int param) {
        Log.i(TAG, "... targetCflowbelowMethod ... "+joinPoint.getSignature().getName()+" "+joinPoint.getSourceLocation());
    }
}
