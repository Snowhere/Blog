package leetcode;
/**
 * 给定一个非负整数数组，你最初位于数组的第一个位置。
 *
 * 数组中的每个元素代表你在该位置可以跳跃的最大长度。
 *
 * 你的目标是使用最少的跳跃次数到达数组的最后一个位置。
 *
 * 示例:
 *
 * 输入: [2,3,1,1,4]
 * 输出: 2
 * 解释: 跳到最后一个位置的最小跳跃数是 2。
 *      从下标为 0 跳到下标为 1 的位置，跳 1 步，然后跳 3 步到达数组的最后一个位置。
 * 说明:
 *
 * 假设你总是可以到达数组的最后一个位置。
 *
 */
public class No45 {

    /**
     * nums[i]+i 是i元素能跳到的最远位置
     * 每跳一步，范围内的步数所能确定的最远范围
     */
    public int jump(int[] nums) {
        if (nums == null || nums.length <= 1) {
            return 0;
        }
        int result = 1;
        int far = nums[0];
        int i = 0;
        while (far < nums.length - 1) {
            int max = 0;
            while (i <= far) {
                max = Math.max(max, nums[i] + i);
                i++;
            }
            far = max;
            result++;
        }
        return result;
    }

    public static void main(String[] args) {
        No45 no45 = new No45();
        System.out.println(no45.jump(new int[]{2, 3, 1, 1, 4}));
        System.out.println(no45.jump(new int[]{1, 1}));
        System.out.println(no45.jump(new int[]{2, 1}));
    }
}
