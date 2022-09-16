package com.realfinance.sofa.cg.sup.model;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

public class CgSupplierEvaluationMainDetailsSaveDto {

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
    protected String evaluationSheetNo;

    /**
     *评估表名称
     */
    protected String evaluationSheetName;

    /**
     *评估开始日期
     */
    protected LocalDate evaluationStartDate;

    /**
     *评估结束日期
     */
    protected LocalDate evaluationEndDate;

    /**
     *评估人
     */
    protected Integer evaluator;

    /**
     *发布日期
     */
    protected LocalDate releaseDate;

    /**
     *完成状态
     */
    protected Integer status;

    /**
     *完成日期
     */
    protected LocalDate finishDate;

    /**
     * 考核指标
     */
    @NotEmpty
    protected List<CgSupplierEvaluationSubDto> supplierEvaluationSubs;

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

    public Integer getEvaluator() {
        return evaluator;
    }

    public void setEvaluator(Integer evaluator) {
        this.evaluator = evaluator;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public List<CgSupplierEvaluationSubDto> getSupplierEvaluationSubs() {
        return supplierEvaluationSubs;
    }

    public void setSupplierEvaluationSubs(List<CgSupplierEvaluationSubDto> supplierEvaluationSubs) {
        this.supplierEvaluationSubs = supplierEvaluationSubs;
    }
}
