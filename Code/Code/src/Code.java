public class Code {
    private volatile long lastStat = System.currentTimeMillis();

    public static void main(String args[]) {
        int n = 0;
        print(++n, n);
    }

    public static void print(int a, int b) {
        System.out.println(a + "," + b);
    }
}
