package com.realfinance.sofa.cg.core.model;

import java.util.List;

public class CgProjectDetailsDto extends CgProjectDto {

    private List<CgProjectItemDto> projectItems;

    private List<CgProjectSupDto> projectSups;

    private List<CgProjectAttDto> projectAtts;

    private List<CgProjectOaDatumDto> projectOaData;

    private List<CgProjectEvalDto> projectEvals;

    private List<CgProjectRelationshipDto> relationships;

    private List<CgProjectDrawExpertRuleDto> projectDrawExpertRules;

    public List<CgProjectItemDto> getProjectItems() {
        return projectItems;
    }

    public void setProjectItems(List<CgProjectItemDto> projectItems) {
        this.projectItems = projectItems;
    }

    public List<CgProjectSupDto> getProjectSups() {
        return projectSups;
    }

    public void setProjectSups(List<CgProjectSupDto> projectSups) {
        this.projectSups = projectSups;
    }

    public List<CgProjectAttDto> getProjectAtts() {
        return projectAtts;
    }

    public void setProjectAtts(List<CgProjectAttDto> projectAtts) {
        this.projectAtts = projectAtts;
    }

    public List<CgProjectOaDatumDto> getProjectOaData() {
        return projectOaData;
    }

    public void setProjectOaData(List<CgProjectOaDatumDto> projectOaData) {
        this.projectOaData = projectOaData;
    }

    public List<CgProjectEvalDto> getProjectEvals() {
        return projectEvals;
    }

    public void setProjectEvals(List<CgProjectEvalDto> projectEvals) {
        this.projectEvals = projectEvals;
    }

    public List<CgProjectDrawExpertRuleDto> getProjectDrawExpertRules() {
        return projectDrawExpertRules;
    }

    public void setProjectDrawExpertRules(List<CgProjectDrawExpertRuleDto> projectDrawExpertRules) {
        this.projectDrawExpertRules = projectDrawExpertRules;
    }

    public List<CgProjectRelationshipDto> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<CgProjectRelationshipDto> relationships) {
        this.relationships = relationships;
    }
}
