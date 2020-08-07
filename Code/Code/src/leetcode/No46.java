package leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 给定一个 没有重复 数字的序列，返回其所有可能的全排列。
 *
 * 示例:
 *
 * 输入: [1,2,3]
 * 输出:
 * [
 *   [1,2,3],
 *   [1,3,2],
 *   [2,1,3],
 *   [2,3,1],
 *   [3,1,2],
 *   [3,2,1]
 * ]
 *
 */
public class No46 {

    public List<List<Integer>> permute(int[] nums) {
        return permute(Arrays.stream(nums).boxed().collect(Collectors.toList()));
    }

    public List<List<Integer>> permute(List<Integer> nums) {
        List<List<Integer>> result = new ArrayList<>();
        if (nums.size() == 1) {
            result.add(new ArrayList<>(nums));
        }else {
            for (Integer num : nums) {
                ArrayList<Integer> remain = new ArrayList<>(nums);
                remain.remove(num);
                List<List<Integer>> permute = permute(remain);
                for (List<Integer> integers : permute) {
                    integers.add(num);
                    result.add(integers);
                }
            }

        }
        return result;
    }

    public static void main(String[] args) {
        No46 no46 = new No46();
        System.out.println(no46.permute(new int[]{1,2,3}));
    }
}
