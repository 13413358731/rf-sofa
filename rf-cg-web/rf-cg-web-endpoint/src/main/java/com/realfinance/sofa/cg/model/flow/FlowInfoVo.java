package com.realfinance.sofa.cg.model.flow;

public class FlowInfoVo {
    protected ProcessInstanceVo flowProcessInstance;
    protected FlowTaskVo flowTask;

    public ProcessInstanceVo getFlowProcessInstance() {
        return flowProcessInstance;
    }

    public void setFlowProcessInstance(ProcessInstanceVo flowProcessInstance) {
        this.flowProcessInstance = flowProcessInstance;
    }

    public FlowTaskVo getFlowTask() {
        return flowTask;
    }

    public void setFlowTask(FlowTaskVo flowTask) {
        this.flowTask = flowTask;
    }
}
