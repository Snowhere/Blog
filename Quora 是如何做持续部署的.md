[Continuous Deployment at Quora](https://engineering.quora.com/Continuous-Deployment-at-Quora)

# Quora 是如何做持续部署的？

![](https://qph.ec.quoracdn.net/main-qimg-a8cf38d0593825625742f67b9111af72.webp)

Between 12:00 AM and 11:59 PM on April 25, 2013, Quora released new versions of the site 46 times. This was a normal day for us. We run a very fast continuous-deployment cycle, where changes are pushed live as soon as they are committed. This is made possible by parallelization on several levels. We wanted our push system to be fast, so developers can see their changes in production as soon as possible (currently it takes six to seven minutes on average for a revision to start running in production), but it should also be reliable and flexible, so we can respond to problems quickly.

在 2013 年 4 月 25 日中午 12 点到晚上 11 点 59 分之间，Quora 站点发布了 46 次新版本。这对于我们来说只是普通的一天。我们执行非常快的持续部署周期，代码变动提交后就直接推送到线上。这使得在各个层面上实现平行化开发。我们希望我们的推送系统足够快，让开发者尽快看到他们对生产环境的改动（目前生产环境修订版上线平均要 6、7 分钟），单也要注意可靠性和灵活性，让我们可以迅速响应问题。

From the developer's side, only a single command is required to push code to production: git push

对开发者而言，只需要一个简单的命令来推送代码到生产环境：`git push`

What goes on behind the scenes is more complicated. Every time a developer pushes commits to our main git repository, a post-receive hook adds the latest revision to the list of release candidates, which is maintained in a MySQL database. (This post-receive hook also adds the commit to Phabricator, which we use for code reviews. For more information on our code review process, see Does Quora engineering use a code review process?) An internal monitor webpage shows the status of each revision that's waiting to be released.

这背后发生的事情要复杂很多。每当一个开发者把提交推送到我们的主 git 仓库，一个 post-receive 钩子会将最新的修订版加入到发版申请列表，并记录在 MySQL 数据库。（post-receive 钩子也会把提交加到 [Phabricator](https://www.quora.com/topic/Phabricator)，我们用它做代码评审。更多关于我们代码评审的相关信息参阅这个回答 [Does Quora engineering use a code review process?](https://www.quora.com/Does-Quora-engineering-use-a-code-review-process)）一个内部监控网站展示每个等待发布的修订版的状态。

A backend service watches this list, and when a new revision is committed, it collects the names of all the unit tests in the codebase as of that revision. We have hundreds of test modules containing thousands of individual tests, so this service distributes the tests among several test worker machines to be run in parallel. When the workers are finished running tests, they report the results back to the test service, and it marks the revision's overall result (passed or failed, as well as how many tests failed and what the failures were, if applicable) on the list of release candidates.

一个后端服务监控发版申请列表，每当提交新的修订版，服务收集此版本代码库中所有单元测试的名字。我们有上百个测试模块和上千个独立的测试，服务会将测试分配到一些 worker 机器中并行处理。当 worker 运行完测试，它们将结果返回给测试服务，服务在发版申请的列表中标记修订版的综合结果（成功或失败，以及多少个测试失败了和失败的详情）。

Simultaneously, another service also watches the release-candidate list, waiting for new revisions to package. When a new revision is committed, it generates an archive of all of the code that needs to run on our webservers, and uploads this package to Amazon S3.

同时，另一个服务监控发版申请列表，等待打包新修订版。每当提交新的修订版，它将所有需要运行在我们服务器上的代码进行归档，并打包上传到 Amazon S3。

When packaging is complete for a revision, an integration-testing service pushes that revision to a single machine (not in production), starts a web server using the new package, and makes some requests to this webserver. The integration test passes only if each request returns a 200 status code; if any request returns a 4xx or 5xx error code, then the test has failed.

当修订版打包好后，一个集成测试服务将修订版推送到一台单独的机器（并不是生产环境），用新的包开启 web 服务，并向服务发送请求。只有每个请求返回 200 状态码，集成测试才算通过，如果任何请求返回 4xx 或 5xx 错误码，测试失败。

Finally, a fourth service watches the release-candidate list for revisions that are being processed by the other three services, and when a revision has been tested and packaged without any problems, it marks the revision for deployment (release) by uploading a small metadata file containing the revision ID to S3. Web servers and other machines that use the same code periodically check the active revision ID in that metadata file, and when it changes, they begin to download the new package from S3 immediately. Downloading and decompressing the package takes about a minute, then running the new code takes only a few more seconds. This happens independently on every machine that uses this code. We named this system Zerg, since the deployment process is kind of like a zerg rush on all the machines that use our main package.

最后，第四个监控发版申请列表的服务由其他三个服务处理，当修订版的测试和打包没有问题，服务向 S3 上传一个包含版本号的小型元数据文件，来标记修订版的部署（发布）。Web 服务器和其他使用相同代码的机器会周期性检查元数据文件中的版本号，如果有变化，它们会立刻从 S3 上下载最新的包。下载并解压包大概需要一分钟，然后运行新的代码只需要几秒。每台需要代码的机器会独立完成上述操作。我们将这个系统命名为 [Zerg](https://www.quora.com/What-is-StarCraft-1998-game-culture-like-at-Quora)，因为在所有需要包的机器上进行部署过程很像虫族（zerg）的 rush 战术。

This diagram summarizes the backend architecture:

下图描述了后端架构：

![](https://qph.ec.quoracdn.net/main-qimg-23dc53ea77e8719676d9fac84766dfe3.webp)

This system is very resilient. Failures are rare, but as with any complex system, they do happen from time to time. Maybe a test worker goes down, or the test service fails, or a packager has problems. By construction, internal failures cannot cause consistency problems (such as pushing code to production that failed unit tests), and in most cases, simply restarting the failed machine or service will suffice to get it working again.

这套系统弹性很大，很少出现失败，但正如其他复杂的系统一样，也会出现失败情况。要么测试 worker 宕机，要么测试服务失败，要么打包程序出现问题。通过这种架构，内部的失败并不会引起一致性问题（比如把未通过单元测试的代码推送到生产环境），并且大多数时候只需要重启失败的机器或服务便可以让其正常工作。

For most revisions, the time between git push and release is about six minutes, limited by the length of the longest task. Currently, unit testing is the longest task; packaging takes 2-3 minutes and integration testing takes just over 3 minutes. Over the following ten minutes (after release), machines will download and run the new code, but in the meantime, later revisions begin testing and packaging. (We don't update all machines at once since that would make Quora unavailable for a few minutes every time we release!) This 10-minute window was chosen mainly for reliability - if we need to respond to an emergency, it can be overridden to deploy code immediately.

对于大多数修订版，git push 到发布大约间隔 6 分钟，这取决于其中执行时间最长的任务。目前，单元测试是时间最长的任务；打包需要 2-3 分钟，集成测试需要 3 分钟多一点。之后 10 分钟（发布之后），机器下载运行新代码，同时后面的修订版开始测试和打包。（我们不会同一时间更新所有机器，这会导致每次我们发布时，Quora 会有几分钟处于不可用状态！）

I think we can do even better than 6 minutes for testing + 10 for deployment. There are improvements we can make to the test system's parallelism that will make tests faster in general. In addition, we’ve eliminated some inefficiencies in other services that should allow us to reduce the deployment window from 10 to 5 minutes.

This system design was motivated primarily by problems that other companies have faced with deployment. We decided early on in the company's history to use continuous deployment. It was easy to do this when the company, codebase, and infrastructure were small, but we've worked hard to maintain the process over the years because it plays such an important role in our development process and culture:
It allows us to get product changes into the hands of users as quickly as possible, including everything from bug fixes to major features.
It allows us to more quickly isolate and fix problems that occur. Bugs happen, but would you rather debug one commit, or one hundred that have been batched together into a monolithic release?
It decreases the mental overhead of making small improvements to the site. We didn't really appreciate this until we experienced it - it's empowering to see a problem, make a quick fix, push it, and have it running in production in minutes. The longer the push process takes, the more likely your mind pushes back with "do I really have an hour free to sit and track this push for errors?" Worse, if it's staged until tomorrow, "do I really want to think about this again and test it tomorrow?"
It decreases the development overhead of tracking multiple versions in different release states. There's no question whether something is in production or not, or has been fixed in a release candidate but not pushed yet.
It forces us into a testing mindset. Tests are important for many reasons, but with the additional pressure of changes going live immediately, there’s no room for thinking “I’ll write the tests later.” We write tests early and often.
It's more fun! Coding is fun, and our deployment process shouldn't make it less so.

By decreasing the time spent in processing each revision and emphasizing testing, we increased the number of revisions we can push each day and drastically lowered the barrier to change, which is exactly what a fast-moving startup like Quora wants to do.