package test;


public class FatherClass {
    protected String s = "parent";
    public void test() {
        System.out.println(getString());
    }

    protected synchronized String getString(){
        return "father";
    }
}
