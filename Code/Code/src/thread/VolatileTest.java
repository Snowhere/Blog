package thread;

/**
 * thread volatile
 *
 * @author STH
 * @create 2017-03-31
 **/
public class VolatileTest {
    private volatile long lastStat = System.currentTimeMillis();

    public void test() {
        System.out.print(lastStat-System.currentTimeMillis());
    }
}
