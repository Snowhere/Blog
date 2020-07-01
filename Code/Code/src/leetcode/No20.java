package leetcode;

import java.util.Objects;
import java.util.Stack;

/**
 * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
 *
 * 有效字符串需满足：
 *
 * 左括号必须用相同类型的右括号闭合。
 * 左括号必须以正确的顺序闭合。
 * 注意空字符串可被认为是有效字符串。
 *
 * 示例 1:
 *
 * 输入: "()"
 * 输出: true
 * 示例 2:
 *
 * 输入: "()[]{}"
 * 输出: true
 * 示例 3:
 *
 * 输入: "(]"
 * 输出: false
 * 示例 4:
 *
 * 输入: "([)]"
 * 输出: false
 * 示例 5:
 *
 * 输入: "{[]}"
 * 输出: true
 *
 */
public class No20 {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '(':
                    stack.push('(');
                    break;
                case '[':
                    stack.push('[');
                    break;
                case '{':
                    stack.push('{');
                    break;
                case ')':
                    if (stack.isEmpty() ||!Objects.equals(stack.pop(), '(')) {
                        return false;
                    }
                    break;
                case ']':
                    if (stack.isEmpty() ||!Objects.equals(stack.pop(), '[')) {
                        return false;
                    }
                    break;
                case '}':
                    if (stack.isEmpty() ||!Objects.equals(stack.pop(), '{')) {
                        return false;
                    }
                    break;
            }
        }
        return  stack.isEmpty();
    }

    public static void main(String[] args) {
        No20 no20 = new No20();
        System.out.println(no20.isValid("{[]}"));
    }
}
