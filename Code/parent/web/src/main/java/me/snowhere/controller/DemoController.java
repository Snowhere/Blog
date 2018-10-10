package me.snowhere.controller;

import me.snowhere.common.Response;
import me.snowhere.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("demo")
public class DemoController {
    @Autowired
    private DemoService demoService;

    @RequestMapping("num")
    @ResponseBody
    public Response getNum() {
        throw new RuntimeException();
       // return  Response.FAIL;
    }
}