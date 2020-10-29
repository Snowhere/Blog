package leetcode;

import java.util.Arrays;

/**
 * 根据一棵树的前序遍历与中序遍历构造二叉树。
 *
 * 注意:
 * 你可以假设树中没有重复的元素。
 *
 * 例如，给出
 *
 * 前序遍历 preorder = [3,9,20,15,7]
 * 中序遍历 inorder = [9,3,15,20,7]
 * 返回如下的二叉树：
 *
 *     3
 *    / \
 *   9  20
 *     /  \
 *    15   7
 *
 */
public class No105 {
    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }


    /**
     * 前序遍历 [root,leftTree,rightTree]
     * 中序遍历 [leftTree,root,rightTree]
     * 递归生成
     * preorder 第一个元素是根元素
     * inorder 找到根元素，确定左子树长度，右子树长度
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || preorder.length == 0) {
            return null;
        }
        TreeNode root = new TreeNode(preorder[0]);
        int leftLength = 0;
        while (inorder[leftLength] != root.val) {
            leftLength++;
        }
        root.left = buildTree(Arrays.copyOfRange(preorder, 1, leftLength + 1), Arrays.copyOfRange(inorder, 0, leftLength));
        root.right = buildTree(Arrays.copyOfRange(preorder, leftLength + 1, preorder.length), Arrays.copyOfRange(inorder, leftLength + 1, inorder.length));
        return root;
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
        No105 no105 = new No105();
        TreeNode treeNode = no105.buildTree(new int[]{3, 9, 20, 15, 7}, new int[]{9, 3, 15, 20, 7});
        no105.print(treeNode);
    }
}
