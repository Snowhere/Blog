package leetcode;

import java.util.Arrays;

/**
 * 给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。
 *
 * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
 *
 * 你可以假设除了整数 0 之外，这个整数不会以零开头。
 *
 * 示例 1:
 *
 * 输入: [1,2,3]
 * 输出: [1,2,4]
 * 解释: 输入数组表示数字 123。
 * 示例 2:
 *
 * 输入: [4,3,2,1]
 * 输出: [4,3,2,2]
 * 解释: 输入数组表示数字 4321。
 *
 */
public class No66 {

    public int[] plusOne(int[] digits) {
        int ten = 1;
        int[] result = new int[digits.length + 1];
        for (int i = digits.length - 1; i >= 0; i--) {
            result[i + 1] = digits[i] + ten;
            if (result[i + 1] == 10) {
                result[i + 1] = 0;
                ten = 1;
            } else {
                ten = 0;
            }
        }
        if (ten == 1) {
            result[0] = 1;
            return result;
        } else {
            return Arrays.copyOfRange(result, 1, result.length);
        }
    }

    public static void main(String[] args) {
        No66 no66 = new No66();
        int[] ints = no66.plusOne(new int[]{8, 9, 9, 9});
        for (int anInt : ints) {
            System.out.println(anInt);
        }
    }
}
