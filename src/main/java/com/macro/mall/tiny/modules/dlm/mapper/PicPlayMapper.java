package com.macro.mall.tiny.modules.dlm.mapper;

import com.macro.mall.tiny.modules.dlm.model.PicPlay;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PicPlayMapper extends BaseMapper<PicPlay> {

    @Select("select * from tb_pic_play order by sort")
    List<PicPlay> getPicPlayList();

    @Delete("delete from tb_pic_play where sort = #{sort}")
    void deletePicBySort(@Param("sort") Integer sort);
}
