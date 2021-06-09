package com.macro.mall.tiny.modules.dlm.mapper;

import com.macro.mall.tiny.modules.dlm.model.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper extends BaseMapper<User> {

    @Update("update tb_user set check_num = #{checkNum} where phone = #{phone}")
    void updateUserCheckNum(@Param("phone") String phone,@Param("checkNum") String checkNum);
}
