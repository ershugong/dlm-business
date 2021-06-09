package com.macro.mall.tiny.modules.dlm.weixin;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Wxconfig {

    public static String APPID = "wxfa0ba562ca81e6e8";

    /**
     * 设置微信小程序的Secret
     */
    public static String SECRET = "a3aecb0cfcfd6b3f5238065564bc1aea";

    /**
     * 设置微信小程序消息服务器配置的token
     */
    public static String token;

    /**
     * 设置微信小程序消息服务器配置的EncodingAESKey
     */
    public static String aesKey;

    /**
     * 消息格式，XML或者JSON
     */
    public static String msgDataFormat = "JSON";

    public   static WxMaService wxService;

    @PostConstruct
    public  void init(){
        wxService = new WxMaServiceImpl();
        WxMaDefaultConfigImpl config = new WxMaDefaultConfigImpl();
        config.setAppid(Wxconfig.APPID);
        config.setSecret(Wxconfig.SECRET);
        wxService.setWxMaConfig(config);
    }


}
