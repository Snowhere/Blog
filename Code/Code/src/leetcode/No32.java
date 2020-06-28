package leetcode;

import java.util.Stack;

/**
 * 给定一个只包含 '(' 和 ')' 的字符串，找出最长的包含有效括号的子串的长度。
 *
 * 示例 1:
 *
 * 输入: "(()"
 * 输出: 2
 * 解释: 最长有效括号子串为 "()"
 * 示例 2:
 *
 * 输入: ")()())"
 * 输出: 4
 * 解释: 最长有效括号子串为 "()()"
 *
 */
public class No32 {
    static public int longestValidParentheses(String s) {
        Stack<Character> stack = new Stack<>();
        int max = 0;
        int tmp = 0;
        int remain = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(c);
            }else {

                if (stack.isEmpty()) {
                    tmp = 0;
                }else {

                    stack.pop();
                    if(max==0||remain == stack.size()){
                        tmp+=2;
                    }
                    remain = stack.size();

                    max = Math.max(max, tmp);
                }
            }
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(longestValidParentheses(")()())"));
    }
}
