public class Code {
    private volatile long lastStat = System.currentTimeMillis();

    public static void main(String args[]) {
       int i = 0;
       int j = (i-1)/0;
       System.out.print(j);
    }

}
