package leetcode;

import util.Queue;

/**
 * 翻转一棵二叉树。
 *
 * 示例：
 *
 * 输入：
 *
 *      4
 *    /   \
 *   2     7
 *  / \   / \
 * 1   3 6   9
 * 输出：
 *
 *      4
 *    /   \
 *   7     2
 *  / \   / \
 * 9   6 3   1
 *
 */
public class No226 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public TreeNode invertTree(TreeNode root) {

        if (root != null) {
            TreeNode tmp = root.left;
            root.left = root.right;
            root.right = tmp;
            invertTree(root.left);
            invertTree(root.right);
        }
        return root;
    }

    public static void main(String[] args) {
        No226 no226 = new No226();

        TreeNode treeNode4 = no226.new TreeNode(4);
        TreeNode treeNode2 = no226.new TreeNode(2);
        TreeNode treeNode7 = no226.new TreeNode(7);
        TreeNode treeNode1 = no226.new TreeNode(1);
        TreeNode treeNode3 = no226.new TreeNode(3);
        TreeNode treeNode6 = no226.new TreeNode(6);
        TreeNode treeNode9 = no226.new TreeNode(9);
        treeNode4.left = treeNode2;
        treeNode4.right = treeNode7;
        treeNode2.left = treeNode1;
        treeNode2.right = treeNode3;
        treeNode7.left = treeNode6;
        treeNode7.right = treeNode9;

        no226.printTreeNode(treeNode4);

        TreeNode treeNode = no226.invertTree(treeNode4);

        no226.printTreeNode(treeNode);
    }

    void printTreeNode(TreeNode head) {
        Queue<TreeNode> queue = new Queue<>();
        queue.put(head);
        while (!queue.isEmpty()) {
            TreeNode node = queue.get();
            if (node.left != null) {
                queue.put(node.left);
            }
            if (node.right != null) {
                queue.put(node.right);
            }
            System.out.print(node.val + " ");
        }
        System.out.println();
    }
}
