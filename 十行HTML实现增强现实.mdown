原文[Augmented Reality in 10 Lines of HTML](https://medium.com/arjs/augmented-reality-in-10-lines-of-html-4e193ea9fdbf)
#十行HTML代码实现增强现实

Do you want to do Augmented Reality on the web ? You can now do it in 10 lines of HTML! Seriously! Let me walk you thru the code, it’s crazy simple.

你想通过网络实现增强现实吗？现在你只需要 10 行 HTML 代码！真的！让我带你看一看代码，非常简单。

We released AR.js recently. You can experience efficient augmented reality for the web directly on your phone without installing any applications. But let’s go further and see how to produce your own augmented reality experience. The shortest example of AR.js is only 10lines of HTML thanks to the magic of a-frame. You can see it live on codepen:

我们最近发布了[AR.js](https://github.com/jeromeetienne/ar.js)。你不需要安装任何应用，用你的手机通过网络就能体验到强大的增强现实。但让我们更进一步，看一下如何让你也创作出自己的增强现实体验。多亏了神奇的[a-frame](https://aframe.io/)，最短的 AR.js 只有 10 行 HTML 代码。你可以在[codepen](https://codepen.io/jeromeetienne/pen/mRqqzb)看到在线版：

```html
<!-- Augmented Reality on the Web in 10 lines of html! https://github.com/jeromeetienne/ar.js --> 
<script src="https://aframe.io/releases/0.5.0/aframe.min.js"></script>
<script src="https://rawgit.com/jeromeetienne/ar.js/master/aframe/build/aframe-ar.js"></script>
<script>THREEx.ArToolkitContext.baseURL = 'https://rawgit.com/jeromeetienne/ar.js/master/three.js/'</script>
<body style='margin : 0px; overflow: hidden;'>
    <a-scene embedded artoolkit='sourceType: webcam;'>
        <a-box position='0 0.5 0' material='opacity: 0.5;'></a-box>
        <a-marker-camera preset='hiro'></a-marker-camera>
    </a-scene>
</body>
```

Let’s break it down line by line.

我们来一行一行看.

##Including the Libraries
##引入库

```html
<script src="https://aframe.io/releases/0.5.0/aframe.min.js"></script>
<script src="https://rawgit.com/jeromeetienne/ar.js/master/aframe/build/aframe-ar.js"></script>
<script>THREEx.ArToolkitContext.baseURL = 'https://rawgit.com/jeromeetienne/ar.js/master/three.js/'</script>
```

First, you include a-frame, an effort started by MozillaVR to build VR experiences. A-frame contains three.js. Then you simply include AR.js for a-frame. AR.js will make the 3d displayed in AR run very fast on your phone, even if it’s a 2–3 year old phone!

首先，你需要引入[a-frame](https://aframe.io/)，一款[MozillaVR](https://mozvr.com/)引领的开发 VR 体验的利器。A-frame 包含了 three.js。然后你只需要为 a-frame 引入 AR.js。AR.js能让 AR 中的 3d 显示在你的手机上高速运行，哪怕是 2、3 年前的旧手机。

##Defining the Body
##定义 Body

```html
<body style='margin : 0px; overflow: hidden;'>
    <!-- ... -->
</body>
```

In this step, it’s all business as usual. You define the body, like you would in all HTML pages.

这一步，国际惯例。就像你在所有 HTML 页面中做的一样，定义 body。

##Creating a 3d Scene
##创建 3d 场景

```html
<a-scene embedded artoolkit='sourceType: webcam;'>
    <!-- put your 3d content here -->
</a-scene>
```

Next, we are going to create our a-frame scene. We also need to add the ARToolkit component. ARToolKit is an open-source library that we use to localize it through the phone camera.

然后，我们将要创建我们的 a-farme 场景。我们当然也需要加入 ARToolkit 组件。[ARToolkit](https://artoolkit.org/)是一个开源库，我们通过它来实现摄像头定位。

##Adding Simple Content
##添加简单的内容

```html
<a-box position='0 0.5 0' material='opacity: 0.5;'></a-box>
```

Once we have created the 3d scene, we can start adding objects to it. In this line, we add a simple box. We then modify its material to make it transparent. We also change its position so it displays on top of the AR marker.

一旦我们创建了 3d 场景，我们可以开始向里面添加对象。在这行代码中，我们添加了一个简单的盒子。然后我们修改了它的材质，让它变得透明。我们也改变了它的位置，所以它出现在 AR 标识（AR marker）的上方。

##Adding the AR Camera
##增加 AR 摄像头

```html
<a-marker-camera preset='hiro'></a-marker-camera>
```

In this last step, we are going to add a camera. We include the preset ‘hiro’ (as in Hiro marker). Finally, we make it move as if it were your phone. Easy right?

在最后一步，我们增加一个摄像头。我们预设一个 ‘hiro’（来自[Hiro marker](https://jeromeetienne.github.io/AR.js/data/images/HIRO.jpg)）最后，我们让它像你手机一样移动。是不是很简单？

Congratulations! You are done. You now have augmented reality, in only 10 lines of HTML, running fast on your phone and free of charge.

恭喜！你完成了。你仅用了 10 行 HTML 代码实现了增强现实，手机上也能运行飞快，而且免费。

Check out this video version I made of the tutorial:

可以看一下我做的视频教程（搬运自youtube，字幕为 youtube 自动识别内嵌字幕，有少量识别误差，不影响观看）：


