package com.realfinance.sofa.cg.core.domain.commodity;

import com.realfinance.sofa.cg.core.domain.BaseEntity;
import com.realfinance.sofa.common.model.IEntity;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 采购商品
 */
@Entity
@Table(name = "CG_CORE_Commodity")
//@EntityListeners(AuditingEntityListener.class)
public class Commodity extends BaseEntity implements IEntity<Integer> {

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
     *项目名称
     */
    @Column(nullable = false)
    private String projectName;


    /**
     *购买部门
     */
    @Column(nullable = false)
    private String department;

    /**
     *购买金额
     */
    @Column(nullable = false)
    private BigDecimal totalAmount;

    /**
     *交付时间
     */
//    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime deliveryTime;



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

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }
}
