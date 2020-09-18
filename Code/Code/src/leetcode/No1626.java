package leetcode;

/**
 * 给定一个包含正整数、加(+)、减(-)、乘(*)、除(/)的算数表达式(括号除外)，计算其结果。
 *
 * 表达式仅包含非负整数，+， - ，*，/ 四种运算符和空格  。 整数除法仅保留整数部分。
 *
 * 示例 1:
 *
 * 输入: "3+2*2"
 * 输出: 7
 * 示例 2:
 *
 * 输入: " 3/2 "
 * 输出: 1
 * 示例 3:
 *
 * 输入: " 3+5 / 2 "
 * 输出: 5
 * 说明：
 *
 * 你可以假设所给定的表达式都是有效的。
 * 请不要使用内置的库函数 eval。
 *
 */
public class No1626 {

    /**
     * 只有加减乘除比较简单，不需要复杂的中缀式转后缀式
     * 优先级比较简单，遇到乘除就先算就行了，只需要一个数字栈
     * 读到符号的时候要查看前后的数字，所以遍历过程中我们可以记录上个读到的符号和当前读到的符号，读当前符号时处理上个符号的运算。
     */
    public int calculate(String s) {
       /* if (s == null || s.length() == 0) {
            return 0;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            Character.isDigit()
            switch (c){
                case ' ':break;
                case '+':
                    String num = sb.toString();
                    sb = new StringBuilder();
                    break;
                case '-':break;
                case '*':break;
                case '/':break;
                default:sb.append(c);break;
            }
        }
        String num = sb.toString();*/
       return 1;
    }


    public static void main(String[] args) {
        Character.isDigit('1');
    }
}
