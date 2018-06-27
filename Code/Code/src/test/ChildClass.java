package test;

/**
 * none
 *
 * @author STH
 * @create 2017-06-27
 **/
public class ChildClass extends FatherClass {

    protected String s = "childClass";

    @Override
    protected String getString() {
        return "child";
    }
}
