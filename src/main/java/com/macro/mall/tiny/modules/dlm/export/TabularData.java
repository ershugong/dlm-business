package com.macro.mall.tiny.modules.dlm.export;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TabularData {
    //每个单元格的内容
    private String tabularContent;

    //开始行
    private Integer firstRow;

    //结束行
    private Integer lastRow;

    //开始列
    private Integer firstCol;

    //结束列
    private Integer lastCol;
}
