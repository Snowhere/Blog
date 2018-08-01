package util;

public class MyMessageQueue<T> {

    private MyNode<T> firstNode;

    public void put(T t) {
        MyNode<T> newNode = new MyNode<>(t);
        //记录起始 node
        MyNode node = firstNode;
        if (node == null) {
            firstNode = newNode;
            return;
        }
        //新 node 添加到队尾
        while (node.getNextNode() != null) {
            node = node.getNextNode();
        }
        node.setNextNode(newNode);
    }

    public T get() {
        if (firstNode == null) {
            return null;
        }
        T value = firstNode.getT();
        firstNode = firstNode.getNextNode();
        return value;
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
