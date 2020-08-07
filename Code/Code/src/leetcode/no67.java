package leetcode;

import java.util.Stack;

/**
 * 给你两个二进制字符串，返回它们的和（用二进制表示）。
 *
 * 输入为 非空 字符串且只包含数字 1 和 0。
 *
 *  
 *
 * 示例 1:
 *
 * 输入: a = "11", b = "1"
 * 输出: "100"
 * 示例 2:
 *
 * 输入: a = "1010", b = "1011"
 * 输出: "10101"
 *  
 *
 * 提示：
 *
 * 每个字符串仅由字符 '0' 或 '1' 组成。
 * 1 <= a.length, b.length <= 10^4
 * 字符串如果不是 "0" ，就都不含前导零。
 *
 */
public class no67 {

    public String addBinary(String a, String b) {
        int i = a.length() - 1;
        int j = b.length() - 1;
        int add = 0;
        Stack<Integer> stack = new Stack<>();
        while (i >= 0 && j >= 0) {
            int ca = a.charAt(i) - '0';
            int cb = b.charAt(j) - '0';
            int current = ca + cb + add;
            add = current / 2;
            current = current % 2;
            stack.add(current);
            i--;
            j--;
        }
        while (i >= 0) {
            int ca = a.charAt(i) - '0';
            int current = ca + add;
            add = current / 2;
            current = current % 2;
            stack.add(current);
            i--;
        }
        while (j >= 0) {
            int cb = b.charAt(j) - '0';
            int current = cb + add;
            add = current / 2;
            current = current % 2;
            stack.add(current);
            j--;
        }
        if (add != 0) {
            stack.add(add);
        }
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        no67 no67 = new no67();
        System.out.println(no67.addBinary("1010", "1011"));
    }
}
