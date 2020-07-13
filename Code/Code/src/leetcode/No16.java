package leetcode;

import java.util.Arrays;

/**
 * 给定一个包括 n 个整数的数组 nums 和 一个目标值 target。找出 nums 中的三个整数，使得它们的和与 target 最接近。返回这三个数的和。假定每组输入只存在唯一答案。
 *
 *  
 *
 * 示例：
 *
 * 输入：nums = [-1,2,1,-4], target = 1
 * 输出：2
 * 解释：与 target 最接近的和是 2 (-1 + 2 + 1 = 2) 。
 *  
 *
 * 提示：
 *
 * 3 <= nums.length <= 10^3
 * -10^3 <= nums[i] <= 10^3
 * -10^4 <= target <= 10^4
 *
 */
public class No16 {

    /**
     * 变成两数之和？
     */
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int result = 0;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length - 2; i++) {
            int left = i + 1;
            int right = nums.length - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum == target) {
                    return sum;
                } else if (sum < target) {
                    left++;
                } else {
                    right--;
                }
                int abs = Math.abs(sum - target);
                if (abs < min) {
                    result = sum;
                    min = abs;
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        No16 no16 = new No16();
        System.out.println(no16.threeSumClosest(new int[]{-1, 2, 1, -4}, 1));
    }
}
