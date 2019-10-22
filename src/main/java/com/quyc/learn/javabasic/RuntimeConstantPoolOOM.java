package com.quyc.learn.javabasic;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * 运行时常量池导致的内存溢出
 * VM: -XX:PermSize=10M -XX:MaxPermSize=10M
 * jdk1.6及之前的版本，常量池在永久代，可以通过上面的方法限制方法区大小，从而间接限制永久代大小，下面代码会出现 OutOfMemoryError:PermGen space
 * jdk1.7之后该代码会一直执行下去
 *
 * @author quyuanchao
 * @date 2018/11/5 23:46
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        // 使用List保持常量池引用，避免被Full GC回收常量池行为
        ArrayList<String> strings = new ArrayList<String>();
        // 10M的PermSize在integer范围内足够产生OOM了
        int i = 0;
        while (true) {
            // String.intern()方法是一个Native方法，如果字符串常量池中已经包含一个等于此String对象的字符串，则返回代表池中的这个字符串的引用
            // 否则会将此字符串添加到常量池中，并返回该字符串对象的引用。
            strings.add(String.valueOf(i++).intern());
        }
    }

    /**
     * jdk1.6: false/false
     * jdk1.7: true/false
     * 差异的原因是：
     * 1.6中intern()方法会将首次遇到的字符串实例复制到永久代中，返回的也是永久代中的这个字符串实例的引用，而由StringBuilder创建的字符串实例
     * 在java堆上，所以必然是不同的引用，返回false。
     * 1.7中的intern()方法不会再复制实例，只是在常量池中记录首次出现的实例的引用，因此intern()方法返回的引用和StringBuilder创建的那个字符串是同一个。
     * 而第二个字符串java之所以是false，是因为在执行toString()放之前就已经出现了，因此intern()方法返回的引用必然不是StringBuilder创建的这个引用。
     */
    @Test
    public void testStringConstant() {
        String str1 = new StringBuilder("计算机").append("软件").toString();
        System.out.println(str1.intern() == str1);
        String str2 = new StringBuilder("ja").append("va").toString();
        System.out.println(str2.intern() == str2);

    }
}
