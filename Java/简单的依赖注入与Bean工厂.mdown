#简单的依赖注入与Bean工厂
我们在写代码时，经常需要在多个controller中引用多个service，比如下面这样。
```java
public class UserController {
    UserService userService = new UserService();
    UserValidate userValidate = new UserValidate();
}
public class CodeController {
    UserService userService = new UserService();
}
```
这时候我才意识到依赖注入的好处，每个service只需要生成一个实例，然后注入到各个controller。
所以我想实现下面这样的效果，需要注解和反射。
```java
public class UserController {
    @DI
    UserService userService;
    @DI
    UserValidate userValidate;
}
```
我们梳理一下思路，首先我们需要有一个`Map`来存储已经实例化的Bean，这样在注入到多个地方的时候，不需重复创建新实例。
其次我们需要标注需要注入的位置，可以在需要注入的字段上用一个注解，比如`@DI`。
最后，我们需要获取字段的类型并创建实例，然后注入到这个字段，这并不复杂，用反射可以搞定。
值得注意的是，有可能有多个层次的注入，比如除了service注入到controller外，service也可能互相注入，我们可以用递归解决。

1.首先建立`Map`
```java
private ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
```
其中key保存类的名字，value保存类的实例

2.声明注解
```java
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DI {
}
```
Target设置的是FIELD，表示只能用在类的变量上。

3.向一个实例中有注解的地方注入
```java
private <T> void inject(T bean) throws IllegalAccessException {
    Class claz = bean.getClass();
    //遍历声明的变量
    for (Field field : claz.getDeclaredFields()) {
        //找到有@DI注解的变量
        if (field.getAnnotation(DI.class) != null) {
            //获取变量的实例（getBean方法后面有提到）
            Object o = getBean(field.getType());
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            //利用反射注入
            field.set(bean,o);
        }
    }
}
```

4.用`Map`管理众多实例，以及递归注入
```java
public <T> T getBean(Class<T> claz) {
    //查看map中是否有该bean的实例
    Object bean = map.get(claz.getName());
    try {
        //如果没有，创建实例，调用刚刚写的inject方法其，注入所需字段
        if (bean == null) {
            bean = claz.newInstance();
            map.put(claz.getName(), bean);
            inject(bean);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return (T)bean;
}
```

上面两个方法`inject()`和`getBean()`相互调用，最终递归完成多层次的注入工作。
当我们需要一个bean的时候只需要用`getBean()`方法,上面的代码可以整合成一个简单的Bean工厂

```java
public class BeanFactory {
    private ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
    private static BeanFactory instance = new BeanFactory();

    public static BeanFactory getInstance() {
        return instance;
    }

    public <T> T getBean(Class<T> claz) {
        Object bean = map.get(claz.getName());
        try {
            if (bean == null) {
                bean = claz.newInstance();
                map.put(claz.getName(), bean);
                inject(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T) bean;
    }

    private <T> void inject(T bean) throws IllegalAccessException {
        Class claz = bean.getClass();
        for (Field field : claz.getDeclaredFields()) {
            if (field.getAnnotation(DI.class) != null) {
                Object o = getBean(field.getType());
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                field.set(bean, o);
            }
        }
    }
}
```

通过`BeanFactory.getInstance().getBean()`来获取一个Bean。

最近在用jFinal框架。似乎里面并没有这种依赖注入的功能，所以写这么一个简单的注入。然后我想办法在框架实例化各个controller的时候，使用这个方法。一直没想出来怎么把这段代码嵌入这个框架，最后发现原来不需要这么复杂。
```java
public abstract class BaseController extends Controller {

    protected Log log = Log.getLog(this.getClass());

    public BaseController() {
        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.getAnnotation(DI.class) != null) {
                Object o = BeanFactory.getInstance().getBean(field.getType());
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                try {
                    field.set(this, o);
                } catch (IllegalAccessException e) {
                    log.error(this.getClass().getName() + " inject fail: " + field.getName());
                }
            }
        }
    }
}
```
定义一个基类集成 jFinal 的 Controller，构造方法中实现注入，然后其他 Controller 继承这个 BaseController 就ok了。

最后说一下，这个依赖注入代码灵活性和可伸缩性并不高，使用条件很单一，只能用于这种无参的构造方法，不过对于我来说足够了。

Keep Coding ... Stay Cool ...