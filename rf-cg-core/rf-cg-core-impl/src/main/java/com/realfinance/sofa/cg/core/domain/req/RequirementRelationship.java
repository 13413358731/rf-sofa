package com.realfinance.sofa.cg.core.domain.req;

import com.realfinance.sofa.cg.core.domain.BaseEntity;
import com.realfinance.sofa.cg.core.domain.BasePurRelationship;

import javax.persistence.*;
import java.util.List;

/**
 * 采购需求-企查查数据暂存表(供应商关联关系表)
 */
@Entity
@Table(name = "CG_CORE_REQ_RELATIONSHIP")
public class RequirementRelationship extends BasePurRelationship {

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
