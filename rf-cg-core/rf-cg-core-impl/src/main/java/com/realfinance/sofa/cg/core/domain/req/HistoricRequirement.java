package com.realfinance.sofa.cg.core.domain.req;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CG_CORE_HI_REQ")
public class HistoricRequirement extends BaseRequirement {

    @Column(nullable = false)
    private Integer srcId;

    @ManyToOne
    @JoinColumn(name = "req_id", updatable = false)
    private Requirement requirement;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "hi_req_id")
    private List<HistoricRequirementItem> requirementItems;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "hi_req_id")
    private List<HistoricRequirementSup> requirementSups;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "hi_req_id")
    private List<HistoricRequirementAtt> requirementAtts;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "hi_req_id")
    private List<HistoricRequirementOaDatum> requirementOaData;

    public HistoricRequirement() {
        this.requirementItems = new ArrayList<>();
        this.requirementSups = new ArrayList<>();
        this.requirementAtts = new ArrayList<>();
        this.requirementOaData = new ArrayList<>();
    }

    public Integer getSrcId() {
        return srcId;
    }

    public void setSrcId(Integer srcId) {
        this.srcId = srcId;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }

    public List<HistoricRequirementItem> getRequirementItems() {
        return requirementItems;
    }

    public void setRequirementItems(List<HistoricRequirementItem> requirementItems) {
        this.requirementItems = requirementItems;
    }

    public List<HistoricRequirementSup> getRequirementSups() {
        return requirementSups;
    }

    public void setRequirementSups(List<HistoricRequirementSup> requirementSups) {
        this.requirementSups = requirementSups;
    }

    public List<HistoricRequirementAtt> getRequirementAtts() {
        return requirementAtts;
    }

    public void setRequirementAtts(List<HistoricRequirementAtt> requirementAtts) {
        this.requirementAtts = requirementAtts;
    }

    public List<HistoricRequirementOaDatum> getRequirementOaData() {
        return requirementOaData;
    }

    public void setRequirementOaData(List<HistoricRequirementOaDatum> requirementOaData) {
        this.requirementOaData = requirementOaData;
    }
}
