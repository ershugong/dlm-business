package com.macro.mall.tiny.modules.dlm.weixin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/wx")
public class WxController {

    @Value("${wxAppId}")
    private String appId;
    @Value("${wxAppSecret}")
    private String appSecret;


    @RequestMapping(value = "/callBack", method = RequestMethod.POST)
    public Object callBack(String code,HttpServletRequest request) throws ServletException, IOException {
//        String code =req.getParameter("code");
        System.out.println("授权返回code信息---------:"+code);
        //第二步：通过code换取网页授权access_token (获取openid接口)
        //WXAuthUtil.APPID 公众号的Appid
        //appSecret 公众号的APPSECRET 可以在微信公众号后台获取
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId
                + "&secret="+appSecret
                + "&code="+code
                + "&grant_type=authorization_code";

        //发送请求 get提交 拿code凭证去获取openid和access_token
        JSONObject jsonObject = JSONObject.parseObject(WXAuthUtil.get(url));

        String openid = jsonObject.getString("openid");
        String access_token = jsonObject.getString("access_token");
        String refresh_token = jsonObject.getString("refresh_token");

        //拿到用户openid 和access_token 去获取用户信息
        //第五步,验证access_token是否过期
        String chickUrl="https://api.weixin.qq.com/sns/auth?access_token="+access_token+"&openid="+openid;
        JSONObject chickuserInfo = JSONObject.parseObject(WXAuthUtil.get(chickUrl));

        //由于access_token拥有较短的有效期，当access_token超时后，可以使用refresh_token进行刷新，refresh_token有效期为30天，当refresh_token失效之后，需要用户重新授权
        //如果发送错误，就有可能是access_token过期了 errcode 的值是 0 那么就是没有问题，access_token没有过期,不等于0就是过期，那么我们要去刷新access_token
        if(!"0".equals(chickuserInfo.getString("errcode"))){
            //第三步刷新access_token(刷新access_token接口)
            String refreshTokenUrl="https://api.weixin.qq.com/sns/oauth2/refresh_token?appid="+openid+"&grant_type=refresh_token&refresh_token="+refresh_token;

            JSONObject refreshInfo = JSONObject.parseObject(WXAuthUtil.get(chickUrl));

            access_token=refreshInfo.getString("access_token");
        }

        //获取用户拿到openid 和access_token去获取用户信息，在页面中进行业务处理，获取存储在数据库中:
        //第四步(获取用户接口)
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token="+access_token
                + "&openid="+openid
                + "&lang=zh_CN";

        JSONObject userInfo = JSONObject.parseObject(WXAuthUtil.get(infoUrl));

        System.out.println("userInfo------:"+userInfo.toString());
        userInfo.getString("nickname");
        userInfo.getString("sex");
        userInfo.getString("country");
        userInfo.getString("province");
        userInfo.getString("city");
        userInfo.getString("headimgurl");
        userInfo.getString("language");
        userInfo.getString("privilege");
        userInfo.getString("openid");
        userInfo.getString("unionid");
        request.getSession().setAttribute("user",userInfo);
        //modelMap.put("nickname", userInfo.getString("nickname"));//保存授权用户
        return userInfo;
    }
}
