package com.quyc.learn.lambda.functional._1simplefunctional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: andy
 * @create: 2019/4/23 14:42
 * @description: 函数式编程示例，以及命令式编程、声明式编程和函数式编程之间的区别。
 * <p>
 * 函数式编程 = 声明式编程 + 高阶函数
 *
 * <a href=https://www.ibm.com/developerworks/cn/java/j-java8idioms1/index.html</a>
 */
public class SimpleFunctional {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("Dory", "Gill", "Bruce", "Nemo", "Darla", "Marlin", "Jacques");
        findNemoImperative(names);
        findNemoDeclarative(names);
        simpleFunctional();
    }

    /**
     * findNemoImperative() 方法首先初始化一个可变的flag 变量，也称为垃圾（garbage）变量。
     * 开发人员通常会随便给这些变量命名，比如 f、t 或 temp，这些名称表明了我们对这些变量不应存在的一般态度。
     * <p>
     * 因为这是一个命令式编程的程序 — 许多 Java 开发人员最熟悉的格式 — 所以您需要定义程序的每一步：告诉它迭代每个元素，比较值，设置 flag 变量，然后跳出循环。
     * 命令式格式为您提供了完全的控制权，这有时是件好事。而另一方面，您需要执行所有工作。在许多情况下，可以减少工作量来提高效率。
     *
     * @param names the names
     */
    public static void findNemoImperative(List<String> names) {
        boolean found = false;
        for (String name : names) {
            if (name.equals("Nemo")) {
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("Found Nemo");
        } else {
            System.out.println("Sorry, Nemo not found");
        }
    }

    /**
     * 声明式编程意味着，您仍会告诉程序要做什么，但将实现细节留给底层的函数库。
     * <p>
     * 训练自己以声明式编程思考，这将大大简化向 Java 中的函数式编程的过渡。
     * 为什么呢？因为函数式编程是以声明式为基础而建立的。声明式思考试图逐步从命令式编程过渡到函数式编程。
     *
     * @param names the names
     */
    public static void findNemoDeclarative(List<String> names) {
        if (names.contains("Nemo")) {
            System.out.println("Found Nemo");
        } else {
            System.out.println("Sorry, Nemo not found");
        }
    }


    /**
     * 尽管函数式格式的编程始终是声明式的，但简单地使用声明式编程并不等于函数式编程。这是因为函数式编程合并了声明式方法与高阶函数。
     */
    private static void simpleFunctional() {
        Map<String, Integer> pageVisits = new HashMap<>();
        String page = "https://agiledeveloper.com";
        incrementPageVisit(pageVisits, page);
        incrementPageVisitFunctional(pageVisits, page);
        System.out.println(pageVisits.get(page));
    }

    /**
     * 使用命令式编程实现的页面浏览量递增功能
     * <p>
     * 声明式思考要求您将此方法的设计思路从 “如何做” 转变为 “做什么”。
     * 当调用方法 incrementPageVisit() 时，您希望将给定页面的计数初始化为 1 或将运行值递增 1。这就是做什么。
     *
     * @param pageVisits
     * @param page
     */
    private static void incrementPageVisit(Map<String, Integer> pageVisits, String page) {
        if (!pageVisits.containsKey(page)) {
            pageVisits.put(page, 0);
        }
        pageVisits.put(page, pageVisits.get(page) + 1);
    }

    /**
     * 您在执行声明式编程，所以下一步是扫描 JDK 库，查找 Map 接口中可以完成您的目标的方法 — 也就是说，寻找一个知道如何 完成给定任务的内置方法。
     * merge() 方法能完美实现您的目标，merge() 是一个高阶函数，所以新代码实际上是一个不错的函数式编程示例。
     * <p>
     * page 作为第一个参数传递给 merge()：该键的值应该更新。第二个参数是给该键分配的初始值，如果Map 中不存在该键（在本例中，该值为 “1”）。
     * 第三个参数（一个拉姆达表达式）接收 map 中该键对应的值作为其参数，并且将该值作为变量传递给 merge 方法中的第二个参数。
     * 这个拉姆达表达式返回的是它的参数的和，实际上就是递增计数。
     * <p>
     * 在 Java 程序中采用函数式方法和语法有许多好处：代码简洁，更富于表达，不易出错，更容易并行化，而且通常比面向对象的代码更容易理解。
     * 我们面临的挑战在于将思维方式从命令式编程 — 绝大多数开发人员都熟悉它 — 转变为声明式思考。
     * <p>
     * 尽管函数式编程不那么容易或直观，但您可以通过学习关注您想要程序实现的目的 而不是关注您希望它执行的方式，从而实现思维上的巨大飞跃。
     * 通过允许底层函数库管理执行代码，您将逐步直观地了解高阶函数，它们是函数式编程的构建基块。
     *
     * @param pageVisits
     * @param page
     */
    private static void incrementPageVisitFunctional(Map<String, Integer> pageVisits, String page) {
        pageVisits.merge(page, 1, Integer::sum);
    }
}
