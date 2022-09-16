package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.core.model.CgPurchasePlanSaveDto;

import java.util.List;

public class CgAnnualPlanSaveRequest {


    private Integer id;

    /**
     *  计划年度
     */
    private  String  plan;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }


}
