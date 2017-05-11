package test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 111
 *
 * @author STH
 * @create 2017-04-27
 **/
public class Proxy {
    public static void main(String[] args) {
        final Test test = new Test();
        Object o = java.lang.reflect.Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{TestInterface.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object result = null;
                System.out.println("proxy");
                result = method.invoke(test, args);
                return result;
            }
        });

        ((TestInterface) o).test();
    }
}
