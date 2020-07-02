package leetcode;

import java.util.HashSet;
import java.util.Set;

/**
 * 给你一个由 '1'（陆地）和 '0'（水）组成的的二维网格，请你计算网格中岛屿的数量。
 *
 * 岛屿总是被水包围，并且每座岛屿只能由水平方向或竖直方向上相邻的陆地连接形成。
 *
 * 此外，你可以假设该网格的四条边均被水包围。
 *
 *  
 *
 * 示例 1:
 *
 * 输入:
 * 11110
 * 11010
 * 11000
 * 00000
 * 输出: 1
 * 示例 2:
 *
 * 输入:
 * 11000
 * 11000
 * 00100
 * 00011
 * 输出: 3
 * 解释: 每座岛屿只能由水平和/或竖直方向上相邻的陆地连接而成。
 *
 */
public class No200 {

    /**
     * 不需要set去重，扫描完变0就可以了
     */
    public int numIslands(char[][] grid) {
        int nums = 0;
        if (grid.length == 0) {
            return nums;
        }
        Set<String> set = new HashSet<>();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1' && !set.contains(i + "-" + j)) {
                    nums++;
                    land(grid, i, j, set);
                }
            }
        }
        return nums;
    }

    void land(char[][] grid, int i, int j, Set<String> set) {
        if (i >= 0 && j >= 0 && i < grid.length && j < grid[0].length && grid[i][j] == '1' && !set.contains(i + "-" + j)) {
            set.add(i + "-" + j);
            land(grid, i + 1, j, set);
            land(grid, i - 1, j, set);
            land(grid, i, j + 1, set);
            land(grid, i, j - 1, set);
        }
    }

    public static void main(String[] args) {
        No200 no200 = new No200();
        char[][] grid1 = {
                {'1', '1', '1', '1', '0'},
                {'1', '1', '0', '1', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '0', '0', '0'}};
        char[][] grid2 = {
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'}};
        System.out.println(no200.numIslands(grid1));
        System.out.println(no200.numIslands(grid2));
    }
}
