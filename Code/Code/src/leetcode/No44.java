package leetcode;
/**
 * 给定一个字符串 (s) 和一个字符模式 (p) ，实现一个支持 '?' 和 '*' 的通配符匹配。
 *
 * '?' 可以匹配任何单个字符。
 * '*' 可以匹配任意字符串（包括空字符串）。
 * 两个字符串完全匹配才算匹配成功。
 *
 * 说明:
 *
 * s 可能为空，且只包含从 a-z 的小写字母。
 * p 可能为空，且只包含从 a-z 的小写字母，以及字符 ? 和 *。
 * 示例 1:
 *
 * 输入:
 * s = "aa"
 * p = "a"
 * 输出: false
 * 解释: "a" 无法匹配 "aa" 整个字符串。
 * 示例 2:
 *
 * 输入:
 * s = "aa"
 * p = "*"
 * 输出: true
 * 解释: '*' 可以匹配任意字符串。
 * 示例 3:
 *
 * 输入:
 * s = "cb"
 * p = "?a"
 * 输出: false
 * 解释: '?' 可以匹配 'c', 但第二个 'a' 无法匹配 'b'。
 * 示例 4:
 *
 * 输入:
 * s = "adceb"
 * p = "*a*b"
 * 输出: true
 * 解释: 第一个 '*' 可以匹配空字符串, 第二个 '*' 可以匹配字符串 "dce".
 * 示例 5:
 *
 * 输入:
 * s = "acdcb"
 * p = "a*c?b"
 * 输出: false
 *
 */
public class No44 {

    /**
     * 啥都不说，动态规划
     * f(i,j)表示以i位置结尾的字符串和以j位置结尾的通配符之间的关系
     * f(i,j)==true的状态，由以下几种状态转换而来
     * 当p[j]=='?'则需要f(i-1,j-1)==true
     * 当p[j]=='*'则需要匹配了一次或多次：f(i-1.j)==true或者匹配了0次：f(i.j-1)==true
     * 当p[j]==普通字母时，则需要p[j]==s[i]&&f(i-1,j-1)==true
     * <p>
     * 初始状态
     * f(0,0)=true
     * f(i,0)=false
     * f(0,j)要判断前几位是否都是*
     */
    public boolean isMatch(String s, String p) {
        boolean[][] dp = new boolean[s.length() + 1][p.length() + 1];
        dp[0][0] = true;
        //默认就是false
        /*for (int i = 0; i < s.length(); i++) {
            dp[i][0]=false;
        }*/
        for (int j = 1; j <= p.length(); j++) {
            if (p.charAt(j - 1) == '*') {
                dp[0][j] = true;
            } else {
                break;
            }
        }
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 1; j <= p.length(); j++) {
                switch (p.charAt(j - 1)) {
                    case '*': {
                        dp[i][j] = dp[i - 1][j] || dp[i][j - 1];
                        break;
                    }
                    case '?': {
                        dp[i][j] = dp[i - 1][j - 1];
                        break;
                    }
                    default: {
                        dp[i][j] = s.charAt(i - 1) == p.charAt(j - 1) && dp[i - 1][j - 1];
                    }
                }
            }
        }

        return dp[s.length()][p.length()];
    }


    public static void main(String[] args) {
        No44 no44 = new No44();
        System.out.println(no44.isMatch("adceb", "*a*b"));
        System.out.println(no44.isMatch("acdcb", "a*c?b"));
        System.out.println(no44.isMatch("ab", "?*"));
        System.out.println(no44.isMatch("aab", "c*a*b"));
        System.out.println(no44.isMatch("", ""));
        System.out.println(no44.isMatch("", "a"));
        System.out.println(no44.isMatch("ho", "**ho"));
    }
}
