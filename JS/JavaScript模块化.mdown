# JavaScript模块化
JavaScript进阶知识的碎片拼接起来
最近看有关JavaScript方面的知识，了解了很多相关的概念，比如字面量，闭包，this和var声明变量，作用域，立即执行函数，动态绑定等等。
但这些就像零星的碎片一样在我的脑海中。直到看到JavaScript模式，有关JavaScript模块化概念，把这些知识全部拼接了起来。
先说说字面量创建对象，so easy.
var o = {};

对象中可以有属性和函数
o.num=3;
o.getNum=function(){return this.num};

但是JavaScript中没有私有概念，为了创建私有作用域，我们需要闭包。
``` JavaScript
function obj(){
    var num=3;
    return {
        getNum:function(){return this.num};
    }
}
```
var o = obj();

我们可以用立即执行函数来简化这个过程
(function(){
    var num=3;
    return {
        getNum:function(){return this.num};
    } 
})();