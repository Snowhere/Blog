package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
 *
 * 示例 1:
 *
 * 输入: "abcabcbb"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
 * 示例 2:
 *
 * 输入: "bbbbb"
 * 输出: 1
 * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
 * 示例 3:
 *
 * 输入: "pwwkew"
 * 输出: 3
 * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
 *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
 *
 */
public class No3 {

    /**
     * 判断重复用set
     * 根据特点：以第一个字符开始不重复字符串结尾位置 <= 以第二个字符开始不重复字符串结尾位置
     * 用滑动窗口（两个指针）
     */
    public int lengthOfLongestSubstring(String s) {
        Set<Character> set = new HashSet<>(s.length());
        int right = 0;
        int max = 0;
        for (int left = 0; left < s.length(); left++) {
            int maxTmp = right - left;
            if (left != 0) {
                set.remove(s.charAt(left - 1));
            }
            while (right < s.length()) {
                char c = s.charAt(right);
                if (set.contains(c)) {
                    break;
                } else {
                    right++;
                    maxTmp++;
                    set.add(c);
                }
            }
            max = Math.max(max, maxTmp);
        }
        return max;
    }

    public static void main(String[] args) {
        No3 no3 = new No3();
        System.out.println(no3.lengthOfLongestSubstring("dvdf"));
        System.out.println(no3.lengthOfLongestSubstring("abcabcbb"));
        System.out.println(no3.lengthOfLongestSubstring("bbbbb"));
        System.out.println(no3.lengthOfLongestSubstring("pwwkew"));
    }
}
