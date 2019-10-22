package com.quyc.learn.javabasic.designpattern.create.factorymethod;

import com.quyc.learn.javabasic.designpattern.create.simplefactory.ConcreteProduct;
import com.quyc.learn.javabasic.designpattern.create.simplefactory.Product;

/**
 * Created by quyuanchao on 2019/2/16 18:04.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class ConcreteFactory extends Factory {
    @Override
    public Product factoryMethod() {
        return new ConcreteProduct();
    }
}
