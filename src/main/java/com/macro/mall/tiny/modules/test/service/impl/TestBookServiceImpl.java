package com.macro.mall.tiny.modules.test.service.impl;

import com.macro.mall.tiny.modules.test.model.TestBook;
import com.macro.mall.tiny.modules.test.mapper.TestBookMapper;
import com.macro.mall.tiny.modules.test.service.TestBookService;
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
public class TestBookServiceImpl extends ServiceImpl<TestBookMapper, TestBook> implements TestBookService {

}
