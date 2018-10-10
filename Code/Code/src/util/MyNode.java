package util;

import lombok.Data;

/***
 * @Description: Node 节点
 * @author suntenghao
 * @date 2018-10-10 16:41
 */
@Data
public class MyNode<T> {
    private MyNode<T> preNode;
    private MyNode<T> nextNode;
    private T t;

    public MyNode(T t) {
        this.t = t;
    }
}
