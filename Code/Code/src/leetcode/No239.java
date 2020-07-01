package leetcode;

import java.util.ArrayDeque;
import java.util.PriorityQueue;

/**
 * 给定一个数组 nums，有一个大小为 k 的滑动窗口从数组的最左侧移动到数组的最右侧。你只可以看到在滑动窗口内的 k 个数字。滑动窗口每次只向右移动一位。
 *
 * 返回滑动窗口中的最大值。
 *
 *  
 *
 * 进阶：
 *
 * 你能在线性时间复杂度内解决此题吗？
 *
 *  
 *
 * 示例:
 *
 * 输入: nums = [1,3,-1,-3,5,3,6,7], 和 k = 3
 * 输出: [3,3,5,5,6,7]
 * 解释:
 *
 *   滑动窗口的位置                最大值
 * ---------------               -----
 * [1  3  -1] -3  5  3  6  7       3
 *  1 [3  -1  -3] 5  3  6  7       3
 *  1  3 [-1  -3  5] 3  6  7       5
 *  1  3  -1 [-3  5  3] 6  7       5
 *  1  3  -1  -3 [5  3  6] 7       6
 *  1  3  -1  -3  5 [3  6  7]      7
 *  
 *
 * 提示：
 *
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 * 1 <= k <= nums.length
 *
 */
public class No239 {

    /**
     * 使用最大堆存放当前窗口包含的元素，堆操作的时间复杂度为 log(k)
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];
        PriorityQueue<Integer> queue = new PriorityQueue<>(((o1, o2) -> o2 - o1));
        for (int i = 0; i < nums.length; i++) {
            queue.add(nums[i]);
            if (i >= k) {
                queue.remove(nums[i - k]);
            }
            if (i >= k - 1) {
                result[i - k + 1] = queue.peek();
            }
        }
        return result;
    }

    /**
     * 最大堆在排序中会涉及很多不必要的元素，我们可以优化
     * 用一个双向列表，在窗口滑动过程中，列表只保留比当前元素大的元素，
     * 列表存元素下标
     */
    public int[] maxSlidingWindow2(int[] nums, int k) {
        int[] result = new int[nums.length - k + 1];

        ArrayDeque<Integer> deque = new ArrayDeque<>();

        for (int i = 0; i < nums.length; i++) {
            // clean deque
            while (!deque.isEmpty()) {
                Integer last = deque.peekLast();
                if (nums[last] < nums[i]) {
                    deque.removeLast();
                } else {
                    break;
                }
            }
            // add last
            deque.offerLast(i);
            //remove first
            if (i - deque.peekFirst() >= k) {
                deque.removeFirst();
            }
            // first is max
            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int[] nums = {7, 2, 4};
        No239 no239 = new No239();
        int[] ints = no239.maxSlidingWindow2(nums, 2);
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }
}
