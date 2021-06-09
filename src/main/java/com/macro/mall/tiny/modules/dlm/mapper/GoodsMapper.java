package com.macro.mall.tiny.modules.dlm.mapper;

import com.macro.mall.tiny.modules.dlm.model.Goods;
import com.macro.mall.tiny.modules.dlm.vo.GoodsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface GoodsMapper extends BaseMapper<Goods> {

    @Select({"<script>",
            "select * from tb_goods where status &lt;&gt; -1 ",
            "<if test = 'name != null and name != \"\"'>",
            " and name like concat(concat('%',#{name}),'%') ",
            "</if>",
            "<if test = 'startTime != null and startTime != \"\"'>",
            " and create_time &gt;= #{startTime} ",
            "</if>",
            "<if test = 'endTime != null and endTime != \"\"'>",
            " and create_time &lt;= #{endTime} ",
            "</if>",
            "<if test = 'classifyId != null and classifyId != \"\"'>",
            " and classify_id = #{classifyId} ",
            "</if>",
            "<if test = 'status != null and status != \"\"'>",
            " and status = #{status} ",
            "</if>",
            "<if test = 'classifyName != null and classifyName != \"\"'>",
            " and classify_name like concat(concat('%',#{classifyName}),'%') ",
            "</if>",
            "<if test = 'isGreat != null and isGreat != \"\"'>",
            " and is_great = #{isGreat} ",
            "</if>",
            " order by update_time desc, create_time desc",
            "</script>"
    })
    @Results({
            @Result(property = "createTime",column = "create_time"),
            @Result(property = "headPic",column = "head_pic"),
            @Result(property = "classifyId",column = "classify_id"),
            @Result(property = "classifyName",column = "classify_name"),
            @Result(property = "discountPrice",column = "discount_price"),
            @Result(property = "surplusNum",column = "surplus_num"),

    })
    IPage<GoodsVO> getGoodsListByParam(@Param("page") Page page,
                                       @Param("name") String name,
                                       @Param("startTime") String startTime,
                                       @Param("endTime") String endTime,
                                       @Param("classifyId") String classifyId,
                                       @Param("status") String status,
                                       @Param("classifyName") String classifyName,
                                       @Param("isGreat") Integer isGreat);

    @Select("select * from tb_goods where end_time is not null and end_time <= #{endTime} and status = 1")
    List<Goods> getEndTimeGoodsList(@Param("endTime") Date endTime);

    @Select("select * from tb_goods where id = #{id}")
    GoodsVO getGoodsVOById(@Param("id") String id);
}
