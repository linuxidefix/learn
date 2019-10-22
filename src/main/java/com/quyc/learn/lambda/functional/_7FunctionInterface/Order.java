package com.quyc.learn.lambda.functional._7FunctionInterface;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * @author: andy
 * @create: 2019/5/5 20:14
 * @description: 订单
 */
public class Order {
    List<OrderItem> items;

    public Order(List<OrderItem> orderItems) {
        items = orderItems;
    }

    public void transformAndPrint(Transformer<Stream<OrderItem>> transformOrderItems) {

        transformOrderItems.transform(items.stream())
                .forEach(System.out::println);
    }

    /**
     * 使用内置函数接口代替自定义函数接口
     *
     * @param function
     */
    public void transformAndPrintTwo(Function<Stream<OrderItem>, Stream<OrderItem>> function) {

        function.apply(items.stream())
                .forEach(System.out::println);
    }
}
