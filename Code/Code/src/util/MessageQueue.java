package util;

import lombok.AllArgsConstructor;
import lombok.Data;

public class MessageQueue<T> {

    @Data
    @AllArgsConstructor
    public class Message<M>{
        Message<M> next;
        M m;
    }

    Message<T> root;
    public void put(T t) {
        if (root == null) {
            root = new Message<>(null, t);
        }else {
            Message<T> next = root;
            while (next.getNext() != null) {
                next=next.getNext();
            }
            next.setNext(new Message<>(null,t));
        }
    }

    public T get() {
        if (root == null) {
            return null;
        }else {
            T t = root.getM();
            root=root.getNext();
            return t;
        }
    }

    public static void main(String[] args) {
        MessageQueue<String> queue = new MessageQueue<>();
        queue.put("first");
        queue.put("second");
        System.out.println(queue.get());
        System.out.println(queue.get());
        System.out.println(queue.get());
    }
}
