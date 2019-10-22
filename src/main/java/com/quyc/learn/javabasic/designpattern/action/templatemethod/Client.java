package com.quyc.learn.javabasic.designpattern.action.templatemethod;

/**
 * e.g. java.util.Collections#sort()
 * Created by quyuanchao on 2019/2/16 22:59.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Client {
    public static void main(String[] args) {
        CaffeineBeverage caffeineBeverage = new Coffee();
        caffeineBeverage.prepareRecipe();
        System.out.println("-----------");
        caffeineBeverage = new Tea();
        caffeineBeverage.prepareRecipe();
    }
}
