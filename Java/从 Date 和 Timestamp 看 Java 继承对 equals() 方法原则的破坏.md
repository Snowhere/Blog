# 从 Date 和 Timestamp 看 Java 继承特性和 equals() 方法约定间的冲突

Java 的继承是面向对象最显著的一个特性。Date 和 Timestamp 是 Java 中的两个和时间有关的类。Timestamp 继承自 Date。

equals() 方法是 Java 中最常用的方法之一，在 Object 中定义，任何对象都有的方法，我们在自定义类的时候，一般都会重写此方法。

## equals() 方法的约定

首先讲一讲重写 equals() 方法时要注意的约定

1. 自反性(reflexive)，对于任何非 null 的引用值 x，x.equals(x)必须返回true。
2. 对称性(symmetric)，对于任何非 null 的引用值 x 和 y，当且仅当y.equals(x)返回true时，x.equals(y)必须返回true。
3. 传递性(transitive)，对于任何非 null 的引用值 x、y 和 z,如果 x.equals(y) 返回 true，并且 y.equals(z) 也返回 true，那么 x.equals(z) 也必须返回 true。
4. 一致性(consistent)，对于任何非 null 的引用值 x 和 y，只要 equals 的比较操作在对象中所有的信息没有被修改，多次调用 x.equals(y) 就会一致地返回 true，或者一致地返回 false。
5. 对于任意非 null 的引用值 x，x.equals(null) 必须返回false。　　

这几条简单易懂。一般我们重写 equals() 方法会先判断类型是否一致，然后根据类中的成员域来判断。比如下面这个简化的 Date 类。

``` java
public class Date{
    private transient long fastTime;
    public Date(long date) {
        fastTime = date;
    }
    public long getTime() {
        return fastTime;
    }
    public boolean equals(Object obj) {
        return obj instanceof Date && getTime() == ((Date) obj).getTime();
    }
}
```

equals 方法首先判断是否是 Date 类型，然后判断 fastTime 字段值是否一致。

## 继承

我们再来看一下简化的 Timestamp 类

``` java
public class Timestamp extends Date {
    private int nanos;
    public Timestamp(long time) {
        super((time/1000)*1000);
        nanos = (int)((time%1000) * 1000000);
    }
    public boolean equals(Object ts) {
        if (ts instanceof Timestamp) {
            if (super.equals(ts)) {
                if  (nanos == ts.nanos) {
                    return true;
                } 
            } 
        } 
        return false;
    }
}
```

Timestamp 在 Date 的基础上增加了一个 nanos 字段。所以它的 equals 方法首先判断是否是 Timestamp 类型，然后调用父类 equals 方法判断 fastTime 字段，最后判断 nanos 字段。

我们看下面一段代码。

## 继承破坏约定

``` java
Date date = new Date(1000);
Timestamp timestamp1 = new Timestamp(1000);
timestamp1.setNanos(1);
Timestamp timestamp2 = new Timestamp(1000);
timestamp2.setNanos(2);
System.out.println(date.equals(timestamp1));//true
System.out.println(date.equals(timestamp2));//true
System.out.println(timestamp1.equals(date));//false
System.out.println(timestamp1.equals(timestamp2));//false
```

很明显，正是因为继承关系，导致 equals 方法的对称性和传递性遭到破坏。事实上，Java 源码中 Timestamp 的注释里已经提到了这个问题。

>Note: This type is a composite of a java.util.Date and a separate nanoseconds value. Only integral seconds are stored in the java.util.Date component. The fractional seconds - the nanos - are separate. The Timestamp.equals(Object) method never returns true when passed an object that isn't an instance of java.sql.Timestamp, because the nanos component of a date is unknown. As a result, the Timestamp.equals(Object) method is not symmetric with respect to the java.util.Date.equals(Object) method. Also, the hashCode method uses the underlying java.util.Date implementation and therefore does not include nanos in its computation.
Due to the differences between the Timestamp class and the java.util.Date class mentioned above, it is recommended that code not view Timestamp values generically as an instance of java.util.Date. The inheritance relationship between Timestamp and java.util.Date really denotes implementation inheritance, and not type inheritance.

重点在最后一句。不建议把 Timestamp 实例当做 Date 的实例。它们这种继承关系只是实现层面上的继承，并非类型层面上的继承。

当我们感觉到问题的时候，就应该开始思考如何解决了。

## getClass()

首先想到的就是 instanceof 来判断类型时无法识别继承关系，而用 getClass() 方法可以准确获知类型。
我们用这种思路实现一个 equals() 方法。

``` java
public boolean equals(Object obj) {
     if(obj == null || obj.getClass() != this.getClass()){
        return false;
     }
     //other conditions
}
```

这个 equals 能够很刻薄地判断两个对象是否相等，并能够严格遵守 equals 的各项约定。但是它却违背了面向对象思想的一项很重要的原则，里氏替换原则。

>里氏替换原则(Liskov Substitution Principle LSP)面向对象设计的基本原则之一。 里氏替换原则中说，任何基类可以出现的地方，子类一定可以出现。 LSP是继承复用的基石，只有当衍生类可以替换掉基类，软件单位的功能不受到影响时，基类才能真正被复用，而衍生类也能够在基类的基础上增加新的行为。

这种 equals 方法显然使得子类替换父类受影响，这种行为和继承的思想理念相违背。

## 用组合而不是继承

还有另一种方法，便是放弃继承。
再设计模式中，常常被人提及的就是“组合优先于继承”
上面例子中我们可以把 Date 当做 Timestamp 的一个域，就解决了 equals 的问题。

``` java
public class Timestamp{
    private int nanos;
    private Date date;
    public Timestamp(long time) {
        date = new Date(time);
        nanos = (int)((time%1000) * 1000000);
    }
    public boolean equals(Object ts) {
        if (ts instanceof Timestamp) {
            if  (nanos == ((Timestamp)ts).nanos && date.equals(((Timestamp)ts).date)) {
                return true;
            } 
        } else {
            return false;
        }
    }
}
```

## 总结

我们并没有完美的解决方案调和 Java 的继承特性和 equals 方法约定，它们之间的冲突依然还在。

>参考《Effective Java》