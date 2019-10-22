package com.quyc.learn.lambda.functional._4helpfulcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: andy
 * @create: 2019/4/23 19:23
 * @description: 使用有帮助的代码
 *
 * 尽管可能看似可有可无，但遵循 Java 8 的对齐约定肯定非常有益。
 *
 * 1. 遵循此约定的代码更容易阅读、理解和解释。我们可以在详细检查每部分之前，快速掌握整个目标。
 * 2. 元素非常明确且容易找到，有助于更快地修改。如果我们想包含另一个条件，或者删除或修改一个现有条件，那么可以相对容易找到该行并执行更改。
 * 3. 该代码更容易维护，这表明我们关心团队的其他开发人员。除了让代码更容易维护之外，编写有帮助的代码还能显著提高团队士气。
 */
public class HelpfulCode {

    public static void main(String[] args) {
        // 比较下面两种编码方式可以得出：理解命令式代码所用的平均时间比理解函数式代码所用的平均时间长 30 秒
//        imperativeCode();
//        functionalCode();
        readability();
    }

    /**
     * 函数式编程
     */
    private static void functionalCode() {
        List<String> names = Arrays.asList("Jack", "Jill", "Nate", "Kara", "Kim", "Jullie", "Paul", "Peter");

        System.out.println(
                names.stream()
                        .filter(name -> name.length() == 4)
                        .collect(Collectors.joining(", ")));
    }

    /**
     * 命令是编程
     */
    private static void imperativeCode() {
        List<String> names = Arrays.asList("Jack", "Jill", "Nate", "Kara", "Kim", "Jullie", "Paul", "Peter");

        List<String> subList = new ArrayList<>();
        for (String name : names) {
            if (name.length() == 4) {
                subList.add(name);
            }
        }

        StringBuilder namesOfLength4 = new StringBuilder();
        for (int i = 0; i < subList.size() - 1; i++) {
            namesOfLength4.append(subList.get(i));
            namesOfLength4.append(", ");
        }

        if (subList.size() > 1) {
            namesOfLength4.append(subList.get(subList.size() - 1));
        }

        System.out.println(namesOfLength4);
    }

    /**
     * 命令式编码和函数式编码的可读性比较
     */
    private static void readability() {
        // 命令式编码
        List<Integer> numbers = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        int sum = 0;
        for (Integer number : numbers) {
            if (number > 3 && number % 2 == 0 && number < 8) {
                sum += number * 2;
            }
        }
        System.out.println("sum = " + sum);
        // 函数式编码
        sum = numbers.stream()
                .filter(e -> e > 3)
                .filter(e -> e < 8)
                .filter(e -> e % 2 == 0)
                .mapToInt(e -> e * 2)
                .sum();
        System.out.println("sum = " + sum);
    }
}
