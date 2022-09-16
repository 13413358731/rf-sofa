package com.realfinance.sofa.cg.util;

import com.realfinance.sofa.cg.core.model.CgProjectOaDatumDto;
import com.realfinance.sofa.cg.model.cg.*;
import com.realfinance.sofa.cg.model.system.DepartmentVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hpsf.DocumentSummaryInformation;
import org.apache.poi.hpsf.SummaryInformation;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hhq
 * @date 2021/7/21 - 14:46
 */
public class POIUtils {

    public static ResponseEntity<byte[]> productLibrary2Excel(List<CgProductLibraryVo> list) {
        //1.创建Excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建文档摘要
        workbook.createInformationProperties();
        //3.获取并配置文档摘要信息
        DocumentSummaryInformation docInfo = workbook.getDocumentSummaryInformation();
        //文档类别
        docInfo.setCategory("产品信息");
        //文档管理员
        docInfo.setManager("顺德");
        //设置公司信息
        docInfo.setCompany("www.realfinance.com.cn");
        //4.获取文档摘要信息
        SummaryInformation summInfo = workbook.getSummaryInformation();
        //文档标题
        summInfo.setTitle("产品信息表");
        //文档作者
        summInfo.setAuthor("顺德");
        //文档备注
        summInfo.setComments("本文档由顺德提供");
        //5.创建样式
        //创建标题行样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //日期样式
        //HSSFCellStyle dateCellStyle = workbook.createCellStyle();
        //dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
        HSSFSheet sheet = workbook.createSheet("产品信息表");
        //设置列的宽度
        sheet.setColumnWidth(0, 5 * 256);
        sheet.setColumnWidth(1, 8 * 256);
        sheet.setColumnWidth(2, 8 * 256);
        sheet.setColumnWidth(3, 8 * 256);
        sheet.setColumnWidth(4, 20 * 256);
        sheet.setColumnWidth(5, 10 * 256);
        sheet.setColumnWidth(6, 10 * 256);
        sheet.setColumnWidth(7, 10 * 256);
        sheet.setColumnWidth(8, 8 * 256);
        //6.创建标题行
        HSSFRow r0 = sheet.createRow(0);
        HSSFCell c0 = r0.createCell(0);
        c0.setCellValue("编号");
        c0.setCellStyle(headerStyle);
        HSSFCell c1 = r0.createCell(1);
        c1.setCellStyle(headerStyle);
        c1.setCellValue("产品编码");
        HSSFCell c2 = r0.createCell(2);
        c2.setCellStyle(headerStyle);
        c2.setCellValue("产品名称");
        HSSFCell c3 = r0.createCell(3);
        c3.setCellStyle(headerStyle);
        c3.setCellValue("采购目录");
        HSSFCell c4 = r0.createCell(4);
        c4.setCellStyle(headerStyle);
        c4.setCellValue("产品描述");
        HSSFCell c5 = r0.createCell(5);
        c5.setCellStyle(headerStyle);
        c5.setCellValue("规格型号");
        HSSFCell c6 = r0.createCell(6);
        c6.setCellStyle(headerStyle);
        c6.setCellValue("产品代码");
        HSSFCell c7 = r0.createCell(7);
        c7.setCellStyle(headerStyle);
        c7.setCellValue("英文名");
        HSSFCell c8 = r0.createCell(8);
        c8.setCellStyle(headerStyle);
        c8.setCellValue("主计量单位");
        for (int i = 0; i < list.size(); i++) {
            CgProductLibraryVo vo = list.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(vo.getId());
            row.createCell(1).setCellValue(vo.getProductEncoded());
            row.createCell(2).setCellValue(vo.getProductName());
            row.createCell(3).setCellValue(vo.getPurchaseCatalog().getName());
            row.createCell(4).setCellValue(vo.getProductDescribe());
            row.createCell(5).setCellValue(vo.getModel());
            row.createCell(6).setCellValue(vo.getProductCode());
            row.createCell(7).setCellValue(vo.getEnglishName());
            row.createCell(8).setCellValue(vo.getCalculateUnit());
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setContentDispositionFormData("attachment", new String("产品表.xls".getBytes("UTF-8"), "ISO-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            workbook.write(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(stream.toByteArray(), headers, HttpStatus.CREATED);
    }

    public static ResponseEntity<byte[]> purchasePlan2Excel(List<CgPurchasePlanVo> list) {
        //1.创建Excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建文档摘要
        workbook.createInformationProperties();
        //3.获取并配置文档摘要信息
        DocumentSummaryInformation docInfo = workbook.getDocumentSummaryInformation();
        //文档类别
        docInfo.setCategory("年度计划信息");
        //文档管理员
        docInfo.setManager("顺德");
        //设置公司信息
        docInfo.setCompany("www.realfinance.com.cn");
        //4.获取文档摘要信息
        SummaryInformation summInfo = workbook.getSummaryInformation();
        //文档标题
        summInfo.setTitle("年度计划信息表");
        //文档作者
        summInfo.setAuthor("顺德");
        //文档备注
        summInfo.setComments("本文档由顺德提供");
        //5.创建样式
        //创建标题行样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //日期样式
        //HSSFCellStyle dateCellStyle = workbook.createCellStyle();
        //dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
        HSSFSheet sheet = workbook.createSheet("产品信息表");
        //设置列的宽度
        sheet.setColumnWidth(0, 5 * 256);
        sheet.setColumnWidth(1, 10 * 256);
        sheet.setColumnWidth(2, 15 * 256);
        sheet.setColumnWidth(3, 15 * 256);
        sheet.setColumnWidth(4, 10 * 256);
        sheet.setColumnWidth(5, 18 * 256);
        sheet.setColumnWidth(6, 15 * 256);
        sheet.setColumnWidth(7, 10 * 256);
        sheet.setColumnWidth(8, 15 * 256);
        sheet.setColumnWidth(9, 15 * 256);
        sheet.setColumnWidth(10, 15 * 256);
        sheet.setColumnWidth(11, 15 * 256);
        sheet.setColumnWidth(12, 15 * 256);
        sheet.setColumnWidth(13, 15 * 256);
        sheet.setColumnWidth(14, 10 * 256);
        sheet.setColumnWidth(15, 15 * 256);
        sheet.setColumnWidth(16, 10 * 256);
        sheet.setColumnWidth(17, 15 * 256);
        sheet.setColumnWidth(18, 10 * 256);
        sheet.setColumnWidth(19, 15 * 256);
        //6.创建标题行
        HSSFRow r0 = sheet.createRow(0);
        HSSFCell c0 = r0.createCell(0);
        c0.setCellValue("编号");
        c0.setCellStyle(headerStyle);
        HSSFCell c1 = r0.createCell(1);
        c1.setCellStyle(headerStyle);
        c1.setCellValue("项目名称");
        HSSFCell c2 = r0.createCell(2);
        c2.setCellStyle(headerStyle);
        c2.setCellValue("采购内容说明");
        HSSFCell c3 = r0.createCell(3);
        c3.setCellStyle(headerStyle);
        c3.setCellValue("采购估算金额(元)");
        HSSFCell c4 = r0.createCell(4);
        c4.setCellStyle(headerStyle);
        c4.setCellValue("项目分类");
        HSSFCell c5 = r0.createCell(5);
        c5.setCellStyle(headerStyle);
        c5.setCellValue("本年计划列支额(元)");
        HSSFCell c6 = r0.createCell(6);
        c6.setCellStyle(headerStyle);
        c6.setCellValue("计划采购年限");
        HSSFCell c7 = r0.createCell(7);
        c7.setCellStyle(headerStyle);
        c7.setCellValue("采购类别");
        HSSFCell c8 = r0.createCell(8);
        c8.setCellStyle(headerStyle);
        c8.setCellValue("拟签合同模式");
        HSSFCell c9 = r0.createCell(9);
        c9.setCellStyle(headerStyle);
        c9.setCellValue("拟选供应商数(个)");
        HSSFCell c10 = r0.createCell(10);
        c10.setCellStyle(headerStyle);
        c10.setCellValue("是否属长期延续项目");
        HSSFCell c11 = r0.createCell(11);
        c11.setCellStyle(headerStyle);
        c11.setCellValue("计划提交采购申请日");
        HSSFCell c12 = r0.createCell(12);
        c12.setCellStyle(headerStyle);
        c12.setCellValue("项目计划实施日期");
        HSSFCell c13 = r0.createCell(13);
        c13.setCellStyle(headerStyle);
        c13.setCellValue("统筹部门");
        HSSFCell c14 = r0.createCell(14);
        c14.setCellStyle(headerStyle);
        c14.setCellValue("统筹部门联系人");
        HSSFCell c15 = r0.createCell(15);
        c15.setCellStyle(headerStyle);
        c15.setCellValue("需求部门");
        HSSFCell c16 = r0.createCell(16);
        c16.setCellStyle(headerStyle);
        c16.setCellValue("需求部门联系人");
        HSSFCell c17 = r0.createCell(17);
        c17.setCellStyle(headerStyle);
        c17.setCellValue("拟采用的采购方式");
        HSSFCell c18 = r0.createCell(18);
        c18.setCellStyle(headerStyle);
        c18.setCellValue("备注");
        HSSFCell c19 = r0.createCell(19);
        c19.setCellStyle(headerStyle);
        c19.setCellValue("上次合同到期");


        for (int i = 0; i < list.size(); i++) {
            CgPurchasePlanVo vo = list.get(i);
            HSSFRow row = sheet.createRow(i + 1);
            row.createCell(0).setCellValue(vo.getNumber());
            row.createCell(1).setCellValue(vo.getProjectName());
            row.createCell(2).setCellValue(vo.getContentDescription());
            if (vo.getEstimatedAmount() != null) {
                row.createCell(3).setCellValue(String.valueOf(vo.getEstimatedAmount()));
            }
            if (vo.getProjectClassification() != null) {
                if ("0".equals(vo.getProjectClassification())) {
                    row.createCell(4).setCellValue("本年度新增");

                } else if ("1".equals(vo.getProjectClassification())) {
                    vo.setProjectClassification("上年度递延");
                }
            }
            if (vo.getPlannedExpenditure() != null) {
                row.createCell(5).setCellValue(String.valueOf(vo.getPlannedExpenditure()));
            }
            if (vo.getPlannedProcurementPeriod() != null) {
                if ("0".equals(vo.getPlannedProcurementPeriod())) {
                    row.createCell(6).setCellValue("6个月");
                } else if ("1".equals(vo.getPlannedProcurementPeriod())) {
                    row.createCell(6).setCellValue("1年");
                } else if ("2".equals(vo.getPlannedProcurementPeriod())) {
                    row.createCell(6).setCellValue("2年");
                } else if ("3".equals(vo.getPlannedProcurementPeriod())) {
                    row.createCell(6).setCellValue("3年");
                } else if ("4".equals(vo.getPlannedProcurementPeriod())) {
                    row.createCell(6).setCellValue("长期");
                } else if ("5".equals(vo.getPlannedProcurementPeriod())) {
                    row.createCell(6).setCellValue("一次性");
                } else if ("6".equals(vo.getPlannedProcurementPeriod())) {
                    row.createCell(6).setCellValue("待定");
                }
            }
            if (vo.getPurchaseCategory() != null) {
                if ("0".equals(vo.getPurchaseCategory())) {
                    row.createCell(7).setCellValue("货物");
                } else if ("1".equals(vo.getPurchaseCategory())) {
                    row.createCell(7).setCellValue("服务");
                } else if ("2".equals(vo.getPurchaseCategory())) {
                    row.createCell(7).setCellValue("工程");

                }
            }
            if (vo.getContractMode() != null) {
                if ("0".equals(vo.getContractMode())) {
                    row.createCell(8).setCellValue("总价合同");
                } else if ("1".equals(vo.getContractMode())) {
                    row.createCell(8).setCellValue("单价合同");
                }
            }
            row.createCell(9).setCellValue(vo.getSupplierNumber() == null ? "" : (vo.getSupplierNumber()).toString());
            if (vo.getContinue() != null) {
                if ("0".equals(vo.getContinue())) {
                    row.createCell(10).setCellValue("是");
                } else if ("1".equals(vo.getContinue())) {
                    row.createCell(10).setCellValue("否");
                }
            }
            if (vo.getPurchaseApplicationDate() != null) {
                row.createCell(11).setCellValue((vo.getPurchaseApplicationDate().toString()));
            }
            if (vo.getImplementationDate() != null) {
                row.createCell(12).setCellValue((vo.getImplementationDate().toString()));
            }
            row.createCell(13).setCellValue(vo.getCoOrdinationDepartment());
            row.createCell(14).setCellValue(vo.getCoOrdinationDepartmentContacts());
            row.createCell(15).setCellValue(vo.getDemandDepartment());
            row.createCell(16).setCellValue(vo.getDemandDepartmentContacts());
            if (vo.getProcurementMethod() != null) {
                if ("0".equals(vo.getProcurementMethod())) {
                    row.createCell(17).setCellValue("单一来源");
                } else if ("1".equals(vo.getProcurementMethod())) {
                    row.createCell(17).setCellValue("公开招标");
                } else if ("2".equals(vo.getProcurementMethod())) {
                    row.createCell(17).setCellValue("邀请招标");
                } else if ("3".equals(vo.getProcurementMethod())) {
                    row.createCell(17).setCellValue("竞争性谈判");
                } else if ("4".equals(vo.getProcurementMethod())) {
                    row.createCell(17).setCellValue("竞争性磋商");
                } else if ("5".equals(vo.getProcurementMethod())) {
                    row.createCell(17).setCellValue("询价");
                }
            }
            row.createCell(18).setCellValue(vo.getRemarks());
            if (vo.getDueDate() != null) {
                row.createCell(19).setCellValue((vo.getDueDate()).toString());
            }
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setContentDispositionFormData("attachment", new String("年度计划信息表.xls".getBytes("UTF-8"), "ISO-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            workbook.write(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(stream.toByteArray(), headers, HttpStatus.CREATED);
    }

    public static ResponseEntity<byte[]> project2Excel(CgProjectVo vo) {
        //1.创建Excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建文档摘要
        workbook.createInformationProperties();
        //3.获取并配置文档摘要信息
        DocumentSummaryInformation docInfo = workbook.getDocumentSummaryInformation();
        //文档类别
        docInfo.setCategory("采购文件内容");
        //文档管理员
        docInfo.setManager("顺德");
        //设置公司信息
        docInfo.setCompany("www.realfinance.com.cn");
        //4.获取文档摘要信息
        SummaryInformation summInfo = workbook.getSummaryInformation();
        //文档标题
        summInfo.setTitle("采购文件内容");
        //文档作者
        summInfo.setAuthor("顺德");
        //文档备注
        summInfo.setComments("本文档由顺德提供");
        //5.创建样式
        //创建标题行样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //日期样式
        //HSSFCellStyle dateCellStyle = workbook.createCellStyle();
        //dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
        HSSFSheet sheet = workbook.createSheet("采购文件内容");
        //设置列的宽度
        sheet.setColumnWidth(0, 5 * 256);
        sheet.setColumnWidth(1, 25 * 256);
        sheet.setColumnWidth(2, 25 * 256);

        //6.创建标题行
        HSSFRow r0 = sheet.createRow(0);
        HSSFCell c0 = r0.createCell(0);
        c0.setCellValue("序号");
        c0.setCellStyle(headerStyle);
        HSSFCell c1 = r0.createCell(1);
        c1.setCellStyle(headerStyle);
        c1.setCellValue("采购内容");
        HSSFCell c2 = r0.createCell(2);
        c2.setCellStyle(headerStyle);
        c2.setCellValue("具体说明");
        HSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("1");
        row1.createCell(1).setCellValue("采购编号");
        row1.createCell(2).setCellValue(vo.getProjectNo());
        HSSFRow row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("2");
        row2.createCell(1).setCellValue("采购申请名称");
        row2.createCell(2).setCellValue(vo.getName());
        HSSFRow row3 = sheet.createRow(3);
        row3.createCell(0).setCellValue("3");
        row3.createCell(1).setCellValue("成交/中标单位数");
        row3.createCell(2).setCellValue(vo.getNumberOfWinSup());
        HSSFRow row4 = sheet.createRow(4);
        row4.createCell(0).setCellValue("4");
        row4.createCell(1).setCellValue("合同有效期(月)");
        row4.createCell(2).setCellValue(vo.getContractValidity());
        HSSFRow row5 = sheet.createRow(5);
        row5.createCell(0).setCellValue("5");
        row5.createCell(1).setCellValue("合同类别");
        String contractCategory = "";
        if (vo.getContractCategory().equals("DJHT")) {
            contractCategory = "单价合同";
        } else if (vo.getContractCategory().equals("ZJHT")) {
            contractCategory = "总价合同";
        } else if (vo.getContractCategory().equals("KJHT")) {
            contractCategory = "框架合同";
        } else {
            contractCategory = vo.getContractCategory();
        }
        row5.createCell(2).setCellValue(contractCategory);
        HSSFRow row6 = sheet.createRow(6);
        row6.createCell(0).setCellValue("6");
        row6.createCell(1).setCellValue("参与供应商资格要求");
        row6.createCell(2).setCellValue(vo.getSupRequirements() == null ? "" : vo.getSupRequirements());
        HSSFRow row7 = sheet.createRow(7);
        row7.createCell(0).setCellValue("7");
        row7.createCell(1).setCellValue("内容及数量");
        row7.createCell(2).setCellValue(vo.getRequirement().getPurContent());
        HSSFRow row8 = sheet.createRow(8);
        row8.createCell(0).setCellValue("8");
        row8.createCell(1).setCellValue("配套服务");
        row8.createCell(2).setCellValue(vo.getRequirement().getNote());
        HSSFRow row9 = sheet.createRow(9);
        row9.createCell(0).setCellValue("9");
        row9.createCell(1).setCellValue("服务团队要求");
        row9.createCell(2).setCellValue(vo.getRequirement().getServiceRequirements());
        HSSFRow row10 = sheet.createRow(10);
        row10.createCell(0).setCellValue("10");
        row10.createCell(1).setCellValue("售后服务");
        row10.createCell(2).setCellValue(vo.getRequirement().getAfterSalesService());
        HSSFRow row11 = sheet.createRow(11);
        row11.createCell(0).setCellValue("11");
        row11.createCell(1).setCellValue("交付质量及要求");
        row11.createCell(2).setCellValue(vo.getRequirement().getQualityRequirements());
        HSSFRow row12 = sheet.createRow(12);
        row12.createCell(0).setCellValue("12");
        row12.createCell(1).setCellValue("交付时间");
        row12.createCell(2).setCellValue(vo.getRequirement().getDeliveryTime());
        HSSFRow row13 = sheet.createRow(13);
        row13.createCell(0).setCellValue("13");
        row13.createCell(1).setCellValue("交付地点");
        row13.createCell(2).setCellValue(vo.getRequirement().getSupplyLocation());
        HSSFRow row14 = sheet.createRow(14);
        row14.createCell(0).setCellValue("14");
        row14.createCell(1).setCellValue("包装运输要求");
        row14.createCell(2).setCellValue(vo.getRequirement().getTransportRequirements());
        HSSFRow row15 = sheet.createRow(15);
        row15.createCell(0).setCellValue("15");
        row15.createCell(1).setCellValue("付款方式");
        row15.createCell(2).setCellValue(vo.getRequirement().getPaymentMethod());
        HSSFRow row16 = sheet.createRow(16);
        row16.createCell(0).setCellValue("16");
        row16.createCell(1).setCellValue("是否设保证金");
        row16.createCell(2).setCellValue(vo.getRequirement().getHasBond().equals(false) ? "否" : "是");
        HSSFRow row17 = sheet.createRow(17);
        row17.createCell(0).setCellValue("17");
        row17.createCell(1).setCellValue("履约保证金（元）");
        row17.createCell(2).setCellValue(vo.getRequirement().getPerformanceBond() == null ? "" : vo.getRequirement().getPerformanceBond().toString());
        HSSFRow row18 = sheet.createRow(18);
        row18.createCell(0).setCellValue("18");
        row18.createCell(1).setCellValue("履约保证金年限");
        if (vo.getRequirement().getPerformanceYears() == null) {
            row18.createCell(2).setCellValue("");
        } else {
            row18.createCell(2).setCellValue(vo.getRequirement().getPerformanceYears());
        }
        HSSFRow row19 = sheet.createRow(19);
        row19.createCell(0).setCellValue("19");
        row19.createCell(1).setCellValue("质保金（元）");
        row19.createCell(2).setCellValue(vo.getRequirement().getWarrantyBond() == null ? "" : vo.getRequirement().getWarrantyBond().toString());
        HSSFRow row20 = sheet.createRow(20);
        row20.createCell(0).setCellValue("20");
        row20.createCell(1).setCellValue("质保金年限");
        if (vo.getRequirement().getWarrantyYears() == null) {
            row20.createCell(2).setCellValue("");
        } else {
            row20.createCell(2).setCellValue(vo.getRequirement().getWarrantyYears());
        }
        HSSFRow row21 = sheet.createRow(21);
        row21.createCell(0).setCellValue("21");
        row21.createCell(1).setCellValue("其他约定");
        row21.createCell(2).setCellValue(vo.getRequirement().getBreachClause());
        HSSFRow row22 = sheet.createRow(22);
        row22.createCell(0).setCellValue("22");
        row22.createCell(1).setCellValue("补充说明");
        if (vo.getRequirement().getSupplement() == null) {

        } else {
            row22.createCell(2).setCellValue(vo.getRequirement().getSupplement());
        }
        HSSFRow row23 = sheet.createRow(23);
        row23.createCell(0).setCellValue("23");
        row23.createCell(1).setCellValue("是否需提供样板");
        row23.createCell(2).setCellValue(vo.getRequirement().getNeedSample().equals(false) ? "否" : "是");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setContentDispositionFormData("project", new String("采购文件内容.xls".getBytes("UTF-8"), "ISO-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            workbook.write(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(stream.toByteArray(), headers, HttpStatus.CREATED);
    }

    public static ResponseEntity<byte[]> requirementItem2Excel(List<CgRequirementItemVo> list) {
        //1.创建Excel文档
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建文档摘要
        workbook.createInformationProperties();
        //3.获取并配置文档摘要信息
        DocumentSummaryInformation docInfo = workbook.getDocumentSummaryInformation();
        //文档类别
        docInfo.setCategory("货物类采购清单");
        //文档管理员
        docInfo.setManager("顺德");
        //设置公司信息
        docInfo.setCompany("www.realfinance.com.cn");
        //4.获取文档摘要信息
        SummaryInformation summInfo = workbook.getSummaryInformation();
        //文档标题
        summInfo.setTitle("货物类采购清单");
        //文档作者
        summInfo.setAuthor("顺德");
        //文档备注
        summInfo.setComments("本文档由顺德提供");
        //5.创建样式
        //创建标题行样式
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //日期样式
        //HSSFCellStyle dateCellStyle = workbook.createCellStyle();
        //dateCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
        HSSFSheet sheet = workbook.createSheet("货物类采购清单");
        //设置列的宽度
        sheet.setColumnWidth(0, 25 * 256);
        sheet.setColumnWidth(1, 15 * 256);
        sheet.setColumnWidth(2, 15 * 256);
        sheet.setColumnWidth(3, 5 * 256);
        sheet.setColumnWidth(4, 5 * 256);
        sheet.setColumnWidth(5, 15 * 256);
        sheet.setColumnWidth(6, 25 * 256);
        sheet.setColumnWidth(7, 25 * 256);

        //6.创建标题行
        HSSFRow r0 = sheet.createRow(0);
        HSSFCell c0 = r0.createCell(0);
        c0.setCellValue("项目分类名称");
        c0.setCellStyle(headerStyle);
        HSSFCell c1 = r0.createCell(1);
        c1.setCellStyle(headerStyle);
        c1.setCellValue("货物名称");
        HSSFCell c2 = r0.createCell(2);
        c2.setCellStyle(headerStyle);
        c2.setCellValue("型号/规格");
        HSSFCell c3 = r0.createCell(3);
        c3.setCellStyle(headerStyle);
        c3.setCellValue("数量");
        HSSFCell c4 = r0.createCell(4);
        c4.setCellStyle(headerStyle);
        c4.setCellValue("单位");
        HSSFCell c5 = r0.createCell(5);
        c5.setCellStyle(headerStyle);
        c5.setCellValue("详细说明");
        HSSFCell c6 = r0.createCell(6);
        c6.setCellStyle(headerStyle);
        c6.setCellValue("是否提供样板");
        HSSFCell c7 = r0.createCell(7);
        c7.setCellStyle(headerStyle);
        c7.setCellValue("市场参考价(元)");
        for (int i = 0; i < list.size(); i++) {
            HSSFRow row = sheet.createRow(i+1);
            if (list.get(i).getPurchaseCatalog()!=null){
                row.createCell(0).setCellValue(list.get(i).getPurchaseCatalog().getName());
            }
            row.createCell(1).setCellValue(list.get(i).getName());
            row.createCell(2).setCellValue(list.get(i).getModel());
            row.createCell(3).setCellValue(list.get(i).getNumber());
            row.createCell(4).setCellValue(list.get(i).getUnit());
            row.createCell(5).setCellValue(list.get(i).getQualityRequirements());
            row.createCell(6).setCellValue(list.get(i).getNeedSample().equals(false) ? "否" : "是");
            row.createCell(7).setCellValue(list.get(i).getMarketPrice().toString());
        }


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setContentDispositionFormData("project", new String("货物类采购清单.xls".getBytes("UTF-8"), "ISO-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            workbook.write(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(stream.toByteArray(), headers, HttpStatus.CREATED);
    }



    //word文档导出模板
    /*public static ResponseEntity<byte[]> projectWord(CgProjectVo vo) {
        //建一个word文档
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph headParagraph = doc.createParagraph();
        headParagraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun headParagraphRun = headParagraph.createRun();
        headParagraphRun.setText("工作秘密");
        headParagraphRun.setColor("000000");
        headParagraphRun.setFontSize(15);
        //添加标题
        XWPFParagraph titleParagraph = doc.createParagraph();
        //设置段落居中
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleParagraphRun = titleParagraph.createRun();
        titleParagraphRun.setText("采购方案呈批表");
        titleParagraphRun.setColor("000000");
        titleParagraphRun.setFontSize(20);
        XWPFParagraph oneParagraph = doc.createParagraph();
        //设置段落居中
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun oneParagraphRun = oneParagraph.createRun();
        //创建表格 x行 y列
        XWPFTable headTable = doc.createTable(1, 6);
        XWPFTable bodyTable = doc.createTable(13 + vo.getProjectSups().size(), 6);
        //设置表格宽度
        setTableWidth(headTable, "9000");
        setTableWidth(bodyTable, "9000");
        //生成表格
        setHeadTable(headTable, vo);
        setBodyTable(bodyTable, vo);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        HttpHeaders headers = new HttpHeaders();
        try {
            headers.setContentDispositionFormData("采购方案呈批表", new String("采购方案呈批表.docx".getBytes("UTF-8"), "ISO-8859-1"));
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            doc.write(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(stream.toByteArray(), headers, HttpStatus.CREATED);
    }
*/

    /**
     * 设置行宽(word文档)
     *
     * @param table
     * @param width
     */
    private static void setTableWidth(XWPFTable table, String width) {
        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl.getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr.addNewTblW();
        CTJc cTJc = tblPr.addNewJc();
        cTJc.setVal(STJc.Enum.forString("center"));
        tblWidth.setW(new BigInteger(width));
        tblWidth.setType(STTblWidth.DXA);
    }

    /**
     * 给单元格的文本内容设置字体
     *
     * @param row        当前行
     * @param alignment  内容左右对齐/居中
     * @param cellIndex  列下标
     * @param fontFamily 字体风格
     * @param fontSize   字体大小
     * @param text       写入的内容
     * @param bold       是否加粗
     */
    private static void setStyleToFont(XWPFTableRow row, ParagraphAlignment alignment, int cellIndex, String fontFamily, int fontSize, String text, boolean bold) {

        XWPFParagraph pIO = row.getCell(cellIndex).getParagraphs().get(0);
        pIO.setAlignment(alignment);
        XWPFRun rIO = pIO.createRun();
        rIO.setFontFamily(fontFamily);
        rIO.setFontSize(fontSize);
        rIO.setText(text);
        rIO.setBold(bold);

    }

    /**
     * 合并列
     *
     * @param table
     * @param row      当前行下标
     * @param fromCell 起始列
     * @param toCell   终止列
     */
    private static void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if (cellIndex == fromCell) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    private static void setHeadTable(XWPFTable headTable, CgProjectVo vo) {
        //去边框
        headTable.getCTTbl().getTblPr().unsetTblBorders();
        XWPFTableRow row = headTable.getRow(0);
        setStyleToFont(row, ParagraphAlignment.LEFT, 0, "宋体", 10, "采购管理部门(盖章):" + vo.getRequirement().getPurDepartment().getName(), false);
        LocalDateTime createdTime = vo.getCreatedTime();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        String format = createdTime.format(df);
        setStyleToFont(row, ParagraphAlignment.RIGHT, 3, "宋体", 10, "填报日期:" + format, false);
        mergeCellsHorizontal(headTable, 0, 0, 2);
        mergeCellsHorizontal(headTable, 0, 3, 5);
    }

    private static void setBodyTable(XWPFTable bodyTable, CgProjectVo vo) {
        //获取第一行
        XWPFTableRow row0 = bodyTable.getRow(0);
        setStyleToFont(row0, ParagraphAlignment.LEFT, 0, "宋体", 12, "申请部门", true);
        setStyleToFont(row0, ParagraphAlignment.CENTER, 1, "宋体", 12, (vo.getReqDepartment()).getName(), false);
        setStyleToFont(row0, ParagraphAlignment.LEFT, 3, "宋体", 12, "使用机构", true);
        List<String> useDepartments = vo.getRequirement().getUseDepartments().stream().map(DepartmentVo::getName).collect(Collectors.toList());
        setStyleToFont(row0, ParagraphAlignment.CENTER, 4, "宋体", 12, StringUtils.join(useDepartments.toArray(), ","), false);
        XWPFTableRow row1 = bodyTable.getRow(1);
        setStyleToFont(row1, ParagraphAlignment.LEFT, 0, "宋体", 12, "项目名称", true);
        setStyleToFont(row1, ParagraphAlignment.CENTER, 1, "宋体", 12, vo.getName(), false);
        setStyleToFont(row1, ParagraphAlignment.LEFT, 3, "宋体", 12, "采购编号", true);
        setStyleToFont(row1, ParagraphAlignment.CENTER, 4, "宋体", 12, vo.getProjectNo(), false);
        XWPFTableRow row2 = bodyTable.getRow(2);
        List<BigDecimal> approvalAmounts = vo.getProjectOaData().stream().map(CgProjectOaDatumDto::getApprovalAmount).collect(Collectors.toList());
        List<String> approvalTitles = vo.getProjectOaData().stream().map(CgProjectOaDatumDto::getApprovalTitle).collect(Collectors.toList());
        setStyleToFont(row2, ParagraphAlignment.LEFT, 0, "宋体", 12, "立项金额（元）", true);
        setStyleToFont(row2, ParagraphAlignment.CENTER, 1, "宋体", 12, StringUtils.join(approvalAmounts.toArray(), ","), false);
        setStyleToFont(row2, ParagraphAlignment.LEFT, 3, "宋体", 12, "立项审批名", true);
        setStyleToFont(row2, ParagraphAlignment.CENTER, 4, "宋体", 12, StringUtils.join(approvalTitles.toArray(), ","), false);
        XWPFTableRow row3 = bodyTable.getRow(3);
        setStyleToFont(row3, ParagraphAlignment.LEFT, 0, "宋体", 12, "采购金额（元）", true);
        setStyleToFont(row3, ParagraphAlignment.CENTER, 1, "宋体", 12, vo.getRequirement().getReqTotalAmount().toString(), false);
        setStyleToFont(row3, ParagraphAlignment.LEFT, 3, "宋体", 12, "最高限价（元）", true);
        setStyleToFont(row3, ParagraphAlignment.CENTER, 4, "宋体", 12, vo.getRequirement().getMarketPrice().toString(), false);
        XWPFTableRow row4 = bodyTable.getRow(4);
        setStyleToFont(row4, ParagraphAlignment.LEFT, 0, "宋体", 12, "采购计划编号", true);
        setStyleToFont(row4, ParagraphAlignment.CENTER, 1, "宋体", 12, vo.getRequirement().getPurchasePlan() == null ? "" : vo.getRequirement().getPurchasePlan().getNumber(), false);
        setStyleToFont(row4, ParagraphAlignment.LEFT, 3, "宋体", 12, "合同类别", true);
        if (vo.getRequirement().getContractCategory().equals("DJHT")) {
            setStyleToFont(row4, ParagraphAlignment.CENTER, 4, "宋体", 12, "单价合同", false);
        } else if (vo.getRequirement().getContractCategory().equals("ZJHT")) {
            setStyleToFont(row4, ParagraphAlignment.CENTER, 4, "宋体", 12, "总价合同", false);
        } else if (vo.getRequirement().getContractCategory().equals("KJHT")) {
            setStyleToFont(row4, ParagraphAlignment.CENTER, 4, "宋体", 12, "框架合同", false);
        } else {
            setStyleToFont(row4, ParagraphAlignment.CENTER, 4, "宋体", 12, vo.getRequirement().getContractCategory(), false);
        }
        XWPFTableRow row5 = bodyTable.getRow(5);
        setStyleToFont(row5, ParagraphAlignment.LEFT, 0, "宋体", 12, "成交单位数", true);
        setStyleToFont(row5, ParagraphAlignment.CENTER, 1, "宋体", 12, vo.getNumberOfWinSup().toString(), false);
        setStyleToFont(row5, ParagraphAlignment.LEFT, 3, "宋体", 12, "合同有效期（月）", true);
        setStyleToFont(row5, ParagraphAlignment.CENTER, 4, "宋体", 12, vo.getRequirement().getContractValidity().toString(), false);
        XWPFTableRow row6 = bodyTable.getRow(6);
        setStyleToFont(row6, ParagraphAlignment.LEFT, 0, "宋体", 12, "供应商资格条件", true);
        setStyleToFont(row6, ParagraphAlignment.CENTER, 1, "宋体", 12, vo.getSupQualReq(), false);
        XWPFTableRow row7 = bodyTable.getRow(7);
        setStyleToFont(row7, ParagraphAlignment.LEFT, 0, "宋体", 12, "拟用采购方式", true);
        String purMode = "";
        if (vo.getRequirement().getPurMode().equals("GKZB")) {
            purMode = "公开招标";
        } else if (vo.getRequirement().getPurMode().equals("YQZB")) {
            purMode = "邀请招标";
        } else if (vo.getRequirement().getPurMode().equals("JZXTP")) {
            purMode = "竞争性谈判";
        } else if (vo.getRequirement().getPurMode().equals("JZXCS")) {
            purMode = "竞争性磋商";
        } else if (vo.getRequirement().getPurMode().equals("XJ")) {
            purMode = "询价";
        } else if (vo.getRequirement().getPurMode().equals("DYLY")) {
            purMode = "单一来源";
        } else {
            purMode = vo.getRequirement().getPurMode();
        }
        setStyleToFont(row7, ParagraphAlignment.CENTER, 1, "宋体", 12, purMode, false);
        setStyleToFont(row7, ParagraphAlignment.LEFT, 3, "宋体", 12, "评审方式", true);
        String evalMethod = "";
        if (vo.getEvalMethod().equals("ZDPBJF")) {
            evalMethod = "最低评标价法";
        } else if (vo.getEvalMethod().equals("ZJJBJPJZJGF")) {
            evalMethod = "最接近报价平均值价格法";
        } else if (vo.getEvalMethod().equals("HLDJF")) {
            evalMethod = "合理低价法";
        } else if (vo.getEvalMethod().equals("ZHPFF")) {
            evalMethod = "综合评分法";
        } else if (vo.getEvalMethod().equals("XJBF")) {
            evalMethod = "性价比法";
        } else {
            evalMethod = vo.getEvalMethod();
        }
        setStyleToFont(row7, ParagraphAlignment.CENTER, 4, "宋体", 12, evalMethod, false);
        XWPFTableRow row8 = bodyTable.getRow(8);
        setStyleToFont(row8, ParagraphAlignment.LEFT, 0, "宋体", 12, "拟用采购方式原因及说明", true);
        setStyleToFont(row8, ParagraphAlignment.CENTER, 1, "宋体", 12, vo.getEvalMethodReason(), false);
        setStyleToFont(row8, ParagraphAlignment.LEFT, 3, "宋体", 12, "评审要素及说明", true);
        setStyleToFont(row8, ParagraphAlignment.CENTER, 4, "宋体", 12, "评审要素及说明", false);
        XWPFTableRow row9 = bodyTable.getRow(9);
        setStyleToFont(row9, ParagraphAlignment.LEFT, 0, "宋体", 12, "评审小组", true);
        setStyleToFont(row9, ParagraphAlignment.CENTER, 1, "宋体", 12, "评审小组", false);
        XWPFTableRow row10 = bodyTable.getRow(10);
        setStyleToFont(row10, ParagraphAlignment.LEFT, 0, "宋体", 12, "采购情况说明", true);
        setStyleToFont(row10, ParagraphAlignment.CENTER, 1, "宋体", 12, "采购情况说明", false);
        XWPFTableRow row11 = bodyTable.getRow(11);
        setStyleToFont(row11, ParagraphAlignment.LEFT, 0, "宋体", 12, "推荐供应商清单:", true);
        XWPFTableRow row12 = bodyTable.getRow(12);
        setStyleToFont(row12, ParagraphAlignment.CENTER, 0, "宋体", 12, "序号", true);
        setStyleToFont(row12, ParagraphAlignment.CENTER, 1, "宋体", 12, "供应商名称", true);
        setStyleToFont(row12, ParagraphAlignment.CENTER, 2, "宋体", 12, "法人名称", true);
        setStyleToFont(row12, ParagraphAlignment.CENTER, 3, "宋体", 12, "企业地址", true);
        setStyleToFont(row12, ParagraphAlignment.CENTER, 4, "宋体", 12, "是否我行关联方", true);
        Integer j = 1;
        for (CgProjectSupVo projectSup : vo.getProjectSups()) {
            XWPFTableRow row = bodyTable.getRow(12 + j);
            setStyleToFont(row, ParagraphAlignment.CENTER, 0, "宋体", 12, j.toString(), false);
            setStyleToFont(row, ParagraphAlignment.CENTER, 1, "宋体", 12, projectSup.getSupplier().getName(), false);
            setStyleToFont(row, ParagraphAlignment.CENTER, 2, "宋体", 12, projectSup.getSupplier().getStatutoryRepresentative(), false);
            setStyleToFont(row, ParagraphAlignment.CENTER, 3, "宋体", 12, projectSup.getSupplier().getCompanyAddress(), false);
            setStyleToFont(row, ParagraphAlignment.CENTER, 4, "宋体", 12, projectSup.getSupplierRelatedStatus() == "1" ? "是" : "否", false);
            j++;
        }

        //合并单元格
        for (int i = 0; i < bodyTable.getRows().size(); i++) {
            if (i < 12) {
                if (i == 11) {
                    mergeCellsHorizontal(bodyTable, i, 0, 5);
                } else if (i == 6 || i == 9 || i == 10) {
                    mergeCellsHorizontal(bodyTable, i, 1, 5);
                } else {
                    mergeCellsHorizontal(bodyTable, i, 1, 2);
                    mergeCellsHorizontal(bodyTable, i, 4, 5);
                }
            } else {
                mergeCellsHorizontal(bodyTable, i, 4, 5);
            }
        }

    }

}
