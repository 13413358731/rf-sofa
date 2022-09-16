package com.realfinance.sofa.cg.sup.model;

import javax.validation.constraints.NotEmpty;
import java.util.List;

public class CgSupplierAssessmentDetailsSaveDto {

    protected Integer id;

    protected String tenantId;

    protected Integer departmentId;

    protected String assessmentNo;

    protected String assessmentName;

    protected String comment;

    /**
     * 考核指标
     */
    @NotEmpty
    private List<CgSupplierAssessmentIndicatorDto> supplierAssessmentIndicators;

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

    public List<CgSupplierAssessmentIndicatorDto> getSupplierAssessmentIndicators() {
        return supplierAssessmentIndicators;
    }

    public void setSupplierAssessmentIndicators(List<CgSupplierAssessmentIndicatorDto> supplierAssessmentIndicators) {
        this.supplierAssessmentIndicators = supplierAssessmentIndicators;
    }
}
