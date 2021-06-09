package com.macro.mall.tiny.modules.dlm.weixin;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class WxUserRegisterRequest {

    private String sessionKey;
    private String encryptedData;
    private String iv;
    private String openid;
    private String unionid;
    private String nickname;
    private String avatar;
    private int gender;
    private String code;
    private String ip;

}
