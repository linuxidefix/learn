package com.quyc.learn.javabasic.Algorithm.threesum;

/**
 * Created by quyuanchao on 2019-2-28 18:22.
 * <p>Title: com.review.Algorithm.threesum</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class ThreeSumSlow implements ThreeSum {
    @Override
    public int count(int[] nums) {
        int N = nums.length;
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                for (int k = j + 1; k < N; k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        cnt++;
                    }
                }
            }
        }
        return cnt;
    }
}
