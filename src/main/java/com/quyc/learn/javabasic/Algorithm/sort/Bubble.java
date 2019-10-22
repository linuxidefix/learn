package com.quyc.learn.javabasic.Algorithm.sort;

/**
 * Created by quyuanchao on 2019-2-25 15:24.
 * <p>Title: com.review.Algorithm.sort</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Bubble<T extends Comparable<T>> extends Sort<T> {

    @Override
    public void sort(T[] nums) {
        int N = nums.length;
        boolean hasSorted = false;
        for (int i = N - 1; i > 0 && !hasSorted; i--) {
            hasSorted = true;
            for (int j = 0; j < i; j++) {
                if (less(nums[j + 1], nums[j])) {
                    hasSorted = false;
                    swap(nums, j, j + 1);
                }
            }
        }
    }
}
