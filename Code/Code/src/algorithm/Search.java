package algorithm;

/**
 * Created by Administrator on 2017/8/23.
 */
public class Search {
    public <T extends Comparable<T>> int binarySearch(T[] orderArray, T target, int from, int to) {
        if (from > to) {
            return -1;
        }
        int middleIndex = (to - from) / 2 + from;
        T middle = orderArray[middleIndex];
        if (middle.compareTo(target) < 0) {
            return binarySearch(orderArray, target, middleIndex + 1, to);
        } else if (middle.compareTo(target) > 0) {
            return binarySearch(orderArray, target, from, middleIndex - 1);
        } else {
            return middleIndex;
        }
    }

    public static void main(String args[]) {
        Search search = new Search();
        Integer[] array = {1, 2, 3, 5, 6, 7};
        int result = search.binarySearch(array, 4, 0, array.length - 1);
        System.out.println(result);
    }
}
