package com.realfinance.sofa.cg.core.model;

import java.math.BigDecimal;

public class CgPurchaseCatalogDto extends BaseDto {

    private Integer id;

    private CgPurchaseCatalogSmallDto parent;
    private String code;
    private String name;
    private Integer leafCount;

    private BigDecimal centralizedPurchasingLimit;

    private Boolean enabled;

    private String projectCategory;

    public CgPurchaseCatalogDto(){

    }

    public CgPurchaseCatalogDto(Integer id){
        this.id=id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CgPurchaseCatalogSmallDto getParent() {
        return parent;
    }

    public void setParent(CgPurchaseCatalogSmallDto parent) {
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

    public Integer getLeafCount() {
        return leafCount;
    }

    public void setLeafCount(Integer leafCount) {
        this.leafCount = leafCount;
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
