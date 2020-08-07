package leetcode;
/**
 * 给定一个整数数组 nums ，找到一个具有最大和的连续子数组（子数组最少包含一个元素），返回其最大和。
 *
 * 示例:
 *
 * 输入: [-2,1,-3,4,-1,2,1,-5,4]
 * 输出: 6
 * 解释: 连续子数组 [4,-1,2,1] 的和最大，为 6。
 *
 */
public class No53 {

    public int maxSubArray(int[] nums) {
        int[] max = new int[nums.length];
        int result =nums[0];
        max[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            max[i] = max[i - 1] > 0 ? max[i - 1] + nums[i] : nums[i];
            result = Math.max(max[i], result);
        }
        return result;
    }

    public static void main(String[] args) {
        No53 no53 = new No53();
        System.out.println(no53.maxSubArray(new int[]{-2,1,-3,4,-1,2,1,-5,4}));
    }
}
