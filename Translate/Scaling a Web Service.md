原文[https://blog.vivekpanyam.com/scaling-a-web-service-load-balancing/](https://blog.vivekpanyam.com/scaling-a-web-service-load-balancing/)

# Scaling a Web Service: Load Balancing
# 大规模 Web 服务：负载均衡

This post is going to look at one aspect of how sites like Facebook handle billions of requests and stay highly available: load balancing.

Facebook 这类网站如何处理数十亿请求并保持高可用性呢，答案是负载均衡，本文将对其一探究竟。

## What is a load balancer?
## 什么是负载均衡

A load balancer is a device that distributes work across many resources (usually computers). They are generally used to increase capacity and reliability.

负载均衡是许多协同工作资源（通常是计算机）的分配策略。它们通常用于提高容量和可靠性。

In order to talk about load balancing in a general way, I make two assumptions about the service being scaled:

为了便于讨论负载均衡，对于服务扩展我假设以下两点：

* I can start as many instances of it as I want
* Any request can go to any instance

* 我可以运行任意数量实例
* 任何请求可以到达任意实例

The first assumption means that the service is stateless (or has shared state in something like a Redis cluster). The second assumption is not necessary in practice (because of things like sticky load balancing), but I assume it for the sake of simplicity in this post.

第一个假设表明服务是无状态的（或者像 Redis 集群一样可以共享状态）。第二个假设实际中并不是必须的（比如粘性负载均衡），但在这片文章中做这样的假设便于讨论。

Here are the load balancing techniques I'm going to talk about:

下面是我将要讨论的负载均衡技术：

1. Layer 7 load balancing (HTTP, HTTPS, WS)
2. Layer 4 load balancing (TCP, UDP)
3. Layer 3 load balancing
4. DNS load balancing
5. Manually load balancing with multiple subdomains
6. Anycast
        
1. 应用层（OSI 第七层）负载均衡（HTTP、HTTPS、WS）
2. 传输层（OSI 第四层）负载均衡（TCP、UDP）
3. 网络层（OSI 第三层）负载均衡
4. DNS 负载均衡
5. 多个子域手动负载均衡
6. 任播（Anycast）

and some miscellaneous topics at the end:

最后还有一些其他知识点：

* Latency and Throughput
* Direct Server Return

* 延迟和吞吐量
* 服务器直接返回

These techniques are vaguely ordered as "steps" to take as a site gets more traffic. For example, Layer 7 load balancing would be the first thing to do (much earlier than Anycast). The first three techniques help with throughput and availability, but still have a single point of failure. The remaining three techniques remove this single point of failure while also increasing throughput.

这些技术大致按照「网站流量增大时需要的操作步骤」排序。比如，应用层的负载均衡是首先要做的事（远远早于任播）。前三条技术提高吞吐量和可用性，但存在单点故障。其余的三条技术在提高吞吐量的同时避免了单点故障。

To help us understand load balancing, we're going to look at a simple service that we want to scale.

为了帮助我们理解负载均衡，我们来看一个简单的服务扩展。

Note: Each scaling technique is accompanied by a non-technical analogy to purchasing items at a store. These analogies are a simplified representation of the idea behind the technique and may not be entirely accurate.

注意：每种扩展技术都采用了非技术的比喻（在商店购物）。这些比喻仅仅是描述技术背后的思想，并不完全准确。

## The service
## 服务

Let's pretend we're building a service that we want to scale. It might look something like this:

我们假设我们正在构建大规模的服务。就像下图所示：

![](https://blog.vivekpanyam.com/content/images/2016/02/Service-1.png)

This system won't be able to handle a lot of traffic and if it goes down, the whole application goes down.

这个系统并不能处理大量通信，并且如果它宕机，整个应用就停了。

### Analogy:

### 比喻：

* You go to the only checkout line in the store
* You purchase your items
    If there isn't a cashier there, you can't make your purchase

* 你走向商店唯一一条结账的队伍。
* 你购买你的商品，如果那里没有收银员，你无法完成购买。

## Layer 7 load balancing
## 应用层（OSI 第七层）负载均衡

The first technique to start handling more traffic is to use Layer 7 load balancing. Layer 7 is the application layer. This includes HTTP, HTTPS, and WebSockets. A very popular and battle-tested Layer 7 load balancer is Nginx. Let's see how that'll help us scale up:

为了承载更大的通信量，首先用到的就是应用层负载均衡。应用层是 OSI 第七层。它包括 HTTP、HTTPS 和 WebSockets。一款非常流行又久经考验的应用层负载均衡器就是 Nginx。让我们看看它如何帮助我们扩展服务：

![](https://blog.vivekpanyam.com/content/images/2016/02/L7LB.png)

Note that we can actually load balance across tens or hundreds of service instances using this technique. The image above just shows two as an example.

请注意，通过这种技术，我们能负载均衡数十或上百服务器实例。上面图片只展示两个作为例子。

### Analogy:
### 比喻

* An employee of the store directs you to a specific checkout line (with a cashier)
* You purchase your items

* 商店员工引领你到一个特别的（有收银员的）结账队伍
* 你购买你的商品

### Tools:
### 工具：

* Nginx
* HAProxy

* Nginx
* HAProxy

### Notes:
### 注意：

* This is also where we'd terminate SSL

* 我们要在这里停止使用 SSL（分发时不用 SSL)

## Layer 4 load balancing
## 传输层（OSI 第四层）负载均衡（TCP、UDP）

The previous technique will help us handle a lot of traffic, but if we need to handle even more traffic, Layer 4 load balancing could be helpful. Layer 4 is the transport layer. This includes TCP and UDP. Some popular Layer 4 load balancers are HAProxy (which can do Layer 7 load balancing as well) and IPVS. Let's see how they'll help us scale up:

上一条技术帮助我们承载大量通信，但如果我们需要承载更大的通信量，传输层负载均衡非常有用。传输层是 OSI 第四层，包括 TCP 和 UDP。流行的传输层负载均衡器有 HAProxy（这个也用于应用层负载均衡）和 IPVS。让我们看看它们如何帮助我们扩展服务：

![](https://blog.vivekpanyam.com/content/images/2016/02/L4LB.png)

Layer 7 load balancing + Layer 4 load balancing should be able to handle more than enough traffic for most cases. However, we still need to worry about availability. The single point of failure here is the layer 4 load balancer. We'll fix this in the DNS load balancing section below.

应用层负载均衡+传输层负载均衡能处理大多数情况下的通信量。然而我们我们仍要担心可用性。单点故障有可能出现在传输层的负载均衡器。我们在下面一节的 DNS 负载均衡解决这个问题。

### Analogy:
### 比喻：

* There are separate checkout areas for people based on their membership number
    For example, if your membership number is divisible by two, go to the checkout near electronics, otherwise go to the one near food
* Once you get to the right checkout area, an employee of the store directs you to a specific checkout line
* You purchase your items

* 根据客户会员卡卡号有不同结账区域。举个例子，如果你的会员卡卡号是偶数，去电器区附近的结账台，否则就去食品区附近的结账台。
* 一旦你到达正确的结账区域，商店员工引领你到一个特别的结账队伍
* 你购买你的商品

### Tools:
### 工具：

* HAProxy
* IPVS

* HAProxy
* IPVS

## Layer 3 load balancing
## 网络层（OSI 第三层）负载均衡

If we need to scale up even more, we probably should add Layer 3 load balancing. This is more complicated than the first two techniques. Layer three is the network layer, which includes IPv4 and IPv6. Here's how Layer 3 load balancing could look:

如果我们要继续扩展，我们需要增加网络层负载均衡。这比上面两条技术更复杂。网络层是 OSI 第三层，包括 IPv4 和 IPv6。下面是网络层负载均衡：

![](https://blog.vivekpanyam.com/content/images/2016/02/L3LB.png)

To understand how this works, we first need a little background on ECMP (equal cost multi path routing). ECMP is generally used when there are multiple equal cost paths to the same destination. Very broadly, it allows the router or switch to send packets to the destination over different links (allowing for higher throughput).

为了搞清楚它如何工作，我们需要一点等价路由的知识（ECMP）。当有多条等价链路到达相同地址时，我们使用等价路由。简单来说，它允许路由器或交换机通过不同链接发送数据包（支持高吞吐量），最终到达同一地址。

We can exploit this to do L3 load balancing because, from our perspective, every L4 load balancer is the same. This means we can treat each link between the L3 load balancer and a L4 load balancer as a path to the same destination. If we give all of these load balancers the same IP address, we can use ECMP to split our traffic amongst all the L4 load balancers.

我们可以利用这一点来实现网络层负载均衡，因为在我们看来，每个传输层负载均衡器是相同的。这意味着我们可以把从网络层负载均衡器到传输层负载均衡器的链接看做相同目的地的链路。如果我们把所有负载均衡器绑定到相同 IP 地址，我们可以使用等价路由在传输层负载均衡器之间分配通信。

### Analogy:
### 比喻：

* There are two separate identical stores across the street from each other. The one you go to depends on your dominant hand
* Once you get to the right store, there are separate checkout areas for people based on their membership number
    For example, if your membership number is divisible by two, go to the checkout near electronics, otherwise go to the one near food
* Once you get to the right checkout area, an employee of the store directs you to a specific checkout line
* You purchase your items

* 街对面有两家彼此分开却又一模一样的商店，你去哪一家完全取决于你的习惯。
* 一旦你到达了商店，根据客户会员卡卡号有不同结账区域。举个例子，如果你的会员卡卡号是偶数，去电器区附近的结账台，否则就去食品区附近的结账台。
* 一旦你到达正确的结账区域，商店员工引领你到一个特别的结账队伍
* 你购买你的商品

### Tools:
### 工具：

* This is usually done in hardware with top-of-rack switches
* 通常在机柜里交换机内部的硬件中处理。

### TL;DR:
### 太长不阅：

* Unless you're running at a huge scale or have your own hardware, you don't need to do this

* 除非你的服务规模相当大或有自己的硬件，否则你不需要它。

## DNS load balancing
## DNS 负载均衡

DNS is the system that translates names to IP addresses. For example, it may translate example.com to 93.184.216.34. It can also return multiple IP addresses as shown below:

DNS 是将名称转换为 IP 地址的系统。举个例子，它可以把 example.com 转换为 93.184.216.34 。它当然也可以返回多个 IP 地址，像下面这样：

![](https://blog.vivekpanyam.com/content/images/2016/02/Screen-Shot-2016-02-04-at-3-25-32-AM.png)

If multiple IPs are returned, the client will generally use the first one that works (however, some implementations will only look at the first returned IP).

如果返回了多个 IP，客户端通常会使用第一个可用的地址（然而一些应用只看第一个返回的 IP）。

There are many DNS load balancing techniques including GeoDNS and round-robin. GeoDNS returns different responses based on who requests it. This lets us route clients to the server or datacenter that's closest to them. Round-robin returns different IPs for each request, cycling through all the available IP addresses. If multiple IPs are available, both of these techniques just change the ordering of the IPs in the response.

目前有很多 DNS 负载均衡技术，比如 GeoDNS 和轮询调度（round-robin）。GeoDNS 基于不同请求者而返回不同响应。这让我们可以将客户端路由到其最近的服务器或数据中心。轮询调度会循环所有可用的 IP 地址，对于每个响应会返回不同的 IP。如果多个 IP 可用，这两种技术仅仅改变响应里的 IP 顺序。

Here's how DNS load balancing would work:

下图展示 DNS 负载均衡如何工作：

![](https://blog.vivekpanyam.com/content/images/2016/02/DNS.png)

In this example, different users are being routed to different clusters (either randomly or based on their location).

在这个例子中，不同的用户被路由到不同的服务集群（随机或基于地理位置）。

Now there isn't a single point of failure (assuming there are multiple DNS servers). To be even more reliable, we can run multiple clusters in different datacenters.

现在这里不再有单点故障的可能性（假设有多台 DNS 服务器）。为了进一步提高可靠性，我们可以在不同数据中心运行多个服务集群。

### Analogy:
### 比喻：

* You check online for a list of shopping complexes the store operates in. The list puts the closest shopping complexes first. You look up directions to each one and go to the first open one in the list.
* There are two separate identical stores across the street from each other. The one you go to depends on your dominant hand
* Once you get to the right store, there are separate checkout areas for people based on their membership number
    For example, if your membership number is divisible by two, go to the checkout near electronics, otherwise go to the one near food
* Once you get to the right checkout area, an employee of the store directs you to a specific checkout line
* You purchase your items

* 你在网上查询购物中心，返回的列表把最近的购物中心放在第一个。你查看通往每个购物中心的路，然后选择列表中第一个营业的购物中心。
* 街对面有两家彼此分开却又一模一样的商店，你去哪一家完全取决于你的习惯。
* 一旦你到达了商店，根据客户会员卡卡号有不同结账区域。举个例子，如果你的会员卡卡号是偶数，去电器区附近的结账台，否则就去食品区附近的结账台。
* 一旦你到达正确的结账区域，商店员工引领你到一个特别的结账队伍
* 你购买你的商品

## Manual load balancing/routing
## 手动负载均衡和路由

If our content is sharded across many servers or datacenters and we need to route to a specific one, this technique could be helpful. Let's say cat.jpg is stored in a cluster in London, but not any other clusters. Similarly, let's say dog.jpg is stored in NYC, but not in any other datacenters or clusters. This might happen when the content was just uploaded and hasn't been replicated across datacenters yet, for example.

如果你的内容在许多数据中心或服务间共享，而我们需要路由到其中特定的一个，那么这条技术就很有用了。比如 `cat.jpg` 储存在伦敦的集群中，但其他集群中没有。相似的，`dog.jpg` 储存在纽约的集群中，其他数据中心或集群中没有。举个例子，这很可能发生在内容刚刚上传，还未在数据中心之间复制的时候。

However, users shouldn't have to wait for replication to complete in order to access the content. This means our application will need to temporarily direct all requests for cat.jpg to London and all requests for dog.jpg to NYC. So instead of https://cdn.example.net/cat.jpg we want to use https://lon-1e.static.example.net/cat.jpg. Similarly for dog.jpg.

然而，用户获取内容时不应该等待复制完成。这意味着我们的应用需要临时把所有 `cat.jpg` 请求发送到伦敦，所有 `dog.jpg` 请求发送到纽约。所以我们需要用 `https://lon-1e.static.example.net/cat.jpg` 代替 `https://cdn.example.net/cat.jpg`。对 `dog.jpg` 来说也一样。

To do this, we need to set up subdomains for each datacenter (and preferably each cluster and each machine). This can be done in addition to the DNS load balancing above.

为了实现这一点，我们需要为每个数据中心设置子域（最好细分到每个集群每台机器）。除了上面的 DNS 负载均衡，这一点也很有必要。

Note: Our application will need to keep track of where the content is in order to do this rewriting.

注意：我们的应用需要保持追踪内容的位置，以便重写请求。

### Analogy:
### 比喻：

* You call the corporate office asking which locations carry cat food.
* You look up directions to the locations and go to the first open one in the list
* There are two separate identical stores across the street from each other. The one you go to depends on your dominant hand
* Once you get to the right store, there are separate checkout areas for people based on their membership number
    For example, if your membership number is divisible by two, go to the checkout near electronics, otherwise go to the one near food
* Once you get to the right checkout area, an employee of the store directs you to a specific checkout line
* You purchase your items

* 你拨打公司电话询问哪个购物中心提供猫粮。
* 你查看列表上的购物中心路线，然后选择第一个营业的。
* 街对面有两家彼此分开却又一模一样的商店，你去哪一家完全取决于你的习惯。
* 一旦你到达了商店，根据客户会员卡卡号有不同结账区域。举个例子，如果你的会员卡卡号是偶数，去电器区附近的结账台，否则就去食品区附近的结账台。
* 一旦你到达正确的结账区域，商店员工引领你到一个特别的结账队伍
* 你购买你的商品

## Anycast
## 任播（Anycast）

The final technique in this post is Anycast. First a little background:

这篇文章讨论的最后一种技术就是任播。首先来看一点背景知识：

Most of the internet uses Unicast. This essentially means each computer gets a unique IP address. There is another methodology called Anycast. With Anycast, multiple machines can have the same IP address and routers send requests to the closest one. We can combine this with the above techniques to have an extremely reliable and available system that can handle a lot of traffic.

大多数网路使用单播。这本质上意味着每台计算机拥有独一无二的 IP 地址。有另一种称为任播的理论。通过任播，一些机器可以使用相同的 IP 地址和路由，并把请求发送到最近的一台机器。我们可以把这种技术和上面所讲的技术结合起来，构建出高可靠性和可用性，能承载巨大通信量的系统。

Anycast basically allows the internet to handle part of the load balancing for us.

任播根本上来说是允许互联网为我们处理部分负载均衡。

### Analogy:
### 比喻：

* You tell people you're trying to go to the store and they direct you to the closest location
* There are two separate identical stores across the street from each other. The one you go to depends on your dominant hand
* Once you get to the right store, there are separate checkout areas for people based on their membership number
    For example, if your membership number is divisible by two, go to the checkout near electronics, otherwise go to the one near food
* Once you get to the right checkout area, an employee of the store directs you to a specific checkout line
* You purchase your items

* 你告诉别人你打算去商店，他们把你带到最近的位置。
* 街对面有两家彼此分开却又一模一样的商店，你去哪一家完全取决于你的习惯。
* 一旦你到达了商店，根据客户会员卡卡号有不同结账区域。举个例子，如果你的会员卡卡号是偶数，去电器区附近的结账台，否则就去食品区附近的结账台。
* 一旦你到达正确的结账区域，商店员工引领你到一个特别的结账队伍
* 你购买你的商品

## Misc.
## 杂项

### Latency and Throughput
### 延迟和吞吐量

As an aside, these techniques also work to increase the throughput of a low latency service. Instead of trying to make the service itself handle more traffic, add more of them. This way we'll have a low-latency, high-throughput system.

顺便一提的是，这些技术也可以提升低延迟服务的吞吐量。增加服务数量而不是让每个服务处理更多的通信。这样我们就可以得到低延迟、高吞吐量的系统。

### Direct server return
### 服务器直接返回

In a traditional load-balancing system, the request goes through all the layers of load balancing and the response returns through all of these layers as well. One optimization that can offload a lot of the traffic from the load balancers is direct server return. This means that the response from a server doesn't go back through the load balancers. If responses from the service are large, this is a very useful tool.

在传统负载均衡系统中，请求穿过负载均衡的所有层级，响应也同样穿过它们。降低负载均衡通信量的一个优化点就是服务器直接返回。这意味着服务端的响应不通过负载均衡。如果服务端的响应十分巨大，这点尤其有用。