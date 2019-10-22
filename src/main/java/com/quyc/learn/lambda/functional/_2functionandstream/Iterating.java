package com.quyc.learn.lambda.functional._2functionandstream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: andy
 * @create: 2019/4/23 16:21
 * @description: 迭代和排序
 */
public class Iterating {

    public static void main(String[] args) {
        List<String> modelsAfter2000UsingFor = getModelsAfter2000UsingFor(createCars());
        for (String s : modelsAfter2000UsingFor) {
            System.out.println(s);
        }
        List<String> modelsAfter2000UsingLambda = getModelsAfter2000UsingPipeline(createCars());
        for (String s : modelsAfter2000UsingLambda) {
            System.out.println(s);
        }
    }

    public static List<Car> createCars() {
        return Arrays.asList(
                new Car("Jeep", "Wrangler", 2011),
                new Car("Jeep", "Comanche", 1990),
                new Car("Dodge", "Avenger", 2010),
                new Car("Buick", "Cascada", 2016),
                new Car("Ford", "Focus", 2012),
                new Car("Chevrolet", "Geo Metro", 1992)
        );
    }

    /**
     * 使用增强 for 循环
     *
     * @param cars
     * @return
     */
    private static List<String> getModelsAfter2000UsingFor(List<Car> cars) {
        List<Car> carList = new ArrayList<>();
        for (Car car : cars) {
            if (car.getYear() > 2000) {
                carList.add(car);
            }
        }
        Collections.sort(carList, new Comparator<Car>() {
            @Override
            public int compare(Car o1, Car o2) {
                return o1.getYear() - o2.getYear();
            }
        });
        List<String> models = new ArrayList<>();
        for (Car car : carList) {
            models.add(car.getModel());
        }
        return models;
    }

    /**
     * 使用集合管道进行迭代和排序
     * <p>
     * 在函数编程中，通常会通过一系列更小的模块化函数或运算来对复杂运算进行排序。
     * 该系列被称为函数组合（composition of functions, or a function composition）。
     * 当一个数据集合流经一个函数组合时，它就变成一个集合管道。
     * 函数组合和集合管道是函数式编程中常用的两种设计模式。
     * <p>
     * <p>
     * 两种方法的显著区别是：
     * 1. 函数式代码比命令式代码更简洁。
     * 2. 函数式代码不会表现出明显的易变性，而且使用了更少的垃圾变量。
     * 3. 第二个方法中使用的函数/方法都是有返回值的表达式。将此方法与 清单 3 中的 Collections.sort 方法进行对比。
     * 4. getModelsAfter2000UsingPipeline 使用了集合管道模式，而且非常富于表达。
     * <p>
     * getModelsAfter2000UsingPipeline 中的代码如此简洁且富于表达，部分原因是使用了方法引用：Car::getModel。
     *
     * @param cars
     * @return
     */
    private static List<String> getModelsAfter2000UsingPipeline(List<Car> cars) {
        return cars.stream()
                .filter(c -> c.getYear() > 2000)
//                .sorted((c1, c2) -> c1.getYear() - c2.getYear())
                .sorted(Comparator.comparingInt(Car::getYear))
                .map(Car::getModel)
                .collect(Collectors.toList());
    }
}
