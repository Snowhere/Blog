# ThreadLocal

## ThreadLocal 是用来做什么的

我们都知道多线程会带来资源并发访问操作的问题，要解决这个问题，最直接的方法就是避免共享变量，全部采用私有变量的形式。但在一个线程中，我们维护一个私有变量，在流程中传递下去有点麻烦。每个线程都会有唯一的线程id，那我们自然就想到能不能用一个全局的线程安全的 map 来存储数据，用线程 id 作为 key，其中我们可能要存多组数据，所以 value 的结构也是一个 map。

static map<k,v>

v = map.get(Thread.currentThread().getId())



































