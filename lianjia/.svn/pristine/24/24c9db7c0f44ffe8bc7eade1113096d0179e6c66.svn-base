package me.snohwere.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class Util {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static String SPLIT_ONE = "：";
    public static String SPLIT_TWO = ":";
    public static String SPLIT_THREE = "：";
    
    public static List<String> userAgentList= new ArrayList<>();
    public static String getRandomAgent(){
        if (userAgentList.size()==0) {
            userAgentList.add("Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US; rv:1.9.1.6) Gecko/20091201 Firefox/3.5.6");
            userAgentList.add("Mozilla/5.0 (Windows NT 6.2) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.12 Safari/535.11");
            userAgentList.add("Mozilla/5.0 (compatible; MSIE 10.0; Windows NT 6.2; Trident/6.0)");
            userAgentList.add("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:34.0) Gecko/20100101 Firefox/34.0");
            userAgentList.add("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/44.0.2403.89 Chrome/44.0.2403.89 Safari/537.36");
            userAgentList.add("Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50");
            userAgentList.add("Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50");
            userAgentList.add("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0");
            userAgentList.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10.6; rv:2.0.1) Gecko/20100101 Firefox/4.0.1");
            userAgentList.add("Mozilla/5.0 (Windows NT 6.1; rv:2.0.1) Gecko/20100101 Firefox/4.0.1");
            userAgentList.add("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_0) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");
            userAgentList.add("Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; en) Presto/2.8.131 Version/11.11");
            userAgentList.add("Opera/9.80 (Windows NT 6.1; U; en) Presto/2.8.131 Version/11.11");
        
        }
        return userAgentList.get((int)Math.floor(Math.random()*userAgentList.size()));
    }
    
    public static void main(String []arg){
        for(int i=0;i<100;i++){
            System.out.println(getRandomAgent());
        }
    }
}
