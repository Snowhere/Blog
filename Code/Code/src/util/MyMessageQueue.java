package util;

public class MyMessageQueue<T> {

    private MyNode currentNode;

    public void put(T t) {
        MyNode<T> newNode = new MyNode(t);
        MyNode node = currentNode;
        if (node == null) {
            currentNode=newNode;
            return;
        }
        while (node.getNextNode() != null) {
            node = node.getNextNode();
        }
        node.setNextNode(newNode);
        return;
    }

    public T get() {
        MyNode<T> node = currentNode;
        if (node == null) {
            return null;
        }
        currentNode = currentNode.getNextNode();
        return node.getT();
    }

    public static void main(String[] args) {
        MyMessageQueue<String> queue = new MyMessageQueue<>();
        queue.put("test");
        queue.put("test2");
        System.out.println(queue.get());
        System.out.println(queue.get());
        System.out.println(queue.get());
    }
}
