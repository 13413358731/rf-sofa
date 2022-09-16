package com.realfinance.sofa.cg.core.domain.plan;


import com.realfinance.sofa.cg.core.domain.BaseEntity;
import com.realfinance.sofa.cg.core.domain.FlowStatus;
import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.util.List;

/**
 * 计划年度
 */

@Entity
@Table(name = "CG_CORE_ANNUAL_PLAN",indexes = {@Index(columnList = "plan",unique = true)})
public class AnnualPlan extends BaseEntity implements IEntity<Integer> {


    @Column
    @Enumerated
    private FlowStatus status;

    /**
     * 法人（租户）
     */
    @Column(nullable=false, updatable=false)
    private String tenantId;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     *  计划年度
     */
    private  String  plan;


    @OneToMany
    @JoinColumn(name="annualPlan_id", referencedColumnName="id")
    private List<PurchasePlan>  plans;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }
    public FlowStatus getStatus() {
        return status;
    }

    public void setStatus(FlowStatus status) {
        this.status = status;
    }

    public List<PurchasePlan> getPlans() {
        return plans;
    }

    public void setPlans(List<PurchasePlan> plans) {
        this.plans = plans;
    }
}
