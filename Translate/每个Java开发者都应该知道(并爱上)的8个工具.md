原文[Eight Tools Every Java Developer Should Know (and Love)](https://dzone.com/articles/eight-tools-every-java-developer-should-know-and-l)
(译文发布在[码农网](http://www.codeceo.com/article/8-tools-every-java-developer-love.html))

#每个Java开发者都应该知道(并爱上)的8个工具
在Stormpath(一款用户管理和认证服务),我们认真对待质量和效率.任何一个优秀的工匠,仅仅拥有天赋是不够的,你在工作中还需要正确的工具.工程学不仅仅是科学,更是艺术.所以,在Stormpath,尽管我们拥有天赋,我们仍不断寻找所需的有用的工具.我们的工程师一直渴望向团队其他人分享新工具.现在,让我们向所有充满天赋的Java开发者推荐工具.

在这篇文章中,我将分享我们Java团队日常工作使用的工具,并介绍如何使用它们,分享一些实用的小技巧.

#1.Groovy
我们使用[Groovy](http://www.groovy-lang.org/)来写Java测试.为什么?因为它提供了下面这些能力:

**宽松的Java语法**:这是Java语法,但有些其他规则.比如分号,变量类型,访问修饰符都是可选的.后者对于测试意义重大.由于访问修饰符不是严格的,你的测试可以读取并断言类内部的状态.举个例子,我们假设下面一个类:
``` java
public class Foo {
    private String bar = null;

    public void setBar(String bar) {
        this.bar = bar;
    }
}
```
如果你想测试setBar(String)方法正常(即能正确修改bar的值).你可以用Groovy方便的读取变量值.Java中不允许这样的操作(在不涉及Java反射机制前提下).
```  groovy
@Test public void test() { 
    def foo = new Foo() 
    foo.setBar("hello") 
    Assert.isTrue(foo.bar.equals("hello"))
    //groovy 允许我们访问私有变量 bar
}
```
**强大的断言**:Groovy提供强大多样的assert,被称作power assertion statement.Groovy的强大断言能够清晰的展示验证失败时的结果.另外,它比Java更有可读性.
``` java
Assert.isTrue(foo.bar.equals("hello"));
```
可以用Groovy这样写:
``` groovy
assert foo.bar == "hello"
```
当断言失败时它会显示一个非常清晰的描述:
``` groovy
assert foo.bar == "goodbye"
       |   |   |
       |   |   false
       |   hello
       Foo@12f41634
```
**Mocking**:使用Java时,动态模拟框架(如:EasyMock,PowerMock和Mockito)非常流行.这些框架可以在Groovy下方便的使用.耶!

##2.支持REST风格
我们的后端提供REST API服务来创建和管理账户.众多SDK中,我们的Java SDK提供特定语言客户端模型做简单交互.其中一些SDK也提供网页来和后端交互,从而不用编写代码.

为了保证网络框架的互操作性,它们必须表现的一样.因此我们需要创建一系列基于HTTP的测试.这是我们的[兼容性测试工具](https://github.com/stormpath/stormpath-framework-tck?utm_source=dzone&utm_medium=post&utm_content=top-tools-java&utm_campaign=java-2016).这个项目由我们的SDK工程师维护,他们精通不止一种语言.因此我们需要跨语言测试工具.谢天谢地[Rest-assured](https://github.com/rest-assured/rest-assured?utm_source=dzone&utm_medium=post&utm_content=top-tools-java&utm_campaign=java-2016)来了.

Rest-assured是Java领域特定语言(DSL domain-specific language)用来测试REST服务.它简单易用易上手,甚至对于没有用过Java的开发者也是难以置信的强大.它提供先进的特性,比如细节配置,过滤器,定制分析,跨站请求伪造(CSRF)和OAuth 2.0 .它提供非常简单的语法:given-when-then.

举个例子:让我们来看它如何校验"向/login路径发送post认证信息请求返回302状态码":
```
given() .accept(ContentType.HTML) .formParam("login", account.username) .formParam("password", account.password) .when() .post(LoginRoute) .then() .statusCode(302)
```
你可以在我们的[TCK repo](https://github.com/stormpath/stormpath-framework-tck)中看到更多Rest-assured测试

##3.Cargo Plugin
为了让我们的Java SDK按照TCK校验,我们需要开启我们其中一个Web服务,以便测试在上面执行.讲道理的话,我们需要每次构建时自动测试.[Gargo Plugin](https://codehaus-cargo.github.io/cargo/Home.html)就是为此而生.
Cargo用标准的方式简单封装各种应用容器.我们使用Cargo可以毫不费力的在不同的Servlet容器(比如Jetty和Tomcat)中运行我们的代码.我们只需要在我们的pom文件中配置[Cargo Maven2 Plugin](https://codehaus-cargo.github.io/cargo/Maven2+plugin.html)来启动一个Servlet容器(Tomcat7),在测试阶段编译最近的War包.你可以在我们的[Servlet插件例子](https://github.com/stormpath/stormpath-sdk-java/blob/master/examples/servlet/pom.xml)中看到配置.
##4.Git
我们能讨论哪些关于[Git](https://git-scm.com/)你不了解的事情呢?想要深入了解Git,你可以看他们的[About页](https://git-scm.com/about).
我们的Java SDK团队遍布全球,而且彼此之间几乎从未坐在一起.Git保障了我们写的每一行代码.这里有一些非常棒的命令,节省了我们大量的时间:
*  git mv --force foo.java Foo.java:在大小写敏感的文件系统中改变文件名是非常麻烦的.这个命令能让git意识到foo.java重命名为Foo.java
*  git diff-tree --no-commit-id --name-only -r &lt;commit_ID&gt;: 查看所有在&lt;commit_ID&gt;这次提交中更改的文件.
*  git diff --name-only SHA1 SHA2:列举出在SHA1和SHA2两次提交之间所有更改的文件.
*  在一个文件的历史提交记录中查询某个字符串:创建search.sh文件,粘贴下面代码:
```
git rev-list --all $2 | (
    while read revision; do
        git grep -F $1 $revision $2
    done
)
```
命令可以通过这种方式执行:sh ./search.sh string_to_search file_where_to_search

##5.GitHub
[GitHub](https://github.com/)不仅仅为我们的Git项目提供托管服务,它为代码开源并让全世界都看到做出了巨大贡献.这鼓舞了人们去尝试,去交流,去练习,很大程度提高了每个人的项目质量和大家的技术水平.
GitHub允许我们[跟进我们的issue](https://guides.github.com/features/issues/).游客可以提交新需求和报告bug.他们也可以收到我们项目进展的通知.

##6.Maven
[Maven](https://maven.apache.org/)已经足够出名了.所以我不会用长篇幅解释为什么我们使用Maven做构建管理.然而我可以分享几个技巧,让你的Maven更得心应手: 
**管理依赖** :在一个多模块的项目中,你需要在根pom.xml的&lt;dependencyManagement&gt;标签中定义每一个依赖.一旦你这样做,所有下层模块都可以不需要指定版本.这种管理依赖的方式(比如版本升级)可以集中处理,所有下层模块都会自动识别.比如在根pom.xml:
```
<dependencyManagement>
  <dependencies>
    <dependency>
        <groupId>io.jsonwebtoken</groupId>
        <artifactId>jjwt</artifactId>
        <version>${jjwt.version}</version>
     </dependency>
     ...
  <dependencies>
<dependencyManagement>
```
下层模块的pom.xml:
```
<dependencies>
  <dependency>
      <groupId>io.jsonwebtoken</groupId>
      <artifactId>jjwt</artifactId>  <!-- 注意这里没有指定版本 -->
  </dependency>
  ...
<dependencies>
```
**阻止下层模块编译**:在发布的时候我们需要所有下层模块一起发布,但是我们如何避免某个模块(比如example)发布呢?很简单,只需要把下面的pom文件加入到你不想发布的模块:
```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-deploy-plugin</artifactId>
    <version>2.7</version>
    <configuration>
        <skip>true</skip>  <!-- (敲黑板)这是重点 -->
    </configuration>
</plugin>
```
**跳过集成测试**:我们有很多集成测试需要很长时间编译.这些测试确保了后端整体运行正常.在频繁的本地部署期间,我们多次因为新功能或修复bug而更改代码.并不需要每次在本地构建的时候执行这些测试,这会拖慢开发进度.因此我们要确保我们的Java SDK只在我们的[CI](https://en.wikipedia.org/wiki/Continuous_integration)服务器上运行的时候执行集成测试.可以通过下面方法:
根pom.xml文件:
```
<properties>
    <skipITs>true</skipITs>
</properties>
...
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-failsafe-plugin</artifactId>
            <version>2.19.1</version>
            <configuration>
                <skipITs>${skipITs}</skipITs>
                <includes>
                    <include>**/*IT.*</include>
                </includes>
            </configuration>
            <executions>
               <execution>
                   <goals>
                       <goal>integration-test</goal>
                       <goal>verify</goal>
                   </goals>
               </execution>
            </executions>
        </plugin>
  </plugins>
<build>
```
所以你可以想象到,所有集成测试文件以IT结尾,来保证配置正常运作,比如:ApplicationIT.groovy 或 I18nIT.groovy
然后,如果我们想让集成测试运行,我们执行以下构建:mvn clean install -DskipITs=false

##7.JWT Inspector
我们的Java SDK使用[JWT(JSON Web Token)](https://en.wikipedia.org/wiki/JSON_Web_Token)通过安全可靠的方式传输数据.当我们测试排查时,我们需要分析从浏览器接收到的JWT内容.token信息可能在URL,cookie或本地储存中.[JWT Inspector](https://www.jwtinspector.io/)是一款浏览器插件,让我们可以从控制台或内置的界面解码JSON Web Token.你不需要在你的app中跟踪token信息.你只需要按一下插件的按钮,JWT Inspector会自动展示你所需要的所有信息.然后你可以复制其中任何token信息.

##8.Postman
我们重度依赖REST API请求.编写REST请求并不方便,具体语法取决于我们所用的工具,比如curl或[HTTPie](http://httpie.org/).两者都易读,但语法难记.通常,我们需要排查问题时,我们需要测试一些请求.当出问题时,我们无法判断原因是请求还是后端.我们浪费了大量时间来怀疑我们写的请求是否正确.
[Postman](https://www.getpostman.com/)让书写REST API请求变得简单.它也提供很多功能,比如保存,复用请求,生成代码(java,python,curl等等),还可以批量按序执行请求.Postman通过友好的界面帮助你构建复杂的命令.你所需要做的就是填写一个表单.简直不能再棒了.

#总结
使用正确的工具不仅仅帮助你节省时间提高效率,还能提高你作品的质量并享受日常工作.我们要时刻留心,发现并学习新的工具.一开始可能需要一些努力,但你总会意识到付出的时间是值得的.
我很想知道对你帮助最大的开发工具.可以在下面评论留言,或在tweet关注我们[@gostormpath](https://twitter.com/gostormpath)
