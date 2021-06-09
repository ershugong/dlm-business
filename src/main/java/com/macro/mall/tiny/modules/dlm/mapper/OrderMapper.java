package com.macro.mall.tiny.modules.dlm.mapper;

import com.macro.mall.tiny.modules.dlm.model.Order;
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
public interface OrderMapper extends BaseMapper<Order> {

    @Select({"<script>",
            "select * from tb_order where status &lt;&gt; 0",
            "<if test='phone != null and phone != \"\"'>",
            " and customer_phone like concat(concat('%',#{phone}),'%')",
            "</if>",
            "<if test='startTime != null and startTime != \"\"'>",
            " and create_time &gt; #{startTime}",
            "</if>",
            "<if test='endTime != null and endTime != \"\"'>",
            " and create_time &lt; #{endTime}",
            "</if>",
            "<if test='openId != null and openId != \"\"'>",
            " and open_id = #{openId}",
            "</if>",
            "<if test='customerName != null and customerName != \"\"'>",
            " and customer_name like concat(concat('%',#{customerName}),'%')",
            "</if>",
            " order by create_time desc",
    "</script>"})
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "customerPhone",column = "customer_phone"),
            @Result(property = "customerName",column = "customer_name"),
            @Result(property = "shopName",column = "shop_name")
    })
    IPage<OrderVO> getOrderList(@Param("page") Page<OrderVO> page,
                                      @Param("phone")String phone,
                                      @Param("startTime")String startTime,
                                      @Param("endTime")String endTime,
                                      @Param("openId")String openId,
                                      @Param("customerName")String customerName);

    @Select({"<script>",
            "select * from tb_order where status &lt;&gt; 0",
            "<if test='phone != null and phone != \"\"'>",
            " and customer_phone like concat(concat('%',#{phone}),'%')",
            "</if>",
            "<if test='startTime != null and startTime != \"\"'>",
            " and create_time &gt; #{startTime}",
            "</if>",
            "<if test='endTime != null and endTime != \"\"'>",
            " and create_time &lt; #{endTime}",
            "</if>",
            "<if test='openId != null and openId != \"\"'>",
            " and open_id = #{openId}",
            "</if>",
            "<if test='customerName != null and customerName != \"\"'>",
            " and customer_name like concat(concat('%',#{customerName}),'%')",
            "</if>",
            " order by create_time desc",
            "</script>"})
    List<OrderVO> getAllOrderList(@Param("phone")String phone,
                                  @Param("startTime")String startTime,
                                  @Param("endTime")String endTime,
                                  @Param("openId")String openId,
                                  @Param("customerName")String customerName);



    @Select("select * from tb_order where open_id= #{openId} and status = 1 order by create_time desc")
    IPage<OrderVO> getOrderListByOpenId(@Param("page") Page<OrderVO> page,@Param("openId")String openId);
}
