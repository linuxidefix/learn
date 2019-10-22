package com.quyc.learn.javabasic.thinkinginjava.parcel;

/**
 * Created by quyuanchao on 2018-4-24 0:02.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class MultiNestingAccess {
//    class MNA {
        private void f() {
            System.out.println("MNA.f()");
        }

        class A {
            private void b() {
                System.out.println("MNA.A.b()");
            }

            public class B {
                void h() {
                    b();
                    f();
                }
            }
        }
//    }

    public static class Tester {
        public static void main(String[] args) {
//            MNA mna = new MNA();
            MultiNestingAccess mna = new MultiNestingAccess();
            A a = mna.new A();
            A.B b = a.new B();
            b.h();
        }
    }
}

