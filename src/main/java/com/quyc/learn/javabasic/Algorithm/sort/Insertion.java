package com.quyc.learn.javabasic.Algorithm.sort;

/**
 * Created by quyuanchao on 2019-2-25 15:24.
 * <p>Title: com.review.Algorithm.sort</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Insertion<T extends Comparable<T>> extends Sort<T> {

    @Override
    public void sort(T[] nums) {
        int N = nums.length;
        for (int i = 1; i < N; i++) {
            for (int j = i; j > 0 && less(nums[j], nums[j - 1]); j--) {
                swap(nums, j, j - 1);
            }
        }
    }
}
