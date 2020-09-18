package leetcode.offer;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 输入一个字符串，打印出该字符串中字符的所有排列。
 *
 *  
 *
 * 你可以以任意顺序返回这个字符串数组，但里面不能有重复元素。
 *
 *  
 *
 * 示例:
 *
 * 输入：s = "abc"
 * 输出：["abc","acb","bac","bca","cab","cba"]
 *  
 *
 * 限制：
 *
 * 1 <= s 的长度 <= 8
 *
 */
public class No38 {

    public String[] permutation(String s) {
        Set<String> result = new HashSet<>();
        List<Character> list = new ArrayList<>();
        for (int i = 0; i < s.length(); i++) {
            list.add(s.charAt(i));
        }
        sort(list, new ArrayList<>(), result);
        return result.toArray(new String[0]);
    }

    public void sort(List<Character> list, List<Character> current, Set<String> result) {
        if (list.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            for (Character character : current) {
                stringBuilder.append(character);
            }
            result.add(stringBuilder.toString());
        } else {
            for (int i = 0; i < list.size(); i++) {
                Character c = list.get(i);
                current.add(c);
                list.remove(i);
                sort(list, current, result);
                //回溯补回来
                current.remove(current.size() - 1);
                list.add(i, c);
            }
        }

    }

    public static void main(String[] args) {
        No38 no38 = new No38();
        String[] abcs = no38.permutation("abc");
        for (String abc : abcs) {
            System.out.println(abc);
        }
    }
}
