package leetcode;
/**
 * 给定一个字符串，验证它是否是回文串，只考虑字母和数字字符，可以忽略字母的大小写。
 *
 * 说明：本题中，我们将空字符串定义为有效的回文串。
 *
 * 示例 1:
 *
 * 输入: "A man, a plan, a canal: Panama"
 * 输出: true
 * 示例 2:
 *
 * 输入: "race a car"
 * 输出: false
 *
 */
public class No125 {

    public boolean isPalindrome(String s) {
        if (s == null || s.length() < 1) {
            return true;
        }
        int left = 0;
        int right = s.length() - 1;
        Character c = null;
        boolean isPalindrome = true;
        while (left <= right) {
            if (c == null) {
                //寻找左边目标字符
                if (isString(s.charAt(left))) {
                    c = s.charAt(left);
                }
                left++;
            } else {
                //比对右边字符
                Character rightChar = s.charAt(right);
                if (isString(rightChar)) {
                    isPalindrome = c.toString().equalsIgnoreCase(rightChar.toString());
                    c = null;
                }
                right--;
            }
            if (!isPalindrome) {
                break;
            }
        }
        return isPalindrome;
    }

    boolean isString(Character character) {
        return Character.isUpperCase(character) || Character.isLowerCase(character) || Character.isDigit(character);
    }

    public static void main(String[] args) {
        No125 no125 = new No125();
        System.out.println(no125.isPalindrome("A man, a plan, a canal: Panama"));
        System.out.println(no125.isPalindrome("race a car"));
        System.out.println(no125.isPalindrome("ab"));
    }
}
