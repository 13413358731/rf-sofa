package com.realfinance.sofa.cg.sup.model;

import java.util.List;

public class CgSupEvaluationProcessMngDetailsDto extends CgSupEvaluationProcessMngDto {
    protected List<CgSupEvaluationDepartmentDto> supplierEvaluationDepartments;

    public List<CgSupEvaluationDepartmentDto> getSupplierEvaluationDepartments() {
        return supplierEvaluationDepartments;
    }

    public void setSupplierEvaluationDepartments(List<CgSupEvaluationDepartmentDto> supplierEvaluationDepartments) {
        this.supplierEvaluationDepartments = supplierEvaluationDepartments;
    }
}
