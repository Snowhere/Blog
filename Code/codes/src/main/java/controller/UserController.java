package controller;

import com.jfinal.core.Controller;

public class UserController extends Controller {
    /**
     * 登录
     */
    public void login() {
        renderJsp("index.jsp");
    }

    /**
     * 注册
     */
    public void register() {
        render("");
    }
}
