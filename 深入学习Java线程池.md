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

Java 通过 executor 对象，来实现自己的线程池模型。可以使用 executor 接口或其他线程池的实现，它们都允许细粒度的控制。

The java.util.concurrent package contains the following interfaces:

`java.util.concurrent` 包中有以下接口：

* Executor – a simple interface for executing tasks
* ExecutorService – a more complex interface which contains additional methods for managing the tasks and the executor itself
* ScheduledExecutorService – extends ExecutorService with methods for scheduling the execution of a task

* `Executor` —— 执行任务的简单接口
* `ExecutorService` —— 一个较复杂的接口，包含额外方法来管理任务和 executor 本身
* `ScheduledExecutorService` —— 扩展自 `ExecutorService`，增加了执行任务的调度方法

Alongside these interfaces, the package also provides the Executors helper class for obtaining executor instances, as well as implementations for these interfaces.

除了这些接口，这个包中也提供了 `Executors` 类直接获取实现了这些接口的 executor 实例

Generally, a Java thread pool is composed of:

一般来说，一个 Java 线程池包含以下部分：

* the pool of worker threads, responsible for managing the threads
* a thread factory that is responsible for creating new threads
* a queue of tasks waiting to be executed

* 工作线程的池子，负责管理线程
* 线程工厂，负责创建新线程
* 等待执行的任务队列

In the following sections, let’s see how the Java classes and interfaces that provide support for thread pools work in more detail.

在下面的章节，让我们仔细看一看 Java 类和接口如何为线程池提供支持。

### The Executors class and Executor interface
### `Executors` 类和 `Executor` 接口

The Executors class contains factory methods for creating different types of thread pools, while Executor is the simplest thread pool interface, with a single execute() method.

`Executors` 类包含工厂方法创建不同类型的线程池，`Executor` 是个简单的线程池接口，只有一个 `execute()` 方法。

Let’s use these two classes in conjunction with an example that creates a single-thread pool, then uses it to execute a simple statement:

我们通过一个例子来结合使用这两个类(接口)，首先创建一个单线程的线程池，然后用它执行一个简单的语句：

``` java
Executor executor = Executors.newSingleThreadExecutor();
executor.execute(() -> System.out.println("Single thread pool test"));
```

Notice how the statement can be written as a lambda expression – which is inferred to be of Runnable type.

注意语句写成了 lambda 表达式，会被自动推断成 `Runnable` 类型。

The execute() method runs the statement if a worker thread is available, or places the Runnable task in a queue to wait for a thread to become available.

如果有工作线程可用，`execute()` 方法将执行语句，否则就把 `Runnable` 任务放进队列，等待线程可用。

Basically, the executor replaces the explicit creation and management of a thread.

基本上，executor 代替了显式创建和管理线程。

The factory methods in the Executors class can create several types of thread pools:

`Executors` 类里的工厂方法可以创建很多类型的线程池：

* newSingleThreadExecutor() – a thread pool with only one thread with an unbounded queue, which only executes one task at a time
* newFixedThreadPool() – a thread pool with a fixed number of threads which share an unbounded queue; if all threads are active when a new task is submitted, they will wait in queue until a thread becomes available
* newCachedThreadPool() – a thread pool that creates new threads as they are needed
* newWorkStealingThreadPool() – a thread pool based on a “work-stealing” algorithm which will be detailed more in a later section

* `newSingleThreadExecutor()`—— 包含单个线程和无界队列的线程池，同一时间只能执行一个任务
* `newFixedThreadPool()`—— 包含固定数量线程并共享无界队列的线程池；当所有线程处于工作状态，有新任务提交时，任务在队列中等待，直到一个线程变为可用状态
* `newCachedThreadPool()`—— 只有需要时创建新线程的线程池
* `newWorkStealingThreadPool()`—— 基于工作窃取（work-stealing）算法的线程池，后面章节详细说明

Next, let’s take a look into what additional capabilities the ExecutorService interface.

接下来，让我们看一下 `ExecutorService` 接口提供了哪些新功能

### The ExecutorService
### ExecutorService

One way to create an ExecutorService is to use the factory methods from the Executors class:

创建 `ExecutorService` 方式之一便是通过 `Excutors` 类的工厂方法。

``` java
ExecutorService executor = Executors.newFixedThreadPool(10);
```

Besides the execute() method, this interface also defines a similar submit() method that can return a Future object:

除了 `execute()` 方法，接口也定义了相似的 `submit()` 方法，这个方法可以返回一个 `Future` 对象。

``` java
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
```

As you can see in the example above, the Future interface can return the result of a task for Callable objects, and can also show the status of a task execution.

从上面的例子可以看到，`Future` 接口可以返回 `Callable` 类型任务的结果，而且能显示任务的执行状态。

The ExecutorService is not automatically destroyed when there are no tasks waiting to be executed, so to shut it down explicitly, you can use the shutdown() or shutdownNow() APIs:

当没有任务等待执行时，`ExecutorService` 并不会自动销毁，所以你可以使用 `shutdown()` 或 `shutdownNow()` 来显式关闭它。

```java
executor.shutdown();
```

### The ScheduledExecutorService
### ScheduledExecutorService

This is a subinterface of ExecutorService – which adds methods for scheduling tasks:

这是 `ExecutorService` 的一个子接口，增加了调度任务的方法。

```java
ScheduledExecutorService executor = Executors.newScheduledThreadPool(10);
```

The schedule() method specifies a task to be executed, a delay value and a TimeUnit for the value:

`schedule()` 方法的参数指定执行的方法，延时和 `TimeUnit`

```java
Future<Double> future = executor.schedule(callableTask, 2, TimeUnit.MILLISECONDS);
```

Furthermore, the interface defines two additional methods:

另外，这个接口定义了其他两个方法：

```java
executor.scheduleAtFixedRate(
  () -> System.out.println("Fixed Rate Scheduled"), 2, 2000, TimeUnit.MILLISECONDS);

executor.scheduleWithFixedDelay(
  () -> System.out.println("Fixed Delay Scheduled"), 2, 2000, TimeUnit.MILLISECONDS);
```

The scheduleAtFixedRate() method executes the task after 2 ms delay, then repeats it at every 2 seconds. Similarly, the scheduleWithFixedDelay() method starts the first execution after 2 ms, then repeats the task 2 seconds after the previous execution ends.

`scheduleAtFixedRate()` 方法延时 2 毫秒执行任务，然后每 2 秒重复一次。相似的，`scheduleWithFixedDelay()` 方法延时 2 毫秒后执行第一次，然后在上一次执行完成 2 秒后再次重复执行。

In the following sections, let’s also go through two implementations of the ExecutorService interface: ThreadPoolExecutor and ForkJoinPool.

在下面的章节，我们来看一下 `ExecutorService` 接口的两个实现：`ThreadPoolExecutor` 和 `ForkJoinPool`

### The ThreadPoolExecutor
### ThreadPoolExecutor

This thread pool implementation adds the ability to configure parameters, as well as extensibility hooks. The most convenient way to create a ThreadPoolExecutor object is by using the Executors factory methods:

这个线程池的实现增加了配置参数的能力。创建 `ThreadPoolExecutor` 对象最方便的方式就是通过 `Executors` 工厂方法：

```java
ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
```

In this manner, the thread pool is preconfigured for the most common cases. The number of threads can be controlled by setting the parameters:

这种情况下，线程池按照默认值预配置了参数。线程数量由以下参数控制：

* corePoolSize and maximumPoolSize – which represent the bounds of the number of threads
* keepAliveTime – which determines the time to keep extra threads alive

* `corePoolSize` 和 `maximumPoolSize` —— 表示线程数量的边界
* `keepAliveTime` —— 决定了额外线程存活时间


Digging a bit further, here’s how these parameters are used.

深入一点，这些参数如何使用。

If a task is submitted and fewer than corePoolSize threads are in execution, then a new thread is created. The same thing happens if there are more than corePoolSize but less than maximumPoolSize threads running, and the task queue is full. If there are more than corePoolSize threads which have been idle for longer than keepAliveTime, they will be terminated.

当一个任务被提交时，如果执行中的线程数量小于 `corePoolSize`，一个新的线程被创建。如果运行的线程数量大于 `corePoolSize`，但小于 `maximumPoolSize`，并且任务队列已满时，依然会创建新的线程。如果多于 `corePoolSize` 的线程空闲时间超过 `keepAliveTime`，它们会被终止。

In the example above, the newFixedThreadPool() method creates a thread pool with corePoolSize=maximumPoolSize=10, and a keepAliveTime of 0 seconds.

上面那个例子中，`newFixedThreadPool()` 方法创建的线程池，`corePoolSize=maximumPoolSize=10` 并且 `keepAliveTime` 为 0 秒。

If you use the newCachedThreadPool() method instead, this will create a thread pool with a maximumPoolSize of Integer.MAX_VALUE and a keepAliveTime of 60 seconds:

如果你使用 `newCachedThreadPool()` 作为替代，将创建一个线程池，它的 `maximumPoolSize` 为 `Integer.MAX_VALUE`，并且 `keepAliveTime` 为 60 秒。

```java
ThreadPoolExecutor cachedPoolExecutor 
  = (ThreadPoolExecutor) Executors.newCachedThreadPool();
```

The parameters can also be set through a constructor or through setter methods:

这些参数也可以通过构造函数或`setter`方法设置：

```java
ThreadPoolExecutor executor = new ThreadPoolExecutor(
  4, 6, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>()
);
executor.setMaximumPoolSize(8);
```

A subclass of ThreadPoolExecutor is the ScheduledThreadPoolExecutor class, which implements the ScheduledExecutorService interface. You can create this type of thread pool by using the newScheduledThreadPool() factory method:

`ThreadPoolExecutor` 的一个子类便是 `ScheduledThreadPoolExecutor`，它实现了 `ScheduledExecutorService` 接口。你可以通过 `newScheduledThreadPool()` 工厂方法来创建这种类型的线程池。

```java
ScheduledThreadPoolExecutor executor 
  = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(5);
```

This creates a thread pool with a corePoolSize of 5, an unbounded maximumPoolSize and a keepAliveTime of 0 seconds.

上面语句创建了一个线程池，`corePoolSize` 为 5，`maximumPoolSize` 无限，`keepAliveTime` 为 0 秒。

### The ForkJoinPool
### ForkJoinPool

Another implementation of a thread pool is the ForkJoinPool class. This implements the ExecutorService interface and represents the central component of the fork/join framework introduced in Java 7.

另一个线程池的实现是 `ForkJoinPool` 类。它实现了 `ExecutorService` 接口，并且是 Java 7 中 fork/join 框架的重要组件。

The fork/join framework is based on a “work-stealing algorithm”. In simple terms, what this means is that threads that run out of tasks can “steal” work from other busy threads.

 fork/join 框架基于“工作窃取算法”。简而言之，意思就是执行完任务的线程可以从其他运行中的线程“窃取”工作。

A ForkJoinPool is well suited for cases when most tasks create other subtasks or when many small tasks are added to the pool from external clients.

`ForkJoinPool` 适用于任务创建子任务的情况，或者外部客户端创建大量小任务到线程池。

The workflow for using this thread pool typically looks something like this:

这种线程池的工作流程如下：

* create a ForkJoinTask subclass
* split the tasks into subtasks according to a condition
* invoke the tasks
* join the results of each task
* create an instance of the class and add it to the pool

* 创建 `ForkJoinTask` 子类
* 根据某种条件将任务切分成子任务
* 调用执行任务
* 将任务结果合并
* 实例化对象并添加到池中

To create a ForkJoinTask, you can choose one of its more commonly used subclasses, RecursiveAction or RecursiveTask – if you need to return a result.

创建一个 `ForkJoinTask`，你可以选择 `RecursiveAction` 或 `RecursiveTask` 这两个子类，后者有返回值。

Let’s implement an example of a class that extends RecursiveTask and calculates the factorial of a number by splitting it into subtasks depending on a THRESHOLD value:

我们来实现一个继承 `RecursiveTask` 的类，计算阶乘，并把任务根据阈值划分成子任务。

```java
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
```

The main method that this class needs to implement is the overridden compute() method, which joins the result of each subtask.

这个类需要实现的主要方法就是重写 `compute()` 方法，用于合并每个子任务的结果。

The actual splitting is done in the createSubtasks() method:

具体划分任务逻辑在 `createSubtasks()` 方法中：

```java
private Collection<FactorialTask> createSubtasks() {
    List<FactorialTask> dividedTasks = new ArrayList<>();
    int mid = (start + n) / 2;
    dividedTasks.add(new FactorialTask(start, mid));
    dividedTasks.add(new FactorialTask(mid + 1, n));
    return dividedTasks;
}
```

Finally, the calculate() method contains the multiplication of values in a range:

最后，`calculate()` 方法包含一定范围内的乘数。

```java
private BigInteger calculate(int start, int n) {
    return IntStream.rangeClosed(start, n)
      .mapToObj(BigInteger::valueOf)
      .reduce(BigInteger.ONE, BigInteger::multiply);
}
```

Next, tasks can be added to a thread pool:

接下来，任务可以添加到线程池：

```java
ForkJoinPool pool = ForkJoinPool.commonPool();
BigInteger result = pool.invoke(new FactorialTask(100));
```

### ThreadPoolExecutor vs. ForkJoinPool
### ThreadPoolExecutor 与 ForkJoinPool 对比

At first look, it seems that the fork/join framework brings improved performance. However, this may not always be the case depending on the type of problem you need to solve.

初看上去，似乎 fork/join 框架带来性能提升。但是这取决于你所解决问题的类型。

When choosing a thread pool, it’s important to also remember there is overhead caused by creating and managing threads and switching execution from one thread to another.

当选择线程池时，非常重要的一点是牢记创建和管理线程，以及线程间切换执行会带来的开销。

The ThreadPoolExecutor provides more control over the number of threads and the tasks that are executed by each thread. This makes it more suitable for cases when you have a smaller number of larger tasks that are executed on their own threads.

`ThreadPoolExecutor` 可以控制线程数量和每个线程执行的任务。这很适合你需要在不同的线程上执行少量巨大的任务。

By comparison, the ForkJoinPool is based on threads “stealing” tasks from other threads. Because of this, it is best used to speed up work in cases when tasks can be broken up into smaller tasks.

相比较而言，`ForkJoinPool` 基于线程从其他线程“窃取”任务。正因如此，当任务可以分割成小任务时可以提高效率。

To implement the work-stealing algorithm, the fork/join framework uses two types of queues:

为了实现工作窃取算法，fork/join 框架使用两种队列：

* a central queue for all tasks
* a task queue for each thread

* 包含所有任务的主要队列
* 每个线程的任务队列

When threads run out of tasks in their own queues, they attempt to take tasks from the other queues. To make the process more efficient, the thread queue uses a deque (double ended queue) data structure, with threads being added at one end and “stolen” from the other end.

当线程执行完自己任务队列中的任务，它们试图从其他队列获取任务。为了使这一过程更加高效，线程任务队列使用双端队列（double ended queue）数据结构，一端与线程交互，另一端用于“窃取”任务。

Here is a good visual representation of this process from The H Developer:

来自[The H Developer](http://www.h-online.com/developer/features/The-fork-join-framework-in-Java-7-1762357.html)的图很好的表现出了这一过程：

![](http://www.h-online.com/developer/imgs/80/9/5/6/0/3/4/608570fc29847565.png)

In contrast with this model, the ThreadPoolExecutor uses only one central queue.

和这种模型相比，`ThreadPoolExecutor` 只使用一个主要队列。

One last thing to remember is that the choosing a ForkJoinPool is only useful if the tasks create subtasks. Otherwise, it will function the same as a ThreadPoolExecutor, but with extra overhead.

最后要注意的一点 `ForkJoinPool` 只适用于任务可以创建子任务。否则它和 `ThreadPoolExecutor` 没区别，甚至开销更大。

## Tracing Thread Pool Execution
## 跟踪线程池的执行

Now that we have a good foundational understanding of the Java thread pool ecosystem let’s take a closer look at what happens during the execution of an application that uses a thread pool.

现在我们对 Java 线程池生态系统有了基本的了解，让我们通过一个使用了线程池的应用，来看一看执行中到底发生了什么。

By adding some logging statements in the constructor of FactorialTask and the calculate() method, you can follow the invocation sequence:

通过在 `FactorialTask` 的构造函数和 `calculate()` 方法中加入日志语句，你可以看到下面调用序列：

```
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
```

Here you can see there are several tasks created, but only 3 worker threads – so these get picked up by the available threads in the pool.

你可以看到创建了很多任务，但只有 3 个工作线程 —— 所以任务通过线程池被可用线程处理。

Also notice how the objects themselves are actually created in the main thread, before being passed to the pool for execution.

也可以看到在放到执行池之前，主线程中对象如何被创建。

This is actually a great way to explore and understand thread pools at runtime, with the help of a solid logging visualization tool such as Prefix.

通过 [Prefix](https://stackify.com/best-log-viewer-prefix/) 这一类可视化的日志工具，这确实是一个很棒的方式来探索和理解运行时的线程池。

The core aspect of logging from a thread pool is to make sure the thread name is easily identifiable in the log message; Log4J2 is a great way to do that by making good use of layouts for example.

记录线程池日志的核心便是保证在日志信息中方便辨识线程名字。[Log4J2](https://stackify.com/log4j2-java/) 通过使用布局能够很好完成这种工作。

## Potential Risks of Using a Thread Pool
## 使用线程池的潜在风险

Although thread pools provide significant advantages, you can also encounter several problems while using one, such as:

尽管线程池有巨大优势，你在使用中仍会遇到一些问题，比如：

* using a thread pool that is too large or too small – if the thread pool contains too many threads, this can significantly affect the performance of the application; on the other hand, a thread pool that is too small may not bring the performance gain that you would expect
* deadlock can happen just like in any other multi-threading situation; for example, a task may be waiting for another task to complete, with no available threads for this latter one to execute; that’s why it’s usually a good idea to avoid dependencies between tasks
* queuing a very long task – to avoid blocking a thread for too long, you can specify a maximum wait time after which the task is rejected or re-added to the queue

* 用的线程池过大或过小 —— 如果线程池包含太多线程，会明显的影响应用的性能；另一方面，线程池太小并不能带来所期待的性能提升。
* 正如其他多线程情形一样，死锁也会发生。举个例子，一个任务可能等待另一个任务完成，而后者并没有可用线程处理执行。所以说避免任务之间的依赖是个好习惯。
* 等待执行时间很长的任务 —— 为了避免长时间的任务阻塞线程，你可以指定最大等待时间，并决定过期任务是拒绝处理还是重新加入队列。

To mitigate these risks, you have to choose the thread pool type and parameters carefully, according to the tasks that they will handle. Stress-testing your system is also well-worth it to get some real-world data of how your thread pool behaves under load.

## Conclusion
## 结论

Thread pools provide a significant advantage by, simply put, separating the execution of tasks from the creation and management of threads. Additionally, when used right, they can greatly improve the performance of your application.

And, the great thing about the Java ecosystem is that you have access to some of the most mature and battle-tested implementations of thread-pools out there if you learn to leverage them properly and take full advantage of them.

