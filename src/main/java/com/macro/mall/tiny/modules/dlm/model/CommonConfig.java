package com.macro.mall.tiny.modules.dlm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@TableName("tb_config")
@Getter
@Setter
public class CommonConfig {
    private String id;
    @TableField("config_key")
    private String configKey;
    @TableField("config_value")
    private String configValue;
}
