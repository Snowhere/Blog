原文[Giving up on test-first development](http://iansommerville.com/systems-software-and-technology/giving-up-on-test-first-development/)
(译文发布在[伯乐在线](http://blog.jobbole.com/109861/)，感谢[艾凌风](http://www.jobbole.com/members/hanxiaomax)校稿)

#Giving up on test-first development
#放弃测试优先式开发

Test-first or test-driven driven development (TDD) is an approach to software development where you write the tests before you write the program. You write a program to pass the test, extend the test or add further tests and then extend the functionality of the program to pass these tests. You build up a set of tests over time that you can run automatically every time you make program changes. The aim is to ensure that, at all times, the program is operational and passes the all tests. You refactor your program periodically to improve its structure and make it easier to read and change.

测试优先或测试驱动开发（TDD）是你在编写程序前先编写测试的软件开发方法。你编写程序来通过测试，扩展测试或增加测试然后再扩展程序功能来通过这些测试。你先花费时间建立一系列的测试，然后你可以在每次程序修改时自动运行测试。这是为了确保每时每刻程序都能够运行并通过测试。你周期性地重构你的程序来提高它的结构并让它易读易改。

It is claimed that test-first development improves test coverage and makes it much easier to change a program without adversely affecting existing functionality. The tests serve as the program specification so you have a detailed but abstract description of what the program should do.

外界宣称测试优先式开发提高了测试覆盖率，并且在不改变现有功能基础上，能够更简单的改变程序。测试的作用就像文档一样，你有了一个关于程序应该做什么的详细又抽象的描述。

I deliberately decided to experiment with test-first development a few months ago. As a programming pensioner, the programs I am writing are my own personal projects rather than projects for a client with a specification  so what I have to say here may only apply in situations where there’s no hard and fast program specification.

几个月前，我谨慎的决定体验测试优先式开发。作为一个退休的程序员，我编写的是我私人的项目，而不是一个有文档说明的客户端。所以我在此所说的话或许仅仅适用于没有严格程序文档的情况。

At first, I found the approach to be helpful. But as I started implementing a GUI, the tests got harder to write and I didn’t think that the time spent on writing these tests was worthwhile. So, I became less rigid (impure, perhaps) in my approach, so that I didn’t have automated tests for everything and sometimes implemented things before writing tests.

首先，我发现这个方法（测试驱动开发）很有用。但我开始实现一个 GUI 时，编写测试很困难，而且我认为编写测试所花费的时间并不值得。然后，我开始不严格地（或许是不纯粹地）实现，所以我没有对所有东西做自动化测试，有时在编写测试前就实现了一些东西。

But, I’m not giving up on TDD because of the (well-known) difficulties in writing automated tests for GUIs. I’m giving up because I think it encourages conservatism in programming, it encourages you to make design decisions that can be tested rather than the right decisions for the program users, it makes you focus on detail rather than structure and it doesn’t work very well for a major class of program problems – those caused by unexpected data.

但是，我并不是因为编写 GUI 自动化测试的（众所周知的）困难而放弃 TDD 。我放弃是因为我认为它在编程中鼓励保守主义，它鼓励你设计能通过测试的决策，而不是符合用户的正确决策，它使你专注于细节而不是架构，并且它并不能适用于主要的一类程序问题——那些由意外的数据引起的问题。

* Because you want to ensure that you always pass the majority of tests, you tend to think about this when you change and extend the program. You therefore are more reluctant to make large-scale changes that will lead to the failure of lots of tests. Psychologically, you become conservative to avoid breaking lots of tests.

* 因为你想确保你总是可以通过大多数的测试，当你改变并扩展程序时你倾向于思考这一点。你因此不情愿做出会导致大量测试失败的大范围的变更。从心理上讲，你变得保守并避免破坏大量测试。

* It is easier to test some program designs than others. Sometimes, the best design is one that’s hard to test so you are more reluctant to take this approach because you know that you’ll spend a lot more time designing and writing tests (which I, for one, quite a boring thing to do)

* 往往对程序设计做测试比其他更容易。有时，最棒的设计是很难测试的，所以你不情愿采取这种方法，因为你知道你将花费大量时间设计并编写测试（对于我来说真的是一件无聊的事）。

* The most serious problem for me is that it encourages a focus on sorting out detail to pass tests rather than looking at the program as a whole. I started programming at a time where computer time was limited and you had to spend time looking at and thinking about the program as a whole. I think this leads to more elegant and better structured programs. But, with TDD, you dive into the detail in different parts of the program and rarely step back and look at the big picture.

* 对于我来说最严重的问题是 TDD 鼓励专注于整理细节来通过测试，而不是把程序看做一个整体。我每次开始编程时只有有限的上机时间，并且需要花费时间审视并思考程序整体。我认为这带来优雅又优秀的结构化程序。但是 TDD 让你深入程序不同部分的细节，很少能够退后一步审视大局。

* In my experience, lots of program failures arise because the data being processed is not what’s expected by the programmer. It’s really hard to write ‘bad data’ tests that accurately reflect the real bad data you will have to process because you have to be a domain expert to understand the data. The ‘purist’ approach here, of course, is that you design data validation checks so that you never have to process bad data. But the reality is that it’s often hard to specify what ‘correct data’ means and sometimes you have to simply process the data you’ve got rather than the data that you’d like to have.

* 根据我的经验，很多程序出现失败是因为处理的数据并不是程序所期待的。编写能够精确反映出你将要处理的坏数据的「坏数据」测试很困难，因为你需要成为一个领域专家并理解数据。「纯粹主义者」解决这点，当然，那就是你设计了数据校验检查，所以你不需要处理坏数据。但是现实中通常很难定义什么是「正确的数据」，并且有时你不得不处理你得到的数据而不是你预期的数据。

So, I’m going back to writing code first, then the tests. I’ll continue to test automatically wherever it makes sense to do so, but I won’t spend ridiculous amounts of time writing tests when I can read the code and clearly understand what it does. Think-first rather than test-first is the way to go.

所以，我回到原点开始先写代码，然后写测试。我继续在有意义的地方做自动化测试，但在我能清楚读懂代码并理解它做什么的时候，我不会花费大量荒谬的时间编写测试。思考优先比测试优先是一条更好的路。

PS. I’m sure that TDD purists would say that I’m not doing it right so I’m not getting the real benefits of TDD. Maybe they are right. But I have never found zealots to be very convincing.

附言。我很确定 TDD 纯粹主义者会说我做的不正确，所以没有真正从 TDD 受益。或许他们是对的。但是我还没有找到狂热者能够说服我。

(发布在[伯乐在线](http://blog.jobbole.com/109861/)，感谢[艾凌风](http://www.jobbole.com/members/hanxiaomax)校稿)