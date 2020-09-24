package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * 给定一个包含 m x n 个元素的矩阵（m 行, n 列），请按照顺时针螺旋顺序，返回矩阵中的所有元素。
 *
 * 示例 1:
 *
 * 输入:
 * [
 *  [ 1, 2, 3 ],
 *  [ 4, 5, 6 ],
 *  [ 7, 8, 9 ]
 * ]
 * 输出: [1,2,3,6,9,8,7,4,5]
 * 示例 2:
 *
 * 输入:
 * [
 *   [1, 2, 3, 4],
 *   [5, 6, 7, 8],
 *   [9,10,11,12]
 * ]
 * 输出: [1,2,3,4,8,12,11,10,9,5,6,7]
 *
 */
public class No54 {

    /**
     * 一圈一圈递归
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> list = new ArrayList<>();
        if (matrix.length == 0 || matrix[0].length == 0) {
            return list;
        }
        order(matrix, 0, matrix[0].length - 1, 0, matrix.length - 1, list);
        return list;
    }

    public void order(int[][] matrix, int left, int right, int top, int bottom, List<Integer> list) {
        if (left > right || top > bottom) {
            return;
        }
        //右
        for (int i = left; i <= right; i++) {
            list.add(matrix[top][i]);
        }
        //下
        for (int i = top + 1; i <= bottom; i++) {
            list.add(matrix[i][right]);
        }
        //如果多于1列，才需要左
        if (bottom > top) {
            for (int i = right - 1; i >= left; i--) {
                list.add(matrix[bottom][i]);
            }
        }
        //如果多于1行，才需要上
        if (right > left) {
            for (int i = bottom - 1; i > top; i--) {
                list.add(matrix[i][left]);
            }
        }
        order(matrix, left + 1, right - 1, top + 1, bottom - 1, list);
    }

    public static void main(String[] args) {
        No54 no54 = new No54();
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {4, 5, 8},
                {7, 8, 9},
        };
        System.out.println(no54.spiralOrder(matrix));
    }
}
