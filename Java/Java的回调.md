Java的回调

一般意义上的回调是这样的:
回调函数就是一个通过函数指针调用的函数。如果你把函数的指针（地址）作为参数传递给另一个函数，当这个指针被用来调用其所指向的函数时，我们就说这是回调函数。
比如JavaScript里可以把函数当做参数进行传递。
```
function a(callback){
    //Do something else
    callback();
}
function b(){
    alert('回调');
}
a(b);
```
函数b显示hello字符串。而调用a函数时将b函数传递进去，由a函数自己在内部合适的时候调用b函数。这就是回调。

但是Java中不能将方法作为参数传递给其他方法(jdk1.7及之前)。在Java中对回调这个概念有自己的实现方式。
就是A类中调用B类中的某个方法do()，而方法do()中反过来调用A类中的方法callBack()，callBack()这个方法就叫回调方法。效果就是最终调用A的方法callBack(),但经历了B类中一些其他流程。回调的前提就是A对象中有一个B的实例
```
class B{
    public void do(A a){
        //Do something else
        a.callBack();
    }
}
class A{
    private B b = new B();
    public void callBack(){
        System.out.print("回调");
    }
    public void execute(){
        b.do(this);
    }
}

A a = new A();
a.execute("hello");
```
省略大部分代码。如果使用线程，就是异步回调。

这种模型在Java中最简单的体现就是观察者设计模式。在java.util包中有具体的实现，Observable类和Observer接口。