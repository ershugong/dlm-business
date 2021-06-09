package com.macro.mall.tiny.modules.dlm.mapper;

import com.macro.mall.tiny.modules.dlm.model.SendShop;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface SendShopMapper extends BaseMapper<SendShop> {

    @Select("select * from tb_send_shop")
    IPage<SendShop> getSendShopPages(@Param("page") Page page);
}
