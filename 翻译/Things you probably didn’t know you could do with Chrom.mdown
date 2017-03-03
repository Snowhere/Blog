原文[Things you probably didn’t know you could do with Chrome’s Developer Console](https://medium.freecodecamp.com/10-tips-to-maximize-your-javascript-debugging-experience-b69a75859329#.pokq5sefq)
(译文发布在[伯乐在线](http://web.jobbole.com/90300/)，感谢[艾凌风](http://www.jobbole.com/members/hanxiaomax)校稿)

#Things you probably didn’t know you could do with Chrome’s Developer Console
#Chrome 开发者控制台中，你可能意想不到的功能

![](https://cdn-images-1.medium.com/max/2000/1*WWyilxdduXEkWudAJaqKsA.jpeg)

Chrome comes with built-in developer tools. This comes with a wide variety of features, such as Elements, Network, and Security. Today, we’ll focus 100% on its JavaScript console.

Chrome 有内置的开发者工具。它拥有丰富的特性，比如元素（Elements）、网络（Network）和安全（Security）。今天，我们主要关注一下 JavaScript 控制台。

When I started coding, I only used the JavaScript console for logging values like responses from the server, or the value of variables. But over time, and with the help of tutorials, I discovered that the console can do way more than I ever imagined.

当我最初写代码时，我只会使用JavaScript控制台来打印服务器返回值或变量值。但随着时间推移和教程的帮助，我发现这个控制台能做的事远远超乎我的想象。

Here are useful things you can do with it. If you’re reading this in Chrome (Or any other Browser) on a desktop, you can even open up its Developer Tools and try these out immediately.

接下来我们说说你可以通过控制台做到的事情。如果你在电脑的 Chrome 浏览器（或其他浏览器）中阅读这篇文章，你可以立刻打开开发者工具并尝试。

##1. Select DOM Elements

##1. 选取DOM元素

If you’re familiar with jQuery, you know how important the $(‘.class’) and $(‘#id’) selectors are. They select the DOM elements depending upon the class or ID associated with them.

如果你对 jQuery 很熟悉，你应该知道 $('.class') 和 $('#id') 选择器的重要性。他们通过元素关联的 class 或 id 来选取元素。

But when you don’t have access to jQuery in the DOM, you can still do the same in the developer console.

但当你在 DOM 中无法使用 jQuery 时，你依然可以在开发者控制台中做同样的事情。

$(‘tagName’) $(‘.class’) $(‘#id’) and $(‘.class #id’) are equivalent to the document.querySelector(‘ ‘). This returns the first element in the DOM that matches the selector.

$('tagName') 、$('.class') 、$('#id') 和 $('.class #id') 相当于 document.querySelector(' ')。它返回选择器在 DOM 中匹配到的第一个元素。

You can use $$(‘tagName’) or $$(‘.class’) — note the double dollar signs — to select all the elements of the DOM depending on a particular selector. This also puts them into an array. You can again go ahead and select a particular element among them by specifying the position of that element in the array.

你可以使用 $$('tagName') 或 $$('.class') 构建特殊的选择器来选取 DOM 中所有匹配的元素（注意是两个 $ 符号）。这会把结果放入一个数组。你可以继续通过下标在数组中获取到特定的某个元素。

For example, $$(‘.className’) will give you all the elements that have the class className, and $$(‘.className’)[0]and $$(‘.className’)[1] will give you the first and the second element respectively.

举个例子，$$('.className') 将给你返回所有 class 为 className 的元素，$$('.className')[0] 和 $$('.className')[1] 分别给你返回第一个和第二个元素。

![](https://cdn-images-1.medium.com/max/1000/1*x-ygabMZbtYHH0mhGVaWbQ.png)

##2. Convert Your Browser Into An Editor

##2. 将你的浏览器变成编辑器

How many times have you wondered whether you could edit some text in the browser itself? The answer is yes, you can convert your browser into a text editor. You can add text to and remove text from anywhere in the DOM.

你幻想了多少次在浏览器里编辑文本信息？答案是可以的，你可以把你的浏览器变成编辑器。你可以在 DOM 中任意添加或移除文本。

You don’t have to inspect the element and edit the HTML anymore. Instead, go into the developer console and type the following:

你不再需要检查元素和编辑 HTML。相反，打开开发者控制台输入下面内容：

```javascript
document.body.contentEditable=true 
```

This will make the content editable. You can now edit almost anything and everything in the DOM.

这将使内容变为可编辑状态。你可以编辑 DOM 里的任何东西。

##3. Find Events Associated with an Element in the DOM

##3. 查找DOM中元素关联的事件

While debugging, you must be interested in finding the event listeners bound to an element in the DOM. The developer console makes it easier to find these.
getEventListeners($(‘selector’)) returns an array of objects that contains all the events bound to that element. You can expand the object to view the events:

当调试时，你肯定对 DOM 中约束元素的事件监听器感兴趣。开发者控制台让你更容易找到它们。

To find the Listener for a particular event, you can do something like this:

你可以做以下事情来找到特定事件的监听器：

```javascript
getEventListeners($(‘selector’)).eventName[0].listener 
```

This will display the listener associated with a particular event. Here eventName[0] is an array that lists all the events of a particular event. For example:

这将展示与特定事件相关联的监听器。eventName[0] 是一个包含所有特定事件的数组。比如：


```javascript
getEventListeners($(‘firstName’)).click[0].listener 
```

…will display the listener associated with the click event of element with ID ‘firstName’.

它会展示 ID 为「firstName」元素的点击事件所关联的监听器。

##4. Monitor Events

##4. 监控事件

If you want to monitor the events bound to a particular element in the DOM while they are executed, you can this in the console as well. There are different commands you can use to monitor some or all of these events:

如果你想在 DOM 中特定元素绑定的事件执行时监控它们，你可以通过控制台中完成。你可以使用很多不同的命令来监控部分或全部事件：

* monitorEvents($(‘selector’)) will monitor all the events associated with the element with your selector, then log them in the console as soon as they’re fired. For example, monitorEvents($(‘#firstName’)) will log all the events bound to the element with the ID of ‘firstName’ .

* monitorEvents($('selector')) 能够监控你所选取元素所关联的所有事件，然后在控制台打印它们【】。比如 monitorEvents($('#firstName')) 会打印 ID 为「firstName」的元素绑定的所有事件。

* monitorEvents($(‘selector’),’eventName’) will log a particular event bound with an element. You can pass the event name as an argument to the function. This will log only a particular event bound to a particular element. For example, monitorEvents($(‘#firstName’),’click’) will log all the click events bound to the element with the ID ‘firstName’.

* monitorEvents($('selector'),'eventName') 将打印元素绑定的特定事件。你可以将事件名字作为参数传入函数。它将打印特定元素绑定的特定事件。比如，monitorEvents($('#firstName'),'click') 会打印 ID 为「firstName」的元素绑定的点击事件。

* monitorEvents($(‘selector’),[‘eventName1’,’eventName3',….]) will log multiple events depending upon your own requirements. Instead of passing a single event name as an argument, pass an array of strings that contains all the events. For example monitorEvents($(‘#firstName’),[‘click’,’focus’]) will log the click event and focus events bound to the element with the ID ‘firstName’ .

* monitorEvents($('selector'),['eventName1','eventName3',…]) 会根据你的要求打印多个事件。传递参数包含所有事件的字符串数组，而不是单个事件名字。比如，monitorEvents($('#firstName'),['click','focus']) 会打印 ID 为「firstName」的元素绑定的点击事件和焦点事件。

* unmonitorEvents($(‘selector’)) : This will stop monitoring and logging the events in the console.

* unmonitorEvents($('selector')) ：这个会停止监视和在控制台打印事件。

##5. Find the Time Of Execution of a Code Block

##5. 查询代码块执行时间

The JavaScript console has an essential function called console.time(‘labelName’) which takes a label name as an argument, then starts the timer. There’s another essential function called console.timeEnd(‘labelName’) which also takes a label name and ends the timer associated with that particular label.
For example:

JavaScript 控制台有一个名为 console.time('labelName') 的重要函数，它接收一个标记名作为参数，然后开启计时器。另一个重要函数是 console.timeEnd('labelName') ，它也接收一个标记名作为参数，然后结束特定标记名所关联的计时器。
举个例子：

```javascript
console.time('myTime'); //Starts the timer with label - myTime
console.timeEnd('myTime'); //Ends the timer with Label - myTime

//Output: myTime:123.00 ms
```

The above two lines of code give us the time taken from starting the timer to ending the timer.

上面两行代码给我们展示了计时器开始和结束的间隔时间。

We can enhance this to calculate the time taken for executing a block of code.

我们可以改进它来计算代码块的执行时间。

For example, let’s say I want to find the time taken for the execution of a loop. I can do like this:

举个例子，如果我想知道一个循环的执行时间。我可以这样做：

```javascript
console.time('myTime'); //Starts the timer with label - myTime

for(var i=0; i < 100000; i++){
  2+4+5;
}

console.timeEnd('mytime'); //Ends the timer with Label - myTime

//Output - myTime:12345.00 ms
```

##6. Arrange the Values of a Variable into a Table

##6. 将变量里的值排列成表格

Let’s say we have an array of objects that looks like the following:

比如我们有一个下面这样的对象数组：

```javascript
var myArray=[{a:1,b:2,c:3},{a:1,b:2,c:3,d:4},{k:11,f:22},{a:1,b:2,c:3}]
```

When we type the variable name into the console, it gives us the values in the form of an array of objects. This is very helpful. You can expand the objects and see the values.

当我们在控制台输入变量名时，它给我们返回的格式是对象数组。这很有用。你可以展开对象查看属性值。

But this gets difficult to understand when the properties increase. Therefore, to get a clear representation of the variable, we can display them in a table.

但当属性增加时，这变得难于理解。因此，要想清楚地表现变量，我们可以把它展示成表格。

console.table(variableName) represents the variable and all its properties in a tabular structure. Here’s what this looks like:

console.table(variableName) 把变量和它的所有属性展现城表格结构。如下所示：

![](https://cdn-images-1.medium.com/max/1000/1*ODDvO9nnwEWyl1OqaZwifw.png)

##7. Inspect an Element in the DOM

##7. 检查DOM中的元素

You can directly inspect an element from the console:

你可以直接在控制台中检查元素：

* inspect($(‘selector’)) will inspect the element that matches the selector and take you to the Elements tab in the Chrome Developer Tools. For example inspect($(‘#firstName’)) will inspect the element with the ID ‘firstName’ and inspect($(‘a’)[3]) will inspect the 4th anchor element you have in your DOM.

* inspect($('selector')) 会检查与选择器匹配的元素，并切换 Chrome 开发者工具到元素标签页。举个例子，inspect($('#firstName')) 检查 ID 为「firstName」的元素，inspect($('a')[3]) 检查 DOM 中第 4 个锚点元素。

* $0, $1, $2, etc. can help you grab the recently inspected elements. For example $0 gives you the last-inspected DOM element, whereas $1 gives you the second last inspected DOM Element.

* $0、$1、$2 等等能帮助你取到最近检查的元素。比如，$0 给你返回上次检查的 DOM 元素，$1 返回上上次检查的 DOM 元素。

##8. List the Properties of an Element

##8. 列举元素的属性

If you want to list all the properties of an element, you can do that directly from the Console.

你可以在控制台中做以下事情来列举一个元素的所有属性。

dir($(‘selector’)) returns an object with all of the properties associated with its DOM element. You can expand these to view them in more detail.

dir($('selector')) 返回一个对象和与其 DOM 元素关联的所有属性。你可以展开它查看细节。

##9. Retrieve the Value of your Last Result

##9. 检索最近一个结果的值

You can use the console as a calculator. And when you do this, you may need to follow up one calculation with a second one. Here’s how to retrieve the result of a previous calculation from memory:

你可以把控制台当做计算器。一旦你这么做，【你可能需要在计算中使用到另一次计算结果】。下面是如何从内存中取到上一次计算的结果。

```javascript
$_ 
```

Here’s what this looks like:

就像下面这样：

```javascript
2+3+4
9 //- The Answer of the SUM is 9

$_
9 // Gives the last Result

$_ * $_
81  // As the last Result was 9

Math.sqrt($_)
9 // As the last Result was 81

$_
9 // As the Last Result is 9
```

##10. Clear the Console and the Memory

##10. 清空控制台和内存

If you want to clear the console and its memory, just type:

如果你想清空控制台和内存，只需要输入：

```javascript
clear()
```

Then press enter. That’s all there is to it.

然后敲回车键。就是酱紫。

These are just a few examples of what you can do with Chrome’s JavaScript console. I hope these tips make your life a little easier.

这是使用 Chrome JavaScript 控制台的几个例子。我希望这些小贴士能让你的生活更美好。
