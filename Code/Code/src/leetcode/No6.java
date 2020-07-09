package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
 *
 * 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：
 *
 * L   C   I   R
 * E T O E S I I G
 * E   D   H   N
 * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："LCIRETOESIIGEDHN"。
 *
 * 请你实现这个将字符串进行指定行数变换的函数：
 *
 * string convert(string s, int numRows);
 * 示例 1:
 *
 * 输入: s = "LEETCODEISHIRING", numRows = 3
 * 输出: "LCIRETOESIIGEDHN"
 * 示例 2:
 *
 * 输入: s = "LEETCODEISHIRING", numRows = 4
 * 输出: "LDREOEIIECIHNTSG"
 * 解释:
 *
 * L     D     R
 * E   O E   I I
 * E C   I H   N
 * T     S     G
 *
 */
public class No6 {

    /**
     * 找了半天规律还是没找到，人都傻了
     * 其实就是简单遍历，不需要关注Z字型，上下上下拼接字符串就行了
     */
    public String convert(String s, int numRows) {
        if (s == null || s.length() == 0 || numRows <= 1) {
            return s;
        }
        int size = Math.min(s.length(), numRows);
        List<StringBuffer> buffers = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            buffers.add(new StringBuffer());
        }
        boolean down = false;
        int row = 0;
        for (int i = 0; i < s.length(); i++) {
            buffers.get(row).append(s.charAt(i));
            if (i % (numRows - 1) == 0) {
                down = !down;
            }
            row = down ? row + 1 : row - 1;
        }
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < size; i++) {
            result.append(buffers.get(i));
        }
        return result.toString();
    }

    public static void main(String[] args) {
        No6 no6 = new No6();
        System.out.println(no6.convert("LEETCODEISHIRING", 3));
        System.out.println(no6.convert("LEETCODEISHIRING", 4));
        System.out.println(no6.convert("A", 1));
    }
}
