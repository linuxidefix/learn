package com.quyc.learn.lambda.functional._9cascade_lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * The type Cascade lambda.
 *
 * @author: andy
 * @create: 2019 /5/6 9:48
 * @description: 级联Lambda表达式
 */
public class CascadeLambda {

    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        System.out.println(totalSelectedValues(integers, integer -> integer % 2 == 0));
        // 使用接口函数
        Predicate<Integer> isOdd = createIsOdd();
        Predicate<Integer> negate = isOdd.negate();
        System.out.println(isOdd.test(5));
        System.out.println(negate.test(5));
        System.out.println(isOdd.and(integer -> integer % 3 == 0).test(15));
        reuseLambda();
        reuseLambda2();
    }

    /**
     * 一个接收函数的函数
     * 只要一个函数接口显示为一个函数的参数的类型，您看到的就是一个高阶函数
     *
     * @param values
     * @param selector
     * @return
     */
    public static int totalSelectedValues(List<Integer> values, Predicate<Integer> selector) {
        return values.stream()
                .filter(selector)
                .reduce(0, Integer::sum);
    }

    /**
     * 一个返回函数的函数
     *
     * @return
     */
    public static Predicate<Integer> createIsOdd() {
        return integer -> integer % 2 != 0;
    }

    /**
     * .filter(integer -> integer > 3) 这个表达式重复出现两次，可以保存表达式到一个引用，重复使用
     */
    public static void reuseLambda() {
        ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        List<Integer> list1 = integers.stream()
                .filter(integer -> integer > 3)
                .collect(toList());
        System.out.println("list1 = " + list1);
        List<Integer> list2 = integers.stream()
                .filter(integer -> integer > 3)
                .map(integer -> integer * 2)
                .collect(toList());
        System.out.println("list2 = " + list2);
        Predicate<Integer> greaterThan3 = integer -> integer > 3;
        list1 = integers.stream().filter(greaterThan3).collect(toList());
        System.out.println("list1 = " + list1);
        list2 = integers.stream().filter(greaterThan3).map(integer -> integer * 2).collect(toList());
        System.out.println("list2 = " + list2);
    }

    /**
     * 尽管下面每个 lambda 表达式将输入与一个不同的值比较，但它们做的事情完全相同。如何以较少的重复来重写此代码？
     * <p>
     * 函数接口 Function<T, U> 将一个 T 类型的输入转换为 U 类型的输出。
     * <p>
     * Function<Integer, Predicate<Integer>> predicateFunction = pivot -> candidate -> candidate > pivot;
     * 外部 lambda 表达式接收 pivot 作为参数，内部 lambda 表达式接收 candidate 作为参数。
     * 内部 lambda 表达式的主体同时使用它收到的参数 (candidate) 和来自外部范围的参数。
     * 也就是说，内部 lambda 表达式的主体同时依靠它的参数和它的词法范围或定义范围。
     * <p>
     * 读者如何理解一个级联 lambda 表达式？
     * <p>
     * 看到一个只有一个向右箭头 (->) 的 lambda 表达式时，您应该知道您看到的是一个匿名函数，它接受参数（可能是空的）并执行一个操作或返回一个结果值。
     * 看到一个包含两个向右箭头 (->) 的 lambda 表达式时，您看到的也是一个匿名函数，但它接受参数（可能是空的）并返回另一个 lambda 表达式。
     * 返回的 lambda 表达式可以接受它自己的参数或者可能是空的。它可以执行一个操作或返回一个值。
     * 它甚至可以返回另一个 lambda 表达式，但这通常有点大材小用，最好避免。
     */
    public static void reuseLambda2() {
        ArrayList<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
        List<Integer> valuesOver2 = integers.stream()
                .filter(e -> e > 2)
                .collect(toList());
        System.out.println("valuesOver2 = " + valuesOver2);
        List<Integer> valuesOver5 = integers.stream()
                .filter(e -> e > 5)
                .collect(toList());
        System.out.println("valuesOver5 = " + valuesOver5);
        List<Integer> valuesOver7 = integers.stream()
                .filter(e -> e > 7)
                .collect(toList());
        System.out.println("valuesOver7 = " + valuesOver7);
        // 下面的lambda表达式显得有些凌乱，可以继续简化为级联lambda表达式
//        Function<Integer, Predicate<Integer>> predicateFunction = pivot -> {
//            return candidate -> candidate > pivot;
//        };
        // 简化后的级联lambda表达式
        Function<Integer, Predicate<Integer>> predicateFunction = pivot -> candidate -> candidate > pivot;
        valuesOver2 = integers.stream().filter(predicateFunction.apply(2)).collect(Collectors.toList());
        System.out.println("valuesOver2 = " + valuesOver2);
        valuesOver5 = integers.stream().filter(predicateFunction.apply(5)).collect(Collectors.toList());
        System.out.println("valuesOver5 = " + valuesOver5);
        valuesOver7 = integers.stream().filter(predicateFunction.apply(7)).collect(Collectors.toList());
        System.out.println("valuesOver7 = " + valuesOver7);

    }
}
