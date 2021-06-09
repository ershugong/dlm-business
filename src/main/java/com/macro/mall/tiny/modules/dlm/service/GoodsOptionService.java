package com.macro.mall.tiny.modules.dlm.service;

import com.macro.mall.tiny.modules.dlm.mapper.GoodsOptionMapper;
import com.macro.mall.tiny.modules.dlm.model.GoodsOption;
import com.macro.mall.tiny.modules.dlm.util.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class GoodsOptionService {
    @Autowired
    private GoodsOptionMapper goodsOptionMapper;

    @Autowired
    private GoodsService goodsService;

    public void updateAllOptionByGoodsId(String goodsIds, List<GoodsOption> optionArray){
        if(CommonUtil.AssertEmpty(optionArray)){
            return;
        }

        //删除所有
        goodsOptionMapper.deleteOptionByGoodsId(goodsIds);

        //再次添加
        this.insertGoodsOptions(goodsIds, optionArray);
    }

    public void insertGoodsOptions(String goodsIds,List<GoodsOption> optionArray){
        if(CommonUtil.AssertEmpty(optionArray)){
            return;
        }

        for (int i=0;i<optionArray.size();i++){
            GoodsOption goodsOption = optionArray.get(i);
            goodsOption.setId(CommonUtil.getUUID32());
            goodsOption.setSort(i+1);
            goodsOption.setGoodsId(goodsIds);
            goodsOption.setSurplusNum(goodsOption.getNum());
            goodsOption.setSellNum(0);
            //如果设置了 限购数量则添加限购开始时间
            if(goodsOption.getPersonBuyLimit() == null){
                goodsOption.setPersonBuyLimit(0);
            }
            if(goodsOption.getPersonBuyLimit() != 0){
                goodsOption.setBuyLimitTime(new Date());
            }
            goodsOptionMapper.insert(goodsOption);
        }
    }

    public GoodsOption updateGoodsOption(GoodsOption goodsOption){
        GoodsOption history = goodsOptionMapper.selectById(goodsOption.getId());
        if(!history.getNum().equals(goodsOption.getNum())){
            //更新库存
            goodsOption.setSurplusNum(goodsOption.getNum());
        }

        //如果更新过限购数量
        if(goodsOption.getPersonBuyLimit() == null){
            goodsOption.setPersonBuyLimit(0);
        }
        if(goodsOption.getPersonBuyLimit() != 0 && goodsOption.getPersonBuyLimit() != history.getPersonBuyLimit()){
            goodsOption.setBuyLimitTime(new Date());
        }
        goodsOptionMapper.updateById(goodsOption);
        //如果折扣价为null 或者 限购量为null(避免无法更新该两个值的清空修改)
        if(goodsOption.getDiscountPrice() == null || goodsOption.getPersonBuyLimit() == null){
            goodsOptionMapper.updateGoodsOptionDiscountAndLimitNum(goodsOption.getDiscountPrice(),goodsOption.getPersonBuyLimit(),goodsOption.getId());
        }
        goodsService.updateTimeById(history.getGoodsId());
        return goodsOption;
    }

    public GoodsOption delGoodsOption(GoodsOption goodsOption){
        GoodsOption history = goodsOptionMapper.selectById(goodsOption.getId());
        goodsOptionMapper.deleteById(goodsOption.getId());
        if(history != null){
            goodsService.updateTimeById(history.getGoodsId());
        }
        return goodsOption;
    }

    public GoodsOption getGoodsOptionById(String id){
        GoodsOption goodsOption = goodsOptionMapper.selectById(id);
        if(goodsOption.getPrice() != null){
            BigDecimal price = new BigDecimal(goodsOption.getPrice());
            goodsOption.setPrice(price.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        if(goodsOption.getDiscountPrice() != null){
            BigDecimal discountPrice = new BigDecimal(goodsOption.getDiscountPrice());
            goodsOption.setDiscountPrice(discountPrice.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        }
        return goodsOption;
    }

    public GoodsOption insertGoodsOption(GoodsOption goodsOption){
        GoodsOption lastOption = goodsOptionMapper.getLastGoodsOption(goodsOption.getGoodsId());
        goodsOption.setId(CommonUtil.getUUID32());
        goodsOption.setSurplusNum(goodsOption.getNum());
        if(lastOption == null){
            goodsOption.setSort(1);
        }else{
            goodsOption.setSort(lastOption.getSort() + 1);
        }
        goodsOption.setSellNum(0);
        //如果设置了 限购数量则添加限购开始时间
        if(goodsOption.getPersonBuyLimit() == null){
            goodsOption.setPersonBuyLimit(0);
        }
        if(goodsOption.getPersonBuyLimit() != 0){
            goodsOption.setBuyLimitTime(new Date());
        }
        goodsOptionMapper.insert(goodsOption);
        goodsService.updateTimeById(goodsOption.getGoodsId());
        return goodsOption;
    }
}
