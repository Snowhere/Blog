package leetcode;
/**
 * 实现 pow(x, n) ，即计算 x 的 n 次幂函数。
 *
 * 示例 1:
 *
 * 输入: 2.00000, 10
 * 输出: 1024.00000
 * 示例 2:
 *
 * 输入: 2.10000, 3
 * 输出: 9.26100
 * 示例 3:
 *
 * 输入: 2.00000, -2
 * 输出: 0.25000
 * 解释: 2-2 = 1/22 = 1/4 = 0.25
 * 说明:
 *
 * -100.0 < x < 100.0
 * n 是 32 位有符号整数，其数值范围是 [−231, 231 − 1] 。
 *
 */
public class No50 {

    /**
     * 常规迭代思路会超时，采取分治递归 n^4=(n^2)^2
     * 区分n的奇偶
     */
    public double myPow(double x, int n) {
        boolean pos = n >= 0;
        //int 转 long 预防绝对值越界
        long n1 = n;
        n1 = Math.abs(n1);
        return pos ? pow(x, n1) : 1 / pow(x, n1);
    }

    public double pow(double x, long n) {
        if (n == 0) {
            return 1;
        }

        double result;
        double v = pow(x, n / 2);
        if (n % 2 == 0) {
            result = v * v;
        } else {
            result = v * v * x;
        }
        return result;
    }

    public static void main(String[] args) {
        No50 no50 = new No50();
        System.out.println(no50.myPow(2, -2));
        System.out.println(no50.myPow(2, 10));
        System.out.println(no50.myPow(2, -2147483648));
        System.out.println(Math.pow(2, -2147483648));
        System.out.println(Integer.MIN_VALUE);
    }

}
