package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 给出一个区间的集合，请合并所有重叠的区间。
 *
 *  
 *
 * 示例 1:
 *
 * 输入: intervals = [[1,3],[2,6],[8,10],[15,18]]
 * 输出: [[1,6],[8,10],[15,18]]
 * 解释: 区间 [1,3] 和 [2,6] 重叠, 将它们合并为 [1,6].
 * 示例 2:
 *
 * 输入: intervals = [[1,4],[4,5]]
 * 输出: [[1,5]]
 * 解释: 区间 [1,4] 和 [4,5] 可被视为重叠区间。
 * 注意：输入类型已于2019年4月15日更改。 请重置默认代码定义以获取新方法签名。
 *
 *  
 *
 * 提示：
 *
 * intervals[i][0] <= intervals[i][1]
 *
 */
public class No56 {

    public int[][] merge(int[][] intervals) {
        if (intervals.length == 0) {
            return new int[0][0];
        }
        //根据 intervals[i][0] 排序
        Arrays.sort(intervals, Comparator.comparingInt(o -> o[0]));
        //遍历过程中记录左右端点
        int left = intervals[0][0];
        int right = intervals[0][1];
        List<int[]> result = new ArrayList<>();
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] <= right) {
                right = Math.max(right, intervals[i][1]);
            } else {
                result.add(new int[]{left, right});
                left = intervals[i][0];
                right = intervals[i][1];
            }
        }
        result.add(new int[]{left, right});
        return result.toArray(new int[result.size()][2]);
    }

    public static void main(String[] args) {
        No56 no56 = new No56();
        int[][] result = no56.merge(new int[][]{
                {1, 3}, {2, 6}, {8, 10}, {15, 18}
        });
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i][0] + "-" + result[i][1]);
        }
    }
}
