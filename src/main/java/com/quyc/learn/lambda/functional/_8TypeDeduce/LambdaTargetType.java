package com.quyc.learn.lambda.functional._8TypeDeduce;

/**
 * @author: andy
 * @create: 2019/5/7 20:22
 * @description: 目标类型
 *
 * Lambda 表达式没有类型信息。一个 Lambda 表达式的类型由编译器根据其上下文环境在编译时刻推断得来。
 * 由上下文环境所确定的类型称为目标类型，像 lambda 这样类型由目标类型确定的表达式称为多态表达式（poly expression）
 */
public class LambdaTargetType {

    interface S {

    }

    @FunctionalInterface
    interface A extends S {
        void a();
    }

    @FunctionalInterface
    interface B extends S {
        void b();
    }

    static class UseAB {
        void use(A a) {
            System.out.println("Use A");
        }

        void use(B b) {
            System.out.println("Use B");
        }
    }

    /**
     * 由于 Lambda 表达式的类型由目标类型确定，在可能出现歧义的情况下，可能有多个类型满足要求，编译器无法独自完成类型推断。
     * 1. 一个常见的做法是显式地把 Lambda 表达式赋值给一个类型确定的变量。
     * 2. 另外一种做法是显示的指定类型。
     * @param args
     */
    public static void main(String[] args) {
        UseAB useAB = new UseAB();
        // no target method found
//        S s = () -> System.out.println("Use");
        A a = () -> System.out.println("Use");
        useAB.use(a);
    }

}
