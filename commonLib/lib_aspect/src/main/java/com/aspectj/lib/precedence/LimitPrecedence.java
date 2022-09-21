package com.aspectj.lib.precedence;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclarePrecedence;

/**
 * @author shhe
 * @Date 2020/8/26 下午5:21
 * @Description:
 */
@Aspect
@DeclarePrecedence(value = "CommonAspect, ExtendAspect")
public class LimitPrecedence {
}
