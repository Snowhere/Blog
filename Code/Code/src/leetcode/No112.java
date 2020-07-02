package leetcode;
/**
 * 给定一个二叉树和一个目标和，判断该树中是否存在根节点到叶子节点的路径，这条路径上所有节点值相加等于目标和。
 *
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例: 
 * 给定如下二叉树，以及目标和 sum = 22，
 *
 *               5
 *              / \
 *             4   8
 *            /   / \
 *           11  13  4
 *          /  \      \
 *         7    2      1
 * 返回 true, 因为存在目标和为 22 的根节点到叶子节点的路径 5->4->11->2。
 *
 */
public class No112 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }
        if (root.left == null && root.right == null) {
            return sum == root.val;
        } else {
            return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
        }
    }

    public static void main(String[] args) {
        No112 no112 = new No112();
        TreeNode treeNode5 = no112.new TreeNode(5);
        TreeNode treeNode4 = no112.new TreeNode(4);
        TreeNode treeNode8 = no112.new TreeNode(8);
        TreeNode treeNode11 = no112.new TreeNode(11);
        TreeNode treeNode13 = no112.new TreeNode(13);
        TreeNode treeNode44 = no112.new TreeNode(4);
        TreeNode treeNode7 = no112.new TreeNode(7);
        TreeNode treeNode2 = no112.new TreeNode(2);
        TreeNode treeNode1 = no112.new TreeNode(1);

        treeNode5.left = treeNode4;
        treeNode5.right = treeNode8;
        treeNode4.left = treeNode11;
        treeNode8.left = treeNode13;
        treeNode8.right = treeNode44;
        treeNode11.left = treeNode7;
        treeNode11.right = treeNode2;
        treeNode4.right = treeNode1;

        System.out.println(no112.hasPathSum(treeNode5, 22));
    }
}
