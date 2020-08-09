# ThreadLocal

## ThreadLocal 是用来做什么的

我们都知道多线程会带来资源并发访问操作的问题，要解决这个问题，最直接的方法就是避免共享变量，全部采用私有变量的形式。但在一个线程中，我们维护一个私有变量，在逻辑流程中不断传递下去有点麻烦。每个线程都会有唯一的线程 id，那我们自然就想到能不能用一个全局的线程安全的 map 来存储数据，用线程 id 作为 key，其中我们可能要储存的数据比较复杂，所以 value 的结构也是一个 map。

```
public static Map<currentThreadId,Map<key,value>>
```

我们优化一下这种方式，维护最外层这个全局线程安全的 map 是昂贵而且不必要的，因为每个线程都有一个对应的 Thread 对象，我们可以直接针对每个 Thread 对象，维护一个 map。

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






























