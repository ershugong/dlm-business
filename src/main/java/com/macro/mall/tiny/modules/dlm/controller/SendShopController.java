package com.macro.mall.tiny.modules.dlm.controller;

import com.macro.mall.tiny.common.api.CommonPage;
import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.dlm.model.SendShop;
import com.macro.mall.tiny.modules.dlm.service.SendShopService;
import com.macro.mall.tiny.modules.dlm.util.CommonUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/sendShop")
@CrossOrigin
public class SendShopController {

    @Resource
    private SendShopService sendShopService;

    @RequestMapping("/getSendShopPages")
    public CommonResult<CommonPage<SendShop>> getSendShopPages(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                   @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize){
        Page<SendShop> pageResult = new Page<>(page,pageSize);
        return CommonResult.success(CommonPage.restPage(sendShopService.getSendShopPages(pageResult)));
    }

    @RequestMapping("/getAllSendShops")
    public CommonResult<List<SendShop>> getAllSendShops(){
        return CommonResult.success(sendShopService.getAllSendShopList());
    }

    @RequestMapping("/insertSendShop")
    public CommonResult<SendShop> insertSendShop(SendShop sendShop){
        sendShop.setId(CommonUtil.getUUID32());
        sendShopService.insertSendShop(sendShop);
        return CommonResult.success(sendShop);
    }

    @RequestMapping("/deleteSendShop")
    public SendShop deleteSendShop(SendShop sendShop){
        sendShopService.deleteSendShop(sendShop);
        return sendShop;
    }
}
