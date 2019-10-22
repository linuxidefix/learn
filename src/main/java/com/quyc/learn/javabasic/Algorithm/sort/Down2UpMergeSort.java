package com.quyc.learn.javabasic.Algorithm.sort;

/**
 * Created by quyuanchao on 2019-2-25 15:26.
 * <p>Title: com.review.Algorithm.sort</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class Down2UpMergeSort<T extends Comparable<T>> extends MergeSort<T> {

    @Override
    public void sort(T[] nums) {

        int N = nums.length;
        aux = (T[]) new Comparable[N];

        for (int sz = 1; sz < N; sz += sz) {
            for (int lo = 0; lo < N - sz; lo += sz + sz) {
                merge(nums, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
            }
        }
    }
}

