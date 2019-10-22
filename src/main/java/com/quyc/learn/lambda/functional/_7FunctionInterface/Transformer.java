package com.quyc.learn.lambda.functional._7FunctionInterface;

/**
 * @author: andy
 * @create: 2019/5/5 20:11
 * @description: 自定义函数接口
 * <p>
 * 尽管最好尽量使用内置函数接口，但有时需要自定义函数接口。
 * <p>
 * 要创建自己的函数接口，需要做两件事：
 * <p>
 * 1. 使用 @FunctionalInterface 注释该接口，这是 Java 8 对自定义函数接口的约定。
 * 2. 确保该接口只有一个抽象方法。
 * 该约定清楚地表明该接口应接收 lambda 表达式。当编译器看到该注释时，它会验证该接口是否只有一个抽象方法。
 * <p>
 * 使用 @FunctionalInterface 注释可以确保，如果在未来更改该接口时意外违反抽象方法数量规则，您会获得错误消息。
 */
@FunctionalInterface
public interface Transformer<T> {

    T transform(T t);

    //    T transformer(T t);

}
