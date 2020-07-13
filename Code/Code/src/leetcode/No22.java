package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 数字 n 代表生成括号的对数，请你设计一个函数，用于能够生成所有可能的并且 有效的 括号组合。
 *
 *  
 *
 * 示例：
 *
 * 输入：n = 3
 * 输出：[
 *        "((()))",
 *        "(()())",
 *        "(())()",
 *        "()(())",
 *        "()()()"
 *      ]
 *
 */
public class No22 {

    /**
     * 递归
     * 左括号一定对应右括号,最左一定是左括号
     * 形式抽象(a)b  其中ab可以为空
     * 总共n对括号，a有i对，则b有n-1-i对
     * 递归求解a和b，再拼接起来
     * 缓存中间结果，提升递归效率
     */

    public List<String> generateParenthesis(int n) {
        if (n <= 0) {
            return new ArrayList<>();
        }

        return generate(n);
    }

    ArrayList[] cache = new ArrayList[100];

    List<String> generate(int n) {
        if (cache[n] != null) {
            return cache[n];
        }
        if (n == 0) {
            ArrayList<String> list = new ArrayList<>();
            list.add("");
            cache[0] = list;
            return list;
        }
        if (n == 1) {
            ArrayList<String> list = new ArrayList<>();
            list.add("()");
            cache[1] = list;
            return list;
        }
        ArrayList<String> result = new ArrayList<>();
        //(a)b  总共n对括号，a有i对，则b有n-1-i对
        for (int i = 0; i < n; i++) {
            for (String si : generate(i)) {
                for (String sn : generate(n - i - 1)) {
                    result.add("(" + si + ")" + sn);
                }
            }
        }
        cache[n] = result;
        return result;
    }

    public static void main(String[] args) {
        No22 no22 = new No22();
        System.out.println(no22.generateParenthesis(3));
    }
}
