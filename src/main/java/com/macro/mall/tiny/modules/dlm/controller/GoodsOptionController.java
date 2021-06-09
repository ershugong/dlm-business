package com.macro.mall.tiny.modules.dlm.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.dlm.model.GoodsOption;
import com.macro.mall.tiny.modules.dlm.service.GoodsOptionService;
import com.macro.mall.tiny.modules.dlm.service.GoodsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/goodsOption")
public class GoodsOptionController {
    @Resource
    private GoodsOptionService goodsOptionService;

    @RequestMapping("/updateGoodsOption")
    public CommonResult<GoodsOption> updateGoodsOption(GoodsOption goodsOption){
        return CommonResult.success(goodsOptionService.updateGoodsOption(goodsOption));
    }

    @RequestMapping("/delGoodsOption")
    public CommonResult<GoodsOption> delGoodsOption(GoodsOption goodsOption){
        return CommonResult.success(goodsOptionService.delGoodsOption(goodsOption));
    }

    @RequestMapping("/goodsOptionDetail")
    public CommonResult<GoodsOption> GoodsOptionDetail(String id){
        return CommonResult.success(goodsOptionService.getGoodsOptionById(id));
    }

    @RequestMapping("/insertOptionDetail")
    public CommonResult<GoodsOption> insertOptionDetail(GoodsOption goodsOption){
        return CommonResult.success(goodsOptionService.insertGoodsOption(goodsOption));
    }
}
