package test;

import java.util.ArrayList;
import java.util.List;
/***
 * @Description: Producer Extends Consumer Super
 * @author suntenghao
 * @date 2018-10-09 18:30
 */
public class PECS {
    public static void main(String[] args) {
        List<? super SupType> list = new ArrayList<>();
        list.add(new SubType());
        list.add(new SupType());
        Object object = list.get(0);

        List<? extends SupType> list1 = new ArrayList<>();
        list1.add(new SubType());
        list1.add(new SupType());
        SupType supType = list1.get(0);
    }
}
