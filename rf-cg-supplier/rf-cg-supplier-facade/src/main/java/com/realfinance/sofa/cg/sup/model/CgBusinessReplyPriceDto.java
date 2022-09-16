package com.realfinance.sofa.cg.sup.model;

import java.math.BigDecimal;

public class CgBusinessReplyPriceDto {

    private Integer id;

    /**
     * 是否需要评标
     */
    private Boolean needBidEval;

    /**
     * 产品名
     */
    private String productName;

    /**
     * 型号
     */
    private String model;

    /**
     * 详细描述
     */
    private String description;

    /**
     * 数量
     */
    private BigDecimal number;

    /**
     * 单位
     */
    private String unit;

    /**
     * 单价
     */
    private BigDecimal unitPrice;

    /**
     * 总价
     */
    private BigDecimal totalPrice;

    /**
     * 备注
     */
    private String note;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getNeedBidEval() {
        return needBidEval;
    }

    public void setNeedBidEval(Boolean needBidEval) {
        this.needBidEval = needBidEval;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
