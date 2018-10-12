package generic;

public class Erase {
    static class Node<T> {

        public T data;

        public Node(T data) { this.data = data; }

        public void setData(T data) {
            System.out.println("Node.setData");
            this.data = data;
        }

        public T getData() { return data; }

    }

    static class MyNode extends Node<Integer> {
        public MyNode(Integer data) {
            super(data);
        }

        @Override
        public void setData(Integer data) {
            System.out.println("MyNode.setData");
            super.setData(data);
        }
    }

    public static void main(String[] args) {
        Node myNode = new MyNode(1);
        myNode.setData("2");
    }
}
