package tree;

import lombok.Data;

@Data
public class TreeNode<T> {
    private TreeNode leftNode;
    private TreeNode rightNode;
    private T value;

    public TreeNode(T value) {
        this.value = value;
    }
}
