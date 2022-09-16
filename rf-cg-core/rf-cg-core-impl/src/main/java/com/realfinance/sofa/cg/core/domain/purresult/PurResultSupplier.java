package com.realfinance.sofa.cg.core.domain.purresult;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "CG_CORE_PUR_RESULT_SUPPLIER")
public class PurResultSupplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 供应商Id
     */
    @Column()
    private Integer supplierId;

    /**
     * 供应商名称
     */
    @Column()
    private String name;

    /**
     * 是否推荐
     */
    @Column()
    private Boolean isRecommend;

    /**
     * 推荐理由
     */
    @Column()
    private String recommendReason;

    /**
     * 报价明细
     */
    @Column()
    private BigDecimal biddingPrice;

    /**
     * 供应商关联交易情况
     */
    protected String supplierRelatedStatus;

    /**
     * 供应商信用情况
     */
    protected String supplierCreditStatus;

    /**
     * 供应商信用查询时间
     */
    protected LocalDateTime supplierCreditTime;


    @ManyToOne
    @JoinColumn(name = "pur_result_id", updatable = false)
    private PurchaseResult purchaseResult;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public Boolean getRecommend() {
        return isRecommend;
    }

    public void setRecommend(Boolean recommend) {
        isRecommend = recommend;
    }

    public String getRecommendReason() {
        return recommendReason;
    }

    public void setRecommendReason(String recommendReason) {
        this.recommendReason = recommendReason;
    }

    public String getSupplierRelatedStatus() {
        return supplierRelatedStatus;
    }

    public void setSupplierRelatedStatus(String supplierRelatedStatus) {
        this.supplierRelatedStatus = supplierRelatedStatus;
    }

    public String getSupplierCreditStatus() {
        return supplierCreditStatus;
    }

    public void setSupplierCreditStatus(String supplierCreditStatus) {
        this.supplierCreditStatus = supplierCreditStatus;
    }

    public LocalDateTime getSupplierCreditTime() {
        return supplierCreditTime;
    }

    public void setSupplierCreditTime(LocalDateTime supplierCreditTime) {
        this.supplierCreditTime = supplierCreditTime;
    }

    public PurchaseResult getPurchaseResult() {
        return purchaseResult;
    }

    public void setPurchaseResult(PurchaseResult purchaseResult) {
        this.purchaseResult = purchaseResult;
    }

    public BigDecimal getBiddingPrice() {
        return biddingPrice;
    }

    public void setBiddingPrice(BigDecimal biddingPrice) {
        this.biddingPrice = biddingPrice;
    }
}
