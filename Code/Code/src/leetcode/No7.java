package leetcode;
/**
 * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
 *
 * 示例 1:
 *
 * 输入: 123
 * 输出: 321
 *  示例 2:
 *
 * 输入: -123
 * 输出: -321
 * 示例 3:
 *
 * 输入: 120
 * 输出: 21
 * 注意:
 *
 * 假设我们的环境只能存储得下 32 位的有符号整数，则其数值范围为 [−231,  231 − 1]。请根据这个假设，如果反转后整数溢出那么就返回 0。
 *
 */
public class No7 {

    /**
     * 偷个懒用long省略int溢出判断
     */
    public int reverse(int x) {
        long result = 0;
        while (x != 0) {
            int i = x % 10;
            result = result * 10 + i;
            x = x / 10;
        }
        if (result > Integer.MAX_VALUE || result < Integer.MIN_VALUE) {
            return 0;
        }
        return (int) result;
    }

    /**
     * 正统判断溢出方式
     */
    public int reverse2(int x) {
        long result = 0;
        while (x != 0) {
            int i = x % 10;
            if (result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10 && i > 7)) return 0;
            if (result < Integer.MIN_VALUE / 10 || (result == Integer.MIN_VALUE / 10 && i < -8)) return 0;
            result = result * 10 + i;
            x = x / 10;
        }

        return (int) result;
    }

    public static void main(String[] args) {
        No7 no7 = new No7();
        System.out.println(no7.reverse(123));
        System.out.println(no7.reverse(-123));
    }
}
