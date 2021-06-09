package com.macro.mall.tiny.modules.dlm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@TableName("tb_classify")
@Setter
@Getter
public class Classify {
    private String id;
    @TableField("classify_name")
    private String classifyName;
    @TableField("classify_detail")
    private String classifyDetail;
    private Integer sort;
}
