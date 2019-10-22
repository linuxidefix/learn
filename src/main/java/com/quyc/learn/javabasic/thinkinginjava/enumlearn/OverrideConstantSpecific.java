package com.quyc.learn.javabasic.thinkinginjava.enumlearn;//: enumerated/OverrideConstantSpecific.java


public enum OverrideConstantSpecific {
    NUT, BOLT,
    WASHER {
        @Override
        void f() {
            System.out.println("Overridden method");
        }
    };

    public static void main(String[] args) {
        for (OverrideConstantSpecific ocs : values()) {
            System.out.println(ocs + ": ");
            ocs.f();
        }
    }

    void f() {
        System.out.println("default behavior");
    }
} /* Output:
NUT: default behavior
BOLT: default behavior
WASHER: Overridden method
*///:~
