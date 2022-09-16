package com.realfinance.sofa.cg.sup.model;

import java.util.List;

public class CgSupplierEvaluationMainDetailsDto extends CgSupplierEvaluationMainDto {
    protected List<CgSupplierEvaluationSubDto> supplierEvaluationSubs;

    public List<CgSupplierEvaluationSubDto> getSupplierEvaluationSubs() {
        return supplierEvaluationSubs;
    }

    public void setSupplierEvaluationSubs(List<CgSupplierEvaluationSubDto> supplierEvaluationSubs) {
        this.supplierEvaluationSubs = supplierEvaluationSubs;
    }
}
