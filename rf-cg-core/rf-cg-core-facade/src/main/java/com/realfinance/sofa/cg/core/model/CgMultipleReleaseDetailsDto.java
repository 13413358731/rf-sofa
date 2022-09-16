package com.realfinance.sofa.cg.core.model;

import java.util.List;
import java.util.Set;

public class CgMultipleReleaseDetailsDto extends CgMultipleReleaseDto {

    private List<CgProjectExecutionAttDto> multipleReleaseAtts;

    private Set<Integer> supplierIds;

    public List<CgProjectExecutionAttDto> getMultipleReleaseAtts() {
        return multipleReleaseAtts;
    }

    public void setMultipleReleaseAtts(List<CgProjectExecutionAttDto> multipleReleaseAtts) {
        this.multipleReleaseAtts = multipleReleaseAtts;
    }

    public Set<Integer> getSupplierIds() {
        return supplierIds;
    }

    public void setSupplierIds(Set<Integer> supplierIds) {
        this.supplierIds = supplierIds;
    }
}
