原文[We Don't Need StringBuilder for Concatenation Anymore](https://dzone.com/articles/string-concatenation-performacne-improvement-in-ja)
(译文发布在[码农网](http://www.codeceo.com/article/why-java8-not-use-stringbuilder.html))

#我们不再需要StringBuilder拼接字符串
在Java开发者中,字符串的拼接占用资源高往往是热议的话题.
让我们深入讨论一下为什么会占用高资源.
在Java中,字符串对象是不可变的,意思是它一旦创建,你就无法再改变它.所以在我们拼接字符串的时候,创建了一个新的字符串,旧的被垃圾回收器所标记.
![](https://dzone.com/storage/temp/3747916-string-immutable.png)
如果我们处理上百万的字符串.然后,我们就会生成百万的额外字符串被垃圾回收器处理.
虚拟机底层在拼接字符串时执行了众多操作.拼接字符串最直接的点操作(dot operator)就是String#concat(String)操作.
``` java
public String concat(String str) {
    int otherLen = str.length();
    if (otherLen == 0) {
        return this;
    }
    int len = value.length;
    char buf[] = Arrays.copyOf(value, len + otherLen);
    str.getChars(buf, len);
    return new String(buf, true);
}
```
``` java
public static char[] copyOf(char[] original, int newLength) {
    char[] copy = new char[newLength];
    System.arraycopy(original, 0, copy, 0,
                     Math.min(original.length, newLength));
    return copy;
}
```
``` java
void getChars(char dst[], int dstBegin) {
    System.arraycopy(value, 0, dst, dstBegin, value.length);
}
```
你可以看到一个字符数组被创建,长度则是已有字符和拼接的字符长度之和.然后,它们的值复制到新的字符数组中.最后,用这个字符数组创建一个String对象并返回.
所以这些操作繁多,如果你计算一下,会发现是O(n^2)的复杂度.
为了解决这个问题,我们使用StringBuilder类.它就像可变的String类.拼接方法帮助我们避免不必要的复制.它拥有O(n)的复杂度,远远优于O(n^2).

然而Java 8默认使用StringBuilder拼接字符串.
Java 8的[文档说明](https://docs.oracle.com/javase/specs/jls/se8/html/jls-15.html#jls-15.18.1):
>为了提高字字符串拼接的性能,Java编译器可以使用StringBuffer类或类似技术,在使用求值表达式时,减少中间String对象的创建.

Java编译器处理这种情况:
``` java
public class StringConcatenateDemo {
  public static void main(String[] args) {
     String str = "Hello ";
     str += "world";
   }
}
```
上面的代码会被编译成如下字节码:
``` 
public class StringConcatenateDemo {
  public StringConcatenateDemo();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return
  public static void main(java.lang.String[]);
    Code:
       0: ldc           #2                  // String Hello
       2: astore_1
       3: new           #3                  // class java/lang/StringBuilder
       6: dup
       7: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
      10: aload_1
      11: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      14: ldc           #6                  // String world
      16: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      19: invokevirtual #7                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
      22: astore_1
      23: return
}
```
你可以在这些字节码中看到,使用了StringBuilder.所以我们在Java 8中不再需要使用StringBuilder类.