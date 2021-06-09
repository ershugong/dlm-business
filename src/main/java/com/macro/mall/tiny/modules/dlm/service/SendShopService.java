package com.macro.mall.tiny.modules.dlm.service;

import com.macro.mall.tiny.modules.dlm.mapper.SendShopMapper;
import com.macro.mall.tiny.modules.dlm.model.SendShop;
import com.macro.mall.tiny.modules.dlm.util.CommonUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class SendShopService {
    @Resource
    private SendShopMapper sendShopMapper;

    public IPage<SendShop> getSendShopPages(Page<SendShop> page){
        return sendShopMapper.getSendShopPages(page);
    }

    public List<SendShop> getAllSendShopList(){
        return sendShopMapper.selectByMap(new HashMap<>());
    }

    public SendShop insertSendShop(SendShop sendShop){
        sendShop.setId(CommonUtil.getUUID32());
        sendShopMapper.insert(sendShop);
        return sendShop;
    }

    public SendShop deleteSendShop(SendShop sendShop){
        sendShopMapper.deleteById(sendShop.getId());
        return sendShop;
    }
}
