package com.quyc.learn.condition;

import com.quyc.learn.condition.config.BeanConfig;
import com.quyc.learn.condition.module.Person;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * @author: andy
 * @create: 2019/6/13 16:54
 * @description: Condition Test
 */
public class ConditionTest {

    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(BeanConfig.class);

    @Test
    public void test1() {
        Map<String, Person> personMap = context.getBeansOfType(Person.class);
        personMap.forEach((s, person) -> System.out.println(s + " = " + person));
    }
}
