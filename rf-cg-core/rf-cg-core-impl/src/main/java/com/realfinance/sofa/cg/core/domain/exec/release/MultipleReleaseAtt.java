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
@Table(name = "CG_CORE_MULTI_RELEASE_ATT")
public class MultipleReleaseAtt extends BasePurAtt {

    @ManyToOne
    @JoinColumn(name = "multi_release_id", updatable = false)
    private MultipleRelease multipleRelease;

    public MultipleRelease getMultipleRelease() {
        return multipleRelease;
    }

    public void setMultipleRelease(MultipleRelease multipleRelease) {
        this.multipleRelease = multipleRelease;
    }
}
