package com.macro.mall.tiny.modules.dlm.export;

import com.macro.mall.tiny.modules.dlm.vo.OrderVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/export")
@CrossOrigin
public class ExportExcelController {
    @Autowired
    private ExportExcelService exportExcelService;

    @RequestMapping(value = "/exportExcel")
    public ApiResponse exportExcel(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                   @RequestParam(value = "pageSize",defaultValue = "10")Integer pageSize,
                                   String phone,
                                   String startTime,
                                   String endTime,
                                   String shopName,
                                   String customerName) {
        ApiResponse apiResponse;
        pageSize = 10000;
        if(startTime != null && !"".equals(startTime)){
            startTime = startTime + " 00:00:00";
        }
        if(endTime != null && !"".equals(endTime)){
            endTime = endTime + " 23:59:59";
        }
        Page<OrderVO> pageResult = new Page<>(page,pageSize);
        return exportExcelService.exportExcel(pageResult,phone,startTime,endTime,shopName,customerName);

    }
}
