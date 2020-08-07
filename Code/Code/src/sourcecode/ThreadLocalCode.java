package sourcecode;

public class ThreadLocalCode {

    public static void main(String[] args) {
        ThreadLocal<String> str = new ThreadLocal<>();
        System.out.println(str.get());
        str.set("a");
        System.out.println(str.get());
        str.set("b");
        System.out.println(str.get());

        String a = "a";
        System.out.println(a);

        ThreadLocal<String> str1 = new ThreadLocal<>();
        ThreadLocal<String> str2 = new ThreadLocal<>();
        str1.set("1");
        str2.set("2");
        System.out.println(str1.get());
        System.out.println(str2.get());
    }
}
