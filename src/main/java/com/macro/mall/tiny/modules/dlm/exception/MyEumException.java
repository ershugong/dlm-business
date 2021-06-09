package com.macro.mall.tiny.modules.dlm.exception;

import com.macro.mall.tiny.common.api.IErrorCode;

/***
 * 自定义异常枚举类
 */
public enum MyEumException implements IErrorCode {

    TEST_1(789,"TEST1 自定义异常"),
    TEST_2(788,"TEST2 自定义异常"),
    HAVE_REGISTER(8001,"当前手机号已注册"),
    NO_GOODS_IN_ORDER(8002,"当前订单没有货物"),
    THE_GOODS_IS_DOWN(8004,"当前货物「%s」,已下架"),
    EMPTY_ORDER_FOR_EXPORT(8005,"当前没有订单"),
    TIME_OUT_CANCEL(8006,"当前订单下单超过12小时，无法取消"),
    UPLOAD_PIC_FAILURE(8007,"上传图片失败"),
    GOODS_OPTION_EMPTY(8008,"请添加自定义类型"),
    GOODS_OPTION_DEL(8009,"当前标签选项不存在"),
    PERSON_BUY_GOODS_OUT_LIMIT(8010,"当前货物「%s」,限购数量:「%s」"),
    TIME_FORMAT_ERROR(8011,"限时购时间格式有误，请检查「%s」"),
    ADDRESS_NOT_AUDIT(8012,"收货地址未通过审核"),
    CLASSIFY_JSON_FORMAT_FAILURE(8013,"分类列表json格式有误"),
    PIC_PLAY_JSON_FORMAT_FAILURE(8014,"轮播图列表json格式有误"),
    USER_LOGIN_FAILURE(8015,"登录失败，账号或密码错误"),
    GOODS_NUM_NOT_ENOUGH(8003,"当前货物「%s」,库存不足");

    private long code;
    private String message;

    MyEumException(long code,String message){
        this.code = code;
        this.message = message;
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
