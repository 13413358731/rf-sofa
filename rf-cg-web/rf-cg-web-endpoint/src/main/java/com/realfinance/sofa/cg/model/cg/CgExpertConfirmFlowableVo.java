package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.flow.FlowTaskVo;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CgExpertConfirmFlowableVo {
    private CgExpertConfirmVo cgExpertConfirmVo;

    private FlowTaskVo flowTaskVo;

    public CgExpertConfirmVo getCgExpertConfirmVo() {
        return cgExpertConfirmVo;
    }

    public void setCgExpertConfirmVo(CgExpertConfirmVo cgExpertConfirmVo) {
        this.cgExpertConfirmVo = cgExpertConfirmVo;
    }

    public FlowTaskVo getFlowTaskVo() {
        return flowTaskVo;
    }

    public void setFlowTaskVo(FlowTaskVo flowTaskVo) {
        this.flowTaskVo = flowTaskVo;
    }
}
