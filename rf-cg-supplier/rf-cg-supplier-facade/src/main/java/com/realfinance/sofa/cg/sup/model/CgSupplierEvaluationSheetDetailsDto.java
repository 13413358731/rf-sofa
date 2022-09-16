package com.realfinance.sofa.cg.sup.model;

import java.util.List;

public class CgSupplierEvaluationSheetDetailsDto extends CgSupplierEvaluationSheetMainDto {
    protected List<CgSupplierEvaluationSheetSubDto> supplierEvaluationSheetSubs;

    public List<CgSupplierEvaluationSheetSubDto> getSupplierEvaluationSheetSubs() {
        return supplierEvaluationSheetSubs;
    }

    public void setSupplierEvaluationSheetSubs(List<CgSupplierEvaluationSheetSubDto> supplierEvaluationSheetSubs) {
        this.supplierEvaluationSheetSubs = supplierEvaluationSheetSubs;
    }
}
