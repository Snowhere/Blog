# Quora 是如何做持续部署的？
[Continuous Deployment at Quora](https://engineering.quora.com/Continuous-Deployment-at-Quora)

![](https://qph.ec.quoracdn.net/main-qimg-a8cf38d0593825625742f67b9111af72.webp)

Between 12:00 AM and 11:59 PM on April 25, 2013, Quora released new versions of the site 46 times. This was a normal day for us. We run a very fast continuous-deployment cycle, where changes are pushed live as soon as they are committed. This is made possible by parallelization on several levels. We wanted our push system to be fast, so developers can see their changes in production as soon as possible (currently it takes six to seven minutes on average for a revision to start running in production), but it should also be reliable and flexible, so we can respond to problems quickly.

在2013年4月25日中午12点到晚上11点59分之间，Quora 发布

From the developer's side, only a single command is required to push code to production: git push

What goes on behind the scenes is more complicated. Every time a developer pushes commits to our main git repository, a post-receive hook adds the latest revision to the list of release candidates, which is maintained in a MySQL database. (This post-receive hook also adds the commit to Phabricator, which we use for code reviews. For more information on our code review process, see Does Quora engineering use a code review process?) An internal monitor webpage shows the status of each revision that's waiting to be released.

A backend service watches this list, and when a new revision is committed, it collects the names of all the unit tests in the codebase as of that revision. We have hundreds of test modules containing thousands of individual tests, so this service distributes the tests among several test worker machines to be run in parallel. When the workers are finished running tests, they report the results back to the test service, and it marks the revision's overall result (passed or failed, as well as how many tests failed and what the failures were, if applicable) on the list of release candidates.

Simultaneously, another service also watches the release-candidate list, waiting for new revisions to package. When a new revision is committed, it generates an archive of all of the code that needs to run on our webservers, and uploads this package to Amazon S3.

When packaging is complete for a revision, an integration-testing service pushes that revision to a single machine (not in production), starts a web server using the new package, and makes some requests to this webserver. The integration test passes only if each request returns a 200 status code; if any request returns a 4xx or 5xx error code, then the test has failed.

Finally, a fourth service watches the release-candidate list for revisions that are being processed by the other three services, and when a revision has been tested and packaged without any problems, it marks the revision for deployment (release) by uploading a small metadata file containing the revision ID to S3. Web servers and other machines that use the same code periodically check the active revision ID in that metadata file, and when it changes, they begin to download the new package from S3 immediately. Downloading and decompressing the package takes about a minute, then running the new code takes only a few more seconds. This happens independently on every machine that uses this code. We named this system Zerg, since the deployment process is kind of like a zerg rush on all the machines that use our main package.

This diagram summarizes the backend architecture:



This system is very resilient. Failures are rare, but as with any complex system, they do happen from time to time. Maybe a test worker goes down, or the test service fails, or a packager has problems. By construction, internal failures cannot cause consistency problems (such as pushing code to production that failed unit tests), and in most cases, simply restarting the failed machine or service will suffice to get it working again.

For most revisions, the time between git push and release is about six minutes, limited by the length of the longest task. Currently, unit testing is the longest task; packaging takes 2-3 minutes and integration testing takes just over 3 minutes. Over the following ten minutes (after release), machines will download and run the new code, but in the meantime, later revisions begin testing and packaging. (We don't update all machines at once since that would make Quora unavailable for a few minutes every time we release!) This 10-minute window was chosen mainly for reliability - if we need to respond to an emergency, it can be overridden to deploy code immediately.

I think we can do even better than 6 minutes for testing + 10 for deployment. There are improvements we can make to the test system's parallelism that will make tests faster in general. In addition, we’ve eliminated some inefficiencies in other services that should allow us to reduce the deployment window from 10 to 5 minutes.

This system design was motivated primarily by problems that other companies have faced with deployment. We decided early on in the company's history to use continuous deployment. It was easy to do this when the company, codebase, and infrastructure were small, but we've worked hard to maintain the process over the years because it plays such an important role in our development process and culture:
It allows us to get product changes into the hands of users as quickly as possible, including everything from bug fixes to major features.
It allows us to more quickly isolate and fix problems that occur. Bugs happen, but would you rather debug one commit, or one hundred that have been batched together into a monolithic release?
It decreases the mental overhead of making small improvements to the site. We didn't really appreciate this until we experienced it - it's empowering to see a problem, make a quick fix, push it, and have it running in production in minutes. The longer the push process takes, the more likely your mind pushes back with "do I really have an hour free to sit and track this push for errors?" Worse, if it's staged until tomorrow, "do I really want to think about this again and test it tomorrow?"
It decreases the development overhead of tracking multiple versions in different release states. There's no question whether something is in production or not, or has been fixed in a release candidate but not pushed yet.
It forces us into a testing mindset. Tests are important for many reasons, but with the additional pressure of changes going live immediately, there’s no room for thinking “I’ll write the tests later.” We write tests early and often.
It's more fun! Coding is fun, and our deployment process shouldn't make it less so.

By decreasing the time spent in processing each revision and emphasizing testing, we increased the number of revisions we can push each day and drastically lowered the barrier to change, which is exactly what a fast-moving startup like Quora wants to do.