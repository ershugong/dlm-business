package com.macro.mall.tiny.modules.dlm.export;

import com.macro.mall.tiny.modules.dlm.exception.MyEumException;
import com.macro.mall.tiny.modules.dlm.exception.MyException;
import com.macro.mall.tiny.modules.dlm.model.UserOrderVO;
import com.macro.mall.tiny.modules.dlm.service.OrderService;
import com.macro.mall.tiny.modules.dlm.util.CommonUtil;
import com.macro.mall.tiny.modules.dlm.vo.OrderVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
@Service
public class ExportExcelServiceImpl implements ExportExcelService {

    private static final Logger logger = LoggerFactory.getLogger(ExportExcelServiceImpl.class);
    @Resource
    private OrderService orderService;

    @Value("${uploadFileExcel}")
    private String uploadFileExcel;

    @Override
    public ApiResponse exportExcel(Page<OrderVO> page,String phone,String startTime,String endTime,String openId,String customerName) {

        List<OrderVO> orderVOList = orderService.getAllOrderList(phone, startTime, endTime, openId, customerName);
        if(CommonUtil.AssertEmpty(orderVOList)){
            throw new MyException(MyEumException.EMPTY_ORDER_FOR_EXPORT);
        }
        HSSFWorkbook wb = new HSSFWorkbook();//创建工作薄
        HSSFSheet sheet = wb.createSheet("订单列表");

        // 第四步，创建表头样式
        CellStyle headStyle = setHeadStyle(wb);
        // 给单元格内容设置另一个样式
        CellStyle cellStyle = setCellStyle(wb);
        int rowIndex = 0;
        int cellIndex = 0;
        Row titleRow = sheet.createRow(rowIndex);
        String [] titleText = {"手机号","商品名称","标签","商品单价","下单数量","数量单位","下单金额","货物详情","活动","订单创建时间","门店名称","收货地址","联系人名称"};
        for(int i=0;i<titleText.length;i++){
            Cell cell = titleRow.createCell(cellIndex++);
            cell.setCellStyle(headStyle);
            cell.setCellValue(titleText[i]);
        }
        //设置自动列宽(必须在单元格设值以后进行)
//        sheet.autoSizeColumn(0);
//        sheet.setColumnWidth(0, sheet.getColumnWidth(0) * 17 / 10);

        rowIndex++;
//        StringBuffer stringBuffer;
        int index = 1;
        DecimalFormat df=new DecimalFormat("0.00");
        for(OrderVO orderVO : orderVOList){
            List<UserOrderVO> orderDetailList = orderVO.getOrderDetailList();
            for(int n=0;n<orderDetailList.size();n++){
                cellIndex = 0;
                Row row = sheet.createRow(rowIndex++);
                Cell phoneCell = row.createCell(cellIndex++);
                phoneCell.setCellStyle(cellStyle);
                phoneCell.setCellValue(orderVO.getCustomerPhone());

                Cell goodsNameCell = row.createCell(cellIndex++);
                goodsNameCell.setCellStyle(cellStyle);
                goodsNameCell.setCellValue(orderDetailList.get(n).getGoodsName());

                Cell optionTitleCell = row.createCell(cellIndex++);
                optionTitleCell.setCellStyle(cellStyle);
                optionTitleCell.setCellValue(orderDetailList.get(n).getOptionTitle());

                Double currentPrice = orderDetailList.get(n).getDiscountPrice() == null ? orderDetailList.get(n).getPrice():orderDetailList.get(n).getDiscountPrice();
                Cell priceCell = row.createCell(cellIndex++);
                priceCell.setCellStyle(cellStyle);
                priceCell.setCellValue(df.format(currentPrice));

                Cell goodsNumCell = row.createCell(cellIndex++);
                goodsNumCell.setCellStyle(cellStyle);
                goodsNumCell.setCellValue(orderDetailList.get(n).getGoodsNum());

                Cell unitCell = row.createCell(cellIndex++);
                unitCell.setCellStyle(cellStyle);
                unitCell.setCellValue(orderDetailList.get(n).getUnit());


                Double totalPrice = currentPrice * orderDetailList.get(n).getGoodsNum();
                Cell totalPriceCell = row.createCell(cellIndex++);
                totalPriceCell.setCellStyle(cellStyle);
                totalPriceCell.setCellValue(df.format(totalPrice));

                Cell contentCell = row.createCell(cellIndex++);
                contentCell.setCellStyle(cellStyle);
                contentCell.setCellValue(orderDetailList.get(n).getContent());

                Cell activityCell = row.createCell(cellIndex++);
                activityCell.setCellStyle(cellStyle);
                activityCell.setCellValue(orderDetailList.get(n).getActivity());

                Cell createTimeCell = row.createCell(cellIndex++);
                createTimeCell.setCellStyle(cellStyle);
                createTimeCell.setCellValue(orderVO.getCreateTime());

                Cell shopNameCell = row.createCell(cellIndex++);
                shopNameCell.setCellStyle(cellStyle);
                shopNameCell.setCellValue(orderVO.getShopName());

                Cell addressCell = row.createCell(cellIndex++);
                addressCell.setCellStyle(cellStyle);
                addressCell.setCellValue(orderVO.getAddress());

                Cell customerNameCell = row.createCell(cellIndex++);
                customerNameCell.setCellStyle(cellStyle);
                customerNameCell.setCellValue(orderVO.getCustomerName());
                index++;
            }
        }

        // 必须在单元格设值以后进行
        // 设置为根据内容自动调整列宽
        for (int k = 0; k < titleText.length; k++) {
            sheet.autoSizeColumn(k);
        }
        // 处理中文不能自动调整列宽的问题
        this.setSizeColumn(sheet, titleText.length);


        ApiResponse apiResponse = new ApiResponse();
        String result = downloadExcel(wb);
        if (result != null){
            apiResponse.setStatusCode("200");
            apiResponse.setMessage(result);
        } else {
            apiResponse.setStatusCode("400");
            apiResponse.setMessage("export excel failed");
        }
        return apiResponse;
    }

    public String downloadExcel(HSSFWorkbook wb) {
//        boolean flag = true;
//        Date date = new Date();
        String fileName = new Long(new Date().getTime()).toString();
        String filePath = uploadFileExcel+ "/" + fileName + ".xls";
        File file = new File(filePath);

        FileOutputStream fos = null;
        try {

            fos = new FileOutputStream(file);
            wb.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
//            flag = false;
            return null;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                }catch (IOException e) {
                    e.printStackTrace();
//                    flag = false;
                    return null;
                }
            }
        }
        return fileName;
    }

    // 自适应宽度(中文支持)
    private void setSizeColumn(HSSFSheet sheet, int size) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            if(columnNum == 4){
                continue;
            }
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 0; rowNum < sheet.getLastRowNum(); rowNum++) {
                HSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }

                if (currentRow.getCell(columnNum) != null) {
                    HSSFCell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == HSSFCell.CELL_TYPE_STRING) {
                        int length = currentCell.getStringCellValue().getBytes().length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 256);
        }
    }

    private static HSSFCellStyle setHeadStyle(HSSFWorkbook wb) {
        HSSFCellStyle headStyle = wb.createCellStyle();
        // 设置背景颜色白色  HSSFColor.LIGHT_GREEN.index HSSFColor.GREY_25_PERCENT.index
        headStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        headStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        headStyle.setBorderBottom(CellStyle.BORDER_THIN);
        headStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        headStyle.setBorderTop(CellStyle.BORDER_THIN);
        headStyle.setTopBorderColor(HSSFColor.BLACK.index);
        headStyle.setBorderLeft(CellStyle.BORDER_THIN);
        headStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        headStyle.setBorderRight(CellStyle.BORDER_THIN);
        headStyle.setRightBorderColor(HSSFColor.BLACK.index);

        // 设置标题字体
        HSSFFont headFont = wb.createFont();
        // 设置字体大小
        headFont.setFontHeightInPoints((short) 12);
        // 设置字体
        headFont.setFontName("宋体");
        // 设置字体粗体
        headFont.setBold(true);
        // 把字体应用到当前的样式
        headStyle.setFont(headFont);
        return headStyle;
    }

    /**
     * 设置单元格内容样式
     * @param wb
     * @return
     */
    private static HSSFCellStyle setCellStyle(HSSFWorkbook wb) {
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setWrapText(true);
        // 设置标题字体
        HSSFFont cellFont = wb.createFont();
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
        // 设置字体大小
//        cellFont.setFontHeightInPoints((short) 11);
        // 设置字体
//        cellFont.setFontName("等线");
        // 设置字体
        cellFont.setFontName("宋体");
        // 把字体应用到当前的样式
//        cellStyle.setFont(cellFont);
        return cellStyle;
    }

}
