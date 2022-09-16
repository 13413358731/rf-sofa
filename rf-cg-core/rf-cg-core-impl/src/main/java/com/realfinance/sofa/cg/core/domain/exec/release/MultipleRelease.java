package com.realfinance.sofa.cg.core.domain.exec.release;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * 发布、多轮澄清
 */
@Entity
@Table(name = "CG_CORE_MULTI_RELEASE")
public class MultipleRelease extends BaseMultipleRelease {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "multi_release_id")
    private List<MultipleReleaseAtt> multipleReleaseAtts;

    /**
     * 发标的供应商ID
     */
    @Column
    @ElementCollection
    @CollectionTable(name="CG_CORE_MULTI_RELEASE_SUP")
    private Set<Integer> supplierIds;

    public List<MultipleReleaseAtt> getMultipleReleaseAtts() {
        return multipleReleaseAtts;
    }

    public void setMultipleReleaseAtts(List<MultipleReleaseAtt> multipleReleaseAtts) {
        this.multipleReleaseAtts = multipleReleaseAtts;
    }

    public Set<Integer> getSupplierIds() {
        return supplierIds;
    }

    public void setSupplierIds(Set<Integer> supplierIds) {
        this.supplierIds = supplierIds;
    }
}
