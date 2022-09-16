package com.realfinance.sofa.cg.core.model;

import java.math.BigDecimal;

public class CgPurchaseCatalogSaveDto extends BaseDto {

    private Integer id;

    private Integer parent;
    private String code;
    private String name;

    private BigDecimal centralizedPurchasingLimit;

    private Boolean enabled;

    private String projectCategory;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getCentralizedPurchasingLimit() {
        return centralizedPurchasingLimit;
    }

    public void setCentralizedPurchasingLimit(BigDecimal centralizedPurchasingLimit) {
        this.centralizedPurchasingLimit = centralizedPurchasingLimit;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }
}
