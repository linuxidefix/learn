package com.quyc.learn.javabasic.Algorithm.sort.fastsort;

/**
 * Created by quyuanchao on 2019-2-25 15:36.
 * <p>Title: com.review.Algorithm.sort.fastsort</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class ThreeWayQuickSort<T extends Comparable<T>> extends QuickSort<T> {

    protected void sort(T[] nums, int l, int h) {
        if (h <= l) {
            return;
        }
        int lt = l, i = l + 1, gt = h;
        T v = nums[l];
        while (i <= gt) {
            int cmp = nums[i].compareTo(v);
            if (cmp < 0) {
                swap(nums, lt++, i++);
            } else if (cmp > 0) {
                swap(nums, i, gt--);
            } else {
                i++;
            }
        }
        sort(nums, l, lt - 1);
        sort(nums, gt + 1, h);
    }
}
