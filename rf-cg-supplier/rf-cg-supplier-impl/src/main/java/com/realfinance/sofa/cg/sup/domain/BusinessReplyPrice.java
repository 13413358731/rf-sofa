package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * 供应商应答-价格信息
 */
@Entity
@Table(name = "CG_SUP_BIZ_REPLY_PRICE")
public class BusinessReplyPrice implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 是否需要评标
     */
    @Column()
    private Boolean needBidEval;

    /**
     * 产品名
     */
    @Column(nullable = false)
    private String productName;

    /**
     * 型号
     */
    //@Column(nullable = false)
    @Column
    private String model;

    /**
     * 详细描述
     */
    @Column
    private String description;

    /**
     * 数量
     */
    //@Column(nullable = false)
    @Column
    private BigDecimal number;

    /**
     * 单位
     */
    //@Column(nullable = false)
    @Column
    private String unit;

    /**
     * 单价
     */
    //@Column(nullable = false)
    @Column
    private BigDecimal unitPrice;

    /**
     * 总价
     */
    //@Column(nullable = false)
    @Column
    private BigDecimal totalPrice;

    /**
     * 备注
     */
    private String note;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
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
