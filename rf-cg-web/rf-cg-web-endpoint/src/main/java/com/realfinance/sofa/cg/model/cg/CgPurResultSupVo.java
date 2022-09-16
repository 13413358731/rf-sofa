package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "采购结果-供应商对象")
public class CgPurResultSupVo {
    protected Integer id;

    @Schema(description = "供应商Id")
    protected CgSupplierVo supplierId;

    @Schema(description = "供应商名称")
    protected String name;

    @Schema(description = "是否推荐")
    protected Boolean isRecommend;

    @Schema(description = "推荐理由")
    protected String recommendReason;

    @Schema(description = "关联交易情况")
    protected String supplierRelatedStatus;

    @Schema(description = "信用状态")
    protected String supplierCreditStatus;

    @Schema(description = "信用状态查询时间")
    protected LocalDateTime supplierCreditTime;

    @Schema(description = "报价明细")
    private BigDecimal biddingPrice;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CgSupplierVo getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(CgSupplierVo supplierId) {
        this.supplierId = supplierId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public BigDecimal getBiddingPrice() {
        return biddingPrice;
    }

    public void setBiddingPrice(BigDecimal biddingPrice) {
        this.biddingPrice = biddingPrice;
    }
}
