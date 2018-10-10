package util;

import java.util.Iterator;

/***
 * @Description: 实现 for (String var : list)
 * @author suntenghao
 * @date 2018-10-10 16:37
 */
public class MyList implements Iterable<String> {

    /**
     * 定义一个数组
     */
    private String[] list = {"A", "B", "C"};

    /**
     * 实现Iterable接口
     */
    @Override
    public Iterator<String> iterator() {
        return new MyIterator();
    }

    /**
     * 实现Iterator接口
     */
    public class MyIterator implements Iterator<String> {

        private int i = 0;

        @Override
        public boolean hasNext() {
            return i < 3;
        }

        @Override
        public String next() {
            return list[i++];
        }

        @Override
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