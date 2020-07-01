package leetcode;
/**
 * 示例 1:
 *
 * 输入: [1,2,0]
 * 输出: 3
 * 示例 2:
 *
 * 输入: [3,4,-1,1]
 * 输出: 2
 * 示例 3:
 *
 * 输入: [7,8,9,11,12]
 * 输出: 1
 *  
 */
public class No41 {
    /**
     * 缺失的数 1 < n < nums.length+1 ,用一个数组表示bitmap
     */
    public int firstMissingPositive(int[] nums) {
        int[] result = new int[nums.length+2];
        for (int num : nums) {
            if (num > 0&&num<nums.length+1) {
                result[num]=1;
            }
        }
        int i = 1;
        for (; i <= result.length+1; i++) {
            if (result[i] != 1) {
                return i;
            }
        }
        return i+1;
    }

    /**
     * 吾即bitmap
     * 小于等于 0 的赋值 nums.length+1
     * 负号表示存在
     * 下标从 0 开始，第一个正号的下标 i,确定缺失的是数字 i+1
     */
    public int firstMissingPositive2(int[] nums) {

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] <= 0) {
                nums[i] = nums.length+1;
            }
        }
        for (int i = 0; i < nums.length; i++) {
            int abs = Math.abs(nums[i]);
            if (abs<nums.length+1 && nums[abs-1] > 0) {
                nums[abs-1] = -nums[abs-1];
            }
        }
        int i = 0;
        for (; i < nums.length; i++) {
            if (nums[i] > 0) {
                return i+1;
            }
        }
        return i+1;
    }

    public static void main(String[] args) {
        int[] nums = {3, 4, -1, 1};
        No41 no41 = new No41();
        System.out.println(no41.firstMissingPositive2(nums));
    }
}
