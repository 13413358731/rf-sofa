package com.realfinance.sofa.cg.core.domain.AfterSales;

import com.realfinance.sofa.cg.core.domain.BaseEntity;
import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 采购商品
 */
@Entity
@Table(name = "CG_CORE_AfterSales")
public class AfterSales extends BaseEntity implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 法人ID
     */
    @Column(nullable = false)
    private String tenantId;

    /**
     *商品编号
     */
    @Column(nullable = false)
    private Integer commodityId;

    /**
     *商品名称
     */
    @Column(nullable = false)
    private String commodityName;

    /**
     *供应商名称
     */
    @Column(nullable = false)
    private String vendorName;

    /**
     *订单编号
     */
    @Column(nullable = false)
    private Integer ordersId;

    /**
     *商品问题
     */
    @Column(nullable = false)
    private String commodityIssues;

    /**
     *商品金额
     */
    @Column(nullable = false)
    private BigDecimal totalAmount;

    /**
     *联系信息
     */
    @Column(nullable = false)
    private String contactInformation;

    /**
     *是否受理
     */
    @Column(nullable = false)
    private Boolean acceptance;

    /**
     *申请时间
     */
    @Column(nullable = false)
    private LocalDateTime applicationTime;

    /**
     *处理时间
     */
    @Column(nullable = false)
    private LocalDateTime processingTime;

    public Integer getId() {
       return id;
   }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public void setCommodityName(String commodityName) {
        this.commodityName = commodityName;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(Integer ordersId) {
        this.ordersId = ordersId;
    }

    public String getCommodityIssues() {
        return commodityIssues;
    }

    public void setCommodityIssues(String commodityIssues) {
        this.commodityIssues = commodityIssues;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public Boolean getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(Boolean acceptance) {
        this.acceptance = acceptance;
    }

    public LocalDateTime getApplicationTime() {
        return applicationTime;
    }

    public void setApplicationTime(LocalDateTime applicationTime) {
        this.applicationTime = applicationTime;
    }

    public LocalDateTime getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(LocalDateTime processingTime) {
        this.processingTime = processingTime;
    }
}
