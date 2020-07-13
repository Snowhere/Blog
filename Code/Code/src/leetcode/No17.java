package leetcode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 给定一个仅包含数字 2-9 的字符串，返回所有它能表示的字母组合。
 *
 * 给出数字到字母的映射如下（与电话按键相同）。注意 1 不对应任何字母。
 *
 * 1
 * 2 abc
 * 3 def
 * 4 ghi
 * 5 jkl
 * 6 mno
 * 7 pqrs
 * 8 tuv
 * 9 wxyz
 *
 * 示例:
 *
 * 输入："23"
 * 输出：["ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"].
 *
 */
public class No17 {

    /**
     * 简单遍历
     */
    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();

        if (digits == null || digits.length() == 0) {
            return result;
        }
        for (int i = 0; i < digits.length(); i++) {
            String[] list = getString(digits.charAt(i));
            if (result.isEmpty()) {
                Collections.addAll(result, list);
            } else {
                List<String> tmp = new ArrayList<>();
                for (String s : list) {
                    for (String s1 : result) {
                        tmp.add(s1 + s);
                    }
                }
                result = tmp;
            }
        }
        return result;
    }

    String[] getString(char num) {
        switch (num) {
            case '2':
                return new String[]{"a", "b", "c"};
            case '3':
                return new String[]{"d", "e", "f"};
            case '4':
                return new String[]{"g", "h", "i"};
            case '5':
                return new String[]{"j", "k", "l"};
            case '6':
                return new String[]{"m", "n", "o"};
            case '7':
                return new String[]{"p", "q", "r", "s"};
            case '8':
                return new String[]{"t", "u", "v"};
            case '9':
                return new String[]{"w", "x", "y", "z"};
            default:
                return new String[0];
        }
    }

    public static void main(String[] args) {
        No17 no17 = new No17();
        System.out.println(no17.letterCombinations("23"));
    }
}
