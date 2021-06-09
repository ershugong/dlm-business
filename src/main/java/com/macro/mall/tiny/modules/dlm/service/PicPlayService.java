package com.macro.mall.tiny.modules.dlm.service;

import com.macro.mall.tiny.common.api.CommonResult;
import com.macro.mall.tiny.modules.dlm.mapper.PicPlayMapper;
import com.macro.mall.tiny.modules.dlm.exception.MyEumException;
import com.macro.mall.tiny.modules.dlm.exception.MyException;
import com.macro.mall.tiny.modules.dlm.model.PicPlay;
import com.macro.mall.tiny.modules.dlm.util.CommonUtil;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PicPlayService extends ServiceImpl<PicPlayMapper,PicPlay> {

    @Resource
    private PicPlayMapper picPlayMapper;

    @Value("${uploadFilePath}")
    private String uploadFilePath;

    //更换轮播图
    public PicPlay updatePicPlay(MultipartFile file,PicPlay picPlay){
        String uploadPath = CommonUtil.uploadFile(file,uploadFilePath);
        picPlay.setPicPlayUrl("/upload/" + uploadPath);
        if("".equals(picPlay.getId()) || picPlay.getId() == null){
            picPlay.setId(CommonUtil.getUUID32());
            picPlayMapper.insert(picPlay);
        }else{
            PicPlay picPlayHistory = picPlayMapper.selectById(picPlay.getId());
            if(picPlayHistory == null){
                picPlayMapper.insert(picPlay);
                return picPlay;
            }
            picPlayMapper.updateById(picPlay);
        }
        return picPlay;
    }

    //更换轮播图(vue改版)
    public PicPlay savePicPlay(PicPlay picPlay){
        if("".equals(picPlay.getId()) || picPlay.getId() == null){
            picPlay.setId(CommonUtil.getUUID32());
            picPlayMapper.insert(picPlay);
        }else{
            PicPlay picPlayHistory = picPlayMapper.selectById(picPlay.getId());
            if(picPlayHistory == null){
                picPlayMapper.insert(picPlay);
                return picPlay;
            }
            picPlayMapper.updateById(picPlay);
        }
        return picPlay;
    }

    //拉拽轮播图
    public List<PicPlay> savePicPlayForPull(String picPlayListStr){
        List<PicPlay> list = null;
        try{
            list = JSONArray.parseArray(picPlayListStr,PicPlay.class);
            this.updateBatchById(list);
        }catch(Exception e){
            throw new MyException(MyEumException.PIC_PLAY_JSON_FORMAT_FAILURE);
        }
        return list;
    }

    //获取轮播图列表
    public List<PicPlay> getPicPlayList(){
        return picPlayMapper.getPicPlayList();
    }

    public CommonResult deletePicPlayBySort(Integer sort){
        picPlayMapper.deletePicBySort(sort);
        return CommonResult.success(sort);
    }

    public PicPlay deletePicPlay(String id){
        picPlayMapper.deleteById(id);
        PicPlay picPlay = new PicPlay();
        picPlay.setId(id);
        return picPlay;
    }
}
