package com.realfinance.sofa.cg.core.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CgCommodityDto extends BaseDto {

    private Integer id;

    /**
     * 法人ID
     */
    private String tenantId;

    /**
     *商品编号
     */
    private Integer commodityId;

    /**
     *商品名称
     */
    private String commodityName;

    /**
     *供应商名称
     */
    private String vendorName;

    /**
     *项目名称
     */
    private String projectName;

    /**
     *购买部门
     */
    private String department;

    /**
     *购买金额
     */
    private BigDecimal totalAmount;

    /**
     *交付时间
     */
    private LocalDate deliveryTime;

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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDate deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
