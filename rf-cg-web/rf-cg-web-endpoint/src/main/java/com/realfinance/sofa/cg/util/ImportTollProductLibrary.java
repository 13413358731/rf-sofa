package com.realfinance.sofa.cg.util;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.realfinance.sofa.cg.model.cg.CgProductLibraryImportDto;
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
public class ImportTollProductLibrary {

    private static final Logger log = LoggerFactory.getLogger(ImportTollProductLibrary.class);

    public final Map<String, String> headerAlias = new LinkedHashMap<>();

    {
        headerAlias.put("产品编码","productEncoded");
        headerAlias.put("产品名称","productName");
        headerAlias.put("产品描述","productDescribe");
        headerAlias.put("规格型号", "model");
        headerAlias.put("主计量单位", "calculateUnit");
        headerAlias.put("产品代码", "productCode");
        headerAlias.put("英文名", "englishName");
//        headerAlias.put("是否中标", "isBid");
        headerAlias.put("入库时间", "enterRepositoryTime");
//        headerAlias.put("价格", "price");
//        headerAlias.put("是否属长期延续项目", "isContinue");
//        headerAlias.put("计划提交采购申请日", "purchaseApplicationDate");
//        headerAlias.put("项目计划实施日期", "ImplementationDate");
//        headerAlias.put("统筹部门", "coOrdinationDepartment");
//        headerAlias.put("统筹部门联系人", "coOrdinationDepartmentContacts");
//        headerAlias.put("需求部门", "demandDepartment");
//        headerAlias.put("需求部门联系人", "demandDepartmentContacts");
//        headerAlias.put("拟采用的采购方式", "procurementMethod");
//        headerAlias.put("备注", "remarks");
//        headerAlias.put("上次合同到期日", "dueDate");
    }

    public byte[] getExcelTemplate(boolean isXlsx) {
        FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        ExcelUtil.getWriter(isXlsx).writeRow(headerAlias.keySet()).flush(out, true).close();
        return out.toByteArray();
    }

    public List<CgProductLibraryImportDto> readExcel(InputStream in) {
        Objects.requireNonNull(in);
        ExcelReader reader = ExcelUtil.getReader(in).setHeaderAlias(headerAlias);
        List<CgProductLibraryImportDto> tollOrderImportDtos = reader.read(0,1,20001, CgProductLibraryImportDto.class);
        for (CgProductLibraryImportDto dto : tollOrderImportDtos) {
            if (dto.getProductCode()==null || dto.getProductName()==null){
                throw new RuntimeException("导入数据异常有误");
            }
        }
        return tollOrderImportDtos;
    }




}
