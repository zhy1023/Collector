package com.aspectj.lib.precedence;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @author shhe
 * @Date 2020/8/26 上午9:44
 * @Description:
 */
@Aspect
public class PointcutAspect {

    @Pointcut(ConstValue.CALL_FUNCTION)
    public void callFunctionPointcut() {

    }

    @Pointcut(ConstValue.CALL_FUNCTION)
    public void executionFunctionPointcut() {

    }
}
