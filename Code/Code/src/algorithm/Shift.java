package algorithm;

/**
 * 数组移位操作
 * 数组array循环左移n位
 * 1<n<array.length
 */
public class Shift {
    //通用方法:输出显示
    private void show(Object[] array) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + ",");
        }
        System.out.println();
    }

    /**
     * 原始方法1
     * 额外数组存放左边元素，右边元素左移n位
     */
    public void shift1(Object[] array, int n) {
        Object[] temp = new Object[n];
        int i = 0;
        for (; i < n; i++) {
            temp[i] = array[i];
        }
        for (; i < array.length; i++) {
            array[i - n] = array[i];
        }
        for (i = 0; i < n; i++) {
            array[array.length - n + i] = temp[i];
        }
    }

    /**
     * 原始方法2
     * 所有元素左移1位，循环n次
     */
    public void shift2(Object[] array, int n) {
        Object temp;
        for (int i = 0; i < n; i++) {
            temp = array[0];
            for (int j = 0; j < array.length - 1; j++) {
                array[j] = array[j + 1];
            }
            array[array.length - 1] = temp;
        }
    }

    /**
     * 精巧的杂技,坐标对array.length取模
     * x[0] -> temp ; x[n] -> x[0] ; x[2n] -> x[n] .....  如果坐标循环到0，temp -> x[?]
     * 如果没有移动全部元素，从x[1]再次开始
     */
    public void amazingShift(Object[] array, int n) {
        Object temp;
        int count = 0;
        for (int i = 0; i < array.length && count < array.length; i++) {
            temp = array[i];
            int l = i;
            int k = (l + n) % array.length;
            while (k != i) {
                array[l] = array[k];
                l = k;
                k = (k + n) % array.length;
                count++;
            }
            array[l] = temp;
            count++;
        }
    }

    /**
     * 翻转
     * 1,2,3, 4,5,6,7,8
     * 3,2,1, 4,5,6,7,8  前半段
     * 3,2,1, 8,7,6,5,4  后半段
     * 4,5,6,7,8, 1,2,3  全程
     */
    public void reverseShift(Object[] array, int n) {
        reverse(array, 0, n - 1);
        reverse(array, n, array.length - 1);
        reverse(array, 0, array.length - 1);
    }

    private void reverse(Object[] array, int from, int to) {
        Object temp;
        while (from < to) {
            temp = array[from];
            array[from] = array[to];
            array[to] = temp;
            from++;
            to--;
        }
    }

    public static void main(String[] args) {
        String[] src = {"a", "b", "c", "d", "e", "f"};
        int n = 4;
        Shift shift = new Shift();
        shift.show(src);
        shift.reverseShift(src, n);
        shift.show(src);
    }
}
