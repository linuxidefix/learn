package com.quyc.learn.javabasic.Algorithm.threesum;

import java.util.Arrays;

/**
 * Created by quyuanchao on 2019-2-28 18:23.
 * <p>Title: com.review.Algorithm.threesum</p>
 * <p>Description: $DESCRIPTION</p>
 */
public class ThreeSumBinarySearch implements ThreeSum {

    @Override
    public int count(int[] nums) {
        Arrays.sort(nums);
        int N = nums.length;
        int cnt = 0;
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                int target = -nums[i] - nums[j];
                int index = BinarySearch.search(nums, target);
                // 应该注意这里的下标必须大于 j，否则会重复统计。
                if (index > j) {
                    cnt++;
                }
            }
        }
        return cnt;
    }
}
