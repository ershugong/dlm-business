package com.macro.mall.tiny.modules.dlm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@TableName("tb_area_config")
@Getter
@Setter
public class AreaConfig {
    private String id;
    @TableField("area_code")
    private String areaCode;
    @TableField("area_name")
    private String areaName;
}
