package leetcode;
/**
 * 给定一个二叉树，找出其最大深度。
 *
 * 二叉树的深度为根节点到最远叶子节点的最长路径上的节点数。
 *
 * 说明: 叶子节点是指没有子节点的节点。
 *
 * 示例：
 * 给定二叉树 [3,9,20,null,null,15,7]，
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 */
public class No104 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            return Math.max(maxDepth(root.left), maxDepth(root.right)) + 1;
        }
    }

    public static void main(String[] args) {
        No104 no104 = new No104();
        TreeNode treeNode3 = no104.new TreeNode(3);
        TreeNode treeNode9 = no104.new TreeNode(9);
        TreeNode treeNode20 = no104.new TreeNode(20);
        TreeNode treeNode15 = no104.new TreeNode(15);
        TreeNode treeNode7 = no104.new TreeNode(7);

        treeNode3.left = treeNode9;
        treeNode3.right = treeNode20;
        treeNode20.left = treeNode15;
        treeNode20.right = treeNode7;

        System.out.println(no104.maxDepth(treeNode3));
    }
}
