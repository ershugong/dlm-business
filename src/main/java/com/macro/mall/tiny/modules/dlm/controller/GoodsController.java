package com.macro.mall.tiny.modules.dlm.controller;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.dlm.exception.MyEumException;
import com.macro.mall.tiny.modules.dlm.exception.MyException;
import com.macro.mall.tiny.modules.dlm.model.Goods;
import com.macro.mall.tiny.modules.dlm.model.GoodsOption;
import com.macro.mall.tiny.modules.dlm.service.GoodsOptionService;
import com.macro.mall.tiny.modules.dlm.service.GoodsService;
import com.macro.mall.tiny.modules.dlm.util.CommonUtil;
import com.macro.mall.tiny.modules.dlm.vo.GoodsVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/goods")
public class GoodsController {
    @Resource
    private GoodsService goodsService;

    @Value("${uploadFilePath}")
    private String uploadFilePath;

    //添加货物
    @RequestMapping("/insertGoods")
    public CommonResult<Goods> insertGoods(Goods goods,
                                          @RequestParam(value = "file",required = false) MultipartFile file,
                                          @RequestParam(value = "fileContents",required = false) MultipartFile [] fileContents,
                                          @RequestParam(value = "optionArray",required = false) String optionArray,
                                          @RequestParam(value = "goodsEndTime",required = false) String goodsEndTime){
        try {
            if(goodsEndTime != null && !"".equals(goodsEndTime)){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date endTime = simpleDateFormat.parse(goodsEndTime);
                goods.setEndTime(endTime);
            }

        } catch (ParseException e) {
            throw new MyException(MyEumException.TIME_FORMAT_ERROR.getCode(),String.format(MyEumException.TIME_FORMAT_ERROR.getMessage(),goodsEndTime));
        }
        return CommonResult.success(goodsService.saveGoods(goods,fileContents,file,optionArray,uploadFilePath));
    }

    //保存（新增/编辑）货物(vue改版)
    @RequestMapping("/saveGoods")
    public CommonResult<Goods> saveGoods(Goods goods,
                             @RequestParam(value = "optionArray",required = false) String optionArray,
                             @RequestParam(value = "goodsEndTime",required = false) String goodsEndTime){
        try {
            if(goodsEndTime != null && !"".equals(goodsEndTime)){
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date endTime = simpleDateFormat.parse(goodsEndTime);
                goods.setEndTime(endTime);
            }else{
                goods.setEndTime(null);
            }

        } catch (ParseException e) {
            throw new MyException(MyEumException.TIME_FORMAT_ERROR.getCode(),String.format(MyEumException.TIME_FORMAT_ERROR.getMessage(),goodsEndTime));
        }
        return CommonResult.success(goodsService.saveGoodsForVue(goods,optionArray));
    }


    //查询货物列表
//    @CrossOrigin
    @RequestMapping("/getGoodsListByParam")
    public CommonResult getGoodsListByParam(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                      @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                                      String name, String startTime,
                                      String endTime, String classifyId, String status, String classifyName, Integer isGreat, HttpServletResponse response){
        if(startTime != null && !"".equals(startTime)){
            startTime = startTime + " 00:00:00";
        }
        if(endTime != null && !"".equals(endTime)){
            endTime = endTime + " 23:59:59";
        }
        Page<GoodsVO> pageReuslt = new Page<>(page,pageSize);
        return CommonResult.success(goodsService.getGoodsListByParam(pageReuslt,name,startTime,endTime,classifyId,status,classifyName,isGreat));
    }

    //下架 / 发布 /编辑  /删除
    @RequestMapping("/updateGoods")
    public CommonResult updateGoods(Goods goods){
        return CommonResult.success(goodsService.updateGoods(goods));
    }

    //下架 / 发布 /编辑  /删除
    @RequestMapping("/goodsDetail")
    public CommonResult goodsDetail(String id){
        return CommonResult.success(goodsService.getGoodsById(id));
    }

    @RequestMapping("/initGoodsForOption")
    public CommonResult initGoodsForOption(){
        return CommonResult.success(goodsService.initGoodsForOption());
    }

    @RequestMapping("/initGoodsSellNum")
    public CommonResult initGoodsSellNum(){
        return CommonResult.success(goodsService.initGoodsSellNum());
    }
}
