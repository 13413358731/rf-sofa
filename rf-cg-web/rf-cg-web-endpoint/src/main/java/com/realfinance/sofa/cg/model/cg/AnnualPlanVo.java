package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.realfinance.sofa.cg.core.model.CgPurchasePlanDto;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.flow.FlowInfoVo;
import com.realfinance.sofa.cg.model.flow.FlowableVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class AnnualPlanVo extends BaseVo implements IdentityObject<Integer>, FlowableVo {


    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "处理状态", accessMode = Schema.AccessMode.READ_ONLY)
    protected String status;


    @Schema(description = "流程任务")
    protected FlowInfoVo flowInfo;


    private Integer id;


    private  String  plan;




    @Override
    public FlowInfoVo getFlowInfo() {
        return flowInfo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFlowInfo(FlowInfoVo flowInfo) {
        this.flowInfo = flowInfo;
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

}
