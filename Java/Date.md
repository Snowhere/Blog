# Java7 及之前版本中 Date 类设计缺陷
Java 程序猿普遍对 Date 这个类感触深刻，诟病已久，因为它存在太多问题，用起来十分不方便。下面说说主要问题。

## 多个 Date
在 java.util 和 java.sql 的包中都有一个名为 Date 的日期类。但 java.util.Date 同时包含日期和时间，而 java.sql.Date 仅包含日期，java.sql.Time 包含时间。按照英文语义来说，Date 指日期，Time 指时间，而且还有重名类，因此 java.util.Date 命名定义就有问题。

## 常用操作
作为开发者，尤其是初学者，一定会对 java.util.Date 类的格式化印象深刻，因为感觉冗余和繁琐，还需要引入其他类。用于格式化和解析的类在 java.text 包中定义，对于格式化和解析的需求，我们有 java.text.DateFormat 抽象类，一般写代码时，我们习惯用 SimpleDateFormat。
```
Date date = new Date();
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
String dateString = dateFormat.format(date);
```
另外 java.util.Date 没有提供直接对日期时间的加减操作方法，在修改时间十分不方便。

## 局限性
java.util.Date 类并不提供国际化，没有时区支持，开发相关业务时还要引入 java.util.Calendar 和 java.util.TimeZone 类，业务开发时增加了代码复杂度。

## 可变
`java.util.Date` 类最大的缺陷就是可变性。可变性会在开发中带来很多问题，比如在多线程环境中不可靠，再比如下面的代码执行不符合预期。
```
Date d = new Date();
Scheduler.scheduleTask(task1, d);
d.setTime(d.getTime() + ONE_DAY);
Scheduler.scheduleTask(task2, d);
```
task1 和 task2 都会在一天后执行

## 解决方案
在 Java7 及之前的版本中用第三方时间库，比如 Jode-Time，相信大多数程序猿对这个库十分熟悉。
Java8 引入了新的时间库，`java.time` 包，其中有关日期时间的类解决了上面这些问题，十分好用。
