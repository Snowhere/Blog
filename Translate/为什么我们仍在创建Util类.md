原文[Why Do We Still Create Util Classes?](https://dzone.com/articles/why-do-we-still-create-util-classes)

#为什么我们仍在创建Util类
这个问题困扰了我一段时间,甚至几年,当我们有很棒的开源的项目包含所需的类和方法时,为什么我们坚持创建自己的util类,比如 StringUtils,DateUtils,或 CollectionUtils?事实上,最近我又愚蠢的创建了CSVUtil类,而不是使用[Apache Commons CSV](https://commons.apache.org/proper/commons-csv/) 或 [OpenCSV](http://opencsv.sourceforge.net/).

最常见的方法是 StringUtil.isNullOrEmpty . 就像下面这样:
``` java
public static boolean isNullOrEmpty(String myString) {
    if (myString == null || "".equals(myString)) {
        return true;    
    } else {
        return false;    
    } 
}
```

这并不是糟糕的代码,它让你保证DRY(Don't Repeat Yourself)原则,不需要重复的判断null或空白的字符串.

但这里有一些问题:
* 重复发明轮子,因为已经有大量优秀的库提供众多的util.
* 你需要维护所写的代码,并添加测试用例.
* 影响代码覆盖率 - 如果你不添加单元测试,会降低你的代码覆盖率.
* 你可能会有更多的bug.

我们使用这样的代码原因之一是软件遗留问题,比如,在Java8之前我们判断null是因为我们没有Optional类.

#我们用什么代替?
* [Apache Commons](http://commons.apache.org/):经验之谈,最通用的util组件,而且应该最适合替换你自己写的uitl.
* [Google Guava](https://github.com/google/guava):比Apache Commons更年轻,尽管现在也相当成熟.实现方式略有不同,并允许你使用链式方法.这或许更适合你的代码风格.

#选择
选择库保持一致性很重要 - 所以不要在一部分代码中用Apache Commons,另一部分中用Guava实现.一致性使得未来的维护更加容易.

#警告
正如所有伟大的规则一样,我们有时候要打破规则.我会在下面的情况创建Util类:
* 特殊的实现:包中没有我需要的方法,但我试着封装已有的util作为基础.
* Java8之前:我或许会创建一个util作为facade,比如Date或因为我没有Optional类.
* 拖延症:最后我创建util类的原因就是拖延我的主要任务!编写util类让我至少感到我并不是无所事事.

所以我的2017年决心是停止创建这样的类,改为使用久经考验的util包,并且替换掉每一个我所看到的自己写的util类.
