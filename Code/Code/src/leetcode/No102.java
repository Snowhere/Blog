package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 给你一个二叉树，请你返回其按 层序遍历 得到的节点值。 （即逐层地，从左到右访问所有节点）。
 *
 *  
 *
 * 示例：
 * 二叉树：[3,9,20,null,null,15,7],
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回其层次遍历结果：
 *
 * [
 *   [3],
 *   [9,20],
 *   [15,7]
 * ]
 *
 */
public class No102 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        order(root, list, 1);
        return list;
    }

    public void order(TreeNode node, List<List<Integer>> level, int deep) {
        if (node == null) {
            return;
        }
        List<Integer> list;
        if (level.size() < deep) {
            list = new ArrayList<>();
            level.add(list);
        } else {
            list = level.get(deep - 1);
        }
        list.add(node.val);
        order(node.left, level, deep + 1);
        order(node.right, level, deep + 1);
    }

    public static void main(String[] args) {
        No102 no102 = new No102();
        TreeNode treeNode3 = no102.new TreeNode(3);
        TreeNode treeNode9 = no102.new TreeNode(9);
        TreeNode treeNode20 = no102.new TreeNode(20);
        TreeNode treeNode15 = no102.new TreeNode(15);
        TreeNode treeNode7 = no102.new TreeNode(7);
        treeNode3.left = treeNode9;
        treeNode3.right = treeNode20;
        treeNode20.left = treeNode15;
        treeNode20.right = treeNode7;
        List<List<Integer>> lists = no102.levelOrder(treeNode3);
        System.out.println(lists);
    }
}
