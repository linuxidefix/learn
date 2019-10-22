package com.quyc.learn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author: andy
 * @create: 2019/5/24 17:54
 * @description: 启动器
 */
@SpringBootApplication
@EnableSwagger2
public class LearnApplication {
    public static void main(String[] args) {
        SpringApplication.run(LearnApplication.class, args);
    }
}

