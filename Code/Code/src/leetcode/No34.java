package leetcode;
/**
 * 给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
 *
 * 你的算法时间复杂度必须是 O(log n) 级别。
 *
 * 如果数组中不存在目标值，返回 [-1, -1]。
 *
 * 示例 1:
 *
 * 输入: nums = [5,7,7,8,8,10], target = 8
 * 输出: [3,4]
 * 示例 2:
 *
 * 输入: nums = [5,7,7,8,8,10], target = 6
 * 输出: [-1,-1]
 *
 */
public class No34 {

    /**
     * 网友答案：
     * 二分查找变种，用于找到大于等于target的第一个数字
     * 我们找两次，第一次找target，第二次找target+1
     */
    public int[] searchRange(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return new int[]{-1, -1};
        }
        int begin = search(nums, target);
        int end = search(nums, target + 1);
        //没有target
        if (nums[begin] != target) {
            return new int[]{-1, -1};
        }
        //没有大于等于target+1的
        if (end == nums.length - 1 && nums[end] == target) {
            end++;
        }
        return new int[]{begin, end - 1};
    }

    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int middle = (left + right) / 2;
            if (nums[middle] >= target) {
                right = middle;
            } else {
                left = middle + 1;
            }
        }
        return left;
    }

    public static void main(String[] args) {
        No34 no34 = new No34();
        int[] ints = no34.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
        int[] ints2 = no34.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 10);
        for (int anInt : ints2) {
            System.out.println(anInt);
        }
    }
}
