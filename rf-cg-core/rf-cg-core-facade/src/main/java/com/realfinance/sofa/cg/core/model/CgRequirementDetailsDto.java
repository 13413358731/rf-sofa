package com.realfinance.sofa.cg.core.model;

import java.util.List;

public class CgRequirementDetailsDto extends CgRequirementDto {
    private List<CgRequirementItemDto> requirementItems;

    private List<CgRequirementSupDto> requirementSups;

    private List<CgRequirementAttDto> requirementAtts;

    private List<CgRequirementOaDatumDto> requirementOaData;

    private List<CgRequirementRelationshipDto> relationships;

    public List<CgRequirementItemDto> getRequirementItems() {
        return requirementItems;
    }

    public void setRequirementItems(List<CgRequirementItemDto> requirementItems) {
        this.requirementItems = requirementItems;
    }

    public List<CgRequirementSupDto> getRequirementSups() {
        return requirementSups;
    }

    public void setRequirementSups(List<CgRequirementSupDto> requirementSups) {
        this.requirementSups = requirementSups;
    }

    public List<CgRequirementAttDto> getRequirementAtts() {
        return requirementAtts;
    }

    public void setRequirementAtts(List<CgRequirementAttDto> requirementAtts) {
        this.requirementAtts = requirementAtts;
    }

    public List<CgRequirementOaDatumDto> getRequirementOaData() {
        return requirementOaData;
    }

    public void setRequirementOaData(List<CgRequirementOaDatumDto> requirementOaData) {
        this.requirementOaData = requirementOaData;
    }

    public List<CgRequirementRelationshipDto> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<CgRequirementRelationshipDto> relationships) {
        this.relationships = relationships;
    }
}
