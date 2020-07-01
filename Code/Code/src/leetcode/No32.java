package leetcode;

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

    /**
     * 动态规划
     * dp[n] 表示以第 n 个字符为结尾的有效括号长度
     * n 为 ( 时，肯定是 0
     * n 为 ) 时，如果 n-1 位置是 (，则 dp[n] = dp[n-2] + 2
     * n 为 ) 时，如果 n-1 位置是 )，则看看能不能找到对应的 ( , n-1 位置如果有对应的 ( 应该在 n-1-dp[n-1]+1 位置，n 位置对应的 ( 只能出现在 n-1-dp[n-1]+1-1 位置
     *
     */
    static public int longestValidParentheses(String s) {
        //初始化数组，默认赋值 0
        int[] dp = new int[s.length()];
        int max = 0;
        for (int i = 0; i < s.length(); i++) {
            if (i > 0 && s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    dp[i] = i > 1 ? dp[i - 2] + 2 : 2;
                } else {
                    if (i - 1 - dp[i - 1] >= 0 && s.charAt(i - 1 - dp[i - 1]) == '(') {
                        dp[i] = i - 1 - dp[i - 1] - 1 >= 0 ? dp[i - 1 - dp[i - 1] - 1] + dp[i - 1] + 2 : dp[i - 1] + 2;
                    }
                }
            }
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    public static void main(String[] args) {
        System.out.println(longestValidParentheses("()(())"));
    }
}
