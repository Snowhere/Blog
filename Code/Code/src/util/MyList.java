package util;

import java.util.Iterator;

public class MyList implements Iterable<String> {

    // 定义一个数组
    private String[] list = { "A", "B", "C" };

    // 实现Iterable接口
    public Iterator<String> iterator() {
        return new MyIterator();
    }

    // 实现Iterator接口
    public class MyIterator implements Iterator<String> {

        private int i = 0;

        public boolean hasNext() {
            return i < 3;
        }

        public String next() {
            return list[i++];
        }

        public void remove() {
            // TODO 我比较懒，这里和我要测的foreach关系不大，就不写了。
        }
    }

    public static void main(String args[]) {
        MyList list = new MyList();
        for (String var : list) {
            System.out.println(var);
        }
    }
}