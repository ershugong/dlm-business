package com.macro.mall.tiny.modules.dlm.mapper;

import com.macro.mall.tiny.modules.dlm.model.UserOrder;
import com.macro.mall.tiny.modules.dlm.model.UserOrderVO;
import com.macro.mall.tiny.modules.dlm.vo.OrderVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserOrderMapper extends BaseMapper<UserOrder> {

    @Select("select o.*,g.content from tb_user_order o ,tb_goods g where o.goods_id = g.id and order_id = #{orderId} ")
    @Results({
            @Result(property = "goodsId",column = "goods_id"),
            @Result(property = "customerPhone",column = "customer_phone"),
            @Result(property = "goodsNum",column = "goods_num"),
            @Result(property = "goodsName",column = "goods_name"),
            @Result(property = "customerName",column = "customer_name"),
            @Result(property = "orderId",column = "orderId"),
    })
    List<UserOrderVO> getUserOrderByOrderId(@Param("orderId") String orderId);


    @Select({"<script>",
            "select uo.*,o.create_time,o.address,o.customer_phone,o.customer_name,o.shop_name,g.content,uo.option_title from tb_order o,tb_user_order uo,tb_goods g where o.id = uo.order_id and uo.goods_id = g.id and o.status &lt;&gt; 0 ",
            "<if test='phone != null and phone != \"\"'>",
            " and uo.customer_phone like concat(concat('%',#{phone}),'%')",
            "</if>",
            "<if test='customerName != null and customerName != \"\"'>",
            " and o.customer_name like concat(concat('%',#{customerName}),'%')",
            "</if>",
            "<if test='goodsName != null and goodsName != \"\"'>",
            " and uo.goods_name like concat(concat('%',#{goodsName}),'%')",
            "</if>",
            "<if test='startTime != null and startTime != \"\"'>",
            " and o.create_time &gt; #{startTime}",
            "</if>",
            "<if test='endTime != null and endTime != \"\"'>",
            " and o.create_time &lt; #{endTime}",
            "</if>",
            " order by create_time desc",
            "</script>"

    })
    IPage<UserOrderVO> getUserOrderPages(@Param("page") Page<OrderVO> page,
                                         @Param("phone")String phone,
                                         @Param("startTime")String startTime,
                                         @Param("endTime")String endTime,
                                         @Param("customerName")String customerName,
                                         @Param("goodsName")String goodsName);

    @Select("select count(1) from tb_user_order uo,tb_goods_option go,tb_order o where uo.order_id = o.id and uo.option_id = go.id and uo.open_id= #{openId} and uo.option_id = #{optionId} and go.buy_limit_time <= o.create_time and o.status = 1")
    Integer getPersonOrderCount(@Param("openId")String openId,@Param("optionId")String optionId);
}
