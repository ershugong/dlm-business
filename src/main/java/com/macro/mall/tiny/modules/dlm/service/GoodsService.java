package com.macro.mall.tiny.modules.dlm.service;

import com.macro.mall.tiny.modules.dlm.mapper.GoodsMapper;
import com.macro.mall.tiny.modules.dlm.mapper.GoodsOptionMapper;
import com.macro.mall.tiny.modules.dlm.exception.MyEumException;
import com.macro.mall.tiny.modules.dlm.exception.MyException;
import com.macro.mall.tiny.modules.dlm.model.Goods;
import com.macro.mall.tiny.modules.dlm.model.GoodsOption;
import com.macro.mall.tiny.modules.dlm.util.CommonUtil;
import com.macro.mall.tiny.modules.dlm.vo.GoodsVO;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service("goodsService")
public class GoodsService {
    @Resource
    private GoodsMapper goodsMapper;

    @Resource
    private GoodsOptionMapper goodsOptionMapper;

    @Resource
    private GoodsOptionService goodsOptionService;

    //保存货物(新增，编辑)
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public Goods saveGoodsForVue(Goods goods,String optionArray){
        //货物货物自定义类型选项
        List<GoodsOption> optionList = JSONArray.parseArray(optionArray,GoodsOption.class);
        if(CommonUtil.AssertEmpty(optionList) && CommonUtil.StringIsEmpty(goods.getId())){
            throw new MyException(MyEumException.GOODS_OPTION_EMPTY);
        }

        //设置更新时间
        goods.setUpdateTime(new Date());

        //更新货物
        if(!CommonUtil.StringIsEmpty(goods.getId())){
            goodsMapper.updateById(goods);
            return goods;
        }

        goods.setSurplusNum(goods.getNum());
        goods.setCreateTime(new Date());
        goods.setId(CommonUtil.getUUID32());
        this.insertGoods(goods);
        //添加货物选项

        goodsOptionService.insertGoodsOptions(goods.getId(),optionList);
        return goods;
    }

    //保存货物(新增，编辑)
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public Goods saveGoods(Goods goods,MultipartFile[] fileContents,MultipartFile file,String optionArray,String uploadFilePath){
        //上传货物封面图片
        String smallFile = CommonUtil.uploadFile(file,uploadFilePath);
        StringBuffer contentFiles = new StringBuffer();
        for(int i=0;i<fileContents.length;i++){
            contentFiles.append(",");
            contentFiles.append("/upload/");
            contentFiles.append(CommonUtil.uploadFile(fileContents[i],uploadFilePath));
        }

        if(contentFiles.length() > 0){
            contentFiles.deleteCharAt(0);
        }

        //货物货物自定义类型选项
        List<GoodsOption> optionList = JSONArray.parseArray(optionArray,GoodsOption.class);
        if(CommonUtil.AssertEmpty(optionList) && CommonUtil.StringIsEmpty(goods.getId())){
            throw new MyException(MyEumException.GOODS_OPTION_EMPTY);
        }

        //设置更新时间
        goods.setUpdateTime(new Date());

        //更新货物
        if(!CommonUtil.StringIsEmpty(goods.getId())){
            //更新了封面
            if(!"".equals(smallFile)){
                goods.setHeadPic("/upload/" + smallFile);
            }

            //更新了货物详情图片
            if(!"".equals(contentFiles.toString())){
                goods.setFileContent(contentFiles.toString());
            }
            goodsMapper.updateById(goods);

            //更新货物选项
//            goodsOptionService.updateAllOptionByGoodsId(goods.getId(),optionList);


            return goods;
        }

        goods.setSurplusNum(goods.getNum());
        goods.setCreateTime(new Date());
        goods.setHeadPic("/upload/" + smallFile);
        goods.setFileContent(contentFiles.toString());
        goods.setId(CommonUtil.getUUID32());
        this.insertGoods(goods);
        //添加货物选项

        goodsOptionService.insertGoodsOptions(goods.getId(),optionList);
        return goods;
    }

    //添加货物
    public Goods insertGoods(Goods goods){
        goodsMapper.insert(goods);
        return goods;
    }

    //查询货物列表
    public IPage<GoodsVO> getGoodsListByParam(Page page, String name, String startTime, String endTime, String classifyId,String status,String classifyName,Integer isGreat){
        IPage<GoodsVO> goodsVOIPage = goodsMapper.getGoodsListByParam(page,name,startTime,endTime,classifyId,status,classifyName,isGreat);
        List<GoodsVO> goodsVOList = goodsVOIPage.getRecords();
        if(!CommonUtil.AssertEmpty(goodsVOList)){
            for(GoodsVO goodsVO : goodsVOList){
                goodsVO.setGoodsOptionList(goodsOptionMapper.getOptionByGoodsId(goodsVO.getId()));
            }
        }
        return goodsVOIPage;
    }

    //更新货物
    public Goods updateGoods(Goods goods){
        //判断是否修改数量
//        Goods historyGoods = goodsMapper.selectById(goods.getId());
//
//        if("1".equals(goods.getStatus().toString()) && "1".equals(historyGoods.getStatus().toString()) && !goods.getNum().equals(historyGoods.getNum())){
//            goods.setSurplusNum(goods.getNum());
//        }

        goods.setUpdateTime(new Date());
        goodsMapper.updateById(goods);
        return goods;
    }

    //获取货物详情
    public GoodsVO getGoodsById(String id){
        GoodsVO goodsVO = goodsMapper.getGoodsVOById(id);
        goodsVO.setGoodsOptionList(goodsOptionMapper.getOptionByGoodsId(id));
        return goodsVO;
    }

    public Object initGoodsForOption(){
        List<Goods> allGoods = goodsMapper.selectByMap(new HashMap<>());
        if(CommonUtil.AssertEmpty(allGoods)){
            return null;
        }

        Integer successNum = 0;
        GoodsOption goodsOption;
        for(Goods goods : allGoods){
            if (goods.getPrice() != null){
                goodsOption = new GoodsOption();
                goodsOption.setId(CommonUtil.getUUID32());
                goodsOption.setSort(1);
                goodsOption.setSurplusNum(goods.getSurplusNum());
                goodsOption.setTitle("默认");
                goodsOption.setDiscountPrice(goods.getDiscountPrice());
                goodsOption.setNum(goods.getNum());
                goodsOption.setPrice(goods.getPrice());
                goodsOption.setLevel(1);
                goodsOption.setGoodsId(goods.getId());
                goodsOptionMapper.insert(goodsOption);
                successNum++;
            }
        }
        return successNum;
    }

    public Object initGoodsSellNum(){
        List<GoodsOption> allGoods = goodsOptionMapper.selectByMap(new HashMap<>());
        if(CommonUtil.AssertEmpty(allGoods)){
            return null;
        }
        Integer successNum = 0;
        for(GoodsOption goodsOption : allGoods){
            if(goodsOption.getNum() == null || goodsOption.getSurplusNum() == null){
                continue;
            }
            goodsOption.setSellNum(goodsOption.getNum() - goodsOption.getSurplusNum());
            goodsOptionMapper.updateById(goodsOption);
            successNum++;
        }
        return successNum;
    }

    public void updateTimeById(String id){
        Goods goods = goodsMapper.selectById(id);
        goods.setUpdateTime(new Date());
        goodsMapper.updateById(goods);
    }

    public void updateEndTimeGoodsList(Date endTime){
        List<Goods> endTimeGoods = goodsMapper.getEndTimeGoodsList(endTime);
        if(CommonUtil.AssertEmpty(endTimeGoods)){
            return;
        }

        for(Goods goods : endTimeGoods){
            try {
                goods.setStatus(0);
                goodsMapper.updateById(goods);
            }catch (Exception e){
                log.error("Error Task --- goodId: " + goods.getId());
            }
        }
    }
}
