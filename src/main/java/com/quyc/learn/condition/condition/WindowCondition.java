package com.quyc.learn.condition.condition;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * 自定义Condition判断条件
 *
 * @author: andy
 * @create: 2019/6/13 16:59
 * @description: Windows Condition
 */
public class WindowCondition implements Condition {
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String sysName = System.getProperty("os.name");
        return sysName.contains("Windows");
    }
}
