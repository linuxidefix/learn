package com.quyc.learn.lambda.functional._5replacepassthroughlambdas;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * @author: andy
 * @create: 2019/4/23 19:47
 * @description: 传递表达式（pass-through lambdas）的替代方案
 * <p>
 * Lambda 表达式被广泛用在函数式编程中，但它们很难阅读和理解。
 * 在许多情况下，lambda 表达式的存在只是为了传递一个或多个形参，最好将它替换为方法引用。
 * 在本文中，您将学习如何识别代码中的传递 lambda 表达式，以及如何将它们替换为相应的方法引用。
 * 尽管方法引用的使用需要学习，但长期收益很快会超过您最初的付出。
 */
public class ReplacePassThroughLambdas {

    public static void main(String[] args) {
//        simpleMethodReference();
//        instanceReferenceMethod();
//        staticReferenceMethod();
//        targetReferenceMethod();
//        constructReferenceMethod();
//        mutiParamStaticReferenceMethod();
        mutiParamTargetInstanceReferenceMethod();
    }

    private static Object collectingAndThen(Collector<Object, ?, List<Object>> toList, Object o) {
        return null;
    }

    /**
     * 传递多个参数给目标和实例方法
     */
    private static void mutiParamTargetInstanceReferenceMethod() {
        List<String> strings = Arrays.asList("a", "b", "c", "d");
        System.out.println(strings.stream().reduce("letter:", (a, b) -> a.concat(" " + b)));
        System.out.println(strings.stream().reduce("letter:", String::concat));
    }

    /**
     * 传递多个参数给静态方法
     */
    private static void mutiParamStaticReferenceMethod() {
        List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        System.out.println(numbers.stream().reduce(1, (a, b) -> Integer.sum(a, b)));
        System.out.println(numbers.stream().reduce(1, Integer::sum));
    }

    /**
     * 传递构造函数调用 Classname::new
     */
    private static void constructReferenceMethod() {
        List<Integer> numbers = Arrays.asList(0, 2, 1, 5, 4, 3, 6, 7, 9, 8);
        System.out.println(numbers.stream().map(Integer::doubleValue).collect(Collectors.toCollection((Supplier<TreeSet<Double>>) TreeSet::new)));
        System.out.println(numbers.stream().map(Integer::doubleValue).collect(Collectors.toCollection(() -> new TreeSet<>())));
    }

    /**
     * 将形参传递给目标 ClassName::methodName
     */
    private static void targetReferenceMethod() {
        List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        System.out.println(numbers.stream().map(e -> e.doubleValue()).filter(e -> e.compareTo(5d) <= 0).collect(toList()));
        System.out.println(numbers.stream().map(Integer::doubleValue).filter(e -> e.compareTo(5d) <= 0).collect(toList()));
    }

    /**
     * 静态方法方法引用 ClassName::methodName
     */
    private static void staticReferenceMethod() {
        IntStream.range(0, 5).map(e -> incrementStatic(e)).forEach(System.out::println);
        IntStream.range(0, 5).map(ReplacePassThroughLambdas::incrementStatic).forEach(System.out::println);
    }

    /**
     * 实例方法方法引用 referenceToInstance::methodName
     */
    private static void instanceReferenceMethod() {
        ReplacePassThroughLambdas lambdas = new ReplacePassThroughLambdas();
        IntStream.range(0, 5).map(e -> lambdas.increment(e)).forEach(System.out::println);
        IntStream.range(0, 5).map(lambdas::increment).forEach(System.out::println);
    }

    /**
     * 第一个 lambda 表达式实际执行一些工作，而第二个没有
     * e -> System.out.println(e) 即是 传递 lambda 表达式，可将其替代为方法引用
     */
    private static void simpleMethodReference() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        numbers.stream()
                .filter(e -> e % 2 == 0)
//                .forEach(e-> System.out.println(e));
                .forEach(System.out::println);
    }

    private int increment(int i) {
        return ++i;
    }

    private static int incrementStatic(int i) {
        return ++i;
    }
}
