package com.zhengqing.common.util;

import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;

/**
 * spring El表达式 工具类
 *
 * @author zhengqingya
 * @description
 * @date 2020/4/12 0:40
 */
public class SpringElUtil {

    /**
     * 表达式解析器
     */
    private static final ExpressionParser parser = new SpelExpressionParser();

    /**
     * 获取方法的参数名
     */
    private static final LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

    /**
     * 解析spring EL表达式
     *
     * @param key    表达式
     * @param method 方法
     * @param args   方法参数
     * @return java.lang.String
     * @author zhengqingya
     * @date 2022/1/18 14:35
     */
    public static String parse(String key, Method method, Object[] args) {
        // 获取方法的参数名
        String[] paramNameArray = discoverer.getParameterNames(method);
        if (paramNameArray == null) {
            return "";
        }
        // 表达式的上下文
        EvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < paramNameArray.length; i++) {
            // 为了让表达式可以访问该对象, 先把对象放到上下文中
            context.setVariable(paramNameArray[i], args[i]);
        }
        return parser.parseExpression(key).getValue(context, String.class);
    }

}
