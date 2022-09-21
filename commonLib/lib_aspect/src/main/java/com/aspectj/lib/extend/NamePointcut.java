package com.aspectj.lib.extend;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author shhe
 * @Date 2020/8/25 上午10:59
 * @Description: 命名切点
 * 命名切点的类方法作为切点的名称，防范的访问修饰符控制了切点的可引用性
 */
@Aspect
public class NamePointcut {

    /**
     * 通过注解方法 inPackage 对改切点进行命名，方法的可见范围为private，表明改切点只能在本切面中使用
     * 匹配包com.aspectj.lib中的所有类的所有方法
     */
    @Pointcut("within(com.aspectj.lib.*)")
    private void inPackage() {

    }

    /**
     * 通过注解方法 inPackageAndSubPackage 对改切点进行命名，方法的可见范围为private，表明改切点只能在本切面中使用
     * 匹配包com.aspectj.lib及其子包中的所有类的所有方法
     */
    @Pointcut("within(com.aspectj.lib..*)")
    private void inPackageAndSubPackage() {

    }

    /**
     * 通过注解方法 inPackage 对改切点进行命名，方法的可见范围为private，表明改切点只能在本切面中使用
     * 匹配包com.*.lib中的所有类的所有方法
     */
    @Pointcut("within(com.*.lib.*)")
    private void inPackage1() {

    }

    /**
     * 通过注解方法 inPackage 对改切点进行命名，方法的可见范围为private，表明改切点只能在本切面中使用
     * 匹配包com..imp中的所有类的所有方法
     */
    @Pointcut("within(com..imp.*)")
    private void inPackage2() {

    }

    /**
     * 通过注解方法 inPackageAndSubPackage 对改切点进行命名，方法的可见范围为private，表明改切点只能在本切面中使用
     * 匹配包com.*.lib及其子包中的所有类的所有方法
     */
    @Pointcut("within(com.*.lib..*)")
    private void inPackageAndSubPackage1() {

    }

    /**
     * 通过注解方法 inPackageAndSubPackage 对改切点进行命名，方法的可见范围为private，表明改切点只能在本切面中使用
     * 匹配包 com..lib 及其子包中的所有类的所有方法
     */
    @Pointcut("within(com..lib..*)")
    private void inPackageAndSubPackage2() {

    }

    /**
     * 通过注解方法 inPackageAndSubPackage 对改切点进行命名，方法的可见范围为private，表明改切点只能在本切面中使用
     * 匹配包 com..imp 及其子包中的所有类的所有方法
     */
    @Pointcut("within(com..imp..*)")
    private void inPackageAndSubPackage3() {

    }

    /**
     * 通过注解方法 methodExecute 对改切点进行命名，方法可视范围是 protected,表明该命名切点可以在当前的切面类及子切面类中使用
     * 匹配任意函数的执行
     */
    @Pointcut("execution(* *(..))")
    protected void methodExecute() {

    }

    /**
     * 注解方法 inPackageMethodExecute 对该切点命名，改切点引用命名切点inPackage及methodExecute定义的切点
     */
    @Pointcut("inPackage() && methodExecute()")
    public void inPackageMethodExecute() {

    }

    /**
     * 注解方法 inPackageAndSubPacMethodExecute 对该切点命名，改切点引用命名切点inPackage及methodExecute定义的切点
     */
    @Pointcut("inPackageAndSubPackage() && methodExecute()")
    public void inPackageAndSubPacMethodExecute() {

    }

    @Pointcut("inPackage1() && methodExecute()")
    public void inPackageMethodExecute1() {

    }

    @Pointcut("inPackage2() && methodExecute()")
    public void inPackageMethodExecute2() {

    }

    @Pointcut("inPackageAndSubPackage1() && methodExecute()")
    public void inPackageAndSubPacMethodExecute1() {

    }

    @Pointcut("inPackageAndSubPackage2() && methodExecute()")
    public void inPackageAndSubPacMethodExecute2() {

    }

    @Pointcut("inPackageAndSubPackage3() && methodExecute()")
    public void inPackageAndSubPacMethodExecute3() {

    }
}
