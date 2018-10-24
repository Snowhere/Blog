public class Code {
    private volatile long lastStat = System.currentTimeMillis();

    public static void main(String args[]) {
        Integer a = null;
        String key = "" + a;
        System.out.println(key);
    }


}
