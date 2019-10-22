package com.quyc.learn.javabasic.designpattern.action.chainofresponsibility;

/**
 * Created by quyuanchao on 2019/2/16 21:56.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public abstract class Handler {
    protected Handler successor;


    public Handler(Handler successor) {
        this.successor = successor;
    }


    protected abstract void handleRequest(Request request);
}
