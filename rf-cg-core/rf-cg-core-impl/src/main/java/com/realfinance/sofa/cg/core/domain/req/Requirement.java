package com.realfinance.sofa.cg.core.domain.req;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 采购需求
 */
@Entity
@Table(name = "CG_CORE_REQ")
public class Requirement extends BaseRequirement implements IEntity<Integer> {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "req_id")
    private List<RequirementItem> requirementItems;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "req_id")
    private List<RequirementSup> requirementSups;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "req_id")
    private List<RequirementAtt> requirementAtts;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "req_id")
    private List<RequirementOaDatum> requirementOaData;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "req_id")
    private List<RequirementRelationship> relationships;

    public Requirement() {
        this.requirementItems = new ArrayList<>();
        this.requirementSups = new ArrayList<>();
        this.requirementAtts = new ArrayList<>();
        this.requirementOaData = new ArrayList<>();
        this.relationships = new ArrayList<>();
    }

    /**
     * 重新计算市场价
     */
    public void resetMarketPrice() {
        BigDecimal sum = getRequirementItems().stream()
                .map(e -> e.getMarketPrice().multiply(BigDecimal.valueOf(e.getNumber())))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
        setMarketPrice(sum);
    }

    public List<RequirementItem> getRequirementItems() {
        return requirementItems;
    }

    public void setRequirementItems(List<RequirementItem> requirementItems) {
        this.requirementItems = requirementItems;
    }

    public List<RequirementSup> getRequirementSups() {
        return requirementSups;
    }

    public void setRequirementSups(List<RequirementSup> requirementSups) {
        this.requirementSups = requirementSups;
    }

    public List<RequirementAtt> getRequirementAtts() {
        return requirementAtts;
    }

    public void setRequirementAtts(List<RequirementAtt> requirementAtts) {
        this.requirementAtts = requirementAtts;
    }

    public List<RequirementOaDatum> getRequirementOaData() {
        return requirementOaData;
    }

    public void setRequirementOaData(List<RequirementOaDatum> requirementOaData) {
        this.requirementOaData = requirementOaData;
    }

    public List<RequirementRelationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<RequirementRelationship> relationships) {
        this.relationships = relationships;
    }
}
