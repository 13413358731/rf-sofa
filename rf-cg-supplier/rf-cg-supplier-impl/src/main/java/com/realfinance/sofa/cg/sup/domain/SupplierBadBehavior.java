package com.realfinance.sofa.cg.sup.domain;


import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 供应商不良行为录入
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_BAD_BEHAVIOR")
public class SupplierBadBehavior extends BaseEntity implements IEntity<Integer> {

    /**
     * 法人（租户）
     */
    @Column(nullable = false, updatable = false)
    private String tenantId;



    /**
     * 部门
     */
    @Column(nullable = false, updatable = false)
    private Integer departmentId;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    /**
     * 供应商id
     */
    @Column(nullable = false)
    private Integer supplierId;

    /**
     * 发生时期
     */
    @Column(nullable = false)
    private LocalDateTime happenTime;

    /**
     * 不良行为具体描述
     */
    @Column(length = 1000,nullable = false)
    private String behaviorDescription;

    /**
     * 供应商名称
     */
    private  String supplierName;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public LocalDateTime getHappenTime() {
        return happenTime;
    }

    public void setHappenTime(LocalDateTime happenTime) {
        this.happenTime = happenTime;
    }

    public String getBehaviorDescription() {
        return behaviorDescription;
    }

    public void setBehaviorDescription(String behaviorDescription) {
        this.behaviorDescription = behaviorDescription;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
