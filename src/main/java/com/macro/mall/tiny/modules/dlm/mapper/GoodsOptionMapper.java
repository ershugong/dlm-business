package com.macro.mall.tiny.modules.dlm.mapper;

import com.macro.mall.tiny.modules.dlm.model.GoodsOption;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoodsOptionMapper extends BaseMapper<GoodsOption> {

    @Update("delete from tb_goods_option where goods_id = #{goodsId}")
    void deleteOptionByGoodsId(@Param("goodsId") String goodsId);

    @Select("select * from tb_goods_option where goods_id = #{goodsId} order by level,sort")
    List<GoodsOption> getOptionByGoodsId(@Param("goodsId") String goodsId);

    @Select("select * from tb_goods_option where goods_id = #{goodsId} order by sort desc limit 1")
    GoodsOption getLastGoodsOption(@Param("goodsId") String goodsId);

    @Update("update tb_goods_option set discount_price = #{discountPrice},person_buy_limit = #{personBuyLimit} where id = #{id}")
    void updateGoodsOptionDiscountAndLimitNum(@Param("discountPrice") Double discountPrice,@Param("personBuyLimit") Integer personBuyLimit,@Param("id") String id);
}
