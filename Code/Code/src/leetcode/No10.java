package leetcode;
/**
 * 给你一个字符串 s 和一个字符规律 p，请你来实现一个支持 '.' 和 '*' 的正则表达式匹配。
 *
 * '.' 匹配任意单个字符
 * '*' 匹配零个或多个前面的那一个元素
 * 所谓匹配，是要涵盖 整个 字符串 s的，而不是部分字符串。
 *
 * 说明:
 *
 * s 可能为空，且只包含从 a-z 的小写字母。
 * p 可能为空，且只包含从 a-z 的小写字母，以及字符 . 和 *。
 * 示例 1:
 *
 * 输入:
 * s = "aa"
 * p = "a"
 * 输出: false
 * 解释: "a" 无法匹配 "aa" 整个字符串。
 * 示例 2:
 *
 * 输入:
 * s = "aa"
 * p = "a*"
 * 输出: true
 * 解释: 因为 '*' 代表可以匹配零个或多个前面的那一个元素, 在这里前面的元素就是 'a'。因此，字符串 "aa" 可被视为 'a' 重复了一次。
 * 示例 3:
 *
 * 输入:
 * s = "ab"
 * p = ".*"
 * 输出: true
 * 解释: ".*" 表示可匹配零个或多个（'*'）任意字符（'.'）。
 * 示例 4:
 *
 * 输入:
 * s = "aab"
 * p = "c*a*b"
 * 输出: true
 * 解释: 因为 '*' 表示零个或多个，这里 'c' 为 0 个, 'a' 被重复一次。因此可以匹配字符串 "aab"。
 * 示例 5:
 *
 * 输入:
 * s = "mississippi"
 * p = "mis*is*p*."
 * 输出: false
 *
 */
public class No10 {

    /**
     * f(i,j)表示字符串s到i为止的字符串和p到j为止的模式匹配
     * f(i,j)为true的条件是
     * 1.p[j]是'.'，f(i-1,j-1)为true
     * 2.p[j]是字母，s[i]==p[j] 且 f(i-1,j-1)为true
     * 3.p[j]是'*'，匹配了多次，s[i]与p[j-1]匹配 且 f(i-1,j)为true   ---意思是去掉s[i]这个字符之后，s[i-1]依然可以匹配多次或0次
     * 4.p[j]是'*'，匹配0次，无论s[i]与p[j-1]是否匹配， 只需要 f(i,j-2)为true
     *
     * 简化，我们定义考察单个字符时“匹配”含义为 p[j]=='.' 或 s[i]==p[j]
     * 则1和2可以合为一种情况，3和4只需要判断s[i]与p[j-1]是否匹配
     *
     * 数组[0][0]表示空字符串，所以行列=length+1
     * 使用 charAt 函数时记得 -1
     *
     */
    public boolean isMatch(String s, String p) {
        if (p.startsWith("*")) {
            return false;
        }
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];

        dp[0][0] = true;

        for (int i = 0; i <= s.length(); i++) {
            for (int j = 1; j <= p.length(); j++) {
                if (p.charAt(j - 1) == '*') {
                    dp[i][j] = dp[i][j - 2];
                    if (match(s, p, i, j - 1)) {
                        dp[i][j] = dp[i][j] || dp[i - 1][j];
                    }
                } else {
                    if (match(s, p, i, j)) {
                        dp[i][j] = dp[i - 1][j - 1];
                    }
                }
            }

        }

        return dp[s.length()][p.length()];
    }

    boolean match(String s, String p, int i, int j) {
        if (i == 0) {
            return false;
        }
        if (p.charAt(j - 1) == '.') {
            return true;
        } else {
            return s.charAt(i - 1) == p.charAt(j - 1);
        }
    }


    public static void main(String[] args) {
        No10 no10 = new No10();
        System.out.println(no10.isMatch("aab", "c*a*b*"));
        System.out.println(no10.isMatch("mississippi", "mis*is*p*."));
        System.out.println(no10.isMatch("aab", "c*"));
        System.out.println(no10.isMatch("ab", ".*.."));
    }
}
