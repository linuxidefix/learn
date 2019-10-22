package com.quyc.learn.javabasic.designpattern.action.state;

/**
 * Created by quyuanchao on 2019/2/16 22:47.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public interface State {
    /**
     * 投入 25 分钱
     */
    void insertQuarter();

    /**
     * 退回 25 分钱
     */
    void ejectQuarter();

    /**
     * 转动曲柄
     */
    void turnCrank();

    /**
     * 发放糖果
     */
    void dispense();
}