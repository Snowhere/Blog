从 Date 和 Timestamp 看 Java 中的继承对 equals() 方法原则的破坏

Java 的继承是面向对象最显著的一个特性。Date 和 Timestamp 是Java中的两个和时间有关的类。Timestamp 继承自 Date。

equals() 方法是 Java 中最常用的方法之一，在 Object 中定义，任何对象都有的方法，我们在自定义类的时候，一般都会重写此方法。

首先讲一讲重写 equals() 方法时要注意的原则

1. 自反性，对于任何非控引用x，x.equals(x)都应该返回true。
2. 对称性，对于任何引用x和y，如果x.equals(y)返回true，那么y.equals(x)也应该返回true。
3. 传递性，如果x.equals(y)，y.equals(z)都返回true，那么，x.equals(z)返回true。
4. 一致性，如果x和y引用的对象没有发生变化，那么无论调用多少次x.equals(y)都返回相同的结果。
5. 对于任意非空引用x，x.equals(null)都应该返回false。　　

一般我们重写 equals() 方法会先判断类型，然后根据类中的成员域来判断。比如下面这个简化的 Date 类。

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
                } else {
                    return false;
                }
            } 
        } else {
            return false;
        }
    }
}
```

很明显，Timestamp 在 Date 的基础上增加了一个 nanos 字段。

``` java
Date date = new Date(1000);
Timestamp timestamp1 = new Timestamp(1000);
timestamp1.setNanos(1);
Timestamp timestamp2 = new Timestamp(1000);
timestamp2.setNanos(2);
System.out.println(date.equals(timestamp1));
System.out.println(date.equals(timestamp2));
System.out.println(timestamp1.equals(date));
System.out.println(timestamp1.equals(timestamp2));
```

破坏了对称性和传递性。

如何避免

用组合而不是继承