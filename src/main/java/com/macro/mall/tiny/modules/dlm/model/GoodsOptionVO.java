package com.macro.mall.tiny.modules.dlm.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@TableName("tb_goods_option")
@Getter
@Setter
public class GoodsOptionVO {
    private String id;
    @TableField("goods_id")
    private String goodsId;
    private String price;
    private Integer level;
    private Integer sort;
    private String title;
    @TableField("discount_price")
    private String discountPrice;
    private Integer num;
    @TableField("surplus_num")
    private Integer surplusNum;
    @TableField("sell_num")
    private Integer sellNum;
    @TableField("person_buy_limit")
    private Integer personBuyLimit;
    @TableField("buy_limit_time")
    private String buyLimitTime;
}
