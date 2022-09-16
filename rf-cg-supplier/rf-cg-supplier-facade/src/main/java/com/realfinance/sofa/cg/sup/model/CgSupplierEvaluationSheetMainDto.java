package com.realfinance.sofa.cg.sup.model;

import java.time.LocalDate;

public class CgSupplierEvaluationSheetMainDto extends BaseDto {
    public CgSupplierEvaluationSheetMainDto() {
    }

    public CgSupplierEvaluationSheetMainDto(Integer id) {
        this.id = id;
    }

    private Integer id;

    /**
     * 法人ID
     */
    private String tenantId;

    /**
     * 制单人部门
     */
    private Integer departmentId;

    /**
     *评估表编号
     */
    private String evaluationSheetNo;

    /**
     *评估表名称
     */
    private String evaluationSheetName;

    /**
     *填表说明
     */
    private String description;

    /**
     * 审批状态
     */
    private String status;

    /**
     * 是否生效
     */
    private Boolean valid;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
