package com.realfinance.sofa.cg.core.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@MappedSuperclass
public abstract class BasePurItem implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    /**
     * 采购目录
     */
    @ManyToOne
    @JoinColumn(name = "purchase_catalog_id")
    protected PurchaseCatalog purchaseCatalog;

    /**
     * 名称
     */
    @Column(nullable = false)
    protected String name;

    /**
     * 数量
     */
    @Column(nullable = false)
    protected Integer number;

    /**
     * 市场参考价/市场控制总价
     */
    @Column(nullable = false)
    protected BigDecimal marketPrice;

    /**
     * 质量和技术要求
     */
    @Column(nullable = false, length = 1000)
    protected String qualityRequirements;

    /**
     * 备注
     */
    @Column
    protected String note;

    /**
     * 来源
     */
    @Column
    protected String source;

    /**
     * 是否需提供样板
     */
    @Column(nullable = false)
    protected Boolean needSample;

    /**
     * 型号
     */
    @Column
    protected String model;

    /**
     * 单位
     */
    @Column
    protected String unit;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public PurchaseCatalog getPurchaseCatalog() {
        return purchaseCatalog;
    }

    public void setPurchaseCatalog(PurchaseCatalog purchaseCatalog) {
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
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
