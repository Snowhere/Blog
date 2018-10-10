package generic;

/**
 * T
 *
 * @author STH
 * @create 2017-04-06
 **/
public class Box {

    public static <K, V> boolean compare(K k, V v) {
        return k.equals(v);
    }

    public static void main(String args[]) {
        System.out.print(Box.compare(new Integer(1), new Integer(1)));
    }
}
