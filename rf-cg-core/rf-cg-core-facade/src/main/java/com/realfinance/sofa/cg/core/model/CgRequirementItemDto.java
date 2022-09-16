package com.realfinance.sofa.cg.core.model;

import java.math.BigDecimal;
import java.util.List;

public class CgRequirementItemDto {

    private Integer id;

    private CgPurchaseCatalogDto purchaseCatalog;

    private String name;

    private Integer number;

    private BigDecimal marketPrice;

    private String qualityRequirements;

    private String note;

    private List<CgRequirementSupDto> requirementItemSups;

    private Boolean needSample;

    protected String model;

    protected String unit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CgPurchaseCatalogDto getPurchaseCatalog() {
        return purchaseCatalog;
    }

    public void setPurchaseCatalog(CgPurchaseCatalogDto purchaseCatalog) {
        this.purchaseCatalog = purchaseCatalog;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getQualityRequirements() {
        return qualityRequirements;
    }

    public void setQualityRequirements(String qualityRequirements) {
        this.qualityRequirements = qualityRequirements;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<CgRequirementSupDto> getRequirementItemSups() {
        return requirementItemSups;
    }

    public void setRequirementItemSups(List<CgRequirementSupDto> requirementItemSups) {
        this.requirementItemSups = requirementItemSups;
    }

    public Boolean getNeedSample() {
        return needSample;
    }

    public void setNeedSample(Boolean needSample) {
        this.needSample = needSample;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
