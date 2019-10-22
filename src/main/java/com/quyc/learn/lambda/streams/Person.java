package com.quyc.learn.lambda.streams;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by quyuanchao on 2019/4/21 16:41.
 * <p>Title: </p>
 * <p>Description: </p>
 */
@Data
@AllArgsConstructor
public class Person {

    private String firstName;
    private String lastName;
    private String job;
    private String gender;
    private int age;
    private int salary;
}
