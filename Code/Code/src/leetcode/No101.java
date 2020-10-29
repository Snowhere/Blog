package leetcode;
/**
 * 给定一个二叉树，检查它是否是镜像对称的。
 *
 *  
 *
 * 例如，二叉树 [1,2,2,3,4,4,3] 是对称的。
 *
 *     1
 *    / \
 *   2   2
 *  / \ / \
 * 3  4 4  3
 *  
 *
 * 但是下面这个 [1,2,2,null,3,null,3] 则不是镜像对称的:
 *
 *     1
 *    / \
 *   2   2
 *    \   \
 *    3    3
 *
 */
public class No101 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        return reverse(root.left, root.right);
    }

    boolean reverse(TreeNode node1, TreeNode node2) {
        if (node1 == null || node2 == null) {
            return node1 == node2;
        }
        return node1.val == node2.val && reverse(node1.left, node2.right) && reverse(node1.right, node2.left);
    }

    public static void main(String[] args) {
        No101 no101 = new No101();
        TreeNode treeNode11 = no101.new TreeNode(1);
        TreeNode treeNode21 = no101.new TreeNode(2);
        TreeNode treeNode22 = no101.new TreeNode(2);
        TreeNode treeNode31 = no101.new TreeNode(3);
        TreeNode treeNode32 = no101.new TreeNode(3);
        TreeNode treeNode33 = no101.new TreeNode(3);
        TreeNode treeNode34 = no101.new TreeNode(3);
        treeNode11.left = treeNode21;
        treeNode11.right = treeNode22;
        treeNode21.left = treeNode31;
        treeNode21.right = treeNode32;
        treeNode22.left = treeNode33;
        treeNode22.right = treeNode34;
        System.out.println(no101.isSymmetric(treeNode11));
    }
}
