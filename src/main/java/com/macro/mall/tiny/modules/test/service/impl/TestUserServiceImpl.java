package com.macro.mall.tiny.modules.test.service.impl;

import com.macro.mall.tiny.modules.test.model.TestUser;
import com.macro.mall.tiny.modules.test.mapper.TestUserMapper;
import com.macro.mall.tiny.modules.test.service.TestUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author macro
 * @since 2021-05-13
 */
@Service
public class TestUserServiceImpl extends ServiceImpl<TestUserMapper, TestUser> implements TestUserService {

}
