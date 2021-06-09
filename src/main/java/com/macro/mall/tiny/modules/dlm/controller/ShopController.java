package com.macro.mall.tiny.modules.dlm.controller;

import com.macro.mall.tiny.common.api.CommonPage;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.dlm.model.Shop;
import com.macro.mall.tiny.modules.dlm.service.ShopService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
@CrossOrigin
@Api(description = "商店信息")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @RequestMapping(value = "/saveShop",method = RequestMethod.POST)
    public CommonResult saveShop(Shop shop){
        return CommonResult.success(shopService.saveShop(shop));
    }

    @RequestMapping(value = "/delShop",method = RequestMethod.POST)
    public CommonResult delShop(String id){
        return CommonResult.success(shopService.delShop(id));
    }

    @ApiOperation(value = "获取商店列表分页展示",notes = "商店列表分页展示")
    @RequestMapping(value = "/getShopPage",method = RequestMethod.POST)
    public CommonResult<CommonPage<Shop>> getShopPage(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                                                Integer status,
                                                String customerName,
                                                String shopName){
        Page<Shop> pageParam = new Page<>(page,pageSize);
        return CommonResult.success(CommonPage.restPage(shopService.getShopPage(pageParam,status,customerName,shopName)));
    }

    @RequestMapping(value = "/getShopListByOpenId",method = RequestMethod.POST)
    public CommonResult<List<Shop>> getShopListByOpenId(@RequestParam(value = "openId") String openId,
                                          @RequestParam(value = "shopName",required = false) String shopName,
                                          @RequestParam(value = "customerName",required = false) String customerName){
        return CommonResult.success(shopService.getShopListByOpenId(openId,shopName,customerName));
    }

    @RequestMapping(value = "/getShopDetail",method = RequestMethod.POST)
    public CommonResult<Shop> getShopDetail(@RequestParam(value = "id") String id){
        return CommonResult.success(shopService.getShopDetail(id));
    }

    @RequestMapping(value = "/updateShopDefault",method = RequestMethod.POST)
    public CommonResult updateShopDefault(@RequestParam(value = "id") String id,
                              @RequestParam(value = "openId") String openId){
        return CommonResult.success(shopService.updateShopDefault(openId,id));
    }
}
