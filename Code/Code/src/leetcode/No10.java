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
     * p[j]是'.'，f(i-1,j-1)为true
     * p[j]是字母，s[i]==p[j] 且 f(i-1,j-1)为true
     * p[j]是'*'，匹配了多次，s[i]==p[j-1] 且 f(i-1,j)为true   ---去掉s[i]这个字符之后，s[i-1]依然可以匹配多次或0次
     * p[j]是'*'，匹配0次， f(i,j-2)为true
     */
   public boolean isMatch(String s, String p) {
        if (s == null || s.equals("") || p == null || p.equals("")) {
            return false;
        }
        if (p.startsWith("*")) {
            return false;
        }

        return match(s, p, s.length() - 1, p.length() - 1);
    }

    boolean match(String s, String p, int i, int j) {
        if (i <= 0) {
            if (j == 0) {
                return s.charAt(i) == p.charAt(j) || p.charAt(j) == '.';
            } else if (j == 1) {
                return p.charAt(1) == '*';
            }
        }
        if (j < 0) {
            return false;
        }

        switch (p.charAt(j)) {
            case '.':
                return match(s, p, i - 1, j - 1);
            case '*':
                return ((s.charAt(i) == p.charAt(j - 1)||p.charAt(j-1)=='.') && match(s, p, i - 1,   j))||match(s,p,i,j-2);
            default:
                return s.charAt(i) == p.charAt(j) && match(s, p, i - 1, j - 1);
        }
    }


    public static void main(String[] args) {
        No10 no10 = new No10();
        System.out.println(no10.isMatch("aab", "c*a*b*"));
        System.out.println(no10.isMatch("mississippi","mis*is*p*."));
        System.out.println(no10.isMatch("aab", "c*"));
        System.out.println(no10.isMatch("ab", ".*.."));
    }
}
