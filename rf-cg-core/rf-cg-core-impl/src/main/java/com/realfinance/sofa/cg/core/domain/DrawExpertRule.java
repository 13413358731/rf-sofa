package com.realfinance.sofa.cg.core.domain;

import javax.persistence.*;

/**
 * 专家抽取规则
 */
@Entity
@Table(name = "CG_CORE_DRAWEXPERTRULE")
public class DrawExpertRule extends BaseDrawExpertRule {

    @ManyToOne
    @JoinColumn(name = "drawExpert_id")
    private DrawExpert drawExpert;

    public DrawExpert getDrawExpert() {
        return drawExpert;
    }

    public void setDrawExpert(DrawExpert drawExpert) {
        this.drawExpert = drawExpert;
    }
}
