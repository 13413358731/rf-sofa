package com.realfinance.sofa.cg.model.cg;


import com.realfinance.sofa.common.model.ReferenceObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "保存产品对象")
public class CgProductLibrarySaveRepuest {


    private Integer id;

    @Schema(description = "产品编码")
    private String productEncoded;

    @Schema(description = "产品名称")
    private String productName;

    @Schema(description = "产品描述")
    private  String productDescribe;

    @Schema(description = "采购目录")
    private ReferenceObject<Integer>  purchaseCatalog;

    @Schema(description = "规格型号")
    private  String model;

    @Schema(description = "计量单位")
    private  String  calculateUnit;

    @Schema(description = "产品代码")
    private  String productCode;

    @Schema(description = "英文名")
    private  String englishName;

    @Schema(description = "采购方案ID")
    private  ReferenceObject<Integer> project;



    @Schema(description = "中标")
    private  Boolean  isBid;


    @Schema(description = "入库时间")
    private LocalDateTime enterRepositoryTime;


    @Schema(description = "供应商ID")
    private  ReferenceObject<Integer> supplier;


    @Schema(description = "价格")
    private BigDecimal price;


    public String getCalculateUnit() {
        return calculateUnit;
    }

    public void setCalculateUnit(String calculateUnit) {
        this.calculateUnit = calculateUnit;
    }

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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ReferenceObject<Integer> getPurchaseCatalog() {
        return purchaseCatalog;
    }

    public void setPurchaseCatalog(ReferenceObject<Integer> purchaseCatalog) {
        this.purchaseCatalog = purchaseCatalog;
    }

    public ReferenceObject<Integer> getProject() {
        return project;
    }

    public void setProject(ReferenceObject<Integer> project) {
        this.project = project;
    }

    public ReferenceObject<Integer> getSupplier() {
        return supplier;
    }

    public void setSupplier(ReferenceObject<Integer> supplier) {
        this.supplier = supplier;
    }
}
