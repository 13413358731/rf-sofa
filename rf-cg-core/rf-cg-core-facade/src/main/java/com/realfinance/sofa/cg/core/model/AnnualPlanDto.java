package com.realfinance.sofa.cg.core.model;


import java.util.List;

public class AnnualPlanDto extends  BaseDto{

    private String status;

    private Integer id;

    private  String  plan;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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
