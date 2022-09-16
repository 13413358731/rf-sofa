package com.realfinance.sofa.cg.core.domain.proj;

import com.realfinance.sofa.cg.core.domain.BaseDrawExpertRule;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CG_CORE_PROJ_DRAW_EXPERT_RULE")
public class ProjectDrawExpertRule extends BaseDrawExpertRule {

    @ManyToOne
    @JoinColumn(name = "proj_id", updatable = false)
    private Project project;

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
