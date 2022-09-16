package com.realfinance.sofa.cg.core.domain.proj;

import com.realfinance.sofa.cg.core.domain.BasePurRelationship;
import com.realfinance.sofa.cg.core.domain.req.Requirement;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 采购方案-企查查数据暂存表(供应商关联关系表)
 */
@Entity
@Table(name = "CG_CORE_PROJ_RELATIONSHIP")
public class ProjectRelationship extends BasePurRelationship {

    @ManyToOne
    @JoinColumn(name = "proj_id",updatable = false)
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
