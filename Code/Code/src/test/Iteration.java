package test;

import com.google.common.collect.Sets;

import java.util.Set;

public class Iteration {
    public static void main(String[] args) {
        Set<String> data = Sets.newHashSet("1","2","3");

        //lambda
        data.stream().forEach(item-> System.out.println(item));

        //foreach
        for (String item : data) {
            System.out.println(item);
        }

        //c style
        String[] dataList = data.toArray(new String[data.size()]);
        int length = dataList.length;
        for (int i = 0; i < length; i++) {
            System.out.println(dataList[i]);
        }
    }
}
