package com.macro.mall.tiny.modules.dlm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@TableName("tb_goods")
@Getter
@Setter
public class Goods {
    private String id;
    private String name;
    private Integer num;
    private String content;
    @TableField("head_pic")
    private String headPic;
    @TableField("create_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Integer status;
    @TableField("classify_id")
    private String classifyId;
    @TableField("classify_name")
    private String classifyName;
    @TableField("surplus_num")
    private Integer surplusNum;
    private Double price;
    @TableField("discount_price")
    private Double discountPrice;
    private String activity;
    private String unit;
    @TableField("file_content")
    private String fileContent;
    @TableField("is_great")
    private Integer isGreat;
    @TableField("update_time")
    private Date updateTime;
    @TableField("end_time")
    private Date endTime;
}
