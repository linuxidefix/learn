package com.quyc.learn.javabasic.Algorithm.sort;

import java.util.Arrays;
import java.util.List;

/**
 * Created by quyuanchao on 2019-2-25 15:24.
 * <p>Title: com.review.Algorithm.sort</p>
 * <p>Description: $DESCRIPTION</p>
 *
 * @param <T> the type parameter
 */
public class Selection<T extends Comparable<T>> extends Sort<T> {

    @Override
    public void sort(T[] nums) {
        int N = nums.length;
        for (int i = 0; i < N - 1; i++) {
            int min = i;
            for (int j = i + 1; j < N; j++) {
                if (less(nums[j], nums[min])) {
                    min = j;
                }
            }
            swap(nums, i, min);
        }
    }


    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(2, 5, 3, 1, 8, 4, 7, 9, 6);
//        for (int i = 0; i < integers.size(); i++) {
//            int min = i;
//            for (int j = i + 1; j < integers.size(); j++) {
//                if (integers.get(min) > integers.get(j)) {
//                    min = j;
//                }
//            }
//            int temp = integers.get(min);
//            integers.set(min, integers.get(i));
//            integers.set(i, temp);
//        }

        boolean hasSorted = false;
        for (int i = 0; i < integers.size(); i++) {
            hasSorted = true;
            for (int j = i + 1; j < integers.size(); j++) {
                if (integers.get(i) > integers.get(j)) {
                    hasSorted = false;
                    int temp = integers.get(j);
                    integers.set(j, integers.get(i));
                    integers.set(i, temp);
                }
            }
        }
        System.out.println("integers = " + integers);
        int i = binarySearch(6);
        System.out.println("i = " + i);
    }

    /**
     * 二分法查找
     * @param i
     * @return
     */
    public static int binarySearch(int i) {
        int[] integers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int min = 0;
        int max = integers.length - 1;
        while (min <= max) {
            int mid = (min + max) / 2;
            if (i == integers[mid]) {
                return mid;
            } else if (i < integers[mid]) {
                max = mid - 1;
            } else {
                min = mid + 1;
            }
        }
        return -1;
//        int l = 0, h = integers.length - 1;
//        while (l <= h) {
//            int m = l + (h - l) / 2;
//            if (i == integers[m]) {
//                return m;
//            } else if (i > integers[m]) {
//                l = m + 1;
//            } else {
//                h = m - 1;
//            }
//        }
//        return -1;
    }


}
