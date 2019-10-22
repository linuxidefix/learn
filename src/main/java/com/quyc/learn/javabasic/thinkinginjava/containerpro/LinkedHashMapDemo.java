package com.quyc.learn.javabasic.thinkinginjava.containerpro;//: containers/LinkedHashMapDemo.java
// What you can do with a LinkedHashMap.


import com.quyc.learn.javabasic.thinkinginjava.net.mindview.util.CountingMapData;

import java.util.LinkedHashMap;


/**
 * LinkedHashMap 功能演示
 */
public class LinkedHashMapDemo {
    public static void main(String[] args) {
        LinkedHashMap<Integer, String> linkedMap =
                new LinkedHashMap<Integer, String>(
                        new CountingMapData(9));
        System.out.println(linkedMap);
        // Least-recently-used order: 最近最少使用算法（LRU），没有被访问过的元素会出现在队列的最前面，对于需要定期清理元素的需求可以很容易实现。
        linkedMap = new LinkedHashMap<Integer, String>(16, 0.75f, true);
        linkedMap.putAll(new CountingMapData(9));
        System.out.println(linkedMap);
        // Cause accesses:
        for (int i = 0; i < 6; i++) {
            linkedMap.get(i);
        }
        System.out.println(linkedMap);
        linkedMap.get(0);
        System.out.println(linkedMap);
    }
} /* Output:
{0=A0, 1=B0, 2=C0, 3=D0, 4=E0, 5=F0, 6=G0, 7=H0, 8=I0}
{0=A0, 1=B0, 2=C0, 3=D0, 4=E0, 5=F0, 6=G0, 7=H0, 8=I0}
{6=G0, 7=H0, 8=I0, 0=A0, 1=B0, 2=C0, 3=D0, 4=E0, 5=F0}
{6=G0, 7=H0, 8=I0, 1=B0, 2=C0, 3=D0, 4=E0, 5=F0, 0=A0}
*///:~
