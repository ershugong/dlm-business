package com.macro.mall.tiny.modules.dlm.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.dlm.model.Classify;
import com.macro.mall.tiny.modules.dlm.service.ClassifyService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/classify")
public class ClassifyController {

    @Resource
    private ClassifyService classifyService;

    @RequestMapping("/getClassifyList")
    public CommonResult getClassifyList(HttpServletRequest request){
        return CommonResult.success(classifyService.getClassifyList());
    }

    @RequestMapping("/insertClassify")
    public CommonResult insertClassify(Classify classify){
        return CommonResult.success(classifyService.insertClassify(classify));
    }


    @RequestMapping("/deleteClassify")
    public CommonResult deleteClassify(Classify classify){
        return CommonResult.success(classifyService.deleteClassify(classify));
    }

    @RequestMapping("/updateClassifyListByPull")
    public CommonResult<List<Classify>> updateClassifyListByPull(String classifyListStr){
        return CommonResult.success(classifyService.updateClassifyListByPull(classifyListStr));
    }
}
