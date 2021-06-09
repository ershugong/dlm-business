package com.macro.mall.tiny.modules.dlm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@TableName("tb_goods_option")
@Getter
@Setter
public class GoodsOption {
    private String id;
    @TableField("goods_id")
    private String goodsId;
    private Double price;
    private Integer level;
    private Integer sort;
    private String title;
    @TableField("discount_price")
    private Double discountPrice;
    private Integer num;
    @TableField("surplus_num")
    private Integer surplusNum;
    @TableField("sell_num")
    private Integer sellNum;
    @TableField("person_buy_limit")
    private Integer personBuyLimit;
    @TableField("buy_limit_time")
    private Date buyLimitTime;
}
