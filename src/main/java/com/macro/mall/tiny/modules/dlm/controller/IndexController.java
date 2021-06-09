package com.macro.mall.tiny.modules.dlm.controller;


import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.dlm.service.CommonConfigService;
import com.macro.mall.tiny.modules.dlm.util.CpuUsage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/index")
public class IndexController {

    @Autowired
    private CommonConfigService commonConfigService;

    @RequestMapping("/getMemory")
    public CommonResult getMemory(){
        return CommonResult.success(CpuUsage.getMemory());
    }

    @RequestMapping("/getValueByKey")
    public CommonResult getValueByKey(String key){
        return CommonResult.success(commonConfigService.getValueByKey(key));
    }
}
