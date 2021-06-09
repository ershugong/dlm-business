package com.macro.mall.tiny.modules.dlm.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.dlm.model.PicPlay;
import com.macro.mall.tiny.modules.dlm.service.PicPlayService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/picPlay")
public class PicPlayController {

    @Resource
    private PicPlayService picPlayService;

    @RequestMapping("/updatePicPlay")
    public CommonResult<PicPlay> updatePicPlay(@RequestParam(value = "file",required = false) MultipartFile file, PicPlay picPlay){
        return CommonResult.success(picPlayService.updatePicPlay(file,picPlay));
    }

    @RequestMapping("/savePicPlay")
    public CommonResult<PicPlay> savePicPlay(PicPlay picPlay){
        return CommonResult.success(picPlayService.savePicPlay(picPlay));
    }

    @RequestMapping("/savePicPlayForPull")
    public CommonResult<List<PicPlay>> savePicPlayForPull(String picPlayListStr){
        return CommonResult.success(picPlayService.savePicPlayForPull(picPlayListStr));
    }

    //getPicPlayList
    @RequestMapping("/getPicPlayList")
    public CommonResult<List<PicPlay>> getPicPlayList(){
        return CommonResult.success(picPlayService.getPicPlayList());
    }

    @RequestMapping("/deletePicPlayBySort")
    public CommonResult deletePicPlayBySort(Integer sort){
        return CommonResult.success(picPlayService.deletePicPlayBySort(sort));
    }

    @RequestMapping("/deletePicPlay")
    public CommonResult deletePicPlay(String id){
        return CommonResult.success(picPlayService.deletePicPlay(id));
    }
}
