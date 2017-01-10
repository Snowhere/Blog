package test;

import java.util.ArrayList;
import java.util.List;

public class Code {

	public static void main(String[] args) {
	    List<String> list = new ArrayList<>();
	    list.add("1");
	    list.add("2");
	    list.add("3");
	    list.add("4");
	    list.add("5");
	    for (int i=0; i < list.size();i++) {
	        String dim = list.get(i);
            if (dim.equals("3") || dim.equals("3")) {
                list.remove(i);
                break;
            }
        }
	    for(int i=0; i < list.size();i++){
	        System.out.println(list.get(i));;
	    }
	}
	
	

}
