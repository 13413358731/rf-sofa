package com.realfinance.sofa.cg.core.domain.exec.release;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

/**
 * 发布、多轮澄清
 */
@Entity
@Table(name = "CG_CORE_HI_MULTI_RELEASE")
public class HistoricMultipleRelease extends BaseMultipleRelease {

    @Column(nullable = false)
    private Integer srcId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "hi_multi_release_id")
    private List<HistoricMultipleReleaseAtt> multipleReleaseAtts;

    /**
     * 发标的供应商ID
     */
    @Column
    @ElementCollection
    @CollectionTable(name="CG_CORE_HI_MULTI_RELEASE_SUP")
    private Set<Integer> supplierIds;

    public Integer getSrcId() {
        return srcId;
    }

    public void setSrcId(Integer srcId) {
        this.srcId = srcId;
    }

    public List<HistoricMultipleReleaseAtt> getMultipleReleaseAtts() {
        return multipleReleaseAtts;
    }

    public void setMultipleReleaseAtts(List<HistoricMultipleReleaseAtt> multipleReleaseAtts) {
        this.multipleReleaseAtts = multipleReleaseAtts;
    }

    public Set<Integer> getSupplierIds() {
        return supplierIds;
    }

    public void setSupplierIds(Set<Integer> supplierIds) {
        this.supplierIds = supplierIds;
    }
}
