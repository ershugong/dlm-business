package com.macro.mall.tiny.modules.dlm.export;

import com.macro.mall.tiny.modules.dlm.vo.OrderVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface ExportExcelService {
    ApiResponse exportExcel(Page<OrderVO> page,String phone,String startTime,String endTime,String shopName,String customerName);
}
