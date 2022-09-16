package com.realfinance.sofa.cg.core.domain.req;

import com.realfinance.sofa.cg.core.domain.BasePurSup;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 推荐供应商
 */
@Entity
@Table(name = "CG_CORE_REQ_SUP")
public class RequirementSup extends BasePurSup {

    @ManyToOne
    @JoinColumn(name = "req_id", updatable = false)
    private Requirement requirement;

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }
}
