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

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> list = new ArrayList<>();
        order(matrix, 0, matrix[0].length, 0, matrix.length, list);
        return list;
    }

    public void order(int[][] matrix, int left, int right, int top, int bottom, List<Integer> list) {
        if (right >= left) {
            left++;
            right--;
        }

        top++;
        bottom--;
        order(matrix,left,right,top,bottom,list);
    }

    public static void main(String[] args) {
        No54 no54 = new No54();
        int[][] matrix = new int[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9,10,11,12},
        };
        System.out.println(no54.spiralOrder(matrix));
    }
}
