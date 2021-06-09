package com.macro.mall.tiny.modules.dlm.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
//@RequestMapping("/")
public class ErrorController {

    @Value("${interceptorLoginPath}")
    private String interceptorLoginPath;

    @RequestMapping("/login")
    public void sendLoginRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect(interceptorLoginPath);
        response.setStatus(401);
//        ModelAndView mav=new ModelAndView("http://localhost:8080/lubao/login.html"); // 绝对路径OK
//        return new User();
    }
}
