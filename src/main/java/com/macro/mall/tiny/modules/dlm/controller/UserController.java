package com.macro.mall.tiny.modules.dlm.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.dlm.model.User;
import com.macro.mall.tiny.modules.dlm.service.ShopService;
import com.macro.mall.tiny.modules.dlm.service.UserService;
import com.macro.mall.tiny.modules.dlm.util.MailUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private ShopService shopService;

    /***
     * 测试获取用户列表
     * @return
     */
    @RequestMapping("/getAllUserList")
    public CommonResult getAllUserList(){
        return CommonResult.success(userService.getUsers());
    }

    /***
     * 测试获取用户
     * @param user
     * @return
     */
    @RequestMapping("/insertUser")
    public CommonResult<User> insertUser(User user){
        return CommonResult.success(userService.insertUser(user));
    }


    /***
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping("/registerUser")
    public CommonResult registerUser(User user){
        return CommonResult.success(userService.registerUser(user));
    }

    /****
     * 用户登录
     * @param user
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("/checkLoginForAdmin")
    public CommonResult checkLoginForAdmin(User user, HttpServletRequest httpServletRequest){
        return CommonResult.success(userService.checkLoginForAdmin(user,httpServletRequest));
    }

    /****
     * 用户登录
     * @param openId
     * @param httpServletRequest
     * @return
     */
    @RequestMapping("/loginForPortal")
    public CommonResult loginForPortal(String openId, HttpServletRequest httpServletRequest){
        httpServletRequest.getSession().setAttribute("user",openId);
        return CommonResult.success(shopService.getAuditShopListCount(openId));
    }

    /**
     * 发送验证码
     * @return
     */
//    @RequestMapping("/sendCheckNum")
//    public CommonResult<Boolean> sendCheckNum(User user){
//        return CommonResult.success(userService.sendCheckNum(user));
//    }

    @RequestMapping("/testSend")
    public void testSend(User user){
        userService.testSend(user);
    }

    @RequestMapping("/sendMail")
    public void sendMail(){
        MailUtil.sendMail();
    }

}
