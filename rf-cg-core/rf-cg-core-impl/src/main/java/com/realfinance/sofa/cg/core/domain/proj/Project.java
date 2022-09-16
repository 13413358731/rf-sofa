package com.realfinance.sofa.cg.core.domain.proj;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 采购方案
 */
@Entity
@Table(name = "CG_CORE_PROJ")
public class Project extends BaseProject {

    /**
     * 是否退回到申请
     */
    @Column
    private Boolean returnReq;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "proj_id")
    private List<ProjectItem> projectItems;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "proj_id")
    private List<ProjectSup> projectSups;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "proj_id")
    private List<ProjectAtt> projectAtts;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "proj_id")
    private List<ProjectOaDatum> projectOaData;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "proj_id")
    private List<ProjectEval> projectEvals;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "proj_id")
    private List<ProjectDrawExpertRule> projectDrawExpertRules;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "proj_id")
    private List<ProjectRelationship> relationships;
    public Project() {
        this.projectItems = new ArrayList<>();
        this.projectSups = new ArrayList<>();
        this.projectAtts = new ArrayList<>();
        this.projectOaData = new ArrayList<>();
        this.projectEvals = new ArrayList<>();
        this.projectDrawExpertRules = new ArrayList<>();
    }

    /**
     * 重新计算市场价
     */
    public void resetMarketPrice() {
        BigDecimal sum = getProjectItems().stream()
                .map(e -> e.getMarketPrice().multiply(BigDecimal.valueOf(e.getNumber())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        setMarketPrice(sum);
    }

    public Boolean getReturnReq() {
        return returnReq;
    }

    public void setReturnReq(Boolean returnReq) {
        this.returnReq = returnReq;
    }

    public List<ProjectItem> getProjectItems() {
        return projectItems;
    }

    public void setProjectItems(List<ProjectItem> projectItems) {
        this.projectItems = projectItems;
    }

    public List<ProjectSup> getProjectSups() {
        return projectSups;
    }

    public void setProjectSups(List<ProjectSup> projectSups) {
        this.projectSups = projectSups;
    }

    public List<ProjectAtt> getProjectAtts() {
        return projectAtts;
    }

    public void setProjectAtts(List<ProjectAtt> projectAtts) {
        this.projectAtts = projectAtts;
    }

    public List<ProjectOaDatum> getProjectOaData() {
        return projectOaData;
    }

    public void setProjectOaData(List<ProjectOaDatum> projectOaData) {
        this.projectOaData = projectOaData;
    }

    public List<ProjectEval> getProjectEvals() {
        return projectEvals;
    }

    public void setProjectEvals(List<ProjectEval> projectEvals) {
        this.projectEvals = projectEvals;
    }

    public List<ProjectDrawExpertRule> getProjectDrawExpertRules() {
        return projectDrawExpertRules;
    }

    public void setProjectDrawExpertRules(List<ProjectDrawExpertRule> projectDrawExpertRules) {
        this.projectDrawExpertRules = projectDrawExpertRules;
    }

    public List<ProjectRelationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<ProjectRelationship> relationships) {
        this.relationships = relationships;
    }
}