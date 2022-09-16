package com.realfinance.sofa.cg.core.domain.exec.release;

import com.realfinance.sofa.cg.core.domain.BasePurAtt;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 发布附件
 */
@Entity
@Table(name = "CG_CORE_HI_MULTI_RELEASE_ATT")
public class HistoricMultipleReleaseAtt extends BasePurAtt {

    @ManyToOne
    @JoinColumn(name = "hi_multi_release_id", updatable = false)
    private HistoricMultipleRelease multipleRelease;

    public HistoricMultipleRelease getMultipleRelease() {
        return multipleRelease;
    }

    public void setMultipleRelease(HistoricMultipleRelease multipleRelease) {
        this.multipleRelease = multipleRelease;
    }
}
