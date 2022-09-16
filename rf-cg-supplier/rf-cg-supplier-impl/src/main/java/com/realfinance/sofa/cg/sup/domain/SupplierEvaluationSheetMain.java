package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 供应商评估样表主表
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_EVALUATION_SHEET_MAIN",indexes = {
        @Index(columnList = "evaluationSheetNo",unique = true)
})
public class SupplierEvaluationSheetMain extends BaseEntity implements IEntity<Integer> {

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
     *评估表编号
     */
    @Column(nullable = false)
    private String evaluationSheetNo;

    /**
     *评估表名称
     */
    @Column(nullable = false)
    private String evaluationSheetName;

    /**
     *填表说明
     */
    @Column(nullable = false)
    private String description;

    /**
     * 审批流状态
     */
    @Enumerated
    @Column(nullable = false)
    private FlowStatus status;

    /**
     * 是否生效
     */
    @Column()
    private Boolean valid;

    /**
     * 审批通过时间
     */
    @Column
    private LocalDateTime passTime;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "supplierEvaluationSheetMain_id")
    private List<SupplierEvaluationSheetSub> supplierEvaluationSheetSubs;

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

    public String getEvaluationSheetNo() {
        return evaluationSheetNo;
    }

    public void setEvaluationSheetNo(String evaluationSheetNo) {
        this.evaluationSheetNo = evaluationSheetNo;
    }

    public String getEvaluationSheetName() {
        return evaluationSheetName;
    }

    public void setEvaluationSheetName(String evaluationSheetName) {
        this.evaluationSheetName = evaluationSheetName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public FlowStatus getStatus() {
        return status;
    }

    public void setStatus(FlowStatus status) {
        this.status = status;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public LocalDateTime getPassTime() {
        return passTime;
    }

    public void setPassTime(LocalDateTime passTime) {
        this.passTime = passTime;
    }

    public List<SupplierEvaluationSheetSub> getSupplierEvaluationSheetSubs() {
        return supplierEvaluationSheetSubs;
    }

    public void setSupplierEvaluationSheetSubs(List<SupplierEvaluationSheetSub> supplierEvaluationSheetSubs) {
        this.supplierEvaluationSheetSubs = supplierEvaluationSheetSubs;
    }
}

