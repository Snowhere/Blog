package run;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;

public class Main {

    public static void main(String[] args) {
        C3p0Plugin cp = new C3p0Plugin("jdbc:mysql://localhost:3306/map", "root",
            "qwer");
        cp.start();
        ActiveRecordPlugin arp = new ActiveRecordPlugin(cp);
        //arp.addMapping("valid", "pager", Valid.class);
        arp.start();

    }

}
