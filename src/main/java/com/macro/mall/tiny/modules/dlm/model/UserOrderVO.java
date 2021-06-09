package com.macro.mall.tiny.modules.dlm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@TableName("tb_user_order")
@Getter
@Setter
public class UserOrderVO {
    private String id;
    @TableField("goods_id")
    private String goodsId;
    @TableField("customer_phone")
    private String customerPhone;
    @TableField("goods_num")
    private Integer goodsNum;
//    @TableField("shop_name")
//    private String shopName;
    @TableField("goods_name")
    private String goodsName;
    private Integer status;
    private String activity;
    //    @TableField("customer_name")
//    private String customerName;
    @TableField("order_id")
    private String orderId;
    private Double price;
    private String unit;
    @TableField("open_id")
    private String openId;
    @TableField("head_pic")
    private String headPic;
    @TableField("discount_price")
    private Double discountPrice;
    private String content;
    @TableField("option_title")
    private String optionTitle;
    @TableField("option_id")
    private String optionId;

    @TableField("customer_name")
    private String customerName;
    @TableField("create_time")
    private String createTime;
    private String address;
    @TableField("shop_name")
    private String shopName;

}
