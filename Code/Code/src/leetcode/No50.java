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
     * 常规思路会超时，采取分治递归
     */
    public double myPow(double x, int n) {
        if (x == 1) {
            return 1;
        }
        if (x == 0) {
            return 0;
        }

        double result=1;
        boolean pos = n>=0;

        for (int i = 0; i < Math.abs(n); i++) {
            result=result*x;
        }
        return pos?result:1/result;
    }

    public static void main(String[] args) {
        No50 no50 = new No50();
        System.out.println(no50.myPow(2, -2));
        System.out.println(no50.myPow(2,10));
        System.out.println(no50.myPow(2,-2147483648));
        System.out.println(Math.pow(2,-2147483648));
        System.out.println(Integer.MIN_VALUE);
    }

}
