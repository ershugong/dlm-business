package com.macro.mall.tiny.modules.dlm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@TableName("tb_send_shop")
@Getter
@Setter
public class SendShop {
    private String id;
    @TableField("send_shop_name")
    private String sendShopName;
    private String address;
    private String lat;
    private String lng;
    @TableField("detail_address")
    private String detailAddress;
    private String introduces;
}
