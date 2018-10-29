java.util.Collection

Java中围绕集合有大量的内容，集合是这门语言的一个重要的核心，也是面试最容易考察的方面。众多接口和类编织了Java中集合的概念。
从数学的角度可以看到集合论的思想，从计算机的角度又有数组，链表，树等等具体的实现。
我们来看看Java中集合的根基
public interface Collection<E> extends Iterable<E>

其中Iterable<T>只是一个定义可以使用foreach迭代的接口声明，其只声明三个个方法用于迭代
boolean hasNext();
E next();
void remove();
所以Iterable无需过多研究。

//返回集合里元素个数(如果大于Integer.MAX_VALUE，就返回Integer.MAX_VALUE)
int size();

//判断是否有元素
boolean isEmpty();

//判断是否包含某个特定元素
boolean contains(Object o);

//迭代元素，并不能保证顺序
Iterator<E> iterator();

//集合转数组，这个方法方便使用数组的API
Object[] toArray();

//加了泛型的集合转数组
<T> T[] toArray(T[] a);

//添加元素(如果拒绝添加特殊的元素，最好抛异常而不是返回false)
 boolean add(E e);

//
boolean remove(Object o);

//
boolean containsAll(Collection<?> c);

//
boolean addAll(Collection<? extends E> c);

//
boolean removeAll(Collection<?> c);

//
boolean retainAll(Collection<?> c);

//
void clear();

//最后两个不必多看的方法
boolean equals(Object o);
int hashCode();