package com.macro.mall.tiny.modules.dlm.vo;

import com.macro.mall.tiny.modules.dlm.model.UserOrderVO;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@TableName("tb_order")
@Getter
@Setter
public class OrderVO {
    private String id;
    @TableField("customer_phone")
    private String customerPhone;
    @TableField("shop_name")
    private String shopName;
    @TableField("create_time")
    private String createTime;
    private String address;
    @TableField("customer_name")
    private String customerName;
    @TableField("total_price")
    private String totalPrice;
    @TableField("open_id")
    private String openId;
    private Integer status;
    private String remark;
    private String shopAddress;
    @TableField("shop_address_id")
    private String shopAddressId;
    @TableField("worker_man")
    private String workerMan;
    //具体的订单信息
    List<UserOrderVO> orderDetailList;

}
