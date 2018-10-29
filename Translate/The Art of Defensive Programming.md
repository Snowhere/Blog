原文[The Art of Defensive Programming](https://dev.to/0x13a/the-art-of-defensive-programming)
(译文发布在[伯乐在线](http://blog.jobbole.com/110123/)，感谢[刘唱](http://www.jobbole.com/members/liuchang/)校稿)
#The Art of Defensive Programming
#防御性编程的艺术


Why don’t developers write secure code? We’re not talking yet another time about “clean code” here. We’re talking about something more, on a pure practical perspective, software’s safety and security. Yes, because an insecure software is pretty much useless. Let’s see what does insecure software mean.

为什么开发者不编写安全的代码？我们在这并不是要再一次讨论「整洁代码」。我们要从纯粹的实用观点出发，讨论其他东西——软件的安全性和保密性。是的，因为一个不安全的软件是无用的。我们来看看什么是不安全的软件。

* The European Space Agency’s Ariane 5 Flight 501 was destroyed 40 seconds after takeoff (June 4, 1996). The US$1 billion prototype rocket self-destructed due to a bug in the on-board guidance software.

* 欧洲航天局的 Ariane 5 Flight 501 在起飞后 40 秒毁坏（1996年6月4日）。价值 10 亿美金的原型火箭因为搭载的导航软件里的一个 bug 而自毁。

* A bug in the code controlling the Therac-25 radiation therapy machine was directly responsible for at least five patient deaths in the 1980s when it administered excessive quantities of X-rays.

* 20 世纪 80 年代，在 Therac-25 radiation 医疗机器的控制代码里的一个 bug 使得 X光强度过大，直接导致至少 5 名病人死亡。

* The software error of a MIM-104 Patriot, caused its system clock to drift by one third of a second over a period of one hundred hours — resulting in failure to locate and intercept an incoming missile. The Iraqi missile impacted in a military compound in Dhahran, Saudi Arabia (February 25, 1991), killing 28 Americans.

* MIM-104 Patriot（爱国者）里的一个软件错误使它的系统每一百小时有三分之一秒的时钟偏移——导致定位拦截入侵导弹失败。伊拉克导弹飞入在达兰（沙特阿拉伯东北部城市）的一个军营（1991年2月25日），杀害了28名美国人。

This should be enough to understand how important is to write secure software, specially in certain contexts. But also in other use cases, we should be aware where our software bugs can lead us to.

这应该能够说明编写安全软件的重要性了，尤其在特定的环境中。当然也包括其他用例中，我们也应该意识到我们的软件 bug 会导致什么。

##A first sight to Defensive Programming
##防御式编程初窥

Why do I think Defensive Programming is a good approach to issue these problems in certain kind of projects?

为什么我认为在特定种类工程中,防御式编程是解决这些问题好的方式。

>Defend against the impossible, because the impossible will happen.

>抵御那些不可能的事，因为看似不可能的事也会发生。

There are many definitions for Defensive Programming, it also depends on the level of “security” and level of resources you need for your software projects.

防御式编程中有很多防御方式，这也取决于你的软件项目所需的「安全」的级别和资源级别。

**Defensive programming* is a form of defensive design intended to ensure the continuing function of a piece of software under unforeseen circumstances. Defensive programming practices are often used where high availability, safety or security is needed — Wikipedia*

防御式编程是[防御式设计](https://en.wikipedia.org/wiki/Defensive_design)的一种形式，用来确保[软件](https://en.wikipedia.org/wiki/Software)在未预料的环境中能继续运行。防御式编程的实践往往用于需要高可靠性、安全性、保密性的地方。——[Wikipedia](https://en.wikipedia.org/wiki/Defensive_programming)

I personally believe this approach to be suitable when you’re dealing with a big, long-lived project where many people are involved. Also for instance, with an open source project that requires a lot of extensive maintenance.

我个人相信这种实现适合很多人调用的大型、长期的项目。例如，一个需要大量维护的开源项目。

Let’s explore some of my diluted key points in order to achieve a Defensive Programming approach.

我们来探索一下我提出的关键点，来完成一个防御式编程的实现。

##Never trust user input

##永远不要相信用户输入

Assume always you’re going to receive something you don’t expect. This should be your approach as a defensive programmer, against user input, or in general things coming into your system. That’s because as we said we can expect the unexpected. Try to be as strict as possible. Assert that your input values are what you expect.

设想你总是将获取到你不期待的东西。这将使你成为防御式程序员，针对用户输入或传入你的系统的一般东西。因为我们说过我们期待异常情况。试着尽可能严谨。[断言](https://en.wikipedia.org/wiki/Assertion_(software_development))输入值是你所期待的。
![](https://res.cloudinary.com/practicaldev/image/fetch/s--Pic7qAkP--/c_limit,f_auto,fl_progressive,q_auto,w_725/https://medium2.global.ssl.fastly.net/max/2000/1%2AwJBEFQ8XcNR7RzlMnTF_fw.png)
The best defense is a good offense

进攻是最好的防守

Do whitelists not blacklists, for example when validating an image extension, don’t check for the invalid types but check for the valid types, excluding all the rest. In PHP however you also have an infinite number of open source validation libraries to make your job easier.

设置白名单而不是黑名单。举个例子，当你验证图像扩展名时，不要检查非法的类型，而是检查合法的类型并排除其他类型。在 PHP 有无数的开源校验库让你的工作变得简单。

The best defense is a good offense. Be strict

进攻是最好的防守。共勉

##Use database abstraction

##使用数据库抽象

The first of OWASP Top 10 Security Vulnerabilities is Injection. That means someone (a lot of people out there) is yet not using secure tools to query their databases. Please use Database Abstraction packages and libraries. In PHP you can use PDO to ensure basic injection protection.

在 [OWASP Top 10 Security Vulnerabilities](https://www.veracode.com/directory/owasp-top-10) 排首位的是注入攻击。这意味着有些人（很多人）还没有使用安全的工具来查询数据库。请使用数据库抽象包或库。在 PHP 里你可以使用 [PDO](http://php.net/manual/en/book.pdo.php) 来[确保防御基本注入](http://stackoverflow.com/questions/134099/are-pdo-prepared-statements-sufficient-to-prevent-sql-injection)。

##Don’t reinvent the wheel

##不要重复发明轮子

You don’t use a framework (or micro framework)? Well you like doing extra work for no reason, congratulations! It’s not only about frameworks, but also for new features where you could easily use something that’s already out there, well tested, trusted by thousands of developers and stable, rather than crafting something by yourself only for the sake of it. The only reasons why you should build something by yourself is that you need something that doesn’t exists or that exists but doesn’t fit within your needs (bad performance, missing features etc).

你不用框架（或微框架）吗？好吧你喜欢毫无理由地做额外的工作。这并不仅仅有关框架，也意味着你可以方便的[使用已经存在的、测试过的、受万千开发者信任的、稳定的](https://packagist.org/)新特性，而不是你自己仅为了从中受益而制作的东西。你自己创建东西的唯一原因是你需要的东西不存在，或存在但不符合你的需求（性能差、缺失特性等等）。

That’s what is used to call intelligent code reuse. Embrace it

这就是所谓的智能代码重用。拥抱它吧。

##Don’t trust developers

##不要相信开发者

Defensive programming can be related to something called Defensive Driving. In Defensive Driving we assume that everyone around us can potentially and possibly make mistakes. So we have to be careful even to others’ behavior. The same concept applies to Defensive Programming where us, as developers shouldn’t trust others developers’ code. We shouldn’t trust our code neither.

防御式编程与防御驱动相关联。在防御驱动中，我们假设我们周围的每个人都可能犯错。所以我们要注意别人的行为。相同观念也适用于防御式编程，我们作为开发者不要相信其他开发者的代码。我们同样也不要相信我们的代码。

In big projects, where many people are involved, we can have many different ways we write and organize code. This can also lead to confusion and even more bugs. That’s because why we should enforce coding styles and mess detector to make our life easier.

在很多人调用的大型项目中，我们有许多方式编写并组织代码。这也导致混乱甚至更多的 bug。这也是为什么我们需要规范代码风格并做代码检查，让生活更轻松。

##Write SOLID code

##编写符合 SOLID 原则的代码

That’s the tough part for a (defensive) programmer, writing code that doesn’t suck. And this is a thing many people know and talk about, but nobody really cares or put the right amount of attention and effort into it in order to achieve SOLID code.

这是（防御式）编程最困难的部分——编写不糟糕的代码。这也是很多人知道并讨论的，但没有人关心或注意并致力于实现符合 SOLID 原则的代码。

Let’s see some bad examples

让我们看一些糟糕的例子

>Don’t: Uninitialized properties

>避免：未初始化的属性

``` php
<?php

class BankAccount
{
    protected $currency = null;
    public function setCurrency($currency) { ... }
    public function payTo(Account $to, $amount)
    {
        // sorry for this silly example
        $this->transaction->process($to, $amount, $this->currency);
    }
}

// I forgot to call $bankAccount->setCurrency('GBP');
$bankAccount->payTo($joe, 100);
```

In this case we have to remember that for issuing a payment we need to call first setCurrency. That’s a really bad thing, a state change operation like that (issuing a payment) shouldn’t be done in two steps, using two(n) public methods. We can still have many methods to do the payment, but we must have only one simple public method in order to change the status (Objects should never be in an inconsistent state).

在这个例子中，我们需要牢记签发付款前要先调用 setCurrency。这是很糟糕的事情，一个像这样的改变状态的操作（签发付款）不应该分两步，使用两个公开的方法。我们可以拥有许多方法付款，但我们必须只有一个公开的方法来改变状态（类不应该存在不一致的状态）。

In this case we made it even better, encapsulating the uninitialised property into the Money object.

在这个例子中，我们把它改进，将未初始化的属性封装进 Money 类。

```php
<?php

class BankAccount
{
    public function payTo(Account $to, Money $money) { ... }
}

$bankAccount->payTo($joe, new Money(100, new Currency('GBP')));
```

Make it foolproof. Don’t use uninitialized object properties

它变得极其简单和安全。不要使用未初始化的对象属性。

>Don’t: Leaking state outside class scope.

>避免：类的作用域外泄露状态

```php
<?php

class Message
{
    protected $content;
    public function setContent($content)
    {
        $this->content = $content;
    }
}

class Mailer
{
    protected $message;
    public function __construct(Message $message)
    {
        $this->message = $message;
    }
    public function sendMessage(
    {
        var_dump($this->message);
    }
}

$message = new Message();
$message->setContent("bob message");
$joeMailer = new Mailer($message);

$message->setContent("joe message");
$bobMailer = new Mailer($message);

$joeMailer->sendMessage();
$bobMailer->sendMessage();
```

In this case Message is passed by reference and the result will be in both cases “joe message”. A solution would be either cloning the message object in the Mailer constructor. But what we should always try to do is to use a (immutable) value object instead of a plain Message mutable object. Use immutable objects when you can.

在上述代码中，Message 通过引用传递“joe message”到每个例子中。一个解决方案是克隆 message 对象到 Mailer 构造函数。但是我们应该做的是试着使用（不变的）[值对象](https://en.wikipedia.org/wiki/Value_object)，而不是简单易变的 Message 对象。尽可能使用不变的对象。

```php
<?php

class Message
{
    protected $content;
    public function __construct($content)
    {
        $this->content = $content;
    }
}

class Mailer 
{
    protected $message;
    public function __construct(Message $message)
    {
        $this->message = $message;
    }
    public function sendMessage()
    {
        var_dump($this->message);
    }
}

$joeMailer = new Mailer(new Message("bob message"));
$bobMailer = new Mailer(new Message("joe message"));

$joeMailer->sendMessage();
$bobMailer->sendMessage();
```

##Write tests

##编写测试

We still need to say that? Writing unit tests will help you adhering to common principles such as High Cohesion, Single Responsibility, Low Coupling and right object composition. It helps you not only testing the working small unit case but also the way you structured your object’s. Indeed you’ll clearly see when testing your small functions how many cases you need to test and how many objects you need to mock in order to achieve a 100% code coverage.

这点我们很还需要再说吗？编写单元测试可以帮助你秉承一般的原则，比如高内聚、单一职责、低耦合和正确的对象组合。它帮助你不仅仅测试小的单元用例，也测试你组织对象方式。确实，当测试你的小功能时，你会清晰的看到你需要测试多少情况和需要模拟多少对象，来达到 100% 的覆盖率。

##Conclusions

##结论

Hope you liked the article. Remember those are just suggestions, it’s up to you to know when, where and if to apply them.

希望你喜欢这篇文章。记住这些仅仅是建议，由你决定何时、何处以及是否应用它们。

Thanks for reading!

感谢阅读！