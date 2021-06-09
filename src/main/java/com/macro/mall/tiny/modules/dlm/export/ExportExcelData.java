package com.macro.mall.tiny.modules.dlm.export;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ExportExcelData {
    //导出Excel的文件名
    private String fileName;

    //模板类型
    private Integer templateType;

    //每个sheet页的数据
    private List<SheetData> sheetData;


}
