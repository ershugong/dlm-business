package com.macro.mall.tiny.modules.test.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import com.macro.mall.tiny.modules.test.service.TestBookService;
import com.macro.mall.tiny.modules.test.model.TestBook;
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
@RequestMapping("/testBook")
public class TestBookController {

    @Autowired
    public TestBookService testBookService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(@RequestBody TestBook testBook) {
        boolean success = testBookService.save(testBook);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(@PathVariable Long id, @RequestBody TestBook testBook) {
        testBook.setId(id);
        boolean success = testBookService.updateById(testBook);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(@PathVariable Long id) {
        boolean success = testBookService.removeById(id);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult deleteBatch(@RequestParam("ids") List<Long> ids) {
        boolean success = testBookService.removeByIds(ids);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }


    @RequestMapping(value = "/listAll", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<List<TestBook>> listAll() {
        List<TestBook> testBookList = testBookService.list();
        return CommonResult.success(testBookList);
    }

    @RequestMapping(value = "/listByPage", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult<Page<TestBook>> listByPage(@RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                                    @RequestParam(value = "pageIndex",defaultValue = "1") Integer pageIndex) {
        Page<TestBook> page = new Page<>(pageIndex,pageSize);
        QueryWrapper<TestBook> queryWrapper = new QueryWrapper<>();
        Page<TestBook> pageResult = testBookService.page(page, queryWrapper);
        return CommonResult.success(pageResult);
        }
}

