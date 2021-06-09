package com.macro.mall.tiny.modules.dlm.mapper;

import com.macro.mall.tiny.modules.dlm.model.CommonConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface CommonConfigMapper extends BaseMapper<CommonConfig> {

    @Select("select config_value from tb_config where config_key = #{configKey}")
    String getValueByKey(@Param("configKey") String configKey);
}
