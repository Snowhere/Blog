package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 你有一个用于表示一片土地的整数矩阵land，该矩阵中每个点的值代表对应地点的海拔高度。若值为0则表示水域。由垂直、水平或对角连接的水域为池塘。池塘的大小是指相连接的水域的个数。编写一个方法来计算矩阵中所有池塘的大小，返回值需要从小到大排序。
 *
 * 示例：
 *
 * 输入：
 * [
 *   [0,2,1,0],
 *   [0,1,0,1],
 *   [1,1,0,1],
 *   [0,1,0,1]
 * ]
 * 输出： [1,2,4]
 * 提示：
 *
 * 0 < len(land) <= 1000
 * 0 < len(land[i]) <= 1000
 *
 */
public class No1619 {

    //遍历所有节点，遇到水域则深度遍历然后染色
    public int[] pondSizes(int[][] land) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < land.length; i++) {
            for (int j = 0; j < land[0].length; j++) {
                if (land[i][j] == 0) {
                    result.add(size(land, i, j));
                }
            }
        }
        int[] ints = result.stream().mapToInt(Integer::valueOf).toArray();

        Arrays.sort(ints);
        return ints;
    }


    public int size(int[][] land, int i, int j) {
        if (i >= land.length || j >= land[0].length || i < 0 || j < 0) {
            return 0;
        }
        if (land[i][j] == 0) {
            land[i][j] = -1;
            return 1 + size(land, i, j + 1) + size(land, i + 1, j) + size(land, i + 1, j + 1)
                    + size(land, i, j - 1) + size(land, i - 1, j) + size(land, i - 1, j - 1)
                    + size(land, i - 1, j + 1) + size(land, i + 1, j - 1);
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        int[][] land = new int[][]{
                {0, 2, 1, 0},
                {0, 1, 0, 1},
                {1, 1, 0, 1},
                {0, 1, 0, 1}
        };
        No1619 no1619 = new No1619();
        int[] result = no1619.pondSizes(land);
        for (int i : result) {
            System.out.println(i);
        }
    }
}
