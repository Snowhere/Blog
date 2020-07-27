package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 给定一个无重复元素的数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 *
 * candidates 中的数字可以无限制重复被选取。
 *
 * 说明：
 *
 * 所有数字（包括 target）都是正整数。
 * 解集不能包含重复的组合。 
 * 示例 1：
 *
 * 输入：candidates = [2,3,6,7], target = 7,
 * 所求解集为：
 * [
 *   [7],
 *   [2,2,3]
 * ]
 * 示例 2：
 *
 * 输入：candidates = [2,3,5], target = 8,
 * 所求解集为：
 * [
 *   [2,2,2,2],
 *   [2,3,3],
 *   [3,5]
 * ]
 *  
 *
 * 提示：
 *
 * 1 <= candidates.length <= 30
 * 1 <= candidates[i] <= 200
 * candidate 中的每个元素都是独一无二的。
 * 1 <= target <= 500
 *
 */
public class No39 {

    /**
     * 简单思路：递归
     * 为防止重复，原数组排序，递归记录当前考察元素的位置
     */
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        Arrays.sort(candidates);
        return combinationSum(candidates,target,0);
    }

    public List<List<Integer>> combinationSum(int[] candidates, int target,int current) {
        List<List<Integer>> result = new ArrayList<>();
        for (int i=current;i<candidates.length;i++) {
            if (target == candidates[i]) {
                List<Integer> list = new ArrayList<>();
                list.add(candidates[i]);
                result.add(list);
            } else if (target >= candidates[i]) {
                List<List<Integer>> lists = combinationSum(candidates, target - candidates[i],i);
                for (List<Integer> list : lists) {
                    list.add(candidates[i]);
                    result.add(list);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        No39 no39 = new No39();
        System.out.println(no39.combinationSum(new int[]{2, 3, 6, 7}, 7));
        System.out.println(no39.combinationSum(new int[]{2, 3, 5}, 8));
    }
}
