java.lang.Iterable 和 java.util.Iterator 和 java.util.Enumeration


这两个接口都为泛型接口，定义简单
先来看一看Iterable接口，@since 1.5。
而Iterable存在的目的就是使目标可以使用foreach循环

public interface Iterable<T> {
    Iterator<T> iterator();
}

里面只有一个方法，就是返回一个Iterator，用于迭代。
我们马上来看一看Iterator接口

public interface Iterator<E> {
    boolean hasNext();  
    E next();
    void remove();
}

也很简单，三个方法，一个判断是否有后续元素，一个获取后续元素，一个删除当前元素。
这里要注意remove方法，因为是删除当前元素，所以调用remove方法前，至少要调用一次next方法。
一般实现时通过一个标识变量，在next方法中修改为可remove状态，在remove方法中先判断标识，然后remove，最后变量改为不可remove状态。

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

输出结果
A
B
C

Iterable定义在1.5版本，Itertor定义在1.2版本。
而定义在最初的1.0版本用于枚举和迭代的，是Enumeration接口。
Itertor是作为Enumeration的替代，为了向下兼容，Enumeration并没有标注弃用

public interface Enumeration<E> {
    boolean hasMoreElements();
    E nextElement();
}

Itertor相比Enumeration有两点改进
1.增加了remove方法
2.简化了方法名。