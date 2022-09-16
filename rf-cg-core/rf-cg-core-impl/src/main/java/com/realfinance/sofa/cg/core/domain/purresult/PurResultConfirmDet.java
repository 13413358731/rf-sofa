package com.realfinance.sofa.cg.core.domain.purresult;

import com.realfinance.sofa.cg.core.domain.PurchaseCatalog;
import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CG_CORE_PUR_RESULT_CON_DET")
public class PurResultConfirmDet implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    /**
     * 采购目录
     */
    protected Integer purchaseCatalog;

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

    @ManyToOne
    @JoinColumn(name = "pur_result_id", updatable = false)
    private PurchaseResult purchaseResult;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
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

    public PurchaseResult getPurchaseResult() {
        return purchaseResult;
    }

    public void setPurchaseResult(PurchaseResult purchaseResult) {
        this.purchaseResult = purchaseResult;
    }

//    public String getSource() {
//        return source;
//    }
//
//    public void setSource(String source) {
//        this.source = source;
//    }
//
//    public Boolean getNeedSample() {
//        return needSample;
//    }
//
//    public void setNeedSample(Boolean needSample) {
//        this.needSample = needSample;
//    }

}
