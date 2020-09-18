package leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 给出二叉树的根节点 root，树上每个节点都有一个不同的值。
 *
 * 如果节点值在 to_delete 中出现，我们就把该节点从树上删去，最后得到一个森林（一些不相交的树构成的集合）。
 *
 * 返回森林中的每棵树。你可以按任意顺序组织答案。
 *
 *  
 *
 * 示例：
 *
 *
 *
 * 输入：root = [1,2,3,4,5,6,7], to_delete = [3,5]
 * 输出：[[1,2,null,4],[6],[7]]
 *  
 *
 * 提示：
 *
 * 树中的节点数最大为 1000。
 * 每个节点都有一个介于 1 到 1000 之间的值，且各不相同。
 * to_delete.length <= 1000
 * to_delete 包含一些从 1 到 1000、各不相同的值。
 *
 */
public class No1110 {

    public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < to_delete.length; i++) {
            set.add(to_delete[i]);
        }
        List<TreeNode> result = new ArrayList<>();
        if (root != null && !set.contains(root.val)) {
            result.add(root);
        }
        collect(root, set, result);
        return result;
    }

    public void collect(TreeNode node, Set<Integer> deleteSet, List<TreeNode> list) {
        if (node == null) {
            return;
        }
        if (deleteSet.contains(node.val)) {
            if (node.left != null && !deleteSet.contains(node.left.val)) {
                list.add(node.left);
            }
            if (node.right != null && !deleteSet.contains(node.right.val)) {
                list.add(node.right);
            }
        }

        collect(node.left, deleteSet, list);
        collect(node.right, deleteSet, list);
        if (node.left != null && deleteSet.contains(node.left.val)) {
            node.left = null;
        }
        if (node.right != null && deleteSet.contains(node.right.val)) {
            node.right = null;
        }
    }

    public void print(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.print(node.val);
        print(node.left);
        print(node.right);
    }

    public static void main(String[] args) {
        No1110 no1110 = new No1110();
        TreeNode treeNode1 = no1110.new TreeNode(1);
        TreeNode treeNode2 = no1110.new TreeNode(2);
        TreeNode treeNode3 = no1110.new TreeNode(3);
        TreeNode treeNode4 = no1110.new TreeNode(4);
        TreeNode treeNode5 = no1110.new TreeNode(5);
        TreeNode treeNode6 = no1110.new TreeNode(6);
        TreeNode treeNode7 = no1110.new TreeNode(7);

        treeNode1.left = treeNode2;
        treeNode1.right = treeNode3;
        treeNode2.left = treeNode4;
        treeNode2.right = treeNode5;
        treeNode3.left = treeNode6;
        treeNode3.right = treeNode7;

        List<TreeNode> list = no1110.delNodes(treeNode1, new int[]{3, 4});
        for (TreeNode treeNode : list) {
            no1110.print(treeNode);
            System.out.println();
        }
    }
}
