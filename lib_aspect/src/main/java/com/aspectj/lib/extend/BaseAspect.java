package com.aspectj.lib.extend;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author shhe
 * @Date 2020/8/25 下午2:47
 * @Description:
 */
@Aspect
public abstract class BaseAspect {

    /**
     * 通过注解方法 methodExecute 对改切点进行命名，方法可视范围是 protected,表明该命名切点可以在当前的切面类及子切面类中使用
     * 匹配任意函数的执行
     */
    @Pointcut("execution(* *(..))")
    protected void methodExecute() {

    }

    @Pointcut
    protected abstract void multiExecute();
}
