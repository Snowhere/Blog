package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个二叉树，返回其节点值的锯齿形层次遍历。（即先从左往右，再从右往左进行下一层遍历，以此类推，层与层之间交替进行）。
 *
 * 例如：
 * 给定二叉树 [3,9,20,null,null,15,7],
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 * 返回锯齿形层次遍历如下：
 *
 * [
 *   [3],
 *   [20,9],
 *   [15,7]
 * ]
 * 通过次数75,875
 *
 */
public class No103 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> list = new ArrayList<>();
        order(root, list, 1, true);
        return list;
    }

    public void order(TreeNode node, List<List<Integer>> level, int deep, boolean order) {
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
        if (order) {

            list.add(node.val);
        } else {
            list.add(0, node.val);
        }
        order(node.left, level, deep + 1, !order);
        order(node.right, level, deep + 1, !order);
    }

    public static void main(String[] args) {
        No103 no103 = new No103();
        TreeNode treeNode3 = no103.new TreeNode(3);
        TreeNode treeNode9 = no103.new TreeNode(9);
        TreeNode treeNode20 = no103.new TreeNode(20);
        TreeNode treeNode15 = no103.new TreeNode(15);
        TreeNode treeNode7 = no103.new TreeNode(7);
        treeNode3.left = treeNode9;
        treeNode3.right = treeNode20;
        treeNode20.left = treeNode15;
        treeNode20.right = treeNode7;
        List<List<Integer>> lists = no103.zigzagLevelOrder(treeNode3);
        System.out.println(lists);
    }
}
