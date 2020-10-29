package leetcode;

import java.util.Arrays;

/**
 * 将一个按照升序排列的有序数组，转换为一棵高度平衡二叉搜索树。
 *
 * 本题中，一个高度平衡二叉树是指一个二叉树每个节点 的左右两个子树的高度差的绝对值不超过 1。
 *
 * 示例:
 *
 * 给定有序数组: [-10,-3,0,5,9],
 *
 * 一个可能的答案是：[0,-3,9,-10,null,5]，它可以表示下面这个高度平衡二叉搜索树：
 *
 *       0
 *      / \
 *    -3   9
 *    /   /
 *  -10  5
 *
 */
public class No108 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }
        if (nums.length == 1) {
            return new TreeNode(nums[0]);
        }
        int middle = nums.length / 2;
        TreeNode node = new TreeNode(nums[middle]);
        node.left = sortedArrayToBST(Arrays.copyOfRange(nums, 0, middle));
        node.right = sortedArrayToBST(Arrays.copyOfRange(nums, middle + 1, nums.length));
        return node;
    }

    public void print(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.println(node.val);
        print(node.left);
        print(node.right);
    }

    public static void main(String[] args) {
        No108 no108 = new No108();
        TreeNode treeNode = no108.sortedArrayToBST(new int[]{-10, -3, 0, 5, 9});
        no108.print(treeNode);
    }
}
