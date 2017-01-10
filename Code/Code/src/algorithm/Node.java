package algorithm;

import java.util.LinkedList;
import java.util.List;

public class Node {
    protected Object value;//the value of node,can be a number or string.

    protected Node parentNode;//non-essential. Only be needed when you want to insert a node into a tree

    protected List<Node> childNodes;

    public Node(Object value) {
        this.value = value;
    }

    public void addChild(Node node) {
        node.parentNode = this;//non-essential
        if (childNodes == null) {
            childNodes = new LinkedList<>();
        }
        childNodes.add(node);
    }

    public boolean isLeaf(){
        return this.childNodes==null||this.childNodes.isEmpty();
    }
    
    
    public static void main(String args[]) {
        Node node1 = new Node(1);
        Node node2 = new Node(2);
        Node node3 = new Node(3);
        Node node4 = new Node(4);
        Node node5 = new Node(5);
        Node node6 = new Node(6);
        Node node7 = new Node(7);
        Node node8 = new Node(8);
        Node node9 = new Node(9);

        node1.addChild(node2);
        node1.addChild(node3);
        node1.addChild(node4);
        node2.addChild(node5);
        node4.addChild(node6);
        node4.addChild(node7);
        node4.addChild(node8);
        node4.addChild(node9);
        /**
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         * 
         */
    }
}
