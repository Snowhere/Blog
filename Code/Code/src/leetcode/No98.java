package leetcode;
/**
 * 给定一个二叉树，判断其是否是一个有效的二叉搜索树。
 *
 * 假设一个二叉搜索树具有如下特征：
 *
 * 节点的左子树只包含小于当前节点的数。
 * 节点的右子树只包含大于当前节点的数。
 * 所有左子树和右子树自身必须也是二叉搜索树。
 * 示例 1:
 *
 * 输入:
 *     2
 *    / \
 *   1   3
 * 输出: true
 * 示例 2:
 *
 * 输入:
 *     5
 *    / \
 *   1   4
 *      / \
 *     3   6
 * 输出: false
 * 解释: 输入为: [5,1,4,null,null,3,6]。
 *      根节点的值为 5 ，但是其右子节点值为 4 。
 *
 */
public class No98 {

    public class TreeNode {
     int val;
    TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }

        public boolean isValidBST(TreeNode root) {
            return isValid(null,null,root);
        }

    boolean isValid(Integer min, Integer max, TreeNode node) {
        boolean flag= true;
        if (node == null) {
            return true;
        }else {
            if(min!=null){
                flag = flag && (min < node.val);
            }
            if (max != null) {
                flag = flag && (max > node.val);
            }
            flag = flag && isValid(min, node.val, node.left) && isValid(node.val,max,node.right);
        }
        return flag;
    }

    public static void main(String[] args) {
        No98 no98 = new No98();
        TreeNode treeNode5 = no98.new TreeNode(5);
        TreeNode treeNode1 = no98.new TreeNode(1);
        TreeNode treeNode7 = no98.new TreeNode(7);
        TreeNode treeNode4 = no98.new TreeNode(4);
        TreeNode treeNode8 = no98.new TreeNode(8);

        treeNode5.left=treeNode1;
        treeNode5.right=treeNode7;
        treeNode7.left=treeNode4;
        treeNode7.right=treeNode8;

        System.out.println(no98.isValidBST(treeNode5));

    }
}
