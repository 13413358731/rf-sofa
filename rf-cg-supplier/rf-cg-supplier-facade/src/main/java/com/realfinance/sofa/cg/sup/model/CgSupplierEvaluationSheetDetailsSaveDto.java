package com.realfinance.sofa.cg.sup.model;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

public class CgSupplierEvaluationSheetDetailsSaveDto extends BaseDto{
    public CgSupplierEvaluationSheetDetailsSaveDto(Integer id) {
        this.id = id;
    }

    protected Integer id;

    protected String tenantId;

    protected Integer departmentId;

    protected String evaluationSheetNo;

    protected String evaluationSheetName;

    protected String description;

    protected Boolean valid;
    /**
     * 考核指标
     */
    @NotEmpty
    private List<CgSupplierEvaluationSheetSubDto> supplierEvaluationSheetSubs;

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

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public List<CgSupplierEvaluationSheetSubDto> getSupplierEvaluationSheetSubs() {
        return supplierEvaluationSheetSubs;
    }

    public void setSupplierEvaluationSheetSubs(List<CgSupplierEvaluationSheetSubDto> supplierEvaluationSheetSubs) {
        this.supplierEvaluationSheetSubs = supplierEvaluationSheetSubs;
    }
}
