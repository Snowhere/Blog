package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * 给定一个数组 candidates 和一个目标数 target ，找出 candidates 中所有可以使数字和为 target 的组合。
 *
 * candidates 中的每个数字在每个组合中只能使用一次。
 *
 * 说明：
 *
 * 所有数字（包括目标数）都是正整数。
 * 解集不能包含重复的组合。 
 * 示例 1:
 *
 * 输入: candidates = [10,1,2,7,6,1,5], target = 8,
 * 所求解集为:
 * [
 *   [1, 7],
 *   [1, 2, 5],
 *   [2, 6],
 *   [1, 1, 6]
 * ]
 * 示例 2:
 *
 * 输入: candidates = [2,5,2,1,2], target = 5,
 * 所求解集为:
 * [
 *   [1,2,2],
 *   [5]
 * ]
 *
 */
public class No40 {

    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> result = new ArrayList<>();
        if (candidates == null || candidates.length == 0) {
            return result;
        }
        Arrays.sort(candidates);
        for (int i = 0; i < candidates.length; i++) {
            if (i > 0 && candidates[i] == candidates[i - 1]) {
                continue;
            }
            combine(candidates, target, i, new Stack<>(), result);
        }

        return result;
    }

    public void combine(int[] candidates, int target, int current, Stack<Integer> stack, List<List<Integer>> result) {
        int remain = target - candidates[current];
        stack.push(candidates[current]);
        if (remain == 0) {
            result.add(new ArrayList<>(stack));
        } else if (remain > 0) {
            for (int i = current + 1; i < candidates.length; i++) {
                if (i > current + 1 && candidates[i] == candidates[i - 1]) {
                    continue;
                }
                combine(candidates, remain, i, stack, result);

            }
        }
        stack.pop();
    }






    public static void main(String[] args) {
        No40 no40 = new No40();
        System.out.println(no40.combinationSum2(new int[]{10, 1, 2, 7, 6, 1, 5}, 8));
        System.out.println(no40.combinationSum2(new int[]{2, 5, 2, 1, 2}, 5));
    }
}
