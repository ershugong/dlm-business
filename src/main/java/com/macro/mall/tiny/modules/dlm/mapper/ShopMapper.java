package com.macro.mall.tiny.modules.dlm.mapper;

import com.macro.mall.tiny.modules.dlm.model.Shop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopMapper extends BaseMapper<Shop> {

    @Select({"<script>",
            "select * from tb_shop where 1=1 ",
            "<if test = 'status != null'>",
            "and status = #{status} ",
            "</if>",
            "<if test = 'customerName != null and customerName != \"\"'>",
            "and customer_name like concat(concat('%',#{customerName}),'%') ",
            "</if>",
            "<if test = 'shopName != null and shopName != \"\"'>",
            "and shop_name like concat(concat('%',#{shopName}),'%') ",
            "</if>",
            " order by create_time desc",
            "</script>"})
    IPage<Shop> getShopPage(@Param("page") Page<Shop> page,
                            @Param("status") Integer status,
                            @Param("customerName") String customerName,
                            @Param("shopName") String shopName);

    @Select({"<script>",
            "select * from tb_shop where open_id = #{openId} " ,
            "<if test = 'customerName != null and customerName != \"\"'>",
            "and customer_name like concat(concat('%',#{customerName}),'%') ",
            "</if>",
            "<if test = 'shopName != null and shopName != \"\"'>",
            "and shop_name like concat(concat('%',#{shopName}),'%') ",
            "</if>",
            " order by create_time desc",
            "</script>"})
    List<Shop> getShopListByOpenId(@Param("openId") String openId,@Param("shopName") String shopName,@Param("customerName") String customerName);

    @Select("select count(1) from tb_shop where open_id = #{openId} and status in (1,3)")
    Integer getAuditShopListCount(@Param("openId") String openId);

    @Update("update tb_shop set is_default = 0 where open_id = #{openId} and is_default = 1")
    void updateShopNotDefault(@Param("openId") String openId);
}
