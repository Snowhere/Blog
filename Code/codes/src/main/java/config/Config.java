package config;

import com.jfinal.config.*;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.c3p0.C3p0Plugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import controller.IndexController;
import model.CodeModel;
import model.UserAuthModel;
import model.UserModel;

/**
 * API引导式配置
 */
public class Config extends JFinalConfig {

    /**
     * 配置常量
     */
    public void configConstant(Constants me) {
        me.setDevMode(true);
        me.setViewType(ViewType.JSP);
        me.setMaxPostSize(1024 * 1024 * 30);
        me.setError404View("");
        me.setError500View("");
    }

    /**
     * 配置路由
     */
    public void configRoute(Routes me) {
        me.add("/", IndexController.class);
    }



    /**
     * 配置插件
     */
    public void configPlugin(Plugins me) {
        // 配置C3p0数据库连接池插件
        C3p0Plugin c3p0Plugin = new C3p0Plugin(
            "jdbc:mysql://localhost/map?useUnicode=true", "", "");
        me.add(c3p0Plugin);

        // 配置ActiveRecord插
        ActiveRecordPlugin arp = new ActiveRecordPlugin(c3p0Plugin);
        arp.setShowSql(true);
        me.add(arp);

        arp.addMapping("code", CodeModel.class);
        arp.addMapping("user", UserModel.class);
        arp.addMapping("user_auth", UserAuthModel.class);
    }

    @Override
    public void configInterceptor(Interceptors me) {
    }

    @Override
    public void configHandler(Handlers me) {
    }
    public void configEngine(Engine engine) {

    }
}