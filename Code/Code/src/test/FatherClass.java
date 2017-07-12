package test;


public class FatherClass {
    private void test() {
        System.out.println("father");
    }

    private void test2() {
        test();
    }
    public void print() {
        test2();
    }
}
