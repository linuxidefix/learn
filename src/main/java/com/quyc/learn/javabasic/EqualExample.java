package com.quyc.learn.javabasic;

import java.util.Objects;

/**
 * Created by quyuanchao on 2019-2-18 16:19.
 * <p>Title: com.review</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class EqualExample {

    private int x;
    private int y;
    private int z;

    public EqualExample(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EqualExample that = (EqualExample) o;
        return x == that.x &&
                y == that.y &&
                z == that.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
