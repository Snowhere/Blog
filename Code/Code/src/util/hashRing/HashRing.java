package util.hashRing;

import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 哈希环(实现一致性哈希)
 * 我们模拟将元素根据一致性哈希算法放入多个缓存服务节点(node)
 *
 * @author suntenghao
 * @date 2019-06-11
 */
public class HashRing<K, V> {


    private ConcurrentSkipListMap<Integer, CacheNode<K, V>> ring = new ConcurrentSkipListMap<>();

    public HashRing(CacheNode<K, V>[] nodeList) {
        for (CacheNode<K, V> node : nodeList) {
            ring.put(hashCode(node.getNodeKey()), node);
        }
    }

    /**
     * 增加节点，需要对其后的最近一个node里的所有元素rehash
     *
     * @author suntenghao
     * @date 2019-06-12
     */
    public void addNode(CacheNode<K, V> node) {
        int nodeHash = hashCode(node.getNodeKey());
        //后面最近的一个node
        CacheNode<K, V> nextNode = getNearestCacheNode(nodeHash);
        //插入当前node
        ring.put(nodeHash, node);
        //最近的node中所有元素rehash
        CacheNode<K, V>.Entry<K, V> entry = null;
        if (nextNode != null) {
            entry = nextNode.getRoot();
            nextNode.setRoot(null);
        }
        while (entry != null) {
            getNearestCacheNode(hashCode(entry.getK())).put(entry.getK(), entry.getV());
            entry = entry.getNext();
        }
    }

    /**
     * 删除节点，需要对此节点内所有元素rehash
     *
     * @author suntenghao
     * @date 2019-06-12
     */
    public void deleteNode(K nodeKey) {
        int nodeHash = hashCode(nodeKey);
        CacheNode<K, V> node = ring.remove(nodeHash);
        CacheNode<K, V>.Entry<K, V> entry = node == null ? null : node.getRoot();
        while (entry != null) {
            getNearestCacheNode(hashCode(entry.getK())).put(entry.getK(), entry.getV());
            entry = entry.getNext();
        }
    }

    public void put(K k, V v) {
        int keyHash = hashCode(k);
        getNearestCacheNode(keyHash).put(k, v);
    }

    public V get(K k) {
        int keyHash = hashCode(k);
        return getNearestCacheNode(keyHash).get(k);
    }

    private int hashCode(K k) {
        return Math.abs(k.hashCode());
    }

    private CacheNode<K, V> getNearestCacheNode(int keyHash) {
        ConcurrentNavigableMap<Integer, CacheNode<K, V>> tailMap = ring.tailMap(keyHash);
        return tailMap.isEmpty() ? ring.firstEntry().getValue() : tailMap.firstEntry().getValue();
    }
}
