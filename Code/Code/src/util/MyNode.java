package util;

import lombok.Data;

@Data
public class MyNode<T> {
    private MyNode<T> preNode;
    private MyNode<T> nextNode;
    private T t;

    public MyNode(T t) {
        this.t = t;
    }
}
