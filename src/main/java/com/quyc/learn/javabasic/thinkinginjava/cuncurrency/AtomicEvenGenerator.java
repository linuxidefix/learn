package com.quyc.learn.javabasic.thinkinginjava.cuncurrency;//: concurrency/AtomicEvenGenerator.java
// Atomic classes are occasionally useful in regular code.
// {RunByHand}

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicEvenGenerator extends IntGenerator {
    private AtomicInteger currentEvenValue =
            new AtomicInteger(0);

    public static void main(String[] args) {
        EvenChecker.test(new AtomicEvenGenerator());
    }

    @Override
    public int next() {
        return currentEvenValue.addAndGet(2);
    }
} ///:~
