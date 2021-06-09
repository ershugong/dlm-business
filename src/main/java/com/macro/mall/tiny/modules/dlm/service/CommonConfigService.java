package com.macro.mall.tiny.modules.dlm.service;

import com.macro.mall.tiny.modules.dlm.mapper.CommonConfigMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonConfigService {
    @Autowired
    private CommonConfigMapper commonConfigMapper;

    public String getValueByKey(String key){
        return commonConfigMapper.getValueByKey(key);
    }


}
