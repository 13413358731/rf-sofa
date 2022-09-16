package com.realfinance.sofa.cg.core.domain.req;

import com.realfinance.sofa.cg.core.domain.BasePurAtt;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CG_CORE_REQ_ATT")
public class RequirementAtt extends BasePurAtt {

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
