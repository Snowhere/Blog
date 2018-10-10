package tree;

import java.util.LinkedList;

public class TraversalBinaryTree {


    //前序遍历 中，左，右
    //Preorder Traversal (DLR)
    public static void DLR(TreeNode root) {
        if (root != null) {
            System.out.print(root.getValue());
            DLR(root.getLeftNode());
            DLR(root.getRightNode());
        }
    }

    //中序遍历 左，中，右
    //Inorder Traversal (LDR)
    public static void LDR(TreeNode root) {
        if (root != null) {
            LDR(root.getLeftNode());
            System.out.print(root.getValue());
            LDR(root.getRightNode());
        }
    }

    //后序遍历 左，右，中
    //Postorder Traversal (LRD)
    public static void LRD(TreeNode root) {
        if (root != null) {
            LRD(root.getLeftNode());
            LRD(root.getRightNode());
            System.out.print(root.getValue());
        }
    }

    //层级遍历，用队列
    public static void traversal(TreeNode root) {
        LinkedList<TreeNode> queue = new LinkedList<>();
        if (root != null) {
            queue.offer(root);
        }
        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            System.out.print(node.getValue());
            if (node.getLeftNode() != null) {
                queue.offer(node.getLeftNode());
            }
            if (node.getRightNode() != null) {
                queue.offer(node.getRightNode());
            }
        }
    }

    public static void main(String[] args) {
        TreeNode<Integer> node1 = new TreeNode(1);
        TreeNode<Integer> node2 = new TreeNode(2);
        TreeNode<Integer> node3 = new TreeNode(3);
        TreeNode<Integer> node4 = new TreeNode(4);
        TreeNode<Integer> node5 = new TreeNode(5);
        TreeNode<Integer> node6 = new TreeNode(6);
        TreeNode<Integer> node7 = new TreeNode(7);
        TreeNode<Integer> node8 = new TreeNode(8);
        TreeNode<Integer> node9 = new TreeNode(9);

        node1.setLeftNode(node2);
        node1.setRightNode(node3);
        node2.setLeftNode(node4);
        node2.setRightNode(node5);
        node5.setLeftNode(node7);
        node5.setRightNode(node8);
        node3.setRightNode(node6);

        DLR(node1);
        System.out.println();
        LDR(node1);
        System.out.println();
        LRD(node1);
        System.out.println();
        traversal(node1);
    }
}
