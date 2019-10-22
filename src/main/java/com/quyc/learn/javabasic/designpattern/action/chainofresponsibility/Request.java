package com.quyc.learn.javabasic.designpattern.action.chainofresponsibility;

/**
 * Created by quyuanchao on 2019/2/16 21:56.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Request {
    private RequestType type;
    private String name;


    public Request(RequestType type, String name) {
        this.type = type;
        this.name = name;
    }


    public RequestType getType() {
        return type;
    }


    public String getName() {
        return name;
    }
}
