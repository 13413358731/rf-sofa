package com.realfinance.sofa.cg.core.domain.proj;

import javax.persistence.*;

/**
 * 评分细则
 */
@Entity
@Table(name = "CG_CORE_HI_PROJ_EVAL_RULE")
public class HistoricProjectEvalRule extends BaseProjectEvalRule {

    @Column(nullable = false)
    private Integer srcId;

    @ManyToOne
    @JoinColumn(name = "hi_proj_eval_id", updatable = false)
    private HistoricProjectEval projectEval;

    public Integer getSrcId() {
        return srcId;
    }

    public void setSrcId(Integer srcId) {
        this.srcId = srcId;
    }

    public HistoricProjectEval getProjectEval() {
        return projectEval;
    }

    public void setProjectEval(HistoricProjectEval projectEval) {
        this.projectEval = projectEval;
    }
}
