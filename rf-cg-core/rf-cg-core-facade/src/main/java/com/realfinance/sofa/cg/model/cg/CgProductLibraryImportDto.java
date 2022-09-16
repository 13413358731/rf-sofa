package com.realfinance.sofa.cg.model.cg;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author hhq
 * @date 2021/7/20 - 15:23
 */
public class CgProductLibraryImportDto {

    private Integer id;

    private String productEncoded;

    private String productName;

    private Integer purchaseCatalog;

    private String productDescribe;

    private String model;

    private  String  calculateUnit;

    private String productCode;

    private String englishName;

    private Integer project;

    private Boolean isBid;

    private LocalDateTime enterRepositoryTime;

    private Integer supplier;

    private BigDecimal price;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductEncoded() {
        return productEncoded;
    }

    public void setProductEncoded(String productEncoded) {
        this.productEncoded = productEncoded;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPurchaseCatalog() {
        return purchaseCatalog;
    }

    public void setPurchaseCatalog(Integer purchaseCatalog) {
        this.purchaseCatalog = purchaseCatalog;
    }

    public String getProductDescribe() {
        return productDescribe;
    }

    public void setProductDescribe(String productDescribe) {
        this.productDescribe = productDescribe;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getCalculateUnit() {
        return calculateUnit;
    }

    public void setCalculateUnit(String calculateUnit) {
        this.calculateUnit = calculateUnit;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    public Boolean getBid() {
        return isBid;
    }

    public void setBid(Boolean bid) {
        isBid = bid;
    }

    public LocalDateTime getEnterRepositoryTime() {
        return enterRepositoryTime;
    }

    public void setEnterRepositoryTime(LocalDateTime enterRepositoryTime) {
        this.enterRepositoryTime = enterRepositoryTime;
    }

    public Integer getSupplier() {
        return supplier;
    }

    public void setSupplier(Integer supplier) {
        this.supplier = supplier;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
