package leetcode;
/**
 * 编写一个函数来查找字符串数组中的最长公共前缀。
 *
 * 如果不存在公共前缀，返回空字符串 ""。
 *
 * 示例 1:
 *
 * 输入: ["flower","flow","flight"]
 * 输出: "fl"
 * 示例 2:
 *
 * 输入: ["dog","racecar","car"]
 * 输出: ""
 * 解释: 输入不存在公共前缀。
 *
 */
public class No14 {

    /**
     * 纵向扫描
     */
    public String longestCommonPrefix(String[] strs) {
        String pre = "";
        if (strs.length == 0) {
            return pre;
        }
        for (int i = 0; ; i++) {
            if (strs[0].length() < i + 1) {
                return pre;
            }
            char c = strs[0].charAt(i);
            for (int j = 0; j < strs.length; j++) {
                if (strs[j].length() < i + 1) {
                    return pre;
                }
                if (c != strs[j].charAt(i)) {
                    return pre;
                }
            }

            pre += c;
        }

    }

    public static void main(String[] args) {
        No14 no14 = new No14();
        System.out.println(no14.longestCommonPrefix(new String[]{"a"}));
        System.out.println(no14.longestCommonPrefix(new String[]{"flower", "flow", "flight"}));
        System.out.println(no14.longestCommonPrefix(new String[]{"dog", "racecar", "car"}));
    }
}
