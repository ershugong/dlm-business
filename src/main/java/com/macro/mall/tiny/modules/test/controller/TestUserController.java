package com.macro.mall.tiny.modules.test.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.macro.mall.tiny.common.api.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import com.macro.mall.tiny.modules.test.service.TestUserService;
import com.macro.mall.tiny.modules.test.model.TestUser;
import java.util.List;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author macro
 * @since 2021-05-13
 */
@RestController
@RequestMapping("/testUser")
public class TestUserController {

    @Autowired
    public TestUserService testUserService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody TestUser testUser) {
        boolean success = testUserService.save(testUser);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody TestUser testUser) {
        testUser.setId(id);
        boolean success = testUserService.updateById(testUser);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@PathVariable Long id) {
        boolean success = testUserService.removeById(id);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean success = testUserService.removeByIds(ids);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }


    @RequestMapping(value = "/listAll", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<List<TestUser>> listAll() {
        List<TestUser> testUserList = testUserService.list();
        return CommonResult.success(testUserList);
    }

    @RequestMapping(value = "/listByPage", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Page<TestUser>> listByPage(@RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                                   @RequestParam(value = "pageIndex",defaultValue = "1") Integer pageIndex) {
        Page<TestUser> page = new Page<>(pageIndex,pageSize);
        QueryWrapper<TestUser> queryWrapper = new QueryWrapper<>();
        Page<TestUser> pageResult = testUserService.page(page, queryWrapper);
        return CommonResult.success(pageResult);
    }
}

