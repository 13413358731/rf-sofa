package com.realfinance.sofa.cg.core.model;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CgProjectItemSaveDto {
    private Integer id;

    @NotNull
    private Integer purchaseCatalog;

    private String name;

    private Integer number;

    private BigDecimal marketPrice;

    private String qualityRequirements;

    private String note;

    private Boolean needSample;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPurchaseCatalog() {
        return purchaseCatalog;
    }

    public void setPurchaseCatalog(Integer purchaseCatalog) {
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

    public Boolean getNeedSample() {
        return needSample;
    }

    public void setNeedSample(Boolean needSample) {
        this.needSample = needSample;
    }
}
