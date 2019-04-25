# 聊聊MQ

## 什么是 MQ
MQ，Message Queue，消息队列。从名字就能直观地看出它的用途和意义。
消息是数据的抽象载体，无论是 json 还是 xml 还是什么格式的内容，都可以成为消息。
队列是我们非常熟悉的一种数据结构，有序排列，一端进一端出，先进先出。

我们来试着用一段 Java 代码实现一个简单的消息队列

```
public class Queue<T> {

    @Data
    @AllArgsConstructor
    class Message<T>{
        Message<T> next;
        T data;
    }

    Message<T> first;

    public T get() {
        if (first == null) {
            return null;
        } else {
            T data = first.getData();
            first = first.getNext();
            return data;
        }
    }

    public void put(T t) {
        if (first == null) {
            first = new Message<>(null, t);
        }else {
            Message<T> node = first;
            while (node.getNext() != null) {
                node=node.getNext();
            }
            node.setNext(new Message<>(null, t));
        }
    }

    public static void main(String[] args) {
        Queue<String> queue = new Queue<>();
        queue.put("1");
        queue.put("2");
        System.out.println(queue.get());
        System.out.println(queue.get());
        System.out.println(queue.get());
    }
}
```

用泛型作为信息的载体
用单链表实现简单队列
提供进队和出队操作

别看上面代码简洁，但它是一个实实在在的消息队列，满足消息队列的定义。但这只是一段本地代码，不能当做独立的服务部署；它只提供了最简单的进队出队操作，不能提供其他更加实用的操作。我们之后会了解到其他主流的消息队列，可以对比上面这个简单的队列，看看在哪些层面做了哪些扩展。

## 有哪些 Message Queue
市面上的消息队列种类很多，主流的大概这三种：
RabbitMQ
RocketMQ
Kafka
这次也主要讲讲这三个。

### RabbitMQ
首先是 RabbitMQ。RabbitMQ 是实现了高级消息队列协议（AMQP）的消息队列。AMQP，即 Advanced Message Queuing Protocol，一个提供统一消息服务的应用层标准高级消息队列协议，是应用层协议的一个开放标准，为面向消息的中间件设计。也就是说 AMQP 是一种约定和规范，并不是一种技术。就像 Java 中定义的接口一样，比如定义了很多有关数据库的接口，各数据库厂商对照自己的数据库自行实现接口方法。RabbitMQ 则是开发者用 Erlang 这门语言实现 AMQP 协议的产物。所以说 RabbitMQ 的起源和目的很明显，就是做一个标准的消息队列。

Broker
Virtual host
Exchange
Queue
Binding

Direct
Fanout
Topic

http://tryrabbitmq.com/

延迟队列

### kafka
Kafka是一种高吞吐量的分布式发布订阅消息系统。
它最初由 LinkedIn 公司开发，之后成为 Apache 项目的一部分。Kafka 是一个分布式的，可划分的，冗余备份的持久性的日志服务。它主要用于处理活跃的流式数据。


## 为什么要用 Message Queue
解耦，扩展、灵活
异步，
削峰


高可用-集群
消费幂等