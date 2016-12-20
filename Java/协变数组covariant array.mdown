协变数组covariant array

来看两行Java代码

```
Object[] list = new Integer[10];
list[0] = "A"; 
```

这两行代码编译不会报错，运行会抛出一个错误java.lang.ArrayStoreException
我们从头来了解这里面的奥妙。
许多语言支持子类，Java也不例外。
举个例子，Cat是Animal的子类，那么Animal声明可以引用Cat对象，比如表达式中用Cat对象Animal，返回值中用Cat替换Animal。

```
public Animal get(){
    Animal a = new Cat();
    Cat b = new Cat();
    return b;
}
```

上面这些代码我们都不陌生，这是子类对象和父类对象之间is-a的关系,Cat is an Animal。
但是Cat[]与Animal[]之间的关系是怎样的呢。于是编程语言的设计者要考虑和决定由此衍生的问题，比如数组、继承、泛型等等。
一般出现4种定义
||||
|----|---|---|
|协变| covariant     |   a Cat[] is an Animal[]
|逆变| contravariant  |  an Animal[] is a Cat[]
|双变| bivariant     |   an Animal[] is a Cat[] and a Cat[] is an Animal[]
|不变| invariant     |   an Animal[] is not a Cat[] and a Cat[] is not an Animal[]

拿"协变 covariant"举个例子，如果Cat和Dog都是Animal子类，Cat[]可以作为Animal[]对待，我们可以把Dog放到Animal[]里，但我们知道不能把Dog放到Cat[]里，所以存放有问题。
再说说"逆变 contravariant "，如果Cat和Dog都是Animal子类，Animal[]可以作为Cat[]对待，我们从Cat[]中期待只能取到Cat，但Animal[]可能存有Dog被我们取到。

由此可见，如果我们为了保证对数组的读和写都不会有类型错误，最安全的做法是选择"不变 invariant"
即规定这样的声明是错误的 Animal[] list = new Cat[];
但是Java中并没有这样做，而是选择了"协变 covariant "。为什么呢。
这是有历史原因，Java 1.2版本中才引入了泛型，而最初为了让ArrayList等一系列类可以正常工作，只能这样选择。
ArrayList内部保存一个Object[]，封装了一系列的对数组的操作。Object是所有类型的父类，如果不是协变的，我们无法存取它的子类，比如我们创建一个ArrayList却不能存放String类型，这将是多大的不便。
当然为了正确处理异常，定义了java.lang.ArrayStoreException

不过，协变引起的问题不仅仅是这一点。
如果你看过ArrayList<E>的源码，会在其中一个构造方法中看到这样一行注释

```
public ArrayList(Collection<? extends E> c) {
        elementData = c.toArray();
        size = elementData.length;
        // c.toArray might (incorrectly) not return Object[] (see 6260652)
        if (elementData.getClass() != Object[].class)
                elementData = Arrays.copyOf(elementData, size, Object[].class);
}
```

仔细想想就能明白其中原因。我就不多说了。
在官网上根据编号查看这个bug的详细信息时，有个很有意思的回复，[bug是2005-04-25提交的]
[http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6260652](http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6260652)


>10 years later, I still believe this is "just" a bug that should be fixed.
>
>2015-06-26



参考[https://en.wikipedia.org/wiki/Covariance_and_contravariance_(computer_science)](https://en.wikipedia.org/wiki/Covariance_and_contravariance_(computer_science))