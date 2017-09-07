package algorithm;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/3.
 */
public class Random {
    public <T> List<T> random(List<T> array, int need) {
        List<T> result = new ArrayList<>();
        //当前概率=当前需要/当前剩余
        for (int remain = array.size(); remain > 0; remain--) {
            if (Math.random() * remain < need) {
                result.add(array.get(array.size() - remain));
                need -= 1;
            }
        }
        return result;
    }

    public static void main(String args[]) {
        List<Integer> list = Lists.newArrayList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Random random = new Random();
        List<Integer> random1 = random.random(list, 3);
        System.out.println(random1);
    }
}
