package util;

/**
 * size固定3
 * @param <K>
 * @param <V>
 */
public class MyHashMap<K, V> {
    private Node[] array;
    private int size = 3;

    MyHashMap() {
        array = new Node[3];
    }

    public void put(K key, V value) {
        int hashCode = key.hashCode();
        Node node = array[hashCode % 3];
        if (node == null) {
            array[hashCode % 3] = new Node(key, value);
            return;
        }
        if (node.getKey() == key) {
            node.setValue(value);
            return;
        }
        for (; node.getNextNode() != null; node = node.getNextNode())
            if (node.getNextNode().getKey() == key) {
                node.getNextNode().setValue(value);
                return;
            }
        node.setNextNode(new Node(key, value));
    }

    public V get(K key) {
        int hashCode = key.hashCode();
        Node<K, V> node = array[hashCode % 3];
        while (node != null) {
            if (node.getKey() == key) {
                return node.getValue();
            }
            node = node.getNextNode();
        }
        return null;
    }

    class Node<K, V> {
        private K key;
        private V value;
        private Node nextNode;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public Node getNextNode() {
            return nextNode;
        }

        public void setNextNode(Node nextNode) {
            this.nextNode = nextNode;
        }
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
