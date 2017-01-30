原文[ActiveRecord Is Even Worse Than ORM](https://dzone.com/articles/activerecord-is-even-worse-than-orm)

#ActiveRecord比ORM还糟糕
你或许还记得我怎么[看待ORM](http://www.yegor256.com/2014/12/01/orm-offensive-anti-pattern.html),一个非常流行的设计模式.简而言之,它鼓励我们把对象转变成被动的,贫乏的DTO,甚至称不上是对象.结局往往是戏剧的-整个编程范式从面向对象转移到面向过程.这些年,我试着在JPoint和JEEConf解释这些.每次访谈之后,总有人告诉我,我的建议可以称作[ActiveRecord](https://en.wikipedia.org/wiki/Active_record_pattern)或[Repository](https://msdn.microsoft.com/en-us/library/ff649690.aspx)模式.

另外,他们声称ActiveRecord真正解决了我在ORM中发现的问题.他们还让我在我的访谈中解释我所宣称的SQL-speaking objects早已存在,就是ActiveRecord.

我不同意.另外,我认为ActiveRecord比ORM还糟糕.

ORM包含两部分:会话和DTO,或称为"实体".实体对象没有任何功能;它们只通过会话传输数据.这就是问题所在-对象不应该仅仅是保存数据.你可以了解更多的内容.现在我们只需要同意ORM是错的,然后继续我们的讨论.

ActiveRecord是为了解决什么问题?它又是如何解决问题的?它把引擎放在父类中,然后我们所有的实体继承自它.下面是我们如何用ORM保存一个实体(伪代码):
```java
book.setTitle("Java in a Nutshell"); session.update(book);
```
然后是我们如何用ActiveRecord做同样的事:
```java
book.setTitle("Java in a Nutshell"); book.update();
```
*update()*方法在book的父类中定义,并把book类作为数据容器.调用时,它从容器(book)中取到数据并更新到数据库中.这个ORM有什么区别?这显而易见没有区别.book仍然只是个容器,没有关联SQL或任何持久化机制.

相比ORM,ActiveRecord更糟糕的地方在于它隐藏了了对象是数据容器这个事实.一个book,伪装成"真正的类",然而事实上它只是数据包.

那些说我的SQL-speaking objects原理和ActiveRecord设计模式相同的人,我相信他们是在误导大家.

SQL-speaking objects和ActiveRecord不一样,一点都不一样.