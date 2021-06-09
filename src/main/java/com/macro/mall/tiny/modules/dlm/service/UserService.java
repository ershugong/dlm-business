package com.macro.mall.tiny.modules.dlm.service;

import com.macro.mall.tiny.modules.dlm.mapper.UserMapper;
import com.macro.mall.tiny.modules.dlm.exception.MyEumException;
import com.macro.mall.tiny.modules.dlm.exception.MyException;
import com.macro.mall.tiny.modules.dlm.model.User;
import com.macro.mall.tiny.modules.dlm.util.CommonUtil;
//import com.kewail.sdk.sms.yun.SmsSingleSender;
//import com.kewail.sdk.sms.yun.SmsSingleSenderResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("userService")
public class UserService {
    @Resource
    private UserMapper userMapper;

    public User insertUser(User user){
        userMapper.insert(user);
        return user;
    }

    public List<User> getUsers(){
        return userMapper.selectByMap(new HashMap<>());
    }

    //用户注册
    public User registerUser(User user){
        Map<String,Object> map = new HashMap<>(1);
        map.put("phone",user.getPhone());
        List<User> userList = userMapper.selectByMap(map);
        //当前手机号已注册
        if(!CommonUtil.AssertEmpty(userList)){
            throw new MyException(MyEumException.HAVE_REGISTER);
        }
        user.setId(CommonUtil.getUUID32());
        userMapper.insert(user);
        return user;
    }

    //用户登录
    public User checkLogin(User user, HttpServletRequest httpServletRequest){
        if(!CommonUtil.StringIsEmpty(user.getPhone()) && (!CommonUtil.StringIsEmpty(user.getCheckNum()) || !CommonUtil.StringIsEmpty(user.getPassword()))){
            Map<String,Object> map = new HashMap<>(2);
            map.put("phone",user.getPhone());
            if(!CommonUtil.StringIsEmpty(user.getCheckNum())){
                map.put("checkNum",user.getCheckNum());
            }else{
                map.put("password",user.getPassword());
            }
            List<User> userList = userMapper.selectByMap(map);
            if (!CommonUtil.AssertEmpty(userList)){
                //储存于session中  避免重复登录
                User userResult= userList.get(0);
                httpServletRequest.getSession().setAttribute("user",userResult);
                return userResult;
            }
        }

        return null;
    }

    //用户登录
    public User checkLoginForAdmin(User user, HttpServletRequest httpServletRequest){
            Map<String,Object> map = new HashMap<>(2);
            map.put("user_name",user.getUserName());
            map.put("password",user.getPassword());
            List<User> userList = userMapper.selectByMap(map);
            if (!CommonUtil.AssertEmpty(userList)){
                //储存于session中  避免重复登录
                User userResult= userList.get(0);
                httpServletRequest.getSession().setAttribute("user",userResult);
                return userResult;
            }else{
                throw new MyException(MyEumException.USER_LOGIN_FAILURE);
            }
    }

    //发送六位随机验证码
//    public boolean sendCheckNum(User user){
//        try {
//            //请根据实际 accesskey 和 secretkey 进行开发，以下只作为演示 sdk 使用
//            String accesskey = "5fa15903c780c3082ba79756";
//            String secretkey ="80680596186b4d99bd36eb308773e215";
//            //手机号码
//            String phoneNumber = "18816794829";
//            //初始化单发
//            SmsSingleSender singleSender = new SmsSingleSender(accesskey, secretkey);
//            SmsSingleSenderResult singleSenderResult;
//
//            String checkNum = CommonUtil.getSixRandomNum().toString();
//
//            //普通单发,注意前面必须为【】符号包含，置于头或者尾部。 0:普通短信;1:营销短信（强调：要按需填值，不然会影响到业务的正常使用）
//            singleSenderResult = singleSender.send(0, "86", phoneNumber, "【多来美】尊敬的用户：您的验证码：" + checkNum + "，工作人员不会索取，请勿泄漏。", "", "");
//            System.out.println(singleSenderResult);
//            if(singleSenderResult.result == 0){
//                userMapper.updateUserCheckNum(user.getPhone(),checkNum);
//                return true;
//            }
//
//            return false;
//
//            //语音验证码发送
//            //SmsVoiceVerifyCodeSender smsVoiceVerifyCodeSender = new SmsVoiceVerifyCodeSender(accesskey,secretkey);
//            //SmsVoiceVerifyCodeSenderResult smsVoiceVerifyCodeSenderResult = smsVoiceVerifyCodeSender.send("86",phoneNumber, "444144",2,"");
//            //System.out.println(smsVoiceVerifyCodeSenderResult);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return false;
//    }

    public void testSend(User user){
        userMapper.updateUserCheckNum(user.getPhone(),CommonUtil.getSixRandomNum().toString());
    }
}
