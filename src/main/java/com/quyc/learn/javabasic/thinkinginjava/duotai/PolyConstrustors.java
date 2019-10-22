package com.quyc.learn.javabasic.thinkinginjava.duotai;

/**
 * 构造器内部的多态行为
 *
 * 初始化的实际过程：
 *  1. 在其他任何事物发生之前，将分配给对象的内存空间初始化成二级制的0
 *  2. 调用基类构造器
 *  3. 按照生命的顺序调用成员的初始化方法
 *  4. 调用导出类的构造器主体
 *
 * Created by quyuanchao on 2018-4-22 0:03.
 * <p>Title: $TITLE</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class PolyConstrustors {
    public static void main(String[] args) {
        new RoundGlyph(5);
    }
}

class Glyph {
    void draw() {
        System.out.println("Glyph draw()");
    }

    Glyph() {
        System.out.println("Glyph draw() before");
        draw();
        System.out.println("Glyph draw() after");
    }
}

class RoundGlyph extends Glyph {
    private int radius = 1;

    @Override
    void draw() {
        System.out.println("RoundGlyph draw(),radius = " + radius);
    }

    RoundGlyph(int radius) {
        this.radius = radius;
        System.out.println("RoundGlyph.RoundGlyph(),radius = " + radius);
    }
}
