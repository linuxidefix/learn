package com.quyc.learn.lambda.functional._6lambda_pro;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author: andy
 * @create: 2019/5/5 19:15
 * @description: 深入学习lambda
 */
public class LambdaPro {

    public static void main(String[] args) {
//        twoLambda();
//        imperativeAndFunctional();
//        dangerousLambda();
        beautifulLambda();
    }

    /**
     * lambda 表达式是匿名函数，它们天生就很简洁
     * 普通的函数或方法通常有 4 个元素：
     * 1. 一个名称
     * 2. 返回类型
     * 3. 参数列表
     * 4. 主体
     * lambda 表达式只有这 4 元素中的最后两个：(parameter list) -> body
     */
    public static void twoLambda() {
        List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        // 1. 单行lambda
        integers.forEach(integer -> System.out.println(integer * 2));
        // 2. 多行lambda，如果感觉好像 Java 因为我们编写多行 lambda 表达式而惩罚我们，—或许我们应该接受这样的暗示。
        List<Integer> collect = integers.stream().map(e -> {
            int result = e * 2;
            return result;
        }).collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    /**
     * 两段代码获得了相同的结果。在命令式代码中，我们需要读入 for 循环，按照分支和中断来跟随流程。第二段代码使用了函数组合，更容易阅读一些。
     * 本质上，第二段代码读起来像是一个问题陈述：给定一些值，仅选择大于 3 的值。从这些值中，仅选择偶数值，并将它们乘以 2。
     * 最后，挑选第一个结果。如果没有任何值存在，则返回 0。
     * <p>
     * 函数组合的表达能力很大程度上依赖于每个 lambda 表达式的简洁性。如果您的 lambda 表达式包含多行，您可能没有理解函数式编程的关键点。
     */
    public static void imperativeAndFunctional() {
        List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        int result = 0;
        for (int e : integers) {
            if (e > 3 && e % 2 == 0) {
                result = e * 2;
                break;
            }
        }
        System.out.println("result = " + result);
        result = integers.stream()
                .filter(e -> e > 3)
                .filter(e -> e % 2 == 0)
                .map(e -> e * 2)
                .findFirst()
                .orElse(0);
        System.out.println("result = " + result);
    }

    /**
     * 危险的lambda
     * 1.难以读懂
     * 2.用途不明
     * 3.代码质量差
     * 4.难以测试
     * 5.代码覆盖范围小
     */
    public static void dangerousLambda() {
        List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        System.out.println(
                integers.stream()
                        .mapToInt(e -> {
                            int sum = 0;
                            for (int i = 1; i <= e; i++) {
                                if (e % i == 0) {
                                    sum += i;
                                }
                            }

                            return sum;
                        })
                        .sum());
    }

    /**
     * 重写上面的代码，使其变得优雅
     * 简短的 lambda 表达式能提高代码可读性，这是函数式编程的重要好处之一。
     * 通过将多行 lambda 表达式的主体转移到一个命名函数中，然后从 lambda 表达式内调用该函数，这样很容易避免这些问题。
     * 推荐尽可能将 lambda 表达式替换为方法引用。
     */
    public static void beautifulLambda() {
        List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        System.out.println(
                integers.stream()
//                        .mapToInt(e -> sumOfFactors(e))
                        .mapToInt(LambdaPro::sumOfFactors)
                        .sum());

    }

    private static int sumOfFactors(Integer e) {
//        int sum = 0;
//        for (Integer i = 1; i <= e; i++) {
//            if (e % i == 0) {
//                sum += i;
//            }
//        }
//        return sum;
        return IntStream.rangeClosed(1, e)
                .filter(i -> e % i == 0)
                .sum();
    }


}
