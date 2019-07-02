package util.hashRing;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用简单链表模拟缓存服务节点(node)
 * 提供put和get两个方法，用Entry封装元素
 *
 * @author suntenghao
 * @date 2019-06-11
 */
@Data
public class CacheNode<K, V> {
    @Data
    @AllArgsConstructor
    class Entry<K, V> {
        K k;
        V v;
        Entry<K, V> next;
    }

    K nodeKey;
    Entry<K, V> root = null;

    public CacheNode(K nodeKey) {
        this.nodeKey = nodeKey;
    }

    public void put(K k, V v) {
        if (root == null) {
            root = new Entry<>(k, v, null);
        } else {
            Entry<K, V> current = root;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(new Entry<>(k, v, null));
        }
    }

    public V get(K k) {
        Entry<K, V> current = root;
        while (current != null) {
            if (current.getK().equals(k)) {
                return current.getV();
            }
            current = current.getNext();
        }
        return null;
    }

}
