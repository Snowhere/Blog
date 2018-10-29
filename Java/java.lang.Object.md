混沌开天地.万物始于Object
来看这条注释@since   JDK1.0
从Java这门语言诞生之初，Object类如同基石般在那里。
public class Object
12个方法
1个private,2个protected,9个public
7个native
6个final
下面我大概说一下这些方法，其中private和native以及final修饰的方法我就不说太多，这些关键字修饰的方法都是稳固的方法
```
public class Object
//这个就不说了
private static native void registerNatives();
static {
    registerNatives();
}
```
```
//子类不能重写的获取类型的方法，运行期得到对象类型信息
public final native Class<?> getClass();
```
```
//常用，默认返回和对象内存地址有关内容。建议重写，比如Interger类就返回value值;
public native int hashCode();
```
```
//常用，默认比较对象是否指向同一位置。建议重写，比如String类比较字符是否相同
public boolean equals(Object obj) {
    return (this == obj);
}
```
```
/*这个克隆方法是个大坑，首先The method clone for class Object performs a specific cloning operation. First, if the class of this object does not implement the interface Cloneable, then a CloneNotSupportedException is thrown. 其次对于数组this method performs a "shallow copy" of this object, not a "deep copy" operation. */
protected native Object clone() throws CloneNotSupportedException;
```
```
//常用，默认返回Class名+@+十六进制hashCode。建议重写
public String toString() {
    return getClass().getName() + "@" + Integer.toHexString(hashCode());
}
```
```
//下面5个方法涉及线程等待和唤醒      
public final native void notify();

public final native void notifyAll();

public final native void wait(long timeout) throws InterruptedException;

public final void wait(long timeout, int nanos) throws InterruptedException {
    if (timeout < 0) {
        throw new IllegalArgumentException("timeout value is negative");
    }
    if (nanos < 0 || nanos > 999999) {
        throw new IllegalArgumentException("nanosecond timeout value out of range");
    }
    if (nanos >= 500000 || (nanos != 0 && timeout == 0)) {
        timeout++;
    }
    wait(timeout);
}

public final void wait() throws InterruptedException {
    wait(0);
}
```
```
//垃圾回收时调用的方法，一般不需要关注。
protected void finalize() throws Throwable { }
```