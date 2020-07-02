package util;

import lombok.AllArgsConstructor;
import lombok.Data;

public class Queue<T> {

    @Data
    @AllArgsConstructor
    class Message<T>{
        Message<T> next;
        T data;
    }

    Message<T> first;

    public T get() {
        if (first == null) {
            return null;
        } else {
            T data = first.getData();
            first = first.getNext();
            return data;
        }
    }

    public void put(T t) {
        if (first == null) {
            first = new Message<>(null, t);
        }else {
            Message<T> node = first;
            while (node.getNext() != null) {
                node=node.getNext();
            }
            node.setNext(new Message<>(null, t));
        }
    }

    public boolean isEmpty() {
        return first==null;
    }

    public static void main(String[] args) {
        Queue<String> queue = new Queue<>();
        queue.put("1");
        queue.put("2");
        System.out.println(queue.get());
        System.out.println(queue.get());
        System.out.println(queue.get());
    }
}
