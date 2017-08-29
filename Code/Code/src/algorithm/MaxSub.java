package algorithm;

/**
 * 一维数组最大子串
 */
public class MaxSub {

    private int max(int... array) {
        int max = 0;
        for (int i : array) {
            max = Math.max(max, i);
        }
        return max;
    }

    /**
     * 遍历所有子串
     * O(n^3)
     *
     * @param array
     * @return
     */
    public Integer search1(Integer[] array) {
        int max = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = i; j < array.length; j++) {
                int sum = 0;
                for (int k = i; k <= j; k++) {
                    sum += array[k];
                }
                max = max(max, sum);
            }
        }
        return max;
    }

    /**
     * 遍历所有子串
     * 求和优化
     * O(n^2)
     *
     * @param array
     * @return
     */
    public Integer search2(Integer[] array) {
        int max = 0;
        for (int i = 0; i < array.length; i++) {
            int sum = 0;
            for (int j = i; j < array.length; j++) {
                sum += array[j];
                max = max(max, sum);
            }
        }
        return max;
    }

    /**
     * 分治
     * 数组分为前后两段，最大子串有三种情况
     * 在前半段a、后半段b、跨越前后c
     * 递归分析a和b，特殊分析c
     * O(nlogn)
     *
     * @param array
     * @return
     */
    public Integer divideSearch(Integer[] array, int from, int to) {
        //0 element
        if (from > to) {
            return 0;
        }
        //1 element
        if (from == to) {
            return max(0, array[from]);
        }
        //c = lmax + rmax
        int middle = (from + to) / 2;
        int lmax = 0, rmax = 0, sum = 0;
        for (int i = middle; i >= from; i--) {
            sum += array[i];
            lmax = max(lmax, sum);
        }
        sum = 0;
        for (int i = middle + 1; i <= to; i++) {
            sum += array[i];
            rmax = max(sum, rmax);
        }
        return max(lmax + rmax, divideSearch(array, from, middle), divideSearch(array, middle + 1, to));
    }

    /**
     * 一遍扫描
     * 假设找到了array[0]到array[i-1]的最大子串，扩展为包含array[i]
     * 分为两种情况，最大子串 包含/不包含 array[i-1]
     * O(n)
     *
     * @param array
     * @return
     */
    public Integer scanSearch(Integer[] array) {
        int maxSoFar = 0;//不包含
        int maxEndingHere = 0;//包含
        for (int i = 0; i < array.length; i++) {
            maxEndingHere = max(maxEndingHere + array[i], 0);
            maxSoFar = max(maxSoFar, maxEndingHere);
        }
        return maxSoFar;
    }

    public static void main(String args[]) {
        MaxSub maxSub = new MaxSub();
        Integer[] array = {1, -2, 3, 6, -7, 8, -9, 4};
        int result = maxSub.scanSearch(array);
        System.out.println(result);
    }
}
