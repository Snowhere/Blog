package hierarchy;

/**
 * Abstract
 *
 * @author STH
 * @create 2017-04-05
 **/
public class SupClass {
    public void init() {
        System.out.println(get());
    }
    protected String get() {
        return "sup";
    }

    public static void main(String[] args) {
        SubClass subClass = new SubClass();
        subClass.init();

    }
}
