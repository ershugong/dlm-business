package com.macro.mall.tiny.modules.dlm.service;

import com.macro.mall.tiny.modules.dlm.mapper.ShopMapper;
import com.macro.mall.tiny.modules.dlm.model.Shop;
import com.macro.mall.tiny.modules.dlm.util.CommonUtil;
import com.macro.mall.tiny.modules.dlm.util.MailThread;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ShopService {

    @Autowired
    private ShopMapper shopMapper;

    public Shop saveShop(Shop shop){
        if(shop.getIsDefault() != null && 1 == shop.getIsDefault()){
            //把历史默认地址取消
            shopMapper.updateShopNotDefault(shop.getOpenId());
        }

        //更新
        if(shop.getId() != null && !"".equals(shop.getId())){
            shopMapper.updateById(shop);
            return shop;
        }

        //新增
        shop.setId(CommonUtil.getUUID32());
        shop.setStatus(0);
        shop.setCreateTime(new Date());

        //默认白名单
        boolean isWhite = "dlm123456".equals(shop.getBusinessNo());
        if(isWhite){
            shop.setStatus(3);
        }
        shopMapper.insert(shop);

        //创建线程（发送邮件）
        if(!isWhite){
            Thread thread = new Thread(new MailThread());
            thread.start();
        }
        return shop;
    }

    public Shop delShop(String id){
        shopMapper.deleteById(id);
        Shop shop = new Shop();
        shop.setId(id);
        return shop;
    }

    public IPage<Shop> getShopPage(Page<Shop> page,Integer status,String customerName,String shopName){
        return shopMapper.getShopPage(page,status,customerName,shopName);
    }

    public List<Shop> getShopListByOpenId(String openId,String shopName,String customerName){
        return shopMapper.getShopListByOpenId(openId,shopName,customerName);
    }

    public Shop getShopDetail(String id){
        return shopMapper.selectById(id);
    }

    public Shop updateShopDefault(String openId,String shopId){
        //把历史默认地址取消
        shopMapper.updateShopNotDefault(openId);

        //更新默认地址
        Shop shop = new Shop();
        shop.setId(shopId);
        shop.setIsDefault(1);
        shopMapper.updateById(shop);
        return shop;
    }

    public Integer getAuditShopListCount(String openId){
        return shopMapper.getAuditShopListCount(openId);
    }


}
