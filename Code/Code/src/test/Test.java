package test;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Test {


    public static void main(String[] args) throws Exception{
        B b = new B();
        A a = new A();
        a.setString("abc");
        b.setA(a);
        System.out.println(b.getA().getString());
        A a1 = b.getA();
        a1.setString("qwer");
        System.out.println(b.getA().getString());
        List l = new ArrayList<Integer>();
        List list = new ArrayList<String>();
        list.add("213");
        list.add(123);
        Object[] array=new java.lang.String[10];
        array[0]="123";
        array[1]=123;
        System.out.println(array);
    }
}
