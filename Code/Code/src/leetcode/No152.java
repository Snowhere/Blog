package leetcode;

import java.util.Arrays;

/**
 * 给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
 *
 *  
 *
 * 示例 1:
 *
 * 输入: [2,3,-2,4]
 * 输出: 6
 * 解释: 子数组 [2,3] 有最大乘积 6。
 * 示例 2:
 *
 * 输入: [-2,0,-1]
 * 输出: 0
 * 解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。
 *
 */
public class No152 {

    /**
     * n^2
     */
    public int maxProduct(int[] nums) {
        long max = Long.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            long mult = nums[i];
            max = Math.max(mult, max);
            for (int j = i; j < nums.length; j++) {
                if (j != i) {
                    mult = mult * nums[j];
                    max = Math.max(max, mult);
                }
            }
        }
        return (int) max;
    }

    /**
     * f(i)是到i为止，且包含nums[i]的乘积最大子串
     * g(i)是到i为止，且包含nums[i]的乘积最小子串
     * nums[i]>0 ，f(i)=f(i-1)*nums[i]，g(i)=g(i-1)*nums[i]
     * nums[i]=0 , f(i)=g(i)=0
     * nums[i]<0 , f(i)=g(i-1)*nums[i]，g(i)=f(i-1)*nums[i]
     */
    public int maxProduct2(int[] nums) {
        int[] maxSub = Arrays.copyOf(nums, nums.length);
        int[] minSub = Arrays.copyOf(nums, nums.length);
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == 0) {
                maxSub[i] = 0;
                minSub[i] = 0;
            } else if (nums[i] > 0) {
                maxSub[i] = Math.max(maxSub[i], maxSub[i - 1] * nums[i]);
                minSub[i] = Math.min(minSub[i], minSub[i - 1] * nums[i]);
            } else {
                maxSub[i] = Math.max(maxSub[i], minSub[i - 1] * nums[i]);
                minSub[i] = Math.min(minSub[i], maxSub[i - 1] * nums[i]);
            }
            max = Math.max(max, maxSub[i]);
        }
        return max;
    }


    /**
     * 优化空间复杂度，只与上个状态有关，数组变为临时变量
     * 优化思路，无需判断nums[0]正负，直接三者比较
     * f(i) = max(f(i-1)*nums[i], g(i-1)*nums[i], nums[i])
     * g(i) = min(f(i-1)*nums[i], g(i-1)*nums[i], nums[i])
     */
    public int maxProduct3(int[] nums) {
        int maxSub, minSub;
        int maxTemp = nums[0];
        int minTemp = nums[0];
        int max = nums[0];
        for (int i = 1; i < nums.length; i++) {
            maxSub = Math.max(Math.max(maxTemp * nums[i], minTemp * nums[i]), nums[i]);
            minSub = Math.min(Math.min(maxTemp * nums[i], minTemp * nums[i]), nums[i]);
            maxTemp = maxSub;
            minTemp = minSub;
            max = Math.max(max, maxSub);
        }
        return max;
    }

    public static void main(String[] args) {
        No152 no152 = new No152();
        System.out.println(no152.maxProduct3(new int[]{2, 3, -2, 4}));
        System.out.println(no152.maxProduct3(new int[]{-2, 0, -1}));
    }
}
