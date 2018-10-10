package test;

import hierarchy.SubClass;
import hierarchy.SupClass;

import java.util.ArrayList;
import java.util.List;

/***
 * @Description: Producer Extends Consumer Super
 * @author suntenghao
 * @date 2018-10-09 18:30
 */
public class PECS {
    public static void main(String[] args) {
        //不能确定具体子类，所以不能存，只能取，取成父类
        List<? extends SupClass> list1 = new ArrayList<>();
        //list1.add(new SubType());
        //list1.add(new SupType());
        SupClass supType = list1.get(0);

        //super 能存，但取的时候不能确定父类，只能取成 Object
        List<? super SupClass> list = new ArrayList<>();
        list.add(new SubClass());
        list.add(new SupClass());
        Object object = list.get(0);


    }
}
