package com.quyc.learn.lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Created by quyuanchao on 2019/4/21 16:01.
 * <p>Title: Simple Lambda</p>
 * <p>Description: Learn Lambda</p>
 */
public class SimpleLambda {

    /**
     * // 1. 不需要参数,返回值为 5
     * () -> 5
     * <p>
     * // 2. 接收一个参数(数字类型),返回其2倍的值
     * x -> 2 * x
     * <p>
     * // 3. 接受2个参数(数字),并返回他们的差值
     * (x, y) -> x – y
     * <p>
     * // 4. 接收2个int型整数,返回他们的和
     * (int x, int y) -> x + y
     * <p>
     * // 5. 接受一个 string 对象,并在控制台打印,不返回任何值(看起来像是返回void)
     * (String s) -> System.out.print(s)
     */
    public static void main(String[] args) {
//        basicLambda();
//        threadLearn();
//        sortByLambda();
        sortByLambda2();
    }

    /**
     * lambda 基本用法，遍历集合
     */
    public static void basicLambda() {
        String[] atp = {"Rafael Nadal", "Novak Djokovic",
                "Stanislas Wawrinka",
                "David Ferrer", "Roger Federer",
                "Andy Murray", "Tomas Berdych",
                "Juan Martin Del Potro"};
        List<String> players = Arrays.asList(atp);

        // 以前的循环方式
        for (String player : players) {
            System.out.print(player + "; ");
        }

        // 使用 lambda 表达式以及函数操作(functional operation)
        players.forEach((player) -> System.out.println("player = " + player));

        // 在 Java 8 中使用双冒号操作符(double colon operator)
        players.forEach(System.out::println);
    }

    /**
     * lambda 创建匿名内部类
     */
    public static void threadLearn() {
        // 1.1 使用匿名内部类
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(" hello world ");
            }
        }).start();
        // 1.2 使用 lambda expression
        new Thread(() -> System.out.println("hello world!")).start();

        // 2.1 使用匿名内部类
        Runnable task1 = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world!!");
            }
        };

        // 2.2 使用 lambda expression
        Runnable task2 = () -> System.out.println("hello world!!!");

        new Thread(task1).start();
        new Thread(task2).start();
    }

    /**
     * Sort by lambda.
     */
    public static void sortByLambda() {
        String[] players = {"Rafael Nadal", "Novak Djokovic",
                "Stanislas Wawrinka", "David Ferrer",
                "Roger Federer", "Andy Murray",
                "Tomas Berdych", "Juan Martin Del Potro",
                "Richard Gasquet", "John Isner"};

        // 1.1 使用匿名内部类根据 name 排序 players
        List<String> playerList = Arrays.asList(players);
        playerList.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        System.out.println("playerList = " + playerList);
        System.out.println("Andy Murray".compareTo("David Ferrer"));

        // 1.2 使用 lambda expression
        Comparator<String> stringComparator = (s1, s2) -> s1.compareTo(s2);
        playerList.sort(stringComparator);
        System.out.println("playerList = " + playerList);
        // 1.3 使用 Arrays 工具类直接对数据进行排序
        Arrays.sort(players, (s1, s2) -> s1.compareTo(s2));
        Arrays.sort(players, String::compareTo);
        for (String player : players) {
            System.out.println("player = " + player);
        }
        playerList.forEach((player) -> System.out.println(player));
    }

    /**
     * Sort by lambda 2.
     */
    public static void sortByLambda2() {
        String[] players = {"Rafael Nadal", "Novak Djokovic",
                "Stanislas Wawrinka", "David Ferrer",
                "Roger Federer", "Andy Murray",
                "Tomas Berdych", "Juan Martin Del Potro",
                "Richard Gasquet", "John Isner"};
        // 1.1 使用匿名内部类根据 surname 排序 players
        Arrays.sort(players, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.substring(o1.indexOf(" ")).compareTo(o2.substring(o1.indexOf(" ")));
            }
        });
        for (String player : players) {
            System.out.print(player + "; ");
        }
        System.out.println();
        String[] players2 = {"Rafael Nadal", "Novak Djokovic",
                "Stanislas Wawrinka", "David Ferrer",
                "Roger Federer", "Andy Murray",
                "Tomas Berdych", "Juan Martin Del Potro",
                "Richard Gasquet", "John Isner"};
        Arrays.sort(players2, (o1, o2) -> o1.substring(o1.indexOf(" ")).compareTo(o2.substring(o1.indexOf(" "))));
        for (String player : players2) {
            System.out.print(player + "; ");
        }
    }



}
