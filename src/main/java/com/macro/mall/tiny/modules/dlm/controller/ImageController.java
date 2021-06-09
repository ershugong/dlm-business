package com.macro.mall.tiny.modules.dlm.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.dlm.util.CommonUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Value("${uploadFilePath}")
    private String uploadFilePath;

    @RequestMapping("/saveImage")
    public CommonResult saveImage(String fileBase){
        String smallFile = CommonUtil.GenerateImage(fileBase,uploadFilePath);
        if("".equals(smallFile)){
            return CommonResult.success("");
        }
        return CommonResult.success("/upload/" + smallFile);
    }


    @RequestMapping("/saveImageForFile")
    public CommonResult saveImageForFile(@RequestParam(value = "file") MultipartFile file){
        String smallFile = CommonUtil.uploadFile(file,uploadFilePath);
        if("".equals(smallFile)){
            return CommonResult.success( "");
        }
        return CommonResult.success("/upload/" + smallFile);
    }

}
