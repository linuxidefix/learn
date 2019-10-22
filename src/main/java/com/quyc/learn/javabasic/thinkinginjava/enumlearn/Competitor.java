//: enumerated/Competitor.java
// Switching one enum on another.
package com.quyc.learn.javabasic.thinkinginjava.enumlearn;

public interface Competitor<T extends Competitor<T>> {
    Outcome compete(T competitor);
} ///:~
