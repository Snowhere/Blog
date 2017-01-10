package algorithm;

public class Sort {
    //待排序数组
    private int[] numbers = { 4, 3, 1, 3, 2, 5, 2 };

    //通用方法:输出显示
    private void show() {
        for (int i = 0; i < numbers.length; i++) {
            System.out.print(numbers[i]);
        }
        System.out.println();
    }

    //通用方法:交换两个位置的数字
    private static void exchange(int[] list, int i, int j) {
        int temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }

    /**
     *  插入排序-直接插入排序
     *  (从前到后)
     */
    public void sort1() {
        int i, j;
        //考察每个元素
        for (i = 1; i < numbers.length; i++) {
            int temp = numbers[i];
            //已排序好的队列依次向后移位,寻找合适位置
            for (j = i - 1; j >= 0; j--) {
                if (temp < numbers[j]) {
                    numbers[j + 1] = numbers[j];
                } else {
                    break;
                }
            }
            //将改元素插入合适位置
            numbers[j + 1] = temp;
        }
    }

    /**
     *  选择排序-简单选择排序
     *  (从前到后)
     */
    public void sort2() {
        int min, i, j;
        //每次从剩余的队列中选择最小值的位置
        for (i = 0; i < numbers.length; i++) {
            min = i;
            for (j = i; j < numbers.length; j++) {
                if (numbers[j] < numbers[min]) {
                    min = j;
                }
            }
            //与当前元素交换位置
            exchange(numbers, i, min);
        }
    }

    /**
     * 交换排序-冒泡排序
     * (从后到前)
     */
    public void sort3() {
        int i, j;
        for (i = 0; i < numbers.length - 1; i++) {
            for (j = 0; j < numbers.length - i - 1; j++) {
                //每次交换相邻两个元素,大的沉底
                if (numbers[j + 1] < numbers[j]) {
                    exchange(numbers, j, j + 1);
                }
            }
        }
    }

    // 交换排序-快排序

    // 选择一个值作为中间点，小的放前面，大的放后面。递归操作
    public static void main(String[] args) {
        Sort sort = new Sort();
        sort.show();
        sort.sort3();
        sort.show();
    }
}
