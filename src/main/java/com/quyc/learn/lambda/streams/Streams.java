package com.quyc.learn.lambda.streams;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by quyuanchao on 2019/4/21 16:49.
 * <p>Title: Streams Lambda</p>
 * <p>Description: Streams Learn</p>
 * Stream 的总结：
 * 1. 不是数据结构
 * 2. 它没有内部存储，它只是用操作管道从 source（数据结构、数组、generator function、IO channel）抓取数据。
 * 3. 它也绝不修改自己所封装的底层数据结构的数据。例如 Stream 的 filter 操作会产生一个不包含被过滤元素的新 Stream，而不是从 source 删除那些元素。
 * 4. 所有 Stream 的操作必须以 lambda 表达式为参数
 * 5. 不支持索引访问
 * 6. 你可以请求第一个元素，但无法请求第二个，第三个，或最后一个。不过请参阅下一项。
 * 7. 很容易生成数组或者 List
 * 8. 惰性化
 * 9. 很多 Stream 操作是向后延迟的，一直到它弄清楚了最后需要多少数据才会开始。
 * 10. Intermediate 操作永远是惰性化的。
 * 11. 并行能力
 * 12. 当一个 Stream 是并行化的，就不需要再写多线程代码，所有对它的操作会自动并行进行的。
 * 13. 可以是无限的
 * 14. 集合有固定大小，Stream 则不必。limit(n) 和 findFirst() 这类的 short-circuiting 操作可以对无限的 Stream 进行运算并很快完成。
 */
public class Streams {
    private static List<Person> javaProgrammers = new ArrayList<Person>() {
        {
            add(new Person("Elsdon", "Jaycob", "Java programmer", "male", 43, 2000));
            add(new Person("Tamsen", "Brittany", "Java programmer", "female", 23, 1500));
            add(new Person("Floyd", "Donny", "Java programmer", "male", 33, 1800));
            add(new Person("Sindy", "Jonie", "Java programmer", "female", 32, 1600));
            add(new Person("Vere", "Hervey", "Java programmer", "male", 22, 1200));
            add(new Person("Maude", "Jaimie", "Java programmer", "female", 27, 1900));
            add(new Person("Shawn", "Randall", "Java programmer", "male", 30, 2300));
            add(new Person("Jayden", "Corrina", "Java programmer", "female", 35, 1700));
            add(new Person("Palmer", "Dene", "Java programmer", "male", 33, 2000));
            add(new Person("Addison", "Pam", "Java programmer", "female", 34, 1300));
        }
    };

    private static List<Person> phpProgrammers = new ArrayList<Person>() {
        {
            add(new Person("Jarrod", "Pace", "PHP programmer", "male", 34, 1550));
            add(new Person("Clarette", "Cicely", "PHP programmer", "female", 23, 1200));
            add(new Person("Victor", "Channing", "PHP programmer", "male", 32, 1600));
            add(new Person("Tori", "Sheryl", "PHP programmer", "female", 21, 1000));
            add(new Person("Osborne", "Shad", "PHP programmer", "male", 32, 1100));
            add(new Person("Rosalind", "Layla", "PHP programmer", "female", 25, 1300));
            add(new Person("Fraser", "Hewie", "PHP programmer", "male", 36, 1100));
            add(new Person("Quinn", "Tamara", "PHP programmer", "female", 21, 1000));
            add(new Person("Alvin", "Lance", "PHP programmer", "male", 38, 1600));
            add(new Person("Evonne", "Shari", "PHP programmer", "female", 40, 1800));
        }
    };

    public static void main(String[] args) {
//        simpleForeach();
//        learnFilter();
//        learnLimit();
//        streamSort();
//        minOrMax();
//        collectMap();
//        parallel();
//        summaryStatistics();
//        flatMap();
//        takeDropWhile();
//        groupingBy();
//        matchDemo();
//        partitioningBy();
        parallelForEach();
    }

    /**
     * 流的构造与转换
     * <p>
     * 集合和数组
     * 1. Collection.stream()
     * 2. Collection.parallelStream()
     * 3. Arrays.stream(T array) or Stream.of()
     * <p>
     * BufferedReader
     * 4. java.io.BufferedReader.lines()
     * <p>
     * 静态工厂
     * 5. java.util.stream.IntStream.range()
     * 6. java.nio.file.Files.walk()
     * <p>
     * 自己构建
     * 7. java.util.Spliterator
     * <p>
     * 其它
     * 8. Random.ints()
     * 9. BitSet.stream()
     * 10. Pattern.splitAsStream(java.lang.CharSequence)
     * 11. JarFile.stream()
     */
    public static void build() {
        // 1. Individual values
        Stream<String> stream = Stream.of("a", "b", "c");
        // 2. Arrays
        String[] strArray = new String[]{"a", "b", "c"};
        stream = Stream.of(strArray);
        stream = Arrays.stream(strArray);
        // 3. Collections
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();
        stream.forEach(System.out::println);
        // 4. 数值流的构造
        IntStream.of(1, 2, 3).forEach(System.out::println);
        IntStream.range(1, 3).forEach(System.out::println);
        IntStream.rangeClosed(1, 3).forEach(System.out::println);
        // 5. 流转换为其它数据结构
        // 5.1. Array
        String[] strArray1 = stream.toArray(String[]::new);
        // 5.2. Collection
        List<String> list1 = stream.collect(Collectors.toList());
        List<String> list2 = stream.collect(Collectors.toCollection(ArrayList::new));
        Set set1 = stream.collect(Collectors.toSet());
        Stack stack1 = stream.collect(Collectors.toCollection(Stack::new));
        // 5.3. String
        String str = stream.collect(Collectors.joining());
    }

    /**
     * 流的操作
     * 接下来，当把一个数据结构包装成 Stream 后，就要开始对里面的元素进行各类操作了。常见的操作可以归类如下。
     * <p>
     * 1. Intermediate：一个流可以后面跟随零个或多个 intermediate 操作。
     * 其目的主要是打开流，做出某种程度的数据映射/过滤，然后返回一个新的流，交给下一个操作使用。
     * 这类操作都是惰性化的（lazy），就是说，仅仅调用到这类方法，并没有真正开始流的遍历。
     * map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 limit、 skip、 parallel、 sequential、 unordered
     * <p>
     * 2. Terminal：一个流只能有一个 terminal 操作，当这个操作执行后，流就被使用“光”了，无法再被操作。
     * 所以这必定是流的最后一个操作。Terminal 操作的执行，才会真正开始流的遍历，并且会生成一个结果，或者一个 side effect。
     * forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、 anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator
     * <p>
     * 3. Short-circuiting：当操作一个无限大的 Stream，而又希望在有限时间内完成操作。
     * anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 limit
     */

    /**
     * printf 格式
     * %c        单个字符
     * %d        十进制整数
     * %f        十进制浮点数
     * %o        八进制数
     * %s        字符串
     * %u        无符号十进制数
     * %x        十六进制数
     * %%        输出百分号%
     */
    private static void simpleForeach() {
        System.out.println("所有程序员的姓名：");
        javaProgrammers.forEach((p) -> System.out.printf("%s %s; ", p.getFirstName(), p.getLastName()));
        System.out.println();
        phpProgrammers.forEach((p) -> System.out.printf("%s %s; ", p.getFirstName(), p.getLastName()));
        System.out.println();
        System.out.println("给程序员加薪 5% :");
        javaProgrammers.forEach(new Consumer<Person>() {
            @Override
            public void accept(Person p) {
                p.setSalary(p.getSalary() / 100 * 5 + p.getSalary());
            }
        });
        phpProgrammers.forEach((p) -> p.setSalary(p.getSalary() / 100 * 5 + p.getSalary()));
        javaProgrammers.forEach((p) -> System.out.printf("%s %s %d; ", p.getFirstName(), p.getLastName(), p.getSalary()));
        System.out.println();
        phpProgrammers.forEach((p) -> System.out.printf("%s %s %d; ", p.getFirstName(), p.getLastName(), p.getSalary()));
    }

    /**
     * 显示月薪超过1400美元的PHP程序员
     */
    private static void learnFilter() {
        System.out.println("下面是月薪超过 $1,400 的PHP程序员:");
        phpProgrammers.stream().filter((p) -> p.getSalary() > 1400)
                .forEach((p) -> System.out.printf("%s %s %d; ", p.getFirstName(), p.getLastName(), p.getSalary()));
        System.out.println();
        System.out.println("下面是年龄大于 24岁且月薪在$1,400以上的女PHP程序员:");
//        Predicate<Person> ageFilter = (p) -> p.getAge() > 24;
        Predicate<Person> ageFilter = new Predicate<Person>() {
            @Override
            public boolean test(Person person) {
                return person.getAge() > 24;
            }
        };
        Predicate<Person> salaryFilter = (p) -> p.getSalary() > 1400;
        Predicate<Person> genderFilter = (p) -> "female".equals(p.getGender());
        phpProgrammers.stream().filter(ageFilter).filter(salaryFilter).filter(genderFilter)
                .forEach((p) -> System.out.printf("%s %s %d; ", p.getFirstName(), p.getLastName(), p.getSalary()));

    }

    /**
     * 使用limit方法，可以限制结果集的个数
     */
    private static void learnLimit() {
        Predicate<Person> genderFilter = (p) -> "female".equals(p.getGender());
        System.out.println("最前面的3个 Java programmers:");
        javaProgrammers.stream().limit(3).forEach((p) -> System.out.printf("%s %s; ", p.getFirstName(), p.getLastName()));
        System.out.println();
        System.out.println("最前面的3个女性 Java programmers:");
        // 链式处理的先后顺序决定了最后的结果，要注意先后顺序
        javaProgrammers.stream().filter(genderFilter).limit(3).forEach((p) -> System.out.printf("%s %s; ", p.getFirstName(), p.getLastName()));
    }

    /**
     * 使用 stream 进行排序
     */
    private static void streamSort() {
        System.out.println("根据 name 排序,并显示前5个 Java programmers:");
        List<Person> sortByName = javaProgrammers
                .stream()
                .sorted((p1, p2) -> p1.getFirstName().compareTo(p2.getFirstName()))
                .limit(5)
                .collect(Collectors.toList());
        sortByName.forEach((p) -> System.out.printf("%s %s; %n", p.getFirstName(), p.getLastName()));
        System.out.println("根据 salary 排序 Java programmers:");
        List<Person> sortBySalary = javaProgrammers
                .stream()
                .sorted((p1, p2) -> p1.getSalary() - p2.getSalary())
                .collect(Collectors.toList());
        sortBySalary.forEach((p) -> System.out.printf("%s %s %d; ", p.getFirstName(), p.getLastName(), p.getSalary()));
    }

    /**
     * 使用 stream 取最大或最小值
     */
    private static void minOrMax() {
        System.out.println("工资最低的 Java programmer:");
        Person minSalary = javaProgrammers.stream().min((p1, p2) -> p1.getSalary() - p2.getSalary()).get();
        System.out.printf("%s %s %d", minSalary.getFirstName(), minSalary.getLastName(), minSalary.getSalary());
        System.out.println();
        System.out.println("工资最高的 Java programmer:");
        Person maxSalary = javaProgrammers.stream().max((p1, p2) -> p1.getSalary() - p2.getSalary()).get();
        System.out.printf("%s %s %d", maxSalary.getFirstName(), maxSalary.getLastName(), maxSalary.getSalary());
    }

    /**
     * 使用 collect 和 map 对集合数据进行抽取
     */
    private static void collectMap() {
        System.out.println("将 PHP programmers 的 first name 拼接成字符串:");
        String phpFirstNames = phpProgrammers.stream().map(Person::getFirstName).collect(Collectors.joining("; "));
        System.out.println("phpFirstNames = " + phpFirstNames);
        System.out.println("将 Java programmers 的 first name 存放到 Set:");
        Set<String> javaFirstNameSet = javaProgrammers.stream().map(Person::getFirstName).collect(Collectors.toSet());
        javaFirstNameSet.forEach(System.out::println);
        System.out.println("将 Java programmers 的 first name 存放到 TreeSet:");
        Collection<String> javaFirstNameTreeSet = javaProgrammers.stream().map(Person::getFirstName).collect(Collectors.toCollection(TreeSet::new));
        javaFirstNameTreeSet.forEach(System.out::println);
    }

    /**
     * parallel
     */
    private static void parallel() {
        System.out.println("计算付给 Java programmers 的所有money:");
        int sumSalary = javaProgrammers.parallelStream().mapToInt(Person::getSalary).sum();
        System.out.println("sumSalary = " + sumSalary);
        int sum = javaProgrammers.stream().mapToInt(Person::getSalary).sum();
        System.out.println("sum = " + sum);
    }

    /**
     * parallelForEach
     */
    private static void parallelForEach() {
        Map<String, List<Person>> listMap = new HashMap<>();
        for (int i = 0; i < 10; i++) {
            listMap.put("java" + i, javaProgrammers);
        }
        AtomicInteger num = new AtomicInteger(0);
        listMap.entrySet().parallelStream().forEach(stringListEntry -> {
            num.getAndAdd(1);
            System.out.println("num = " + num);
        });
    }

    /**
     * 使用 SummaryStatistics 获取集合的一些统计数据
     */
    private static void summaryStatistics() {
        //计算 count, min, max, sum, and average for numbers
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        IntSummaryStatistics intSummaryStatistics = numbers.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("intSummaryStatistics.getAverage() = " + intSummaryStatistics.getAverage());
        System.out.println("intSummaryStatistics.getCount() = " + intSummaryStatistics.getCount());
        System.out.println("intSummaryStatistics.getMax() = " + intSummaryStatistics.getMax());
        System.out.println("intSummaryStatistics.getMin() = " + intSummaryStatistics.getMin());
        System.out.println("intSummaryStatistics.getSum() = " + intSummaryStatistics.getSum());
    }

    /**
     * flatMap()方法通过一个 Function 把一个元素类型为 T 的流中的每个元素转换成一个元素类型为 R 的流，再把这些转换之后的流合并。
     */
    public static void flatMap() {
        Stream.of(1, 2, 3)
                .map(v -> v + 1)
                .flatMap(v -> Stream.of(v * 5, v * 10))
                .forEach(System.out::println);
        // 输出 10，20，15，30，20，40
    }

    /**
     * takeWhile:从第一个元素开始匹配，直到第一个不符合规则的元素为止，取前面符合要求的元素集合
     * dropWhile:从第一个元素开始匹配，直到第一个不符合规则的元素为止，取后面所有元素集合
     */
    private static void takeDropWhile() {
        List<Integer> list = Arrays.asList(45, 43, 76, 87, 42, 77, 90, 73, 67, 88);
        list.stream().dropWhile(x -> x < 80).forEach(System.out::println);
        System.out.println();
        List<Integer> list1 = Arrays.asList(45, 43, 76, 87, 42, 77, 90, 73, 67, 88);
        list1.stream().takeWhile(x -> x < 80).forEach(System.out::println);
    }

    /**
     * groupingBy 对流中元素进行分组，分组时对流中所有元素应用同一个 Function。
     * 具有相同结果的元素被分到同一组。分组之后的结果是一个 Map，Map 的键是应用 Function 之后的结果，而对应的值是属于该组的所有元素的 List。
     */
    public static void groupingBy() {
        Map<Character, List<String>> names = Stream.of("Alex", "Bob", "David", "Amy").collect(Collectors.groupingBy(v -> v.charAt(0)));
        String nameStr = Stream.of("Alex", "Bob", "David", "Amy").collect(Collectors.joining(","));
        System.out.println("names = " + names);
        // {A=[Alex, Amy], B=[Bob], D=[David]}
        System.out.println("nameStr = " + nameStr);
        // Alex,Bob,David,Amy
        HashSet<String> nameHashSet = Stream.of("Alex", "Bob", "David", "Amy").collect(Collectors.toCollection(HashSet::new));
        System.out.println("nameHashSet = " + nameHashSet);
    }

    /**
     * partitioningBy 根据 Predicate 获得二值型数据结构，通过 get(true/false) 可获得对应的集合
     */
    public static void partitioningBy() {
        Map<Boolean, List<Person>> javaer = javaProgrammers.stream().collect(Collectors.partitioningBy(person -> "female".equals(person.getGender())));
        javaer.forEach((aBoolean, people) -> System.out.println(aBoolean + ": " + people.stream().map(Person::getFirstName).collect(Collectors.toList())));
        System.out.println(javaer.get(false).stream().map(Person::getFirstName).collect(Collectors.toList()));
        System.out.println(javaer.get(true).stream().map(Person::getFirstName).collect(Collectors.toList()));
    }

    /**
     * 1. allMatch：Stream 中全部元素符合传入的 predicate，返回 true
     * 2. anyMatch：Stream 中只要有一个元素符合传入的 predicate，返回 true
     * 3. noneMatch：Stream 中没有一个元素符合传入的 predicate，返回 true
     */
    public static void matchDemo() {
        boolean allMatch = javaProgrammers.stream().allMatch(person -> person.getAge() > 20);
        System.out.println("allMatch = " + allMatch);
        boolean anyMatch = javaProgrammers.stream().anyMatch(person -> person.getAge() > 20);
        System.out.println("anyMatch = " + anyMatch);
        boolean noneMatch = javaProgrammers.stream().noneMatch(person -> person.getAge() > 20);
        System.out.println("noneMatch = " + noneMatch);
    }
}
