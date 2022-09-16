package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.util.List;

/**
 * 供应商考核指标主表
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_ASSESSMENT", indexes = {
        @Index(columnList = "tenantId,assessmentNo", unique = true)
})
public class SupplierAssessment extends BaseEntity implements IEntity<Integer> {

    @Version
    private Long v;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 法人ID
     */
    @Column(nullable = false)
    private String tenantId;

    /**
     * 制单人部门
     */
    @Column(nullable = false)
    private Integer departmentId;

    /**
     *考核项编号
     */
    @Column(nullable = false)
    private String assessmentNo;

    /**
     *考核项名称
     */
    @Column(nullable = false)
    private String assessmentName;

    /**
     *备注
     */
    @Column(nullable = false)
    private String comment;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "supplierAssessment_id")
    private List<SupplierAssessmentIndicator> supplierAssessmentIndicators;

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getAssessmentNo() {
        return assessmentNo;
    }

    public void setAssessmentNo(String assessmentNo) {
        this.assessmentNo = assessmentNo;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<SupplierAssessmentIndicator> getSupplierAssessmentIndicators() {
        return supplierAssessmentIndicators;
    }

    public void setSupplierAssessmentIndicators(List<SupplierAssessmentIndicator> supplierAssessmentIndicators) {
        this.supplierAssessmentIndicators = supplierAssessmentIndicators;
    }
}

