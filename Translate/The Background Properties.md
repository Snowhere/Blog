原文[The Background Properties](https://bitsofco.de/the-background-properties/)
(译文发布在[伯乐在线](http://web.jobbole.com/90616/)，感谢[刘唱](http://www.jobbole.com/members/liuchang/)校稿)

#The Background Properties
#CSS 的 background 属性

As I have mentioned before, every element in the document tree is just a rectangular box. Each of these boxes has a background layer that can either be fully transparent, a colour, or an image. This background layer is controlled by 8 CSS properties (plus 1 shorthand property).

正如我之前所说，文档树中的元素都是一个方盒。每个盒都有背景层，它是透明的、有颜色的或一张图片。背景层由 8 个 CSS 属性（和 1 个简写属性）控制

##background-color

The background-color property sets a background colour for the element. It's value can be any accepted colour value, or the transparent keyword.

`background-color` 属性设置元素背景颜色。它的值可以是一个合法的颜色值或 `transparent` 关键字。

``` css
.left { background-color: #ffdb3a; }
.middle { background-color: #67b3dd; }
.right { background-color: transparent; }
```

![](https://bitsofco.de/content/images/2016/06/color.png)

The background colour is drawn within the area of the box model specified by the background-clip property. If any background images are also set, the colour layer is drawn behind them. Unlike image layers which can be multiple, we can have only one colour layer for an element.

在由 `background-clip` 关键字指定的盒模型区域内填充背景颜色。如果也设置了背景图片，颜色层会在它们后面。图片层可以设置多个，每个元素只拥有一个颜色层。

##background-image

The background-image property defines a background image (or images) for the element. It's value is typically a url to an image defined with the url() notation. A value of none can also be used, which will count as a layer, but an empty one.

`background-image` 属性为元素定义一个（或多个）背景图片。它的值是用 `url()` 符号定义的图片 url。`none` 也是允许的，它会被当做空的一层。

``` css
.left { background-image: url('ire.png'); }
.right { background-image: none; }
```

![](https://bitsofco.de/content/images/2016/06/image-1.png)

We can also specify multiple background images, each separated by a comma. Each subsequent image drawn is placed behind the previous on the z-axis.

我们也可以指定多个背景图片，用逗号隔开。沿着 z 轴从前向后依次绘制每个图片。

``` css
.middle { 
  background-image: url('khaled.png'), url('ire.png');

  /* Other styles */
  background-repeat: no-repeat; 
  background-size: 100px;
}
```

![](https://bitsofco.de/content/images/2016/06/image-2.png)

##background-repeat

The background-repeat property controls how a background image is tiled after it has been sized (by the background-size property) and positioned (by the background-position property).

在 `background-size` 指定大小和 `background-position` 指定位置后，`background-repeat` 属性控制如何平铺背景图片。

The value for this property can be one of the following keywords - repeat-x, repeat-y, repeat, space, round, no-repeat. Besides the first two (repeat-x and repeat-y), the other values can be defined either once for both the x-axis and y-axis, or each dimension individually.

属性值可以是下面关键字之一 `repeat-x`、`repeat-y`、`repeat`、`space`、`round`、`no-repeat`。除了前两个（`repeat-x`和`repeat-y`）之外，其他关键字可以只写一次来同时定义 x 轴和 y 轴，或分开定义两个维度。

``` css
.top-outer-left { background-repeat: repeat-x; }
.top-inner-left { background-repeat: repeat-y; }
.top-inner-right { background-repeat: repeat; }
.top-outer-right { background-repeat: space; }

.bottom-outer-left { background-repeat: round; }
.bottom-inner-left { background-repeat: no-repeat; }
.bottom-inner-right { background-repeat: space repeat; }
.bottom-outer-right { background-repeat: round space; }
```

![](https://bitsofco.de/content/images/2016/06/repeat.png)

##background-size

The background-size property defines the size of the background image. It's value can either be a keyword, a length, or a percentage.

`background-size` 属性定义背景图片的大小。它的值可以是一个关键字、一个长度或一个百分比。

The keywords available for this property are contain and cover. The keyword contain will scale the image to the largest size it can be where both it's height and width can fit within the area. cover, on the other hand, will scale the image to the smallest possible size where entire background area is still covered.

属性可用的关键字是 `contain` 和 `cover`。`contain` 会按比例将图片放大直到宽高完全适应区域。`cover` 会将其调整至能够完全覆盖该区域的最小尺寸。

``` css
.left { 
  background-size: contain;
  background-image: url('ire.png'); 
  background-repeat: no-repeat;
}
.right { background-size: cover; /* Other styles same as .left */ }
```

![](https://bitsofco.de/content/images/2016/06/size-1.png)

For length values and percentage values, we can specify both the width and height of the background image. Percentage values are calculated in relation to the size of the element.

对于长度值和百分比，我们可以用来定义背景图片的宽高。百分比值通过元素的尺寸来计算。

``` css
.left { background-size: 50px; /* Other styles same as .left */ }
.right { background-size: 50% 80%; /* Other styles same as .left */ }
```

![](https://bitsofco.de/content/images/2016/06/size-2.png)

##background-attachment

The background-attachment property controls how the background image scrolls in relation to the viewport and the element. It has three potential values.

`background-attachment` 属性控制背景图片在可视区和元素中如何滚动。它有三个可能的值。

The keyword fixed means that the background image is fixed to the viewport and does not move, even when the user is scrolling along the viewport. local means that the background should be fixed to its position in the element. If the element has a scrolling mechanism and the background image is positioned to the top, as the user scrolls down the element, the background image will scroll out of view. Finally, scroll means that the background image is fixed and will not scroll even with the contents of it's element.

`fixed ` 意思是背景图片相对于可视区固定，即使用户滚动可视区时也不移动。`local` 意思是背景在元素中位置固定。如果元素有滚动机制，背景图片会相对于顶端定位，当用户滚动元素时，背景图片会离开视野。最后，`scroll` 意思是背景图片固定，不会随着元素内容滚动。

``` css
.left { 
  background-attachment: fixed;
  background-size: 50%;
  background-image: url('ire.png'); 
  background-repeat: no-repeat;
  overflow: scroll;
}
.middle { background-attachment: local; /* Other styles same as .left */ }
.right { background-attachment: scroll; /* Other styles same as .left */ }
```

![](https://bitsofco.de/content/images/2016/06/attachment.gif)

##background-position

This property, in combination with the background-origin property, defines where the starting position for the background image should be. It's value can either be a keyword, a length, or a percentage, and we can specify the position along the x-axis as well as the y-axis.

这个属性，结合 `background-origin` 属性，定义了背景图片起始位置。它的值可以是一个关键字、一个长度或一个百分比，我们可以依次定义 x 轴和 y 轴的位置。

The keywords available are top, right, bottom, left, and center. We can use these keywords in any combination, and if only one keyword is specified, the other is presumed to be center.

可用的关键字有`top`、`right`、`bottom`、`left` 和 `center`。我们可以任意组合使用，如果只指定了一个关键字，另一个默认是 `center`。

``` css
.top-left { 
  background-position: top;
  background-size: 50%;
  background-image: url('ire.png'); 
  background-repeat: no-repeat;
}
.top-middle { background-position: right;  /* Other styles same as .top-left */ }
.top-right { background-position: bottom;  /* Other styles same as .top-left */ }
.bottom-left { background-position: left;  /* Other styles same as .top-left */ }
.bottom-right { background-position: center;  /* Other styles same as .top-left */ }
```

![](https://bitsofco.de/content/images/2016/06/position.png)

For length and percentage values, we can also specify the position along the x-axis and y-axis. Percentage values are in relation to the containing element.

对于长度值和百分比值，我们也可以依次定义 x 轴和 y 轴的位置。百分比值相对于容器元素。

``` css
.left { background-position: 20px 70px; /* Others same as .top-left */ }
.right { background-position: 50%; /* Others same as .top-left */ }
```

![](https://bitsofco.de/content/images/2016/06/position-2.png)

##background-origin

The background-origin property specifies which area of the box model the background image should be positioned according to.

`background-origin` 属性定义背景图片根据盒模型的哪个区域来定位。

The values available are border-box, which positions the image based on the Border Area, padding-box, which uses the Padding Area, and content-box, which uses the Content Area.

可用的值有 `border-box`基于边框（Border）区域定位图片，`padding-box` 基于填充（Padding）区域，`content-box` 基于内容（Content）区域。

``` css
.left { 
  background-origin: border-box;
  background-size: 50%;
  background-image: url('ire.png'); 
  background-repeat: no-repeat;
  background-position: top left; 
  border: 10px dotted black; 
  padding: 20px;
}
.middle { background-origin: padding-box;  /* Other styles same as .left*/ }
.right { background-origin: content-box;  /* Other styles same as .left*/ }
```

![](https://bitsofco.de/content/images/2016/06/origin.png)

##background-clip

The background-clip property determines the background painting area, which is the area that the background can be painted onto. Like the background-origin property, it is based on the box model areas.

`background-clip` 属性决定背景绘制区域，也就是背景可以被绘制的区域。像 `background-origin` 属性一样，它也基于盒模型。

``` css
.left{ 
  background-clip: border-box;
  background-size: 50%;
  background-color: #ffdb3a; 
  background-repeat: no-repeat;
  background-position: top left; 
  border: 10px dotted black; 
  padding: 20px;
}
.middle { background-clip: padding-box;  /* Other styles same as .left*/ }
.right { background-clip: content-box;  /* Other styles same as .left*/ }
```

![](https://bitsofco.de/content/images/2016/06/clip.png)

##background

Finally, the background property is a shorthand for the other background related properties. The order of the sub-properties does not matter because the data types for each property are different . However, for the background-origin and background-clip properties, if only one box model area is specified, it is used for both properties. If two are specified, the first sets the background-origin property.

最后，`background` 属性是其他背景相关属性的简写。子属性的顺序并没有影响，因为每个属性的数据类型不同。然而，对于 `background-origin`和`background-clip` 属性，如果只指定了一个盒模型区域，会应用到两个属性。如果指定了两个，第一个设置为`background-origin` 属性。