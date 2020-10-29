package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个非负整数 numRows，生成杨辉三角的前 numRows 行。
 *
 *
 *
 * 在杨辉三角中，每个数是它左上方和右上方的数的和。
 *
 * 示例:
 *
 * 输入: 5
 * 输出:
 * [
 *      [1],
 *     [1,1],
 *    [1,2,1],
 *   [1,3,3,1],
 *  [1,4,6,4,1]
 * ]
 *
 */
public class No118 {

    List<List<Integer>> result = new ArrayList<>();

    public List<List<Integer>> generate(int numRows) {
        List<Integer> list = new ArrayList<>();
        if (numRows <= 0) {
            return result;
        }
        if (numRows == 1) {
            list.add(1);
            result.add(list);
            return result;
        }
        generate(numRows - 1);
        list.add(1);
        List<Integer> last = result.get(numRows - 2);
        for (int i = 1; i < last.size(); i++) {
            list.add(last.get(i - 1) + last.get(i));
        }
        list.add(1);
        result.add(list);
        return result;
    }

    public static void main(String[] args) {
        No118 no118 = new No118();
        List<List<Integer>> generate = no118.generate(5);
        System.out.println(generate);
    }
}
