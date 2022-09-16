package com.realfinance.sofa.cg.core.domain.purresult;

import com.realfinance.sofa.cg.core.domain.BasePurRelationship;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CG_CORE_PUR_RESULT_RELATIONSHIP")
public class PurResultRelationship extends BasePurRelationship {

    @ManyToOne
    @JoinColumn(name = "pur_result_id", updatable = false)
    private PurchaseResult purchaseResult;

    public PurchaseResult getPurchaseResult() {
        return purchaseResult;
    }

    public void setPurchaseResult(PurchaseResult purchaseResult) {
        this.purchaseResult = purchaseResult;
    }
}
