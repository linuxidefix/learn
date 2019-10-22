package com.quyc.learn.lambda.functional._10bibao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author: andy
 * @create: 2019/4/23 15:24
 * @description: 使用闭包捕获状态
 * lambda 表达式是无状态的，而闭包是带有状态的。将 lambda 表达式替换为闭包，是一种管理函数式程序中的状态的好方法。
 * 闭包要完成的任务：它们携带自己的午餐（或状态）。
 *
 * <a src=https://www.ibm.com/developerworks/cn/java/j-java8idioms10/index.html?ca=drs-></a>
 */
public class BiBao {

    public static void main(String[] args) {
        simpleLambda();
        print();
        printLambda();
        biBaoLambda();
    }

    /**
     * 简单的lambda表达式
     * <p>
     * 这两个 lambda 表达式都依赖于传入的参数和字面常量。二者都是独立的，这意味着它们没有任何外部依赖项。
     * 因为它们依赖于传入的参数，而且可能还依赖于一些常量，所以 lambda 表达式是无状态的。
     * <p>
     * 如果我们希望计算给定值的三倍或四倍，该怎么办？
     */
    private static void simpleLambda() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        List<Integer> collect = numbers.stream().filter(x -> x % 2 == 0).map(x -> x * 2).collect(toList());
        // 方法 map 要求接受函数接口 Function<T, R> 的一个实现作为参数。
        // 如果我们传入该接口外的任何内容（比如一个 BiFunction<T, U, R>），map 不会接受。
//         int factor = 3;
//         List<Integer> collect = numbers.stream().filter((x,factor) -> x % factor == 0).map(x -> x * 2).collect(Collectors.toList());
        System.out.println("numbers = " + numbers);
        System.out.println("collect = " + collect);
    }

    /**
     * 匿名内部类接受外部参数
     * 词法范围是函数的定义范围。反过来，它也可能是该定义范围的定义范围。
     * <p>
     * 方法 run 没有定义 location 或接收它作为参数。run 的定义范围是 Runnable 的匿名内部对象。
     * 因为没有将 location 定义为该实例中的字段，所以会继续搜索匿名内部对象的定义范围 — 在本例中为方法 print 的局部范围。
     * 如果 location 不在该范围中，编译器会继续在 print 的定义范围内搜索，直到找到该变量或搜索失败。
     */
    private static void print() {
        String location = "World";
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello " + location);
            }
        };
        runnable.run();
    }

    /**
     * lambda 表达式中的变量 location 被绑定到 lambda 表达式的词法范围中的变量 location。
     * 严格来讲，此代码中的 lambda 表达式是一个闭包（依赖外部传入的location，有状态）。
     */
    private static void printLambda() {
        String location = "World";
        Runnable runnable = () -> System.out.println("Hello " + location);
        runnable.run();
    }

    private static void biBaoLambda() {
        List<Integer> numbers = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        int factor = 3;
        List<Integer> collect = numbers.stream().filter(x -> x % 2 == 0).map(x -> x * factor).collect(toList());
        System.out.println("collect = " + collect);
    }

}

/**
 * 此代码中的闭包使用了来自它的词法范围的变量 value。如果 main 是在堆栈级别 1 上执行的，那么 call 方法的主体会在堆栈级别 2 上执行。
 * 因为 Runnable 的 run 方法是从 call 内调用的，所以该闭包的主体会在级别 3 上运行。
 * 如果 call 方法要将该闭包传递给另一个方法（进而推迟调用的位置），则执行的堆栈级别可能高于 3。
 * <p>
 * 您现在可能想知道在一个堆栈级别中的执行究竟如何能获取之前的另一个堆栈级别中的变量 — 尤其是未在调用中传递上下文时。
 * 简单来讲就是无法获取该变量，但实际上该代码执行成功，暂不知道为何？
 */
class Sample {
    public static void call(Runnable runnable) {
        System.out.println("calling runnable");

        //level 2 of stack
        runnable.run();
    }

    public static void main(String[] args) {
        //level 1 of stack
        int value = 4;
        call(
                //level 3 of stack
                () -> System.out.println(value)
        );
    }
}

/**
 * create 方法有一个局部变量 value，该变量的寿命很短：只要我们退出 create，它就会消失。
 * create 内创建的闭包在其词法范围中引用了这个变量。在完成 create 方法后，该方法将闭包返回给 main 中的调用方。
 * 在此过程中，它从自己的堆栈中删除变量 value，而且 lambda 表达式将会执行。
 */
class Sample2 {
    public static Runnable create() {
        int value = 4;
        Runnable runnable = () -> System.out.println(value);

        System.out.println("exiting create");
        return runnable;
    }

    public static void main(String[] args) {
        Runnable runnable = create();

        System.out.println("In main");
        runnable.run();
    }
}