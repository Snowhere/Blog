package leetcode;
/**
 * 给定两个大小为 m 和 n 的正序（从小到大）数组 nums1 和 nums2。
 *
 * 请你找出这两个正序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 *
 * 你可以假设 nums1 和 nums2 不会同时为空。
 *
 *  
 *
 * 示例 1:
 *
 * nums1 = [1, 3]
 * nums2 = [2]
 *
 * 则中位数是 2.0
 * 示例 2:
 *
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 *
 * 则中位数是 (2 + 3)/2 = 2.5
 *
 */
public class No4 {

    /**
     * 简单的思路是同时遍历两个数组，元素由小到大排列，扫描至一半元素时，就可取到中位数，但时间复杂度不达标
     * log的复杂度一般为递归求解子问题，比如二分查找
     * 中位数特点：会出现在两个数组的中位数之间
     *
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

    }


    double media(int[] nums1,int[]nums2, int left1, int right1,int left2,int right2) {
        if(){

        }else {
            double media1 = media(nums1, left1, right1);
            double media2= media(nums2, left2, right2);
            if(media1)
        }

    }


    double media(int[] nums, int left, int right) {
        int length = right - left;
        return ((double) nums[left + length / 2] + nums[left + (length + 1) / 2]) / 2;
    }

    public static void main(String[] args) {
        No4 no4 = new No4();
        System.out.println(no4.findMedianSortedArrays(new int[]{1,2},new int[]{3,4}));
    }
}
