package pattern.proxy;

public class BaseProxy implements BaseInterface{

    private BaseInterface base=new Base();
    @Override
    public void doSomething() {
        System.out.println("before");
        base.doSomething();
        System.out.println("after");
    }


}
