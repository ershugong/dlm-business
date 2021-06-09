package com.macro.mall.tiny.modules.dlm.task;

import com.macro.mall.tiny.modules.dlm.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class GoodsTask {

    @Autowired
    private GoodsService goodsService;

    //每分钟执行
    @Scheduled(cron = "0 * * * * ?")
    public void goodsDownTimeTasks() {
        goodsService.updateEndTimeGoodsList(new Date());
    }
}
