package com.realfinance.sofa.cg.core.domain.proj;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CG_CORE_HI_PROJ")
public class HistoricProject extends BaseProject {

    @Column(nullable = false)
    private Integer srcId;

    @ManyToOne
    @JoinColumn(name = "proj_id", updatable = false)
    private Project project;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "hi_proj_id")
    private List<HistoricProjectItem> projectItems;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "hi_proj_id")
    private List<HistoricProjectSup> projectSups;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "hi_proj_id")
    private List<HistoricProjectAtt> projectAtts;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "hi_proj_id")
    private List<HistoricProjectOaDatum> projectOaData;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "hi_proj_id")
    private List<HistoricProjectEval> projectEvals;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "hi_proj_id")
    private List<HistoricProjectDrawExpertRule> projectDrawExpertRules;

    public HistoricProject() {
        this.projectItems = new ArrayList<>();
        this.projectSups = new ArrayList<>();
        this.projectAtts = new ArrayList<>();
        this.projectOaData = new ArrayList<>();
        this.projectEvals = new ArrayList<>();
    }

    public Integer getSrcId() {
        return srcId;
    }

    public void setSrcId(Integer srcId) {
        this.srcId = srcId;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<HistoricProjectItem> getProjectItems() {
        return projectItems;
    }

    public void setProjectItems(List<HistoricProjectItem> projectItems) {
        this.projectItems = projectItems;
    }

    public List<HistoricProjectSup> getProjectSups() {
        return projectSups;
    }

    public void setProjectSups(List<HistoricProjectSup> projectSups) {
        this.projectSups = projectSups;
    }

    public List<HistoricProjectAtt> getProjectAtts() {
        return projectAtts;
    }

    public void setProjectAtts(List<HistoricProjectAtt> projectAtts) {
        this.projectAtts = projectAtts;
    }

    public List<HistoricProjectOaDatum> getProjectOaData() {
        return projectOaData;
    }

    public void setProjectOaData(List<HistoricProjectOaDatum> projectOaData) {
        this.projectOaData = projectOaData;
    }

    public List<HistoricProjectEval> getProjectEvals() {
        return projectEvals;
    }

    public void setProjectEvals(List<HistoricProjectEval> projectEvals) {
        this.projectEvals = projectEvals;
    }

    public List<HistoricProjectDrawExpertRule> getProjectDrawExpertRules() {
        return projectDrawExpertRules;
    }

    public void setProjectDrawExpertRules(List<HistoricProjectDrawExpertRule> projectDrawExpertRules) {
        this.projectDrawExpertRules = projectDrawExpertRules;
    }
}
