package util;

public class MyNode<T> {
    private MyNode preNode;
    private MyNode nextNode;
    private T t;

    public MyNode(T t) {
        this.t = t;
    }

    public MyNode getPreNode() {
        return preNode;
    }

    public void setPreNode(MyNode preNode) {
        this.preNode = preNode;
    }

    public MyNode getNextNode() {
        return nextNode;
    }

    public void setNextNode(MyNode nextNode) {
        this.nextNode = nextNode;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
}
