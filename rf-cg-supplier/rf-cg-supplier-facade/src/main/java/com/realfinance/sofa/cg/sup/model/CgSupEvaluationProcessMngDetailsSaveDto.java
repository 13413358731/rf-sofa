package com.realfinance.sofa.cg.sup.model;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CgSupEvaluationProcessMngDetailsSaveDto {

    protected Integer id;

    protected String tenantId;

    protected Integer departmentId;

    /**
     *评估发起编号
     */
    protected String evaluationStartNo;

    /**
     *评估发起名称
     */
    protected String evaluationStartName;

    /**
     *评估表编号
     */
    protected Integer evaluationSheetNo;

    /**
     * 采购方案
     */
    private Integer projectId;

    /**
     * 供应商名称
     */
    private Integer supplierId;

    /**
     *评估发起日期
     */
    private LocalDate evaluationStartDate;

    /**
     *评估结束日期
     */
    private LocalDate evaluationEndDate;

    /**
     * 评估状态
     */
    private Integer evaluationStatus;

    /**
     * 评估分数
     */
    private float evaluationScore;

    /**
     * 评估等级
     */
    private Integer evaluationLevel;

    /**
     *统计时间
     */
    private LocalDateTime statisticalTime;

    /**
     *发布日期
     */
    private LocalDate releaseDate;

    /**
     * 发布状态
     */
    private Integer releaseStatus;

    /**
     * 考核指标
     */
    @NotEmpty
    protected List<CgSupEvaluationDepartmentDto> supplierEvaluationDepartments;

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

    public List<CgSupEvaluationDepartmentDto> getSupplierEvaluationDepartments() {
        return supplierEvaluationDepartments;
    }

    public void setSupplierEvaluationDepartments(List<CgSupEvaluationDepartmentDto> supplierEvaluationDepartments) {
        this.supplierEvaluationDepartments = supplierEvaluationDepartments;
    }
}
