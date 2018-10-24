package me.snowhere.controller;

import me.snowhere.common.Response;
import me.snowhere.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("all")
    @ResponseBody
    public Response getAllUser() {
        return Response.ok(userService.getAllUser());
    }
}
