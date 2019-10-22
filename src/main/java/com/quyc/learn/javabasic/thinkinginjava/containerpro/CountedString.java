package com.quyc.learn.javabasic.thinkinginjava.containerpro;//: containers/CountedString.java
// Creating a good hashCode().

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 实现自定义hashCode()方法的要求：
 * 1. 无论何时，对同一个对象调用hashCode()方法都会得到同样的值
 * 2. 若使hashCode()方法实用，必须速度快，而且有意义，也就是必须基于对象内容生成散列码
 * 3. 散列码不必独一无二，但是通过hashCode()和equals()必须能够完全确定对象身份
 * 4. hashCode()方法应该生成分布均匀的散列码
 *
 * 定义一个hashCode()的简单步骤：
 * 1. 给int类型变量result定义一个常量值
 * 2. 对对象内每一个有意义的域（及每一个可以作为equals()操作的域）计算一个int类型散列码
 * 3. 合并计算每一个散列码
 * 4. 返回result
 * 5. 检查hashCode()方法生成的结果，确保相同对象能够生成相同的散列码。
 */
public class CountedString {
    private static List<String> created =
            new ArrayList<String>();
    private String s;
    private int id = 0;

    public CountedString(String str) {
        s = str;
        created.add(s);
        // id is the total number of instances
        // of this string in use by CountedString:
        for (String s2 : created) {
            if (s2.equals(s)) {
                id++;
            }
        }
    }

    @Override
    public String toString() {
        return "String: " + s + " id: " + id +
                " hashCode(): " + hashCode();
    }

    @Override
    public int hashCode() {
        // The very simple approach:
        // return s.hashCode() * id;
        // Using Joshua Bloch's recipe:
        int result = 17;
        result = 37 * result + s.hashCode();
        result = 37 * result + id;
        return result;
    }

    public static void main(String[] args) {
        Map<CountedString, Integer> map =
                new HashMap<CountedString, Integer>();
        CountedString[] cs = new CountedString[5];
        for (int i = 0; i < cs.length; i++) {
            cs[i] = new CountedString("hi");
            map.put(cs[i], i); // Autobox int -> Integer
        }
        System.out.println(map);
        for (CountedString cstring : cs) {
            System.out.println("Looking up " + cstring);
            System.out.println(map.get(cstring));
        }
    }
} /* Output: (Sample)
{String: hi id: 4 hashCode(): 146450=3, String: hi id: 1 hashCode(): 146447=0, String: hi id: 3 hashCode(): 146449=2, String: hi id: 5 hashCode(): 146451=4, String: hi id: 2 hashCode(): 146448=1}
Looking up String: hi id: 1 hashCode(): 146447
0
Looking up String: hi id: 2 hashCode(): 146448
1
Looking up String: hi id: 3 hashCode(): 146449
2
Looking up String: hi id: 4 hashCode(): 146450
3
Looking up String: hi id: 5 hashCode(): 146451
4
*///:~
