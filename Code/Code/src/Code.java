import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Code {
    private volatile long lastStat = System.currentTimeMillis();

    public static void main(String args[]) {
        AtomicInteger a = new AtomicInteger();
        a.set(Integer.MAX_VALUE);
        int i=0;
        System.out.println(i+1>i);
    }

}
