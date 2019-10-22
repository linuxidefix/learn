package com.quyc.learn.lambda.functional._7FunctionInterface;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author: andy
 * @create: 2019/5/5 19:55
 * @description: 函数接口
 * <p>
 * 因为 lambda 表达式在 Java 中表示为函数接口。
 * 函数接口有 3 条重要法则：
 * <p>
 * 1. 一个函数接口只有一个抽象方法。
 * 2. 在 Object 类中属于公共方法的抽象方法不会被视为单一抽象方法。
 * 3. 函数接口可以有默认方法和静态方法。
 * 任何满足单一抽象方法法则的接口，都会被自动视为函数接口。这包括 Runnable 和 Callable 等传统接口，以及您自己构建的自定义接口。
 */
public class FunctionInterface {

    public static void main(String[] args) {
        FunctionInterface functionInterface = new FunctionInterface();
        // 因为该方法接收一个函数接口作为参数，所以传入一个 lambda 表达式作为参数是可以接受的。
        functionInterface.borrowDevice(device -> System.out.println("using: " + device));
        customizeFunctionInterface();
    }

    public void borrowDevice(Consumer<Device> use) {
        Device device = getFromAvailable();
        device.checkOut();
        try {
            use.accept(device);
        } finally {
            device.checkIn();
        }
    }

    private Device getFromAvailable() {
        return new Device(new Random().nextInt(10));
    }

    /**
     * 在任何需要函数接口的地方，我们都有 3 种选择：
     * <p>
     * 1. 传递一个匿名内部类。
     * 2. 传递一个 lambda 表达式。
     * 3. 在某些情况下传递一个方法引用而不是 lambda 表达式。
     */
    public static void customizeFunctionInterface() {
        Order order = new Order(Arrays.asList(
                new OrderItem(1, 1200),
                new OrderItem(2, 1700),
                new OrderItem(3, 1600)
        ));
//        order.transformAndPrint(new Transformer<Stream<OrderItem>>() {
//            @Override
//            public Stream<OrderItem> transform(Stream<OrderItem> orderItemStream) {
//                return orderItemStream.sorted(Comparator.comparing(OrderItem::getPrice));
//            }
//        });
        order.transformAndPrint(orderItemStream -> orderItemStream.sorted(Comparator.comparing(OrderItem::getPrice)));
        order.transformAndPrintTwo(orderItemStream -> orderItemStream.sorted(Comparator.comparing(OrderItem::getPrice)));
    }
}
