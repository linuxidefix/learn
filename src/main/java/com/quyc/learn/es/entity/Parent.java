package com.quyc.learn.es.entity;

import lombok.Data;

/**
 * @author: andy
 * @create: 2019/7/18 18:38
 * @description: Parent-Child
 */
@Data
public class Parent {

    private String name;
    private String job;
    private String gender;
    private int age;
    private int salary;
    private String desc;
    private int id;
    private String joinField = "parent";

    public Parent(String name, String job, String gender, int age, int salary, String desc, int id) {
        this.name = name;
        this.job = job;
        this.gender = gender;
        this.age = age;
        this.salary = salary;
        this.desc = desc;
        this.id = id;
    }

}
