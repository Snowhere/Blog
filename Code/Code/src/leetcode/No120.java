package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定一个三角形，找出自顶向下的最小路径和。每一步只能移动到下一行中相邻的结点上。
 *
 * 相邻的结点 在这里指的是 下标 与 上一层结点下标 相同或者等于 上一层结点下标 + 1 的两个结点。
 *
 *  
 *
 * 例如，给定三角形：
 *
 * [
 *      [2],
 *     [3,4],
 *    [6,5,7],
 *   [4,1,8,3]
 * ]
 * 自顶向下的最小路径和为 11（即，2 + 3 + 5 + 1 = 11）。
 *
 *  
 *
 * 说明：
 *
 * 如果你可以只使用 O(n) 的额外空间（n 为三角形的总行数）来解决这个问题，那么你的算法会很加分。
 *
 */
public class No120 {

    /**
     * 动态规划，二维数组直接优化为一维数组
     * 每行第一个值和最后一个值单独处理
     * f(i)和上一行的f(i)和f(i-1)有关，所以内层循环由大到小，依次更新f(i)的值，不会冲突
     */
    public int minimumTotal(List<List<Integer>> triangle) {
        int[] minLevel = new int[triangle.size()];
        for (int i = 0; i < triangle.size(); i++) {
            for (int j = i; j >= 0; j--) {
                if (j == 0) {
                    minLevel[j] = minLevel[j] + triangle.get(i).get(0);
                } else if (i == j) {
                    minLevel[j] = minLevel[j - 1] + triangle.get(i).get(j);
                } else {
                    minLevel[j] = Math.min(minLevel[j - 1], minLevel[j]) + triangle.get(i).get(j);
                }

            }
        }
        int min = minLevel[0];
        for (int i = 0; i < minLevel.length; i++) {
            min = Math.min(min, minLevel[i]);
        }
        return min;
    }

    public static void main(String[] args) {
        No120 no120 = new No120();
        List<List<Integer>> triangle = new ArrayList<>();
        triangle.add(Arrays.asList(2));
        triangle.add(Arrays.asList(3, 4));
        triangle.add(Arrays.asList(6, 5, 7));
        triangle.add(Arrays.asList(4, 1, 8, 3));
        System.out.println(no120.minimumTotal(triangle));
    }
}
