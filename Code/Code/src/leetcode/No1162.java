package leetcode;
/**
 * 你现在手里有一份大小为 N x N 的 网格 grid，上面的每个 单元格 都用 0 和 1 标记好了。其中 0 代表海洋，1 代表陆地，请你找出一个海洋单元格，这个海洋单元格到离它最近的陆地单元格的距离是最大的。
 *
 * 我们这里说的距离是「曼哈顿距离」（ Manhattan Distance）：(x0, y0) 和 (x1, y1) 这两个单元格之间的距离是 |x0 - x1| + |y0 - y1| 。
 *
 * 如果网格上只有陆地或者海洋，请返回 -1。
 *
 *  
 *
 * 示例 1：
 *
 *
 *
 * 输入：[[1,0,1],[0,0,0],[1,0,1]]
 * 输出：2
 * 解释：
 * 海洋单元格 (1, 1) 和所有陆地单元格之间的距离都达到最大，最大距离为 2。
 * 示例 2：
 *
 *
 *
 * 输入：[[1,0,0],[0,0,0],[0,0,0]]
 * 输出：4
 * 解释：
 * 海洋单元格 (2, 2) 和所有陆地单元格之间的距离都达到最大，最大距离为 4。
 *  
 *
 * 提示：
 *
 * 1 <= grid.length == grid[0].length <= 100
 * grid[i][j] 不是 0 就是 1
 *
 */
public class No1162 {

    /**
     * 遍历一遍获取陆地
     * 再遍历一遍获取每个海洋和每个陆地的距离
     */
    public int maxDistance(int[][] grid) {
        int distance = -1;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                //是海洋,寻找离它最近的陆地单元格距离
                if(grid[i][j]==0){
                    int min = Integer.MAX_VALUE;
                    for (int n = 0; n < grid.length; n++) {
                        for (int m = 0; m < grid[0].length; m++) {
                            if (grid[n][m] == 1) {
                                min = Math.min(min, Math.abs(i - n) + Math.abs(j - m));
                            }
                        }
                    }
                    if (min != Integer.MAX_VALUE) {
                        distance = Math.max(distance, min);
                    }
                }

            }
        }
        return distance;
    }

    public static void main(String[] args) {
        No1162 no1162 = new No1162();
        System.out.println(no1162.maxDistance(new int[][]{
                {1,0,0},
                {0,0,0},
                {0,0,0}
        }));
    }
}
