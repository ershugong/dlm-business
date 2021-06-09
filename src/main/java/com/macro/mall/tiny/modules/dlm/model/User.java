package com.macro.mall.tiny.modules.dlm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("tb_user")
public class User {
    private String id;
    @TableField("user_name")
    private String userName;
    private String phone;
    @TableField("check_num")
    private String checkNum;
    @TableField("shop_name")
    private String shopName;
    private String password;
}
