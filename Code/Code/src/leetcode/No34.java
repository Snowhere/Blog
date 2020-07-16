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

    //TODO
    public int[] searchRange(int[] nums, int target) {
        return null;
    }

    public static void main(String[] args) {
        No34 no34 = new No34();
        int[] ints = no34.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 8);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
        int[] ints2 = no34.searchRange(new int[]{5, 7, 7, 8, 8, 10}, 6);
        for (int anInt : ints2) {
            System.out.println(anInt);
        }
    }
}
