package leetcode;
/**
 * 给定一个数组，它的第 i 个元素是一支给定股票第 i 天的价格。
 *
 * 如果你最多只允许完成一笔交易（即买入和卖出一支股票一次），设计一个算法来计算你所能获取的最大利润。
 *
 * 注意：你不能在买入股票前卖出股票。
 *
 *  
 *
 * 示例 1:
 *
 * 输入: [7,1,5,3,6,4]
 * 输出: 5
 * 解释: 在第 2 天（股票价格 = 1）的时候买入，在第 5 天（股票价格 = 6）的时候卖出，最大利润 = 6-1 = 5 。
 *      注意利润不能是 7-1 = 6, 因为卖出价格需要大于买入价格；同时，你不能在买入前卖出股票。
 * 示例 2:
 *
 * 输入: [7,6,4,3,1]
 * 输出: 0
 * 解释: 在这种情况下, 没有交易完成, 所以最大利润为 0。
 *
 */
public class No121 {

    /**
     * n^2
     */
    public int maxProfit(int[] prices) {
        int max = 0;
        for (int i = 0; i < prices.length - 1; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                max = Math.max(prices[j] - prices[i], max);
            }
        }
        return max;
    }

    /**
     * 要找的数字是前面部分的最小值，后面部分的最大值
     * 分界线划分不同，当前左半部分区域不同，最小值不同
     * 两个数组，分界线位置为下标
     */
    public int maxProfit2(int[] prices) {
        if (prices.length == 0) {
            return 0;
        }
        int[] left = new int[prices.length];
        int[] right = new int[prices.length];
        int leftMin = prices[0];
        int rightMax = prices[prices.length - 1];
        int max = 0;
        for (int i = 0; i < prices.length; i++) {
            left[i] = Math.min(leftMin, prices[i]);
            leftMin = left[i];
        }
        for (int i = prices.length - 1; i >= 0; i--) {
            right[i] = Math.max(rightMax, prices[i]);
            rightMax = right[i];
        }
        for (int i = 0; i < prices.length - 1; i++) {
            max = Math.max(max, right[i + 1] - left[i]);
        }
        return max;
    }

    /**
     * 每一天都思考一个问题，如果我在之前最低点买进，今天卖了能赚多少钱
     * 遍历数组，记录到目前为止的最小值，记录如果今天卖了赚钱的最大值
     */
    public int maxProfit3(int[] prices) {
        int min = Integer.MAX_VALUE;
        int max = 0;
        for (int i = 0; i < prices.length; i++) {
            min = Math.min(min, prices[i]);
            max = Math.max(max, prices[i] - min);
        }
        return max;
    }

    public static void main(String[] args) {
        No121 no121 = new No121();
        System.out.println(no121.maxProfit3(new int[]{7, 1, 5, 3, 6, 4}));
        System.out.println(no121.maxProfit3(new int[]{7, 6, 4, 3, 1}));
    }
}
