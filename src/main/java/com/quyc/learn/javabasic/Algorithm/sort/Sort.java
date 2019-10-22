package com.quyc.learn.javabasic.Algorithm.sort;

/**
 * Created by quyuanchao on 2019-2-25 15:23.
 * <p>Title: com.review.Algorithm.sort</p>
 * <p>Description: $DESCRIPTION</p>
 */
public abstract class Sort<T extends Comparable<T>> {

    public abstract void sort(T[] nums);

    protected boolean less(T v, T w) {
        return v.compareTo(w) < 0;
    }

    protected void swap(T[] a, int i, int j) {
        T t = a[i];
        a[i] = a[j];
        a[j] = t;
    }
}
