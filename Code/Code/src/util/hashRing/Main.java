package util.hashRing;

public class Main {

    public static void main(String[] args) {
        //创建3个节点的哈希环0,10,20
        CacheNode<Integer,String>[] nodeList = new CacheNode[3];
        for (int i = 0; i < 3; i++) {
            nodeList[i] = new CacheNode<>(i*10);
        }
        HashRing<Integer, String> hashRing = new HashRing<>(nodeList);
        //放入元素1,11,12,22,23
        hashRing.put(1, "1");
        hashRing.put(11, "11");
        hashRing.put(17, "17");
        hashRing.put(22, "22");
        hashRing.put(23, "23");

        //增加节点15
        hashRing.addNode(new CacheNode<>(15));

        //删除节点15
        hashRing.deleteNode(15);

        System.out.println(hashRing.get(11));
    }
}
