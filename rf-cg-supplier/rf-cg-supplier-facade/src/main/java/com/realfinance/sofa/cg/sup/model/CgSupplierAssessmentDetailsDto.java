package com.realfinance.sofa.cg.sup.model;

import java.util.List;

public class CgSupplierAssessmentDetailsDto extends CgSupplierAssessmentDto {
    protected List<CgSupplierAssessmentIndicatorDto> supplierAssessmentIndicators;

    public List<CgSupplierAssessmentIndicatorDto> getSupplierAssessmentIndicators() {
        return supplierAssessmentIndicators;
    }

    public void setSupplierAssessmentIndicators(List<CgSupplierAssessmentIndicatorDto> supplierAssessmentIndicators) {
        this.supplierAssessmentIndicators = supplierAssessmentIndicators;
    }
}
