package com.macro.mall.tiny.modules.dlm.weixin;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AuthorizeResponse {
    private String sessionKey;
    private String openid;
    private String unionid;
    private String accessToken;
    private WxMaUserInfo wxMaUserInfo;
}
