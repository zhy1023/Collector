package com.aspectj.lib;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclarePrecedence;

/**
 * @author shhe
 * @Date 2020/8/25 下午8:34
 * @Description:
 */
@Aspect
@DeclarePrecedence("extend.AdviceTest, extend.AspectExtend, ParameterPassing, AtArgs, GetAnnotationValue, AdviceCategory, PointcutCategory")
public class AspectPrecedence {
}
