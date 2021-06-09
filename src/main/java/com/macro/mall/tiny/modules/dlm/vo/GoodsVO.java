package com.macro.mall.tiny.modules.dlm.vo;

import com.macro.mall.tiny.modules.dlm.model.GoodsOption;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@TableName("tb_goods")
@Getter
@Setter
public class GoodsVO {
    private String id;
    private String name;
    private Integer num;
    private String content;
    @TableField("head_pic")
    private String headPic;
    private Integer status;
    @TableField("create_time")
    private String createTime;
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
    private List<GoodsOption> goodsOptionList;
    @TableField("update_time")
    private String updateTime;
    @TableField("end_time")
    private String endTime;
}
