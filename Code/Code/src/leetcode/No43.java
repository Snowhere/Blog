package leetcode;
/**
 * 给定两个以字符串形式表示的非负整数 num1 和 num2，返回 num1 和 num2 的乘积，它们的乘积也表示为字符串形式。
 *
 * 示例 1:
 *
 * 输入: num1 = "2", num2 = "3"
 * 输出: "6"
 * 示例 2:
 *
 * 输入: num1 = "123", num2 = "456"
 * 输出: "56088"
 * 说明：
 *
 * num1 和 num2 的长度小于110。
 * num1 和 num2 只包含数字 0-9。
 * num1 和 num2 均不以零开头，除非是数字 0 本身。
 * 不能使用任何标准库的大数类型（比如 BigInteger）或直接将输入转换为整数来处理。
 *
 */
public class No43 {

    /**
     * 转换成倒序数组，遍历相乘
     * 最后处理进位
     * 倒序数组转成字符串
     */
    public String multiply(String num1, String num2) {
        if (num1 == null || num1.equals("") || num2 == null || num2.equals("")) {
            return "0";
        }
        //转成数组
        int[] nums1 = new int[num1.length()];
        int[] nums2 = new int[num2.length()];
        for (int i = num1.length() - 1; i >= 0; i--) {
            nums1[num1.length() - 1 - i] = num1.charAt(i) - '0';
        }
        for (int i = num2.length() - 1; i >= 0; i--) {
            nums2[num2.length() - 1 - i] = num2.charAt(i) - '0';
        }
        int[] multply = multply(nums1, nums2);
        String result = "";
        //找到高位第一个不为0的位置
        int i = multply.length - 1;
        while (i > 0) {
            if (multply[i] == 0) {
                i--;
            } else {
                break;
            }
        }
        //倒序装填成字符串
        for (; i >= 0; i--) {
            result += multply[i];
        }
        return result;
    }

    public int[] multply(int[] num1, int[] num2) {
        int[] result = new int[num1.length + num2.length];
        for (int i = 0; i < num1.length; i++) {
            for (int j = 0; j < num2.length; j++) {
                result[i + j] += num1[i] * num2[j];
            }
        }
        for (int i = 0; i < result.length; i++) {
            if (result[i] > 9) {
                result[i + 1] += result[i] / 10;
                result[i] = result[i] % 10;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        No43 no43 = new No43();
        System.out.println(no43.multiply("123", "456"));
    }
}
