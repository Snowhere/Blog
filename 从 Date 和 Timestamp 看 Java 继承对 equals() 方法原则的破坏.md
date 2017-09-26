# 从 Date 和 Timestamp 看 Java 中的继承对 equals() 方法约定的破坏

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
            if (super.equals((Timestamp)ts)) {
                if  (nanos == ((Timestamp)ts).nanos) {
                    return true;
                } 
            } 
        } else {
            return false;
        }
    }
}
```

Timestamp 在 Date 的基础上增加了一个 nanos 字段。

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

破坏了对称性和传递性。

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

>里氏替换原则(Liskov Substitution Principle LSP)面向对象设计的基本原则之一。 里氏替换原则中说，任何基类可以出现的地方，子类一定可以出现。 LSP是继承复用的基石，只有当衍生类可以替换掉基类，软件单位的功能不受到影响时，基类才能真正被复用，而衍生类也能够在基类的基础上增加新的行为。

## 用组合而不是继承

