package com.realfinance.sofa.cg.core.model;


import java.math.BigDecimal;

/**
 * 采购申请货物类采购清单-模板
 */
public class CgRequirementItemImportDto {

    private Integer id;

    // 项目分类名称
    private String purchaseCatalogName;

    // 货物名称
    private String name;

    // 型号/规格
    private String model;

    //数量
    private Integer number;

    //单位
    private String unit;

    //详细说明
    private String qualityRequirements;

    //是否提供样板
    private String needSample;

    //市场参考价(元)
    private BigDecimal marketPrice;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPurchaseCatalogName() {
        return purchaseCatalogName;
    }

    public void setPurchaseCatalogName(String purchaseCatalogName) {
        this.purchaseCatalogName = purchaseCatalogName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getQualityRequirements() {
        return qualityRequirements;
    }

    public void setQualityRequirements(String qualityRequirements) {
        this.qualityRequirements = qualityRequirements;
    }

    public String getNeedSample() {
        return needSample;
    }

    public void setNeedSample(String needSample) {
        this.needSample = needSample;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }
}