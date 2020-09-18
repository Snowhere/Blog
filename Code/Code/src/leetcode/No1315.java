package leetcode;
/**
 * 给你一棵二叉树，请你返回满足以下条件的所有节点的值之和：
 *
 * 该节点的祖父节点的值为偶数。（一个节点的祖父节点是指该节点的父节点的父节点。）
 * 如果不存在祖父节点值为偶数的节点，那么返回 0 。
 *
 *  
 *
 * 示例：
 *
 *
 *
 * 输入：root = [6,7,8,2,7,1,3,9,null,1,4,null,null,null,5]
 * 输出：18
 * 解释：图中红色节点的祖父节点的值为偶数，蓝色节点为这些红色节点的祖父节点。
 *  
 *
 * 提示：
 *
 * 树中节点的数目在 1 到 10^4 之间。
 * 每个节点的值在 1 到 100 之间。
 *
 */
public class No1315 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public int sumEvenGrandparent(TreeNode root) {
        return check(root, -1, -1);
    }

    public int check(TreeNode node, int grandParent, int parent) {
        int value = 0;
        if (node == null) {
            return value;
        }
        if (grandParent == 0) {
            value += node.val;
        }
        value += check(node.left, parent - 1, node.val % 2 == 0 ? 1 : -1);
        value += check(node.right, parent - 1, node.val % 2 == 0 ? 1 : -1);
        return value;
    }

    public static void main(String[] args) {
        No1315 no1315 = new No1315();
        TreeNode treeNode1 = no1315.new TreeNode(6);
        TreeNode treeNode2 = no1315.new TreeNode(7);
        TreeNode treeNode3 = no1315.new TreeNode(8);
        TreeNode treeNode4 = no1315.new TreeNode(2);
        TreeNode treeNode5 = no1315.new TreeNode(7);
        TreeNode treeNode6 = no1315.new TreeNode(1);
        TreeNode treeNode7 = no1315.new TreeNode(3);
        TreeNode treeNode8 = no1315.new TreeNode(9);
        TreeNode treeNode9 = no1315.new TreeNode(1);
        TreeNode treeNode10 = no1315.new TreeNode(4);
        TreeNode treeNode11 = no1315.new TreeNode(5);

        treeNode1.left = treeNode2;
        treeNode1.right = treeNode3;
        treeNode2.left = treeNode4;
        treeNode2.right = treeNode5;
        treeNode3.left = treeNode6;
        treeNode3.right = treeNode7;
        treeNode4.left = treeNode8;
        treeNode5.left = treeNode9;
        treeNode5.right = treeNode10;
        treeNode7.right = treeNode11;

        System.out.println(no1315.sumEvenGrandparent(treeNode1));
    }
}
