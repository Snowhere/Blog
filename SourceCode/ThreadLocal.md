# ThreadLocal

## ThreadLocal 是用来做什么的

我们都知道多线程会带来资源并发访问操作的问题，要解决这个问题，最直接的方法就是避免共享变量，全部采用私有变量的形式。但在一个线程中，我们维护一个私有变量，在逻辑流程中不断传递下去有点麻烦。每个线程都会有唯一的线程 id，那我们自然就想到能不能用一个全局的线程安全的 map 来存储数据，用线程 id 作为 key，其中我们可能要储存的数据比较复杂，所以 value 的结构也是一个 map。

```
public static Map<currentThreadId,Map<key,value>>
```

我们优化一下这种方式，维护最外层这个全局线程安全的 map 是昂贵而且不必要的，因为每个线程都有一个对应的 Thread 对象，我们可以直接针对每个 Thread 对象，维护一个储存数据的 map。进一步，每个业务只关心自己的数据，对其他数据并不关心，因此我们针对每一个业务，生成唯一的 key，用户不需要关心 key 的值，我们抽象出一个数据结构 Data<value>,只提供最简单的 get 和 set 操作，内部实现是 map，但屏蔽 key 的存在，让用户使用起来简单方便。

```
//一个全局的 Map 保存所有线程变量，每个线程的变量也是 Map 结构
map = Map<currentThreadId,Map<key,value>>

//优化为每个线程一个 Map 保存变量
currentThreadId1 = Map<key,value>
currentThreadId2 = Map<key,value>
currentThreadId3 = Map<key,value>
...

//优化为每个线程每个 key 为一个数据结构
currentThreadId1&key11 = value
currentThreadId1&key12 = value
currentThreadId2&key21 = value
currentThreadId2&key22 = value
currentThreadId3&key31 = value
currentThreadId3&key32 = value
...

```

最后我们得到的结构就是 ThreadLocal<T>，其中有一个内部类 ThreadLocalMap，而 ThreadLocalMap 绑定到 Thread 类上，使得每个线程都有这个

```
public class ThreadLocal<T> {
    static class ThreadLocalMap {
        static class Entry extends WeakReference<ThreadLocal<?>> {
        }
    }
}
```

```
public class Thread implements Runnable {
    public static native Thread currentThread();
    ThreadLocal.ThreadLocalMap threadLocals = null;
```

```
public class ThreadLocal<T> {
    static class ThreadLocalMap {
        static class Entry extends WeakReference<ThreadLocal<?>> {
            Object value;
            Entry(ThreadLocal<?> k, Object v) {
                super(k);
                value = v;
            }
        }
    }
}
```


1.ThreadLocalMap 在 ThreadLocal 中定义，在 Thread 中引用
2.ThreadLocalMap 使用弱引用




























