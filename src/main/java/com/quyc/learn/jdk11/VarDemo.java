package com.quyc.learn.jdk11;

/**
 * @author: andy
 * @create: 2019/5/9 16:20
 * @description: 类型推断
 * 从 Java 10 开始，便引入了局部变量类型推断这一关键特性。
 * 类型推断允许使用关键字 var 作为局部变量的类型而不是实际类型，编译器根据分配给变量的值推断出类型。
 * java 10 中有以下几个限制：
 * 1. 只能用于局部变量上
 * 2. 声明时必须初始化
 * 3. 不能用作方法参数
 * 4. 不能在 Lambda 表达式中使用
 *
 * Java 11 与 Java 10 的不同之处在于允许开发者在 Lambda 表达式中使用 var 进行参数声明。
 */
public class VarDemo {

    public static void main(String[] args) {
        var s = "hello world";
        System.out.println("s = " + s);
        System.out.println("s.getClass().getTypeName() = " + s.getClass().getTypeName());
    }

}
