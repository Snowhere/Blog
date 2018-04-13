深入理解 Java 虚拟机

第2章 Java内存区域与内存溢出异常
* 程序计数器（Program Counter Register）是一块较小的内存空间，它可以看作是当前线程做执行的字节码的行号指示器。
* Java虚拟机栈（Java Virtual Machine Stacks）描述的是Java执行的内存模型：每个方法在执行的同时都会创建一个栈帧（Stack Frame）用于存储局部变量表、操作数栈、动态链接、方法出口等信息。局部变量表存放编译器可知的各种数据类型（boolean,byte,char,short,int,long,double,float）、对象引用（reference）和returnAddress类型。两种异常情况：线程请求的栈深度大于虚拟机所允许的深度——StackOverflowError，扩展无法申请到足够内存——OutOfMemoryError。
* 本地方法栈（Native Method Stack）与虚拟机栈相似，只是执行Native方法。
* Java堆（Java Heap）是内存最大的一块。线程共享，存放对象实例。（-Xmx和-Xms控制）。
* 方法区（Method Area）与堆一样，线程共享的内存区域，储存已被虚拟机加载的类信息、常量、静态变量、即时编译器编译后的代码等数据。运行时常量池（Runtime Constant Pool）是方法区的一部分

第3章 垃圾收集器与内存分配策略
Java与C++之间有一堵由内存动态分配和垃圾收集技术所围成的“高墙”，墙外面的人想进去，墙里面的人却想出来。
哪些内存需要回收？什么时候回收？如何回收？
在JDK1.2之后，引用分为强引用（Strong Reference）、软引用（Soft Reference）、弱引用（Weak Reference）、虚引用（Phantom Reference） 
任何一个对象的finalize()方法都只会被系统自动调用一次。

第4章 虚拟机性能监控与故障处理工具
给一个系统定位问题的时候，知识、经验是关键基础，数据是依据，工具是运用知识处理数据的手段。

第5章 调优案例分析与实战

第6章 类文件结构
这章太复杂了，消化不良。

第7章 虚拟机类加载机制
代码编译的结果从本地机器码转变为字节码，是存储格式发展的一小步，却是编程语言发展的一大步。
虚拟机把描述类的数据从Class文件加载到内存，并对数据进行校验、转换解析和初始化，最终形成可以被虚拟机直接使用的Java类型。
类型的加载、链接和初始化过程都是在程序运行期间完成的。
生命周期：加载（Loading）、验证（Verification）、准备（Preparation）、解析（Resolution）、初始化（Initialization）、使用（Using）、卸载（Unloading）。连接（Linking）包括验证、准备、解析阶段
3种系统提供的类加载器：启动类加载器（Bootstrap ClassLoader）、扩展类加载器（Extension ClassLoader）、应用程序类加载器（Application ClassLoader）。加载器之间的父子关系一般以组合（Composition）关系实现。
双亲委派模型：类加载器收到类加载请求，委托给父类加载器，父类加载器无法完成，子类尝试加载。
破坏双亲委派模型：向前兼容、JNDI、OSGi

第8章 虚拟机字节码执行引擎
栈帧（Stack Frame）是虚拟机运行时数据区中的虚拟机栈（Virtual Machine Stack）的栈元素。包括局部变量表（Local Variable Table）、操作栈（Operand Stack）、动态链接（Dynamic Linking）、返回地址（Return Address）和一些额外附加信息。

第9章 类加载及执行子系统的案例与实战
学习JEE规范，看JBoss源码；学习类加载器，看OSGi源码。

第10章 早期优化

第11章 晚期优化

第12章 Java内存模型与线程
计算机的运算速度与它的存储和通信子系统速度的差距太大，大量的时间都花费在磁盘I/O、网络通信或者数据库访问上。
volatile保证可见性和禁止指令重排序优化。
时间先后顺序与先行发生原则之间基本没有太大关系。
线程的2种调度方式：协同式线程调度（Cooperative Threads-Scheduling）和抢占式线程调度（Preemptive Threads-Scheduling）
Java定义5种线程状态：新建（New）、运行（Runable）、无期限等待（Waiting）、限期等待（Timed Waiting）、阻塞（Blocked）、结束（Terminated）

第13章 线程安全与锁优化
当多个线程访问一个对象时，如果不用考虑这些线程在运行时环境下的调度和交替执行，也不需要进行额外的同步，或者在调用方法进行任何其他的协调操作，调用这个对象的行为都可以获得正确的结果，那这个对象是线程安全的。
操作共享数据分为5类：不可变、绝对线程安全、相对线程安全、线程兼容和线程对立。
线程安全的实现方法：互斥同步（Mutual Exclusion & Synchronization）、非阻塞同步（Non-Blocking Synchronization）、无同步方案：可重入代码（Reentrant Code）和线程本地存储（Thread Local Storage）