package com.realfinance.sofa.cg.util;


import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.realfinance.sofa.cg.core.model.CgRequirementItemImportDto;
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
public class ImportTollRequirementItems {

    private static final Logger log = LoggerFactory.getLogger(ImportTollRequirementItems.class);

    public final Map<String, String> headerAlias = new LinkedHashMap<>();

    {
        headerAlias.put("项目分类名称","purchaseCatalogName");
        headerAlias.put("货物名称","name");
        headerAlias.put("型号/规格","model");
        headerAlias.put("数量", "number");
        headerAlias.put("单位", "unit");
        headerAlias.put("详细说明", "qualityRequirements");
        headerAlias.put("是否提供样板", "needSample");
        headerAlias.put("市场参考价(元)", "marketPrice");
    }

    public byte[] getExcelTemplate(boolean isXlsx) {
        FastByteArrayOutputStream out = new FastByteArrayOutputStream();
        ExcelUtil.getWriter(isXlsx).writeRow(headerAlias.keySet()).flush(out, true).close();
        return out.toByteArray();
    }

    public List<CgRequirementItemImportDto> readExcel(InputStream in) {
        Objects.requireNonNull(in);
        ExcelReader reader = ExcelUtil.getReader(in).setHeaderAlias(headerAlias);
        List<CgRequirementItemImportDto> tollOrderImportDtos = reader.read(0,1,20001, CgRequirementItemImportDto.class);
        for (CgRequirementItemImportDto dto : tollOrderImportDtos) {
            if (dto.getPurchaseCatalogName()==null || dto.getName()==null || dto.getNumber()==null || dto.getMarketPrice()==null ||dto.getQualityRequirements()==null ||dto.getNeedSample()==null){
                throw new RuntimeException("导入数据异常!");
            }
        }
        return tollOrderImportDtos;
    }




}
