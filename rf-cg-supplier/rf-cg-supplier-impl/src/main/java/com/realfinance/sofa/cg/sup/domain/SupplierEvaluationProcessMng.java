package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 供应商评估流程管理
 */
@Entity
@Table(name = "CG_SUP_SUPPLIER_EVALUATION_PROCESS_MNG",indexes = {
        @Index(columnList = "evaluationStartNo",unique = true)
})
public class SupplierEvaluationProcessMng extends BaseEntity implements IEntity<Integer> {

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
     *评估发起编号
     */
    @Column(nullable = false)
    private String evaluationStartNo;

    /**
     *评估发起名称
     */
    @Column(nullable = false)
    private String evaluationStartName;

    /**
     *评估表编号
     */
    @Column(nullable = false)
    private Integer evaluationSheetNo;

    /**
     * 采购方案
     */
    @Column(nullable = false)
    private Integer projectId;

    /**
     * 供应商名称
     */
    @Column(nullable = false)
    private Integer supplierId;

    /**
     *评估发起日期
     */
    @Column()
    private LocalDate evaluationStartDate;

    /**
     *评估结束日期
     */
    @Column()
    private LocalDate evaluationEndDate;

    /**
     * 评估状态
     */
    @Column(nullable = false)
    private Integer evaluationStatus;

    /**
     * 评估分数
     */
    @Column()
    private float evaluationScore;

    /**
     * 评估等级
     */
    @Column()
    private Integer evaluationLevel;

    /**
     *统计时间
     */
    @Column()
    private LocalDateTime statisticalTime;

    /**
     *发布日期
     */
    @Column()
    private LocalDate releaseDate;

    /**
     * 发布状态
     */
    @Column(nullable = false)
    private Integer releaseStatus;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "supplierEvaluationProcessMng_id")
    private List<SupplierEvaluationDepartment> supplierEvaluationDepartments;

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
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

    public String getEvaluationStartNo() {
        return evaluationStartNo;
    }

    public void setEvaluationStartNo(String evaluationStartNo) {
        this.evaluationStartNo = evaluationStartNo;
    }

    public String getEvaluationStartName() {
        return evaluationStartName;
    }

    public void setEvaluationStartName(String evaluationStartName) {
        this.evaluationStartName = evaluationStartName;
    }

    public Integer getEvaluationSheetNo() {
        return evaluationSheetNo;
    }

    public void setEvaluationSheetNo(Integer evaluationSheetNo) {
        this.evaluationSheetNo = evaluationSheetNo;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public LocalDate getEvaluationStartDate() {
        return evaluationStartDate;
    }

    public void setEvaluationStartDate(LocalDate evaluationStartDate) {
        this.evaluationStartDate = evaluationStartDate;
    }

    public LocalDate getEvaluationEndDate() {
        return evaluationEndDate;
    }

    public void setEvaluationEndDate(LocalDate evaluationEndDate) {
        this.evaluationEndDate = evaluationEndDate;
    }

    public Integer getEvaluationStatus() {
        return evaluationStatus;
    }

    public void setEvaluationStatus(Integer evaluationStatus) {
        this.evaluationStatus = evaluationStatus;
    }

    public float getEvaluationScore() {
        return evaluationScore;
    }

    public void setEvaluationScore(float evaluationScore) {
        this.evaluationScore = evaluationScore;
    }

    public Integer getEvaluationLevel() {
        return evaluationLevel;
    }

    public void setEvaluationLevel(Integer evaluationLevel) {
        this.evaluationLevel = evaluationLevel;
    }

    public LocalDateTime getStatisticalTime() {
        return statisticalTime;
    }

    public void setStatisticalTime(LocalDateTime statisticalTime) {
        this.statisticalTime = statisticalTime;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(Integer releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    public List<SupplierEvaluationDepartment> getSupplierEvaluationDepartments() {
        return supplierEvaluationDepartments;
    }

    public void setSupplierEvaluationDepartments(List<SupplierEvaluationDepartment> supplierEvaluationDepartments) {
        this.supplierEvaluationDepartments = supplierEvaluationDepartments;
    }
}

