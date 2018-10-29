原文[Is Java Dead? No! Here’s Why...](https://dzone.com/articles/is-java-dead-no-heres-why)
(译文发布在[码农网](http://www.codeceo.com/article/java-no-die.html))
#Java消亡了？不！原因在这...
年复一年，关于"Java消亡了？"的疑问频繁涌现，然而，通过所有外部表现来看，Java仍活着，并且在发展。尽管许多新语言各领风骚，开发语言排行榜(TIOBE)上Java在2015年仍居榜首，并比2014年增长了[5%的使用量](http://www.tiobe.com/news/10/java-language-of-year-2015),远远超过其他语言。
![Is Java Dead -- TIOBE](https://stormpath.com/wp-content/uploads/2016/06/2016-06-14_15-17-25-1024x534.png)
另一方面，[PYPL index](http://pypl.github.io/PYPL.html),根据Google搜索频率为编程语言排名，展示Java以总搜索量的23.9%遥遥领先。

近两年有很多Java死灰复燃的原因(Android开发的爆炸式增长，Java 8的发布，Spring社区的活跃，尤其是Spring Boot),市场优势来自于伟大的产品。这就是为什么我们认为Java并不会消亡。

##Java虚拟机和Java生态环境
Java虚拟机，或称JVM，将程序编译成字节码，稍后被JVM解析执行。因为JVM根植于你的特定硬件和系统，它允许Java在各处运行，Windows、Mac或各种Linux。

JVM带来的巨大优势就是它提供的强大兼容性和稳定性。因为你的应用运行在虚拟机上而不是直接运行在硬件上，你可以编写一次应用并运行在每个有Java虚拟机的设备上。这也是Java核心原则："一次编写，到处运行"。这也让Java应用面对环境变化时有更大弹性。

##安全性和互操作性
![Java + Android](https://stormpath.com/wp-content/uploads/2016/06/java-android.jpg)
Android环境就是Java安全性和互操作性的典型代表。Android占全球手机市场89%，运行基于Java。因为Java允许操作系统运行在虚拟机中，并不需要在每台设备上编译，提高了系统的稳定性和易用性。

Android展现出的Java另一个优点就是与其他基于JVM语言的互操作，比如Scala、Groovy、Clojure、JRuby等等。你可以选择使用这些"更加动态"的语言来构建你的应用，并在需要更多性能的时候重拾Java。

巨大的Android市场在内部开发中也存在危险。Android+Java允许用户运行不受信任的应用，并通过虚拟机来运行所有应用来缓解这种危险。因此，应用充分利用操作系统核心的唯一方式就是放弃虚拟机的实现，也就是那个非常小的、被安全层紧紧保护的虚拟机。

##Java的实际应用规模
Amazon、Google、eBay和许多其他大型电商使用Java作为它们的后台。它们使用Java是因为Java久经考验并且可伸缩。任何语言的后台只能支撑少量用户，但Java可以处理200m或等多。让我们看几个典型例子：

###Hadoop
Apache Hadoop是支持在大规模硬件集群上运行应用的的Java框架。它被设计用来为那些操作和处理大数据的应用提供高吞吐量。如今Hadoop被许多公司，如Facebook、Amazon、IBM、Joost和Yahoo来处理数据、分析或生成记录。

Hadoop的创造者Doug Cutting说过："Java协调了开发者开发效率和程序执行效率。开发者受益于这种简单、强大、类型安全的语言和丰富的高质量库。"简而言之：大数据离不开Hadoop，Hadoop离不开Java。

###Twitter
![Twitter + Java](https://stormpath.com/wp-content/uploads/2016/06/twitter_fail_whale1-300x165.png)
对于Oracle团队和Java来说，Twitter是近几年众人皆知的巨大成功。最初由Ruby on Rails构建，Twitter流行度急速上升，同时RoR框架可伸缩性的缺陷也清晰暴露出来。"Fail Whales(失败鲸)"(Twitter宕机时的标志画面)也频繁出现，直到Twitter在2012年转向Java。如今Twitter运行在Java+Scala之上，Fail Whales(失败鲸)也不复存在。

###Minecraft
![Minecraft + Java](https://stormpath.com/wp-content/uploads/2016/06/text-typography-minecraft-keep-calm-and-2480x3508-wallpaper_www.wallpapermay.com_1.jpg)
Minecraft是款不可思议的成功游戏。开发团队通过它赚了上百万美元，然后以25亿美元价格卖给微软。Minecraft运行在Java之上。

庞大的的Java社区帮助Minecraft蔓延到成千上万的游戏mod。同时Minecraft向新一代开发者推荐Java。Google上搜索"Minecraft mod制作"会返回65.3万的结果，其中大多是教12岁以下人群如何编程。

其中一款开源工具是Eureka，人们用它来跟踪用户登录Netflix所使用的设备，并交叉校验动作和最近活动，来确保当前账户是安全的。

##Java的未来
去年，Oracle宣布Java 9预计在2016年9月到来。这次更新把语言中众多函数拆分成许多小组件，意在让Java更加快速和易用。Oracle的巨大投入(虽然总被黑)帮助Java一方面可以继续支持企业依赖，另一方面也可以有所新的发展。

###Java和物联网(Internet of Things)
"我认为Java的未来就在物联网(IoT)之中。我很想看到Oracle和合作伙伴致力于完善Java端对端的存储解决方案，从设备通过网关到达企业后台。如果能梦想成真并取得成功，将巩固Java在未来20年中的地位。这对于行业来说是巨大的挑战，我相信Java能够胜任。"Eclipse基金会执行董事Mike Milinkovich这样说。

Oracle赞同这点。Development Georges Saab副总裁说："Java对于物联网(IoT)来说是很棒的技术。物联网很多挑战是桌面和客户端的挑战，上世纪90年代Java已经解决。现在有很多不同的硬件设备。你想让开发者关注系统的各个层面，理解并运用。Java是少数能让你这样做的技术之一。"

##Java + Stormpath
有不少批评Java的人，他们部分争论的焦点或许有一些说服力，但这也是好事，比如优秀的部署工具、性能分析、Java虚拟机、庞大的库等等。在Stormpath，我们的后端完全用Java开发，并且[我们升级到了Spring Boot](https://stormpath.com/blog/spring-boot-migration?utm_source=dzone&utm_medium=post&utm_content=is-java-dead&utm_campaign=java-2016)。Java万岁。

##了解更多
Stormpath提供[先进的身份认证管理服务]()，通过Java可以快速部署实现。Stormpath Java SDK、Servlet plugin和Spring框架中的Spring WebMVC、Spring Security WebMVC、Spring Boot WebMVC。Spring Security Spring Boot WebMVC允许开发者无需编写代码就能简单快速构建出很多功能，比如：
* 复杂的[授权支持](https://stormpath.com/product/authorization?utm_source=dzone&utm_medium=post&utm_content=is-java-dead&utm_campaign=java-2016)，通过缓存提高性能。
* [令牌授权与撤回](https://stormpath.com/product/token-auth?utm_source=dzone&utm_medium=post&utm_content=is-java-dead&utm_campaign=java-2016)，支持 JSON Web Tokens和OAuth 2.0
* [多用户应用](https://stormpath.com/product/multi-tenant?utm_source=dzone&utm_medium=post&utm_content=is-java-dead&utm_campaign=java-2016)的本地化支持，通过客户数据分区预构建。
* 完善的[文档](https://docs.stormpath.com/rest/product-guide/latest/about.html?utm_source=dzone&utm_medium=post&utm_content=is-java-dead&utm_campaign=java-2016)和[客户沟通](https://support.stormpath.com/hc/en-us?utm_source=dzone&utm_medium=post&utm_content=is-java-dead&utm_campaign=java-2016)，即使使用的是开发者账号。
* 看看我们的[Java产品文档](https://docs.stormpath.com/java/?utm_source=dzone&utm_medium=post&utm_content=is-java-dead&utm_campaign=java-2016)，下载我们的[Build vs. Buy whitepaper](http://go.stormpath.com/build-vs-buy-customer-identity-user-management?utm_source=dzone&utm_medium=post&utm_content=is-java-dead&utm_campaign=java-2016),或[立刻注册 开始开发](https://api.stormpath.com/register?utm_source=dzone&utm_medium=post&utm_content=is-java-dead&utm_campaign=java-2016)。