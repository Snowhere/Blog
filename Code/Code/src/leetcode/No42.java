package leetcode;
/**
 * 给定 n 个非负整数表示每个宽度为 1 的柱子的高度图，计算按此排列的柱子，下雨之后能接多少雨水。
 *
 * o o o o o o o o o o o o
 * o o o o o o o x o o o o
 * o o o x 0 0 0 x x 0 x o
 * o x 0 x x 0 x x x x x x
 *
 * 上面是由数组 [0,1,0,2,1,0,1,3,2,1,2,1] 表示的高度图，在这种情况下，可以接 6 个单位的雨水（蓝色部分表示雨水）。 感谢 Marcos 贡献此图。
 *
 * 示例:
 *
 * 输入: [0,1,0,2,1,0,1,3,2,1,2,1]
 * 输出: 6
 *
 */
public class No42 {

    /**
     * 第一感觉就是双指针
     * 先找到左右两个峰顶 leftTop rightTop
     * 因为短板效应
     * 峰顶较小的那一边指针移动，
     * 移动过程中遇到波谷和短板计算差值
     * 移动过程中遇到波峰判断和 leftTop rightTop 的关系
     */
    public int trap(int[] height) {
        int amount = 0;
        if (height == null || height.length < 3) {
            return amount;
        }
        int left = 0;
        int right = height.length - 1;

        while (left < height.length - 1) {
            if (height[left + 1] > height[left]) {
                left++;
            } else {
                break;
            }
        }
        while (right > 0) {
            if (height[right - 1] > height[right]) {
                right--;
            } else {
                break;
            }
        }
        int leftTop = height[left];
        int rightTop = height[right];
        while (left < right) {
            if (leftTop < rightTop) {
                left++;
                if (height[left] < leftTop) {
                    amount += leftTop - height[left];
                } else {
                    leftTop = height[left];
                }
            } else {
                right--;
                if (height[right] < rightTop) {
                    amount += rightTop - height[right];
                } else {
                    rightTop = height[right];
                }
            }
        }
        return amount;
    }

    public static void main(String[] args) {
        No42 no42 = new No42();
        System.out.println(no42.trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));
    }
}
