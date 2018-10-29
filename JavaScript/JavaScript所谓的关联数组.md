JavaScript所谓的"关联数组"

>“关联数组”是一种具有特殊索引方式的数组。不仅可以通过整数来索引它,还可以使用字符串或者其他类型的值（除了NULL）来索引它。
————百度百科

大家肯定熟悉这样一种数据结构:Key-Value键值对的集合。在Java语言中用Map来实现,十分方便。
而在JavaScript中只有Array这种内置对象供开发者使用。
```
var a = new Array();
a[0]=0;
a[1]=1;
a.push(2);

alert(a[2]);//2
```
或者这样
```
var a = [0,1,2];
alert(a[2]);//2
```

等等,索引为数字。所以我在网上查找Array是否支持关联数组,也就是是否可以用数字以外的对象作为Key。网上有博客写可以支持关联数组。
比如
```
var a = new Array();
a["one"]=1;
a["two"]=2;

alert(a["two"])//2
```
也可以用对象
```
var b = new Object();
a[b] = 2;

alert(a[b]);//2
```
甚至给出了for each循环
```
var a = new Array();
var b = new Object();
a[b] = 2;
for(var key in a){
    alert(a[key]);//2
}
```
看到这如果你相信了"关联数组",那恭喜你掉进了一个大坑。
首先JavaScript内置对象Array是不支持数字以外的对象作为key的。如果你不信
```
var a = new Array();
a[2]=2;//指定第三位,数组的长度就是3
alert(a.length);//3

var a = new Array();
a["two"] = 2;
alert(a.length);//0

var b = new Object();
a[b] = 2;
alert(a.length);//0
```
这说明什么问题,我们储存的数据根本不在数组的结构中。那我们既然之前能取到值,这些数据存放在哪里了呢。
其实这些值作为对象的属性保存起来了。我们甚至不需要Array这个内置对象,只需要随便一个对象就OK了
```
var a = new Object();
a["two"] = 2;

alert(a["two"]);//2
```
这个等价于
```
var a = {"two":2};

alert(a["two"]);//2
```
那么我们在使用这种方式实现"关联数组"的过程中会遇到哪些问题
首先不要当这是数组,所以内置对象Array所提供的方法不可用
```
a.length==0 //true
```
既然这是保存属性,数据结构应该是指针结构,那么Key应该是String类型的,我们来验证一下。
```
var a = new Array();
var b = new Object();

a[b] = 2;
for(var key in a){
    alert(typeof key);//string
}
```
可以看到,虽然我们储存时使用Object,但遍历时,Key就是string类型。
进一步猜想,在保存属性的时候,自动把作为Key的对象转换成string类型。我们继续验证,这里重写toString方法。
```
var a = new Array();
var b = new Object();
b.toString = function(){
    return "IamKey";
}
a[b] = 2;
for(var key in a){
    alert(key);//"IamKey"
}
```

这导致一个严重的后果就是我们使用自定义对象作为key时,因为没有重写toString方法,使得Key值相同,后来的数据覆盖前数据。

最后我们来反思一下为什么JavaScript会让人误以为有关联数组。罪魁祸首是访问属性的方式。
我们都知道在JavaScript数据结构中对象的属性访问一般用这种方式————对象.属性名
```
var a = {"one":1,"two":2};
alert(a.one);//1
a.one = 11;
a.one = 11;
alert(a.one);//11
```
JavaScript同时提供另一种来访问对象的属性————对象["属性名"]

即a["one"]和a.one等价

这样有什么方便的地方呢,比如我们不知道属性名,或者将属性名存放在某个变量中,属性名可以作为参数传入。比如
```
var arg = "one";
alert(a[arg])//等价于a["one"],等价与a.one
```
综上,JavaScript并没有关联数组。