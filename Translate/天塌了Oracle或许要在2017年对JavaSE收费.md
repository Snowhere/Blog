原文[The Sky Is Falling: Oracle (Might) Want Your Money for Java SE in 2017](https://dzone.com/articles/the-sky-is-falling-oracle-might-want-your-money-fo)
(译文发布在[码农网](http://www.codeceo.com/article/java-se-unfree-2017.html))

#天塌了:Oracle或许要在2017年对Java SE收费
最近[The Register](http://www.theregister.co.uk/)发表文章"[Oracle收购Sun6年之后终于把手伸向了免费用户](http://www.theregister.co.uk/2016/12/16/oracle_targets_java_users_non_compliance/)"伴随着副标题"认为Java是'免费'的?再想想(2017年你就欠我们钱了)",我被震惊到了-因为我有大量基于Java SE的客户端.
等我读完整篇文章,我好奇真相是什么.

#文章摘要
那篇文章主要说了Oracle运作他们的许可证管理服务(LMS)致力于"找出需要付费的人群".文章引用了一名拥有八万PC终端的Java客户,以及他们如何因为没有为Java许可证付费而欠Oracle十多万美金.
文章声称,即使Java软件的合作伙伴也不能避开LMS小组,原因就是Java是免费的的这一错误观念.
然后,文章提及了许可证的等级,每位用户四十美金到三百美金不等或每个处理器一万五千美金.
最后,文章讨论了检查你使用的Java SE,确保你只下载安装了你需要的东西.当然,也要注意LMS小组在2017年找上你.

#我的调研
我决定做的第一件事就是去Oracle的网站看一看Java SE的页面.我希望我能找到一个"添加至购物车"的按钮,允许我购买许可证,确保许可证管理服务小组(LMS)不会找上我.这是我找到的一个截图:
![](http://i.imgur.com/nLWrk2p.png)
不幸的是,只有到下载产品的链接.我之后总会回来看看,即使是周末.
我注意到Oracle有一个在线商店,所以我认为我可以搜索Java SE.我确定它会指引我到一个页面,让我可以添加什么东西到购物车.这是我搜索"java se"的结果.
![](http://i.imgur.com/QnlpxEC.png)
我找到了几个"现在购买"按钮,一个是Oracle Java SE support的,另一个是Oracle Java SE Advanced的.
当我读到有关Java SE Advanced的信息时,我认为我找到了需要购买的东西.然而点击链接之后,我被带到以下页面:
![](http://i.imgur.com/nuChsqY.png)
这看起来并不像我需要的许可证.它像是Java SE,附带捆绑包...或许是"Advanced"版本.更复杂的是我要为我的订单提供至少500个终端(或2个处理器).最低都要每年1万美金.
我把我的调研提高一个层次,从Google搜索"购买java se许可证(buy java se license)",得到了一个链接指向[Java SE概览-常见问题](http://www.oracle.com/technetwork/articles/javase/faqs-jsp-136696.html)页面.问题是"Java仍然免费吗",得到以下信息:
>当前版本的Java-Java SE 8-在一般计算用途的分配使用中是免费可用的.Java SE在Oracle Binary Code License(Oracle二进制代码许可证 BCL)下仍是免费的.在嵌入式设备和其他计算环境中使用JRE可能需要向Oracle支付许可证费用.阅读更多关于嵌入式使用Java SE的信息,或联系你当地的Oracle经销商来获得许可证.

好吧...当我读到嵌入式设备的时心情舒畅,但Oracle在回答中紧接着加了句"...和其他计算环境".所以跳到[这个链接](http://www.oracle.com/technetwork/java/embedded/documentation/default-1971746.html)得到下面截图:
![](http://i.imgur.com/ooRRqJM.png)
这时,我深吸一口气,因为这是Java的分配使用情况,我并不熟悉.

#我的观点
首先,我不是一个软件许可证专家.那篇文章看起来引用的是LMS小组对没有Java SE许可证的嵌入式产品采取的措施.文章提到的其中一名客户在零售业.所以提到的那些PC可能是收银机(或其他东西),使用了嵌入式Java版本用于专有用途?这只是个猜测.
考虑到我以前的客户端运行着Java SE,我读完那篇文章期待着采取一些行动.可能会提高明年预算的行动.至少我可以说(再次声明我不是软件许可证方面的专家),我的客户端并不受那篇文章的影响.
我的团队已经成功转向了OpenJDK,[我在DZone.com上提到过](https://dzone.com/articles/openjdk-is-now-the-time).所以,如果你的团队担心Oracle Java SE许可证,害怕LMS小组,你也可以考虑使用OpenJDK.
我很有兴趣听到你关于这个话题的观点.
Have a really great day!
