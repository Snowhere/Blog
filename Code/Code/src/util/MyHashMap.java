package util;

import lombok.AllArgsConstructor;
import lombok.Data;

/***
 * @Description: size 固定为 3 的 map
 * @author suntenghao
 * @date 2018-10-10 16:29
 */
public class MyHashMap<K, V> {
    private Node<K, V>[] array;
    private int size = 3;

    MyHashMap() {
        array = new Node[size];
    }

    public void put(K key, V value) {
        int hashCode = key.hashCode();
        int index = hashCode % size;
        Node<K, V> node = array[index];
        if (node == null) {
            array[index] = new Node<>(key, value, null);
            return;
        }
        if (node.getKey() == key) {
            node.setValue(value);
            return;
        }
        for (; node.getNextNode() != null; node = node.getNextNode()) {
            if (node.getNextNode().getKey() == key) {
                node.getNextNode().setValue(value);
                return;
            }
        }
        node.setNextNode(new Node<>(key, value, null));
    }

    public V get(K key) {
        int hashCode = key.hashCode();
        Node<K, V> node = array[hashCode % size];
        while (node != null) {
            if (node.getKey() == key) {
                return node.getValue();
            }
            node = node.getNextNode();
        }
        return null;
    }

    @Data
    @AllArgsConstructor
    private class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> nextNode;
    }


    public static void main(String[] args) {
        MyHashMap<Integer, String> map = new MyHashMap<>();
        map.put(1, "1");
        map.put(2, "2");
        map.put(3, "3");
        map.put(4, "4");
        map.put(1, "11");
        map.put(4, "44");
        System.out.println(map.get(1));
        System.out.println(map.get(2));
        System.out.println(map.get(4));

    }
}
