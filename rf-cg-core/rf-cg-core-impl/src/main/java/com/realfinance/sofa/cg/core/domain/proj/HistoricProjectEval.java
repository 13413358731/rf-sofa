package com.realfinance.sofa.cg.core.domain.proj;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 评分大项
 */
@Entity
@Table(name = "CG_CORE_HI_PROJ_EVAL")
public class HistoricProjectEval extends BaseProjectEval {

    @Column(nullable = false)
    private Integer srcId;

    @ManyToOne
    @JoinColumn(name = "hi_proj_id", updatable = false)
    private HistoricProject project;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "hi_proj_eval_id")
    private List<HistoricProjectEvalRule> projectEvalRules;

    public HistoricProjectEval() {
        this.projectEvalRules = new ArrayList<>();
    }

    public Integer getSrcId() {
        return srcId;
    }

    public void setSrcId(Integer srcId) {
        this.srcId = srcId;
    }

    public HistoricProject getProject() {
        return project;
    }

    public void setProject(HistoricProject project) {
        this.project = project;
    }

    public List<HistoricProjectEvalRule> getProjectEvalRules() {
        return projectEvalRules;
    }

    public void setProjectEvalRules(List<HistoricProjectEvalRule> projectEvalRules) {
        this.projectEvalRules = projectEvalRules;
    }
}
