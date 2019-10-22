package com.quyc.learn.lambda.functional._8TypeDeduce;

import java.util.Comparator;
import java.util.stream.IntStream;


/**
 * @author: andy
 * @create: 2019/5/5 20:40
 * @description: 类型推断
 * <p>
 * 由于 Java 是静态类型语言，它需要在编译时知道所有对象和变量的类型。在 lambda 表达式的参数列表中省略类型并不会让 Java 更接近动态类型语言。
 * 但是，添加适当的类型推断功能会让 Java 更接近其他静态类型语言，比如 Scala 或 Haskell。
 */
public class TypeDeduce {

    public static void main(String[] args) {
        simpleTypeDeduce();
    }

    public static void simpleTypeDeduce() {
        IntStream.rangeClosed(0, 5).forEach((int i) -> System.out.println(i * 2));
        // 不再生命为 int 类型，由编译器推断出 i 的类型
        IntStream.rangeClosed(0, 5).forEach(i -> System.out.println(i * 2));
        // 无法编译成功
//        IntStream.rangeClosed(0, 5).forEach(i -> System.out.println(i.length() * 2));
    }

    /**
     * comparing 方法采用了 1 个参数。它期望使用 Function<? super T, ? extends U> 并返回 Comparator<T>。
     * 因为 comparing 是 Comparator<T> 上的一个静态方法，所以编译器目前没有关于 T 或 U 可能是什么的线索。
     * <p>
     * 为了解决此问题，编译器稍微扩展了推断范围，将范围扩大到传递给 comparing 方法的参数之外。
     * 它观察我们是如何处理调用 comparing 的结果的。根据此信息，编译器确定我们仅返回该结果。
     * 接下来，它看到由 comparing 返回的 Comparator<T> 又作为 Comparator<Car> 由 createComparator 返回 。
     * <p>
     * 注意了！编译器现在已明白我们的意图：它推断应该将 T 绑定到 Car。根据此信息，它知道 lambda 表达式中的 car 参数的类型应该为 Car。
     *
     * @return
     */
    public static Comparator<Car> createComparator() {
//        return Comparator.comparing((Car car) -> car.getRegistration());
        return Comparator.comparing(Car::getRegistration);
    }

    /**
     * 在修改过后的版本中，我们将传递 comparable 的结果作为调用 reversed() 的目标。
     * comparable 返回 Comparable<T>，reversed() 没有展示任何有关 T 的可能含义的额外信息。根据此信息，编译器推断 T 的类型肯定是 Object。
     * 遗憾的是，此信息对于该代码而言并不足够，因为 Object 缺少我们在 lambda 表达式中调用的 getRegistration() 方法。
     *
     * @return
     */
    public static Comparator<Car> createComparatorTwo() {
//        return Comparator.comparing(car -> car.getRegistration()).reversed();
        /**
         * 使用方法引用可以很好地解决这个问题
         */
        return Comparator.comparing(Car::getRegistration).reversed();
    }

}

class Car {
    public String getRegistration() {
        return null;
    }
}