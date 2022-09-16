package com.realfinance.sofa.cg.util;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.realfinance.sofa.cg.core.model.CgPurchasePlanImportDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FastByteArrayOutputStream;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 代发模板
 */
@Component
public class ImportTollBatchBiz {

    private static final Logger log = LoggerFactory.getLogger(ImportTollBatchBiz.class);

    public final Map<String, String> headerAlias = new LinkedHashMap<>();

    {
        headerAlias.put("编号","number");
        headerAlias.put("项目名称","projectName");
        headerAlias.put("采购内容说明","contentDescription");
        headerAlias.put("采购估算金额", "estimatedAmount");
        headerAlias.put("项目分类", "projectClassification");
        headerAlias.put("本年计划列支额", "plannedExpenditure");
        headerAlias.put("计划采购年限", "plannedProcurementPeriod");
        headerAlias.put("采购类别", "purchaseCategory");
        headerAlias.put("拟签合同模式", "contractMode");
        headerAlias.put("拟选供应商数", "supplierNumber");
        headerAlias.put("是否属长期延续项目", "isContinue");
        headerAlias.put("计划提交采购申请日", "purchaseApplicationDate");
        headerAlias.put("项目计划实施日期", "ImplementationDate");
        headerAlias.put("统筹部门", "coOrdinationDepartment");
        headerAlias.put("统筹部门联系人", "coOrdinationDepartmentContacts");
        headerAlias.put("需求部门", "demandDepartment");
        headerAlias.put("需求部门联系人", "demandDepartmentContacts");
        headerAlias.put("拟采用的采购方式", "procurementMethod");
        headerAlias.put("备注", "remarks");
        headerAlias.put("上次合同到期日", "dueDate");

    }

    public byte[] getExcelTemplate(boolean isXlsx) {
        FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        ExcelUtil.getWriter(isXlsx).writeRow(headerAlias.keySet()).flush(out, true).close();
        return out.toByteArray();
    }

    public List<CgPurchasePlanImportDto> readExcel(InputStream in) {
        Objects.requireNonNull(in);
        ExcelReader reader = ExcelUtil.getReader(in).setHeaderAlias(headerAlias);
        List<CgPurchasePlanImportDto> tollOrderImportDtos = reader.read(0,1,20001, CgPurchasePlanImportDto.class);

        for (CgPurchasePlanImportDto dto : tollOrderImportDtos) {
            if (dto.getProjectName()==null){
                throw new RuntimeException("导入数据格式有误!");
            }
            if (dto.getProjectClassification()!=null){
                if ("本年度新增".equals(dto.getProjectClassification())){
                    dto.setProjectClassification("0");
                }else if ("上年度递延".equals(dto.getProjectClassification())){
                    dto.setProjectClassification("1");
                }
            }
            if (dto.getPlannedProcurementPeriod()!=null){
                if ("6个月".equals(dto.getPlannedProcurementPeriod())){
                    dto.setPlannedProcurementPeriod("0");
                }else if ("1年".equals(dto.getPlannedProcurementPeriod())){
                    dto.setPlannedProcurementPeriod("1");
                }else if ("2年".equals(dto.getPlannedProcurementPeriod())){
                    dto.setPlannedProcurementPeriod("2");
                }else if ("3年".equals(dto.getPlannedProcurementPeriod())){
                    dto.setPlannedProcurementPeriod("3");
                }else if ("长期".equals(dto.getPlannedProcurementPeriod())){
                    dto.setPlannedProcurementPeriod("4");
                }else if ("一次性".equals(dto.getPlannedProcurementPeriod())){
                    dto.setPlannedProcurementPeriod("5");
                }else if ("待定".equals(dto.getPlannedProcurementPeriod())){
                    dto.setPlannedProcurementPeriod("6");
                }
            }
            if(dto.getPurchaseCategory()!=null){
                if ("货物".equals(dto.getPurchaseCategory())) {
                    dto.setPurchaseCategory("0");
                }else if("服务".equals(dto.getPurchaseCategory())){
                    dto.setPurchaseCategory("1");
                }else if("工程".equals(dto.getPurchaseCategory())){
                    dto.setPurchaseCategory("2");
                }
            }
            if (dto.getContractMode()!=null){
                if ("总价合同".equals(dto.getContractMode())){
                    dto.setContractMode("0");
                }else if("单价合同".equals(dto.getContractMode())){
                    dto.setContractMode("1");
                }
            }
            if (dto.getIsContinue()!=null){
                if ("是".equals(dto.getIsContinue())){
                    dto.setIsContinue("0");
                }else if("否".equals(dto.getIsContinue())){
                    dto.setIsContinue("1");
                }
            }
            if (dto.getProcurementMethod()!=null){
                if ("单一来源".equals(dto.getProcurementMethod())){
                    dto.setProcurementMethod("0");
                }else if("公开招标".equals(dto.getProcurementMethod())){
                    dto.setProcurementMethod("1");
                }else if("邀请招标".equals(dto.getProcurementMethod())){
                    dto.setProcurementMethod("2");
                } else if("竞争性谈判".equals(dto.getProcurementMethod())){
                    dto.setProcurementMethod("3");
                } else if("竞争性磋商".equals(dto.getProcurementMethod())){
                    dto.setProcurementMethod("4");
                }else if("询价".equals(dto.getProcurementMethod())){
                    dto.setProcurementMethod("5");
                }
            }
        }
        return tollOrderImportDtos;
    }




}
