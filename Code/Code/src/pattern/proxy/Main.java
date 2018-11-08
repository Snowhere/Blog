package pattern.proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Main {
    public static void main(String[] args) {

        //normal
        BaseInterface base1 = new Base();
        base1.doSomething();

        //static proxy
        BaseInterface base2 = new BaseProxy();
        base2.doSomething();

        //lang.reflect.Proxy
        BaseInterface base3 = (BaseInterface) Proxy.newProxyInstance(Base.class.getClassLoader(),
                Base.class.getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        System.out.println("Proxy before");
                        Object result = method.invoke(base1, args);
                        System.out.println("Proxy after");
                        return result;
                    }
                });
        base3.doSomething();

        //cglib
        Enhancer enhancer = new Enhancer();
        enhancer.setInterfaces(Base.class.getInterfaces());
        enhancer.setSuperclass(Base.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("cglib before");
                Object result = method.invoke(base1, objects);
                System.out.println("cglib after");
                return result;
            }
        });
        BaseInterface base4 = (Base) enhancer.create();
        base4.doSomething();
    }
}
