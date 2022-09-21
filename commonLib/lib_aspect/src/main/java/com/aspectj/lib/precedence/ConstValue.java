package com.aspectj.lib.precedence;

/**
 * @author shhe
 * @Date 2020/8/26 上午9:34
 * @Description:
 */
public class ConstValue {

    public static final String TAG = "ConstValue";

    public static final String CALL_FUNCTION = "call(* com.aspectj.lib.imp.FundamentalOperationsImpl2.testPrecedence(..))";

    public static final String EXECUTION_FUNCTION = "execution(* com.aspectj.lib.imp.FundamentalOperationsImpl2.testPrecedence(..))";
}
