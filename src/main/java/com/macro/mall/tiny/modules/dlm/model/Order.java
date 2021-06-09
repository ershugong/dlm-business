package com.macro.mall.tiny.modules.dlm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@TableName("tb_order")
@Getter
@Setter
public class Order {
    private String id;
    @TableField("customer_phone")
    private String customerPhone;
    @TableField("shop_name")
    private String shopName;
    @TableField("create_time")
    private Date createTime;
    private String address;
    @TableField("customer_name")
    private String customerName;
    @TableField("total_price")
    private Double totalPrice;
    @TableField("open_id")
    private String openId;
    private Integer status;
    private String remark;
    @TableField("shop_address")
    private String shopAddress;
    @TableField("shop_address_id")
    private String shopAddressId;
    @TableField("worker_man")
    private String workerMan;
}
