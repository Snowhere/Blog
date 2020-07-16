package leetcode;

/**
 * 实现获取下一个排列的函数，算法需要将给定数字序列重新排列成字典序中下一个更大的排列。
 *
 * 如果不存在下一个更大的排列，则将数字重新排列成最小的排列（即升序排列）。
 *
 * 必须原地修改，只允许使用额外常数空间。
 *
 * 以下是一些例子，输入位于左侧列，其相应输出位于右侧列。
 * 1,2,3 → 1,3,2
 * 3,2,1 → 1,2,3
 * 1,1,5 → 1,5,1
 *
 */
public class No31 {

    /**
     * 找规律
     * 从后向前，nums[n]>nums[n+1]不管，nums[n]<nums[n+1],在n后面找到大于nums[n]的第一个数字nums[m]
     * 交换n和m的元素，则n后面还是递减序列，翻转这个递减序列
     */
    public void nextPermutation(int[] nums) {
        int i = nums.length - 2;
        for (; i >= 0; i--) {
            if (nums[i] < nums[i + 1]) {
                int result = search(nums, i, i + 1, nums.length - 1);
                swap(nums, i, result);
                break;
            }
        }
        //翻转
        int left = i == -1 ? 0 : i + 1;
        int right = nums.length - 1;
        while (left < right) {
            swap(nums, left, right);
            left++;
            right--;
        }
    }

    public void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public int search(int[] nums, int target, int left, int right) {
        if (left == right) {
            return left;
        }
        int middle = (right - left + 1) / 2 + left;
        if (nums[middle] <= nums[target]) {
            return search(nums, target, left, middle - 1);
        } else {
            return search(nums, target, middle, right);
        }
    }

    public void print(int[] nums) {
        for (int num : nums) {
            System.out.print(num);
            System.out.print(",");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        No31 no31 = new No31();
        int[] nums = {1, 4, 5, 2, 3};
        no31.nextPermutation(nums);
        no31.print(nums);
        int[] nums2 = {1, 2, 3, 4, 5};
        no31.nextPermutation(nums2);
        no31.print(nums2);
        int[] nums3 = {1, 3, 5, 4, 2};
        no31.nextPermutation(nums3);
        no31.print(nums3);
        int[] nums4 = {1, 5, 1};
        no31.nextPermutation(nums4);
        no31.print(nums4);
    }
}
