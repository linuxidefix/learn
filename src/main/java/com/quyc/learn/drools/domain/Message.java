package com.quyc.learn.drools.domain;

import lombok.Data;

/**
 * @author: andy
 * @create: 2019/6/13 16:12
 * @description: Drools message
 */
@Data
public class Message {

    public static final Integer HELLO = 0;
    public static final Integer GOODBYE = 1;
    private String message;
    private Integer status;

}
