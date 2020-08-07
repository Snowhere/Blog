package leetcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 给定一个字符串数组，将字母异位词组合在一起。字母异位词指字母相同，但排列不同的字符串。
 *
 * 示例:
 *
 * 输入: ["eat", "tea", "tan", "ate", "nat", "bat"]
 * 输出:
 * [
 *   ["ate","eat","tea"],
 *   ["nat","tan"],
 *   ["bat"]
 * ]
 * 说明：
 *
 * 所有输入均为小写字母。
 * 不考虑答案输出的顺序。
 *
 */
public class No49 {

    /**
     * 目的是找到一个有效的hash算法来区分异位词
     */
    public List<List<String>> groupAnagrams(String[] strs) {
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            String hash = hash(str);
            map.compute(hash, (k, v) -> {
                if (v == null) {
                    v = new ArrayList<>();
                }
                v.add(str);
                return v;
            });
        }
        return new ArrayList<>(map.values());
    }

    /**
     * 统计26个字母出现的次数
     */
    String hash(String strs) {
        int[] nums = new int[26];
        for (int i = 0; i < strs.length(); i++) {
            char c = strs.charAt(i);
            nums[c - 'a']++;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < nums.length; i++) {
            sb.append("*").append(nums[i]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        No49 no49 = new No49();
        System.out.println(no49.groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"}));
    }
}
