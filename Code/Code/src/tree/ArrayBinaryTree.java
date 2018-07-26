package tree;


import java.util.Arrays;

/**
 * 一维数组从下标1开始存储
 * i的子节点 array[2*i]和array[2*i+1]
 * i的父节点 array[i/2]
 */
public class ArrayBinaryTree<T extends Comparable<T>> {
    private Object[] array;

    private void exchange(int i, int j) {
        Object temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private void re(int index) {
        if (index == 1) {
            return;
        }
        //合适位置=当前元素大于父节点，小于子节点
        if (((Comparable) array[index]).compareTo(array[index / 2]) < 0) {
            exchange(index, index / 2);
            re(index / 2);
        }
    }

    public void addNode(T node) {
        if (array == null) {
            array = new Object[]{node};
        } else {
            array = Arrays.copyOf(array, array.length + 1);
            array[array.length - 1] = node;
            re(array.length - 1);
        }
    }

    public void printTree() {
        for (Object o : array) {
            System.out.println(o);
        }

    }

    public static void main(String[] args) {
        ArrayBinaryTree<Integer> tree = new ArrayBinaryTree<>();
        tree.addNode(1);
        tree.addNode(3);
        tree.addNode(4);
        tree.addNode(5);
        tree.addNode(6);
        tree.addNode(7);
        tree.addNode(2);
        tree.printTree();
    }
}
