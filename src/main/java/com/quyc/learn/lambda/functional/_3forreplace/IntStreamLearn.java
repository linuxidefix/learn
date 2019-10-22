package com.quyc.learn.lambda.functional._3forreplace;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @author: andy
 * @create: 2019/4/23 17:26
 * @description: 传统 for 循环的函数式替代方案
 */
public class IntStreamLearn {

    public static void main(String[] args) {
//        simpleFor();
//        anonymousInnerClassFor();
//        anonymousInnerClassForInstream();
//        skip();
//        reverseIterate();
        takeDropWhile();
    }

    private static void simpleFor() {
        for (int i = 1; i < 4; i++) {
            System.out.println(i);
        }
        /**
         * 1. 不同于 for，range 不会强迫我们初始化某个可变变量。
         * 2. 迭代会自动执行，所以我们不需要像循环索引一样定义增量。
         */
        IntStream.range(1, 4).forEach(System.out::println);
    }

    /**
     * 我们想在 run 方法中访问索引变量 i，但编译器不允许这么做。
     * 作为此限制的解决办法，我们可以创建一个局部临时变量，比如 temp，它是索引变量的一个副本。每次新的迭代都会创建变量 temp。
     * 在 Java 8 以前，我们需要将该变量标记为 final。从 Java 8 开始，可以将它视为实际的最终结果，因为我们不会再更改它。
     */
    private static void anonymousInnerClassFor() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 5; i++) {
            int temp = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    //If uncommented the next line will result in an error
                    //System.out.println("Running task " + i);
                    //local variables referenced from an inner class must be final or effectively final

                    System.out.println("Running task for " + temp);
                }
            });
        }
        executorService.shutdown();
    }

    /**
     * 在作为一个参数被拉姆达表达式接受后，索引变量 i 的语义与循环索引变量有所不同。
     * for 循环中定义的变量 i 是单个变量，它会在每次对循环执行迭代时发生改变。
     * range 示例中的变量 i 是拉姆达表达式的参数，所以它在每次迭代中都是一个全新的变量。
     */
    private static void anonymousInnerClassForInstream() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        IntStream.range(0, 5).forEach(i -> executorService.submit(() -> System.out.println("Running task intStream " + i)));
        // 闭区间，包含右边界
        IntStream.rangeClosed(0, 5).forEach(i -> executorService.submit(() -> System.out.println("Running task intStream closed " + i)));
        executorService.shutdown();
    }

    /**
     * takeWhile:从第一个元素开始匹配，直到第一个不符合规则的元素为止，取前面符合要求的元素集合
     * dropWhile:从第一个元素开始匹配，直到第一个不符合规则的元素为止，取后面所有元素集合
     */
    private static void takeDropWhile() {
        List<Integer> list = Arrays.asList(45,43,76,87,42,77,90,73,67,88);
        list.stream().dropWhile(x -> x < 80 ).forEach(System.out::println);
        System.out.println();
        List<Integer> list1 = Arrays.asList(45,43,76,87,42,77,90,73,67,88);
        list1.stream().takeWhile(x -> x < 80 ).forEach(System.out::println);
    }

    /**
     * 循环过程中进行跳跃
     * jdk8 中可以使用 IntStream#iterate() 和 IntStream#limit() 方法实现
     * jdk9 中可以使用 IntStream#iterate() 和 IntStream#takeWhile() 方法实现
     */
    private static void skip() {
        int total = 0;
        for (int i = 1; i <= 100; i = i + 3) {
            total += i;
        }
        System.out.println("total = " + total);
        // iterate 方法很容易使用；它只需获取一个初始值即可开始迭代。作为第二参数传入的拉姆达表达式决定了迭代中的下一个值。
        // 但是，在本例中有一个陷阱。不同于 range 和 rangeClosed，没有参数来告诉 iterate 方法何时停止迭代。如果我们没有限制该值，迭代会一直进行下去。
        int sum = IntStream.iterate(1, e -> e + 3).limit(34).sum();
        System.out.println("sum = " + sum);

        int sumTakeWhile = IntStream.iterate(1, e -> e + 3).takeWhile(i -> i <= 100).sum();
        System.out.println("sumTakeWhile = " + sumTakeWhile);
    }

    /**
     * 使用 IntStream#iterate() 实现反向遍历
     */
    private static void reverseIterate() {
        IntStream.iterate(10, i -> i - 1).takeWhile(e -> e >= 0).forEach(System.out::println);
    }
}
