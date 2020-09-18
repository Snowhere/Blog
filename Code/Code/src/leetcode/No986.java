package leetcode;

import java.util.Arrays;

/**
 * 给定两个由一些 闭区间 组成的列表，每个区间列表都是成对不相交的，并且已经排序。
 *
 * 返回这两个区间列表的交集。
 *
 * （形式上，闭区间 [a, b]（其中 a <= b）表示实数 x 的集合，而 a <= x <= b。两个闭区间的交集是一组实数，要么为空集，要么为闭区间。例如，[1, 3] 和 [2, 4] 的交集为 [2, 3]。）
 *
 *  
 *
 * 示例：
 *
 *
 *
 * 输入：A = [[0,2],[5,10],[13,23],[24,25]], B = [[1,5],[8,12],[15,24],[25,26]]
 * 输出：[[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]
 *  
 *
 * 提示：
 *
 * 0 <= A.length < 1000
 * 0 <= B.length < 1000
 * 0 <= A[i].start, A[i].end, B[i].start, B[i].end < 10^9
 *
 */
public class No986 {

    //递归思路求解
    public int[][] intervalIntersection(int[][] A, int[][] B) {

        int[][] union = union(A, B, 0, 0);
        //数组需要反转
        int[][] arr1 = new int[union.length][2];
        for (int x = 0; x < union.length; x++) {
            arr1[x] = union[union.length - x - 1];
        }
        return arr1;

    }

    public int[][] union(int[][] A, int[][] B, int i, int j) {
        if (i < A.length && j < B.length) {
            int a1 = A[i][0];
            int a2 = A[i][1];
            int b1 = B[j][0];
            int b2 = B[j][1];
            if (a1 <= b1) {
                if (a2 >= b1) {
                    //有交集,我们在数组最后加上当前交集
                    if (a2 == b2) {
                        //结束位置相同，略过一组
                        int[][] union = union(A, B, i + 1, j + 1);
                        int[][] result = Arrays.copyOf(union, union.length + 1);
                        result[result.length - 1] = new int[]{b1, b2};
                        return result;
                    } else if (a2 < b2) {
                        B[j][0] = a2;
                        int[][] union = union(A, B, i + 1, j);
                        int[][] result = Arrays.copyOf(union, union.length + 1);
                        result[result.length - 1] = new int[]{b1, a2};
                        return result;
                    } else {
                        A[i][0] = b2;
                        int[][] union = union(A, B, i, j + 1);
                        int[][] result = Arrays.copyOf(union, union.length + 1);
                        result[result.length - 1] = new int[]{b1, b2};
                        return result;
                    }
                } else {
                    //无交集
                    return union(A, B, i + 1, j);
                }

            } else {
                //偷懒换位，少写一半代码
                return union(B, A, j, i);
            }
        } else {
            return new int[0][0];
        }
    }

    public static void main(String[] args) {
        No986 no986 = new No986();
        int[][] result = no986.intervalIntersection(
                new int[][]{
                        {0, 2}, {5, 10}, {13, 23}, {24, 25}
                }, new int[][]{
                        {1, 5}, {8, 12}, {15, 24}, {25, 26}
                });
        for (int[] ints : result) {
            System.out.println(ints[0] + "-" + ints[1]);
        }
    }
}
