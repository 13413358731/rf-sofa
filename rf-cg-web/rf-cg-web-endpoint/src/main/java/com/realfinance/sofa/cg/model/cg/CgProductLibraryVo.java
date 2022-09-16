package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CgProductLibraryVo extends BaseVo implements IdentityObject<Integer> {

    private Integer id;

    /**
     * 产品编码
     */
    private String productEncoded;


    /**
     * 计量单位
     */
    private  String  calculateUnit;

    private CgPurchaseCatalogVo purchaseCatalog;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品描述
     */
    private  String productDescribe;


    /**
     * 规格型号
     */
    private  String model;


    /**
     * 产品代码
     */
    private  String productCode;

    /**
     * 英文名
     */
    private  String englishName;


    /**
     * 采购方案ID
     */
    private  CgProjectVo project;



    /**
     * 是否中标
     */
    private  Boolean  isBid;


    /**
     * 入库时间
     */
    private LocalDateTime enterRepositoryTime;


    /**
     * 供应商ID
     */
    private  CgSupplierVo supplier;


    /**
     * 价格
     */
    private BigDecimal price;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
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

    public String getCalculateUnit() {
        return calculateUnit;
    }

    public void setCalculateUnit(String calculateUnit) {
        this.calculateUnit = calculateUnit;
    }

    public CgPurchaseCatalogVo getPurchaseCatalog() {
        return purchaseCatalog;
    }

    public void setPurchaseCatalog(CgPurchaseCatalogVo purchaseCatalog) {
        this.purchaseCatalog = purchaseCatalog;
    }

    public CgProjectVo getProject() {
        return project;
    }

    public void setProject(CgProjectVo project) {
        this.project = project;
    }

    public CgSupplierVo getSupplier() {
        return supplier;
    }

    public void setSupplier(CgSupplierVo supplier) {
        this.supplier = supplier;
    }
}
