//: enumerated/EnumMaps.java
// Basics of EnumMaps.
package com.quyc.learn.javabasic.thinkinginjava.enumlearn;

import java.util.EnumMap;
import java.util.Map;

import static com.quyc.learn.javabasic.thinkinginjava.enumlearn.AlarmPoints.*;

/**
 * 命令模式：首先需要一个具有单一方法的接口，然后从该接口实现具有不同行为的子类。
 */
interface Command {
    void action();
}

public class EnumMaps {
    public static void main(String[] args) {
        EnumMap<AlarmPoints, Command> em =
                new EnumMap<AlarmPoints, Command>(AlarmPoints.class);
        em.put(KITCHEN, new Command() {
            @Override
            public void action() {
                System.out.println("Kitchen fire!");
            }
        });
        em.put(BATHROOM, new Command() {
            @Override
            public void action() {
                System.out.println("Bathroom alert!");
            }
        });
        for (Map.Entry<AlarmPoints, Command> e : em.entrySet()) {
            System.out.println(e.getKey() + ": ");
            e.getValue().action();
        }
        try { // If there's no value for a particular key:
            em.get(UTILITY).action();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
} /* Output:
BATHROOM: Bathroom alert!
KITCHEN: Kitchen fire!
java.lang.NullPointerException
*///:~
