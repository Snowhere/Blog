package leetcode;
/**
 * 给定两个整数，被除数 dividend 和除数 divisor。将两数相除，要求不使用乘法、除法和 mod 运算符。
 *
 * 返回被除数 dividend 除以除数 divisor 得到的商。
 *
 * 整数除法的结果应当截去（truncate）其小数部分，例如：truncate(8.345) = 8 以及 truncate(-2.7335) = -2
 *
 *  
 *
 * 示例 1:
 *
 * 输入: dividend = 10, divisor = 3
 * 输出: 3
 * 解释: 10/3 = truncate(3.33333..) = truncate(3) = 3
 * 示例 2:
 *
 * 输入: dividend = 7, divisor = -3
 * 输出: -2
 * 解释: 7/-3 = truncate(-2.33333..) = -2
 *  
 *
 * 提示：
 *
 * 被除数和除数均为 32 位有符号整数。
 * 除数不为 0。
 * 假设我们的环境只能存储 32 位有符号整数，其数值范围是 [−2^31,  2^31 − 1]。本题中，如果除法结果溢出，则返回 2^31 − 1。
 *
 */
public class No29 {

    /**
     * 通过减法取商
     * 直接减会超时，因此通过移位操作增大除数，减少减法次数
     * 用long规避溢出判断
     */
    public int divide(int dividend, int divisor) {
        boolean pos = true;
        if ((dividend > 0 && divisor < 0) || dividend < 0 && divisor > 0) {
            pos = false;
        }
        long dividendAbs = Math.abs((long) dividend);
        long divisorAbs = Math.abs((long) divisor);

        long result = 0;

        while (dividendAbs >= divisorAbs) {
            long divisorTmp = divisorAbs;
            long tmp = 1;
            while (divisorTmp <= dividendAbs) {
                divisorTmp = divisorTmp << 1;
                tmp = tmp << 1;
            }
            dividendAbs -= divisorTmp >> 1;
            result += tmp >> 1;
        }
        if (!pos) {
            result = -result;
        }
        if (result < Integer.MIN_VALUE || result > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        } else {
            return (int) result;
        }

    }

    public static void main(String[] args) {
        No29 no29 = new No29();

        System.out.println(no29.divide(-2147483648, -1));
    }
}
