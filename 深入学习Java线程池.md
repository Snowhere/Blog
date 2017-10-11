# 深入学习 Java 线程池[Finally Getting the Most out of the Java Thread Pool](https://stackify.com/java-thread-pools/)

![](https://stackify.com/wp-content/uploads/2017/08/The-Challenge-of-Java-Thread-Pools-793x397.png)

Thread pool is a core concept in multithreaded programming which, simply put, represents a collection of idle threads that can be used to execute tasks.

线程池是多线程编程中的核心概念，简单来说就是一组可以执行任务的空闲线程。

First, let’s outline a frame of reference for multithreading and why we may need to use a thread pool.

首先，我们了解一下多线程框架模型，明白为什么需要线程池。

A thread is an execution context that can run a set of instructions within a process – aka a running program. Multithreaded programming refers to using threads to execute multiple tasks concurrently. Of course, this paradigm is well supported on the JVM.

线程是在一个进程中可以执行一系列指令的执行环境，或称运行程序。多线程编程指的是用多个线程并行执行多个任务。当然，JVM 多线程有良好的支持。

Although this brings several advantages, primarily regarding the performance of a program, the multithreaded programming can also have disadvantages – such as increased complexity of the code, concurrency issues, unexpected results and adding the overhead of thread creation.

尽管这带来了诸多优势，首当其冲的就是程序性能提高，但多线程编程也有缺点 —— 增加了代码复杂度、同步问题、非预期结果和增加创建线程开销。

In this article, we’re going to take a closer look at how the latter issue can be mitigated by using thread pools in Java.

在这篇文章中，我们来了解一下如何使用 Java 线程池来缓解这些问题。

## Why Use a Thread Pool?
## 为什么使用线程池？

Creating and starting a thread can be an expensive process. By repeating this process every time we need to execute a task, we’re incurring a significant performance cost – which is exactly what we were attempting to improve by using threads.

创建并开启一个线程开销很大。如果我们每次需要执行任务时重复这个步骤，那将会是一笔巨大的性能开销，这也是我们希望通过多线程解决的问题。

For a better understanding of the cost of creating and starting a thread, let’s see what the JVM actually does behind the scenes:

为了更好理解创建和开启一个线程的开销，让我们来看一看 JVM 在后台做了哪些事：

* it allocates memory for a thread stack that holds a frame for every thread method invocation
* each frame consists of a local variable array, return value, operand stack and constant pool
* some JVMs that support native methods also allocate a native stack
* each thread gets a program counter that tells it what the current instruction executed by the processor is
* the system creates a native thread corresponding to the Java thread
* descriptors relating to the thread are added to the JVM internal data structures
* the threads share the heap and method area

* 为线程栈分配内存，保存每个线程方法调用的栈帧。
* 每个栈帧包括本地变量数组、返回值、操作栈和常量池
* 一些 JVM 支持本地方法，也将分配本地方法栈
* 每个线程获得一个程序计数器，标识处理器正在执行哪条指令
* 系统创建本地线程，与 Java 线程对应
* 和线程相关的描述符被添加到 JVM 内部数据结构
* 线程共享堆和方法区

Of course, the details of all this will depend on the JMV and the operating system.

当然，这些步骤的具体细节取决于 JVM 和操作系统。

In addition, more threads mean more work for the system scheduler to decide which thread gets access to resources next.

另外，更多的线程意味着更多工作量，系统需要调度和决定哪个线程接下来可以访问资源。

A thread pool helps mitigate the issue of performance by reducing the number of threads needed and managing their lifecycle.

线程池通过减少需要的线程数量并管理线程生命周期，来帮助我们缓解性能问题。

Essentially, threads are kept in the thread pool until they’re needed, after which they execute the task and return the pool to be reused later. This mechanism is especially helpful in systems that execute a large number of small tasks.

本质上，线程在我们使用前一直保存在线程池中，在执行完任务之后，线程会返回线程池等待下次使用。这种机制在执行很多小任务的系统中十分有用。

## Java Thread Pools
## Java 线程池

Java provides its own implementations of the thread pool pattern, through objects called executors. These can be used through executor interfaces or directly through thread pool implementations – which does allow for finer-grained control.

Java 通过 executor 对象，来实现自己的线程池模型

The java.util.concurrent package contains the following interfaces:

Executor – a simple interface for executing tasks
ExecutorService – a more complex interface which contains additional methods for managing the tasks and the executor itself
ScheduledExecutorService – extends ExecutorService with methods for scheduling the execution of a task
Alongside these interfaces, the package also provides the Executors helper class for obtaining executor instances, as well as implementations for these interfaces.

Generally, a Java thread pool is composed of:

the pool of worker threads, responsible for managing the threads
a thread factory that is responsible for creating new threads
a queue of tasks waiting to be executed
In the following sections, let’s see how the Java classes and interfaces that provide support for thread pools work in more detail.

The Executors class and Executor interface

The Executors class contains factory methods for creating different types of thread pools, while Executor is the simplest thread pool interface, with a single execute() method.

Let’s use these two classes in conjunction with an example that creates a single-thread pool, then uses it to execute a simple statement:

Executor executor = Executors.newSingleThreadExecutor();
executor.execute(() -> System.out.println("Single thread pool test"));
Notice how the statement can be written as a lambda expression – which is inferred to be of Runnable type.

The execute() method runs the statement if a worker thread is available, or places the Runnable task in a queue to wait for a thread to become available.

Basically, the executor replaces the explicit creation and management of a thread.

The factory methods in the Executors class can create several types of thread pools:

newSingleThreadExecutor() – a thread pool with only one thread with an unbounded queue, which only executes one task at a time
newFixedThreadPool() – a thread pool with a fixed number of threads which share an unbounded queue; if all threads are active when a new task is submitted, they will wait in queue until a thread becomes available
newCachedThreadPool() – a thread pool that creates new threads as they are needed
newWorkStealingThreadPool() – a thread pool based on a “work-stealing” algorithm which will be detailed more in a later section
Next, let’s take a look into what additional capabilities the ExecutorService interface.

The ExecutorService

One way to create an ExecutorService is to use the factory methods from the Executors class:

ExecutorService executor = Executors.newFixedThreadPool(10);
Besides the execute() method, this interface also defines a similar submit() method that can return a Future object:

Callable<Double> callableTask = () -> {
    return employeeService.calculateBonus(employee);
};
Future<Double> future = executor.submit(callableTask);
// execute other operations
try {
    if (future.isDone()) {
        double result = future.get();
    }
} catch (InterruptedException | ExecutionException e) {
    e.printStackTrace();
}
As you can see in the example above, the Future interface can return the result of a task for Callable objects, and can also show the status of a task execution.

The ExecutorService is not automatically destroyed when there are no tasks waiting to be executed, so to shut it down explicitly, you can use the shutdown() or shutdownNow() APIs:

executor.shutdown();
The ScheduledExecutorService

This is a subinterface of ExecutorService – which adds methods for scheduling tasks:

ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
The schedule() method specifies a task to be executed, a delay value and a TimeUnit for the value:

Future<Double> future = executor.schedule(callableTask, 2, TimeUnit.MILLISECONDS);
Furthermore, the interface defines two additional methods:

executor.scheduleAtFixedRate(
  () -> System.out.println("Fixed Rate Scheduled"), 2, 2000, TimeUnit.MILLISECONDS);

executor.scheduleWithFixedDelay(
  () -> System.out.println("Fixed Delay Scheduled"), 2, 2000, TimeUnit.MILLISECONDS);
The scheduleAtFixedRate() method executes the task after 2 ms delay, then repeats it at every 2 seconds. Similarly, the scheduleWithFixedDelay() method starts the first execution after 2 ms, then repeats the task 2 seconds after the previous execution ends.

In the following sections, let’s also go through two implementations of the ExecutorService interface: ThreadPoolExecutor and ForkJoinPool.

The ThreadPoolExecutor

This thread pool implementation adds the ability to configure parameters, as well as extensibility hooks. The most convenient way to create a ThreadPoolExecutor object is by using the Executors factory methods:

ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
In this manner, the thread pool is preconfigured for the most common cases. The number of threads can be controlled by setting the parameters:

corePoolSize and maximumPoolSize – which represent the bounds of the number of threads
keepAliveTime – which determines the time to keep extra threads alive
Digging a bit further, here’s how these parameters are used.

If a task is submitted and fewer than corePoolSize threads are in execution, then a new thread is created. The same thing happens if there are more than corePoolSize but less than maximumPoolSize threads running, and the task queue is full. If there are more than corePoolSize threads which have been idle for longer than keepAliveTime, they will be terminated.

In the example above, the newFixedThreadPool() method creates a thread pool with corePoolSize=maximumPoolSize=10, and a keepAliveTime of 0 seconds.

If you use the newCachedThreadPool() method instead, this will create a thread pool with a maximumPoolSize of Integer.MAX_VALUE and a keepAliveTime of 60 seconds:

ThreadPoolExecutor cachedPoolExecutor 
  = (ThreadPoolExecutor) Executors.newCachedThreadPool();
The parameters can also be set through a constructor or through setter methods:

ThreadPoolExecutor executor = new ThreadPoolExecutor(
  4, 6, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>()
);
executor.setMaximumPoolSize(8);
A subclass of ThreadPoolExecutor is the ScheduledThreadPoolExecutor class, which implements the ScheduledExecutorService interface. You can create this type of thread pool by using the newScheduledThreadPool() factory method:

ScheduledThreadPoolExecutor executor 
  = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(5);
This creates a thread pool with a corePoolSize of 5, an unbounded maximumPoolSize and a keepAliveTime of 0 seconds.

The ForkJoinPool

Another implementation of a thread pool is the ForkJoinPool class. This implements the ExecutorService interface and represents the central component of the fork/join framework introduced in Java 7.

The fork/join framework is based on a “work-stealing algorithm”. In simple terms, what this means is that threads that run out of tasks can “steal” work from other busy threads.

A ForkJoinPool is well suited for cases when most tasks create other subtasks or when many small tasks are added to the pool from external clients.

The workflow for using this thread pool typically looks something like this:

create a ForkJoinTask subclass
split the tasks into subtasks according to a condition
invoke the tasks
join the results of each task
create an instance of the class and add it to the pool
To create a ForkJoinTask, you can choose one of its more commonly used subclasses, RecursiveAction or RecursiveTask – if you need to return a result.

Let’s implement an example of a class that extends RecursiveTask and calculates the factorial of a number by splitting it into subtasks depending on a THRESHOLD value:

public class FactorialTask extends RecursiveTask<BigInteger> {
    private int start = 1;
    private int n;
    private static final int THRESHOLD = 20;

    // standard constructors

    @Override
    protected BigInteger compute() {
        if ((n - start) >= THRESHOLD) {
            return ForkJoinTask.invokeAll(createSubtasks())
              .stream()
              .map(ForkJoinTask::join)
              .reduce(BigInteger.ONE, BigInteger::multiply);
        } else {
            return calculate(start, n);
        }
    }
}
The main method that this class needs to implement is the overridden compute() method, which joins the result of each subtask.

The actual splitting is done in the createSubtasks() method:

private Collection<FactorialTask> createSubtasks() {
    List<FactorialTask> dividedTasks = new ArrayList<>();
    int mid = (start + n) / 2;
    dividedTasks.add(new FactorialTask(start, mid));
    dividedTasks.add(new FactorialTask(mid + 1, n));
    return dividedTasks;
}
Finally, the calculate() method contains the multiplication of values in a range:

private BigInteger calculate(int start, int n) {
    return IntStream.rangeClosed(start, n)
      .mapToObj(BigInteger::valueOf)
      .reduce(BigInteger.ONE, BigInteger::multiply);
}
Next, tasks can be added to a thread pool:

ForkJoinPool pool = ForkJoinPool.commonPool();
BigInteger result = pool.invoke(new FactorialTask(100));
ThreadPoolExecutor vs. ForkJoinPool

At first look, it seems that the fork/join framework brings improved performance. However, this may not always be the case depending on the type of problem you need to solve.

When choosing a thread pool, it’s important to also remember there is overhead caused by creating and managing threads and switching execution from one thread to another.

The ThreadPoolExecutor provides more control over the number of threads and the tasks that are executed by each thread. This makes it more suitable for cases when you have a smaller number of larger tasks that are executed on their own threads.

By comparison, the ForkJoinPool is based on threads “stealing” tasks from other threads. Because of this, it is best used to speed up work in cases when tasks can be broken up into smaller tasks.

To implement the work-stealing algorithm, the fork/join framework uses two types of queues:

a central queue for all tasks
a task queue for each thread
When threads run out of tasks in their own queues, they attempt to take tasks from the other queues. To make the process more efficient, the thread queue uses a deque (double ended queue) data structure, with threads being added at one end and “stolen” from the other end.

Here is a good visual representation of this process from The H Developer:

fork/join thread pool

In contrast with this model, the ThreadPoolExecutor uses only one central queue.

One last thing to remember is that the choosing a ForkJoinPool is only useful if the tasks create subtasks. Otherwise, it will function the same as a ThreadPoolExecutor, but with extra overhead.

Tracing Thread Pool Execution

Now that we have a good foundational understanding of the Java thread pool ecosystem let’s take a closer look at what happens during the execution of an application that uses a thread pool.

By adding some logging statements in the constructor of FactorialTask and the calculate() method, you can follow the invocation sequence:

13:07:33.123 [main] INFO ROOT - New FactorialTask Created
13:07:33.123 [main] INFO ROOT - New FactorialTask Created
13:07:33.123 [main] INFO ROOT - New FactorialTask Created
13:07:33.123 [main] INFO ROOT - New FactorialTask Created
13:07:33.123 [ForkJoinPool.commonPool-worker-1] INFO ROOT - New FactorialTask Created
13:07:33.123 [ForkJoinPool.commonPool-worker-1] INFO ROOT - New FactorialTask Created
13:07:33.123 [main] INFO ROOT - New FactorialTask Created
13:07:33.123 [main] INFO ROOT - New FactorialTask Created
13:07:33.123 [main] INFO ROOT - Calculate factorial from 1 to 13
13:07:33.123 [ForkJoinPool.commonPool-worker-1] INFO ROOT - New FactorialTask Created
13:07:33.123 [ForkJoinPool.commonPool-worker-2] INFO ROOT - New FactorialTask Created
13:07:33.123 [ForkJoinPool.commonPool-worker-1] INFO ROOT - New FactorialTask Created
13:07:33.123 [ForkJoinPool.commonPool-worker-2] INFO ROOT - New FactorialTask Created
13:07:33.123 [ForkJoinPool.commonPool-worker-1] INFO ROOT - Calculate factorial from 51 to 63
13:07:33.123 [ForkJoinPool.commonPool-worker-2] INFO ROOT - Calculate factorial from 76 to 88
13:07:33.123 [ForkJoinPool.commonPool-worker-3] INFO ROOT - Calculate factorial from 64 to 75
13:07:33.163 [ForkJoinPool.commonPool-worker-3] INFO ROOT - New FactorialTask Created
13:07:33.163 [main] INFO ROOT - Calculate factorial from 14 to 25
13:07:33.163 [ForkJoinPool.commonPool-worker-3] INFO ROOT - New FactorialTask Created
13:07:33.163 [ForkJoinPool.commonPool-worker-2] INFO ROOT - Calculate factorial from 89 to 100
13:07:33.163 [ForkJoinPool.commonPool-worker-3] INFO ROOT - Calculate factorial from 26 to 38
13:07:33.163 [ForkJoinPool.commonPool-worker-3] INFO ROOT - Calculate factorial from 39 to 50
Here you can see there are several tasks created, but only 3 worker threads – so these get picked up by the available threads in the pool.

Also notice how the objects themselves are actually created in the main thread, before being passed to the pool for execution.

This is actually a great way to explore and understand thread pools at runtime, with the help of a solid logging visualization tool such as Prefix.

The core aspect of logging from a thread pool is to make sure the thread name is easily identifiable in the log message; Log4J2 is a great way to do that by making good use of layouts for example.

Potential Risks of Using a Thread Pool

Although thread pools provide significant advantages, you can also encounter several problems while using one, such as:

using a thread pool that is too large or too small – if the thread pool contains too many threads, this can significantly affect the performance of the application; on the other hand, a thread pool that is too small may not bring the performance gain that you would expect
deadlock can happen just like in any other multi-threading situation; for example, a task may be waiting for another task to complete, with no available threads for this latter one to execute; that’s why it’s usually a good idea to avoid dependencies between tasks
queuing a very long task – to avoid blocking a thread for too long, you can specify a maximum wait time after which the task is rejected or re-added to the queue
To mitigate these risks, you have to choose the thread pool type and parameters carefully, according to the tasks that they will handle. Stress-testing your system is also well-worth it to get some real-world data of how your thread pool behaves under load.

Conclusion

Thread pools provide a significant advantage by, simply put, separating the execution of tasks from the creation and management of threads. Additionally, when used right, they can greatly improve the performance of your application.

And, the great thing about the Java ecosystem is that you have access to some of the most mature and battle-tested implementations of thread-pools out there if you learn to leverage them properly and take full advantage of them.

