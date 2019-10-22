package com.quyc.learn.es.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author: andy
 * @create: 2019/7/18 18:45
 * @description: Parent-Child
 */
@Data
@AllArgsConstructor
public class Child {

    private String name;
    private int age;
    private String gender;
    private String address;
    private Date birthday;

    private JoinField joinField;

    public Child(String name, int age, String gender, String address, Date birthday) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.birthday = birthday;
    }

    @Data
    public static class JoinField{
        private String parent;
        private String name = "child";

        public JoinField(String parent) {
            this.parent = parent;
        }
    }

}
