package com.macro.mall.tiny.modules.dlm.mapper;

import com.macro.mall.tiny.modules.dlm.model.Classify;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassifyMapper extends BaseMapper<Classify> {

    @Select(("select * from tb_classify order by sort"))
    List<Classify> getClassifyListBySort();

}
