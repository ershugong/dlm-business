package com.macro.mall.tiny.modules.dlm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@TableName("tb_shop")
@Setter
@Getter
public class Shop {
    private String id;
    @TableField("shop_name")
    @ApiModelProperty("店名")
    private String shopName;
    @ApiModelProperty("店名对应的联系号码")
    @TableField("shop_phone")
    private String shopPhone;
    @TableField("open_id")
    private String openId;
    private String address;
    @TableField("business_no")
    private String businessNo;
    @TableField("business_pic")
    private String businessPic;
    @TableField("is_default")
    private Integer isDefault;
    @TableField("customer_name")
    private String customerName;
    private Integer status;
    @TableField("area_code")
    private String areaCode;
    @TableField("create_time")
    private Date createTime;
}
