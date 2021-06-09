package com.macro.mall.tiny.modules.dlm.export;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class SheetData {
    //每个sheet页的名字
    private String sheetName;

    //单元格的数据
    private List<TabularData> tabularData;
}
