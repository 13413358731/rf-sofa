package com.realfinance.sofa.cg.core.domain.proj;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 评分细则
 */
@Entity
@Table(name = "CG_CORE_PROJ_EVAL_RULE")
public class ProjectEvalRule extends BaseProjectEvalRule {

    @ManyToOne
    @JoinColumn(name = "proj_eval_id", updatable = false)
    private ProjectEval projectEval;

    public ProjectEval getProjectEval() {
        return projectEval;
    }

    public void setProjectEval(ProjectEval projectEval) {
        this.projectEval = projectEval;
    }
}
