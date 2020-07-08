package leetcode;
/**
 * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
 *
 * 示例 1：
 *
 * 输入: "babad"
 * 输出: "bab"
 * 注意: "aba" 也是一个有效答案。
 * 示例 2：
 *
 * 输入: "cbbd"
 * 输出: "bb"
 *
 */
public class No5 {

    /**
     * 动态规划
     * f(i) 表示以s[i]结尾的回文字符串长度，f(i)有至少一个取值1
     * 考察 s[i]
     * 针对所有f(i-1)可能的取值，我们判断如果 s[i]==s[i-1-f(i-1)] 则 f(i)=f(i-1)+2
     * 二维数组直接优化为一维数组
     * 数组记录以s[i]结尾的每个回文字符串的首字符，首字符对应的位置置位true
     * 我们遍历i时，更新整个数组
     * i-true的位置是字符长度，同时我们保存两个位置的游标，用于输出字符串
     * 还是无法避免n^2的时间复杂度
     * 不如遍历每个元素，当做回文中心
     */
    public String longestPalindrome(String s) {
        if (s == null || s.equals("")) {
            return s;
        }
        boolean[] list = new boolean[s.length()];
        for (int i = 0; i < list.length; i++) {
            list[i] = true;
        }
        int max = 1;
        int left = 0;
        int right = 0;
        int length = 0;
        for (int i = 0; i < s.length(); i++) {
            for (int j = 1; j <= i; j++) {
                if (list[j]) {
                    if (s.charAt(j - 1) == s.charAt(i)) {
                        list[j - 1] = true;
                        length = i - j + 2;
                        if (length > max) {
                            left = j - 1;
                            right = i;
                            max = length;
                        }
                    } else {
                        list[j - 1] = false;
                    }
                } else {
                    list[j - 1] = false;
                }
            }
        }
        return s.substring(left, right + 1);
    }


    /**
     * 遍历每个元素，当做回文中心，区分回文长度为偶数和奇数两种情况
     */
    public String longestPalindrome2(String s) {
        if (s == null || s.equals("")) {
            return s;
        }
        int max = 1;
        int left = 0;
        int right = 0;
        int length = 0;
        for (int i = 0; i < s.length(); i++) {

        }
        return s.substring(left, right + 1);
    }

    /**
     * 时间复杂度为n的算法
     * Manacher 算法
     * TODO
     */
    public String longestPalindrome3(String s) {
        if (s == null || s.equals("")) {
            return s;
        }
        int max = 1;
        int left = 0;
        int right = 0;
        int length = 0;
        for (int i = 0; i < s.length(); i++) {

        }
        return s.substring(left, right + 1);
    }


    public static void main(String[] args) {
        No5 no5 = new No5();
        System.out.println(no5.longestPalindrome("babad"));
        System.out.println(no5.longestPalindrome("cbbd"));
        System.out.println(no5.longestPalindrome("aaabaaaa"));
        System.out.println(no5.longestPalindrome("abc"));
    }
}
