package com.quyc.learn.condition.config;

import com.quyc.learn.condition.condition.LinusCondition;
import com.quyc.learn.condition.condition.WindowCondition;
import com.quyc.learn.condition.module.God;
import com.quyc.learn.condition.module.Person;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

/**
 * @author: andy
 * @create: 2019/6/13 16:50
 * @description: Condition Bean Config
 */
@Configuration
public class BeanConfig {

    @Bean
    public God god() {
        return new God();
    }

    /**
     * @return
     * @Conditional(WindowCondition.class) WindowCondition 方法返回true则注入该Bean，若放在类名上，则表示注入该配置文件下的所有Bean
     * @ConditionalOnBean(God.class) 容器中存在至少一个God的实例的时候才会注册该Person Bean(注意Bean注入的顺序)
     * @ConditionalOnMissingBean(God.class) 同上面相反，容器中存在没有God的实例的时候才会注册该Person Bean
     */
    @Bean
    @Conditional(WindowCondition.class)// WindowCondition 方法返回true则注入该Bean，若放在类名上，则表示注入该配置文件下的所有Bean
    @ConditionalOnBean(God.class)// 容器中存在至少一个God的实例的时候才会注册该Person Bean(注意Bean注入的顺序)
//    @ConditionalOnMissingBean(God.class)// 同上面相反，容器中存在没有God的实例的时候才会注册该Person Bean
    public Person bill() {
        return new Person("bill", 54);
    }

    @Bean
    @Conditional(LinusCondition.class)
    public Person linus() {
        return new Person("linus", 46);
    }

}
