package com.macro.mall.tiny.modules.dlm.weixin;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.alibaba.fastjson.JSONObject;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.dlm.exception.MyEumException;
import com.macro.mall.tiny.modules.dlm.exception.MyException;
import com.macro.mall.tiny.modules.dlm.util.CommonUtil;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.io.IOException;

@RestController
@RequestMapping("/wxLittle")
public class WxLittleController {

    /**
     * 根据code获取
     * @param code
     * @return
     */
    @RequestMapping(value = "/getLoginInfo",method = RequestMethod.POST)
    public CommonResult getLoginInfo(String code){
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + Wxconfig.APPID+ "&secret="
                + Wxconfig.SECRET + "&js_code=" + code + "&grant_type=authorization_code";
        String result = CommonUtil.GET(url);
        JSONObject oppidObj = JSONObject.parseObject(result);
        String openid = (String) oppidObj.get("openid");
        String session_key = (String) oppidObj.get("session_key");
        oppidObj.put("openid", openid);
        oppidObj.put("session_key", session_key);
        if (oppidObj.containsKey("unionid")) {
            oppidObj.put("unionid", (String) oppidObj.get("unionid"));
        }else{
            oppidObj.put("unionid", "");
        }

        String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?appid="+Wxconfig.APPID+"&secret="+Wxconfig.SECRET+"&grant_type=client_credential";
        String resultToken = CommonUtil.GET(accessTokenUrl);
        JSONObject tokenObj = JSONObject.parseObject(resultToken);
        oppidObj.put("accessToken",tokenObj.get("access_token"));

        return CommonResult.success(oppidObj);
    }

    /**
     * 根据code获取
     * @param code
     * @return
     */
    @RequestMapping(value = "/getAccessToken",method = RequestMethod.POST)
    public CommonResult getAccessToken(String code){
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + Wxconfig.APPID+ "&secret="
                + Wxconfig.SECRET + "&js_code=" + code + "&grant_type=authorization_code";
        String reusult = CommonUtil.GET(url);
        JSONObject oppidObj = JSONObject.parseObject(reusult);
        String openid = (String) oppidObj.get("openid");
        String session_key = (String) oppidObj.get("session_key");
        oppidObj.put("openid", openid);
        oppidObj.put("session_key", session_key);
        if (oppidObj.containsKey("unionid")) {
            oppidObj.put("unionid", (String) oppidObj.get("unionid"));
        }else{
            oppidObj.put("unionid", "");
        }
        return CommonResult.success(oppidObj);
    }

    @RequestMapping(value = "/callBack", method = RequestMethod.POST)
    public CommonResult<AuthorizeResponse> callBack(WxUserRegisterRequest registerRequest) throws ServletException, IOException {
        try {
            WxMaJscode2SessionResult session = Wxconfig.wxService.getUserService().getSessionInfo(registerRequest.getCode());
            String accessToken = Wxconfig.wxService.getAccessToken();
            WxMaUserInfo userInfo = Wxconfig.wxService.getUserService().getUserInfo(session.getSessionKey(), registerRequest.getEncryptedData(), registerRequest.getIv());
            AuthorizeResponse authorizeResponse = new AuthorizeResponse()
                    .setOpenid(session.getOpenid())
                    .setUnionid(session.getUnionid())
                    .setAccessToken(accessToken)
                    .setWxMaUserInfo(userInfo)
                    .setSessionKey(session.getSessionKey());
            return CommonResult.success(authorizeResponse);
        } catch (WxErrorException e) {
            throw new MyException(MyEumException.WX_LITTLE_ERROR);
        }
    }
}
