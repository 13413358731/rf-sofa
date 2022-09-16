package com.realfinance.sofa.cg.core.domain.proj;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 评分大项
 */
@Entity
@Table(name = "CG_CORE_PROJ_EVAL")
public class ProjectEval extends BaseProjectEval {

    @ManyToOne
    @JoinColumn(name = "proj_id", updatable = false)
    private Project project;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "proj_eval_id")
    private List<ProjectEvalRule> projectEvalRules;

    public ProjectEval() {
        this.projectEvalRules = new ArrayList<>();
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<ProjectEvalRule> getProjectEvalRules() {
        return projectEvalRules;
    }

    public void setProjectEvalRules(List<ProjectEvalRule> projectEvalRules) {
        this.projectEvalRules = projectEvalRules;
    }
}
