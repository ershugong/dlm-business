package com.macro.mall.tiny.modules.dlm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@TableName("tb_pic_play")
@Setter
@Getter
public class PicPlay {
    private String id;
    @TableField("pic_play_url")
    private String picPlayUrl;
    private Integer sort;
}
