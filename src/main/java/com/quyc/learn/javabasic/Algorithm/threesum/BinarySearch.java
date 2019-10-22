package com.quyc.learn.javabasic.Algorithm.threesum;

/**
 * Created by quyuanchao on 2019-2-28 18:23.
 * <p>Title: com.review.Algorithm.threesum</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class BinarySearch {

    public static int search(int[] nums, int target) {
        int l = 0, h = nums.length - 1;
        while (l <= h) {
            int m = l + (h - l) / 2;
            if (target == nums[m]) {
                return m;
            } else if (target > nums[m]) {
                l = m + 1;
            } else {
                h = m - 1;
            }
        }
        return -1;
    }
}
