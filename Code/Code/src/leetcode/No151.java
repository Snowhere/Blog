package leetcode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 给定一个字符串，逐个翻转字符串中的每个单词。
 *
 *  
 *
 * 示例 1：
 *
 * 输入: "the sky is blue"
 * 输出: "blue is sky the"
 * 示例 2：
 *
 * 输入: "  hello world!  "
 * 输出: "world! hello"
 * 解释: 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
 * 示例 3：
 *
 * 输入: "a good   example"
 * 输出: "example good a"
 * 解释: 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
 *  
 *
 * 说明：
 *
 * 无空格字符构成一个单词。
 * 输入字符串可以在前面或者后面包含多余的空格，但是反转后的字符不能包括。
 * 如果两个单词间有多余的空格，将反转后单词间的空格减少到只含一个。
 *  
 */
public class No151 {

    public String reverseWords(String s) {
        String trim = s.trim();
        String[] split = trim.split("\\s+");
        for (String s1 : split) {
            System.out.println(s1);
        }
        List<String> strings = Arrays.asList(split);
        System.out.println(strings);
        Collections.reverse(strings);
        return String.join(" ", strings);
    }

    public static void main(String[] args) {
        No151 no151 = new No151();
        System.out.println(no151.reverseWords("   a good   example  "));
    }
}
