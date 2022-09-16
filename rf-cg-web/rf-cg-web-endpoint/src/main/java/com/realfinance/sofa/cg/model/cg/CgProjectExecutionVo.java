package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.EditableVo;
import com.realfinance.sofa.cg.model.flow.FlowInfoVo;
import com.realfinance.sofa.cg.model.flow.FlowableVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "采购方案执行对象")
public class CgProjectExecutionVo extends BaseVo implements EditableVo,FlowableVo, IdentityObject<Integer> {

    protected Integer id;

    @Schema(description = "报价类型")
    protected String quoteType;

    @Schema(description = "废标原因")
    private String invalidReason;

    @Schema(description = "是否废标")
    private Boolean invalid;

    @Schema(description = "流程任务")
    protected FlowInfoVo flowInfo;

    /**
     * 处理意见
     */
    @Schema(description = "处理意见")
    private String reason;

    @Schema(description = "是否退回申请")
    private Boolean returnReq;

    protected CgProjectVo project;

    private List<CgProjectExecutionStepVo> projectExecutionSteps;

    private List<CgProjectExecutionSupVo> projectExecutionSups;

    private List<CgAttVo> projectExecutionAtts;


    /**
     * 供应商报价信息， 动态表单
     */
    @Schema(description = "报价信息")
    private Object replyInfo;

    public String getExecutionStatus() {
        List<CgProjectExecutionStepVo> projectExecutionSteps = getProjectExecutionSteps();
        Boolean inValid=getInvalid();
        if (inValid==true){
            return "已废标";
        }
        if (projectExecutionSteps == null || projectExecutionSteps.isEmpty()) {
            return "未执行";
        }
        if (projectExecutionSteps.stream().map(CgProjectExecutionStepVo::getEndTime).allMatch(Objects::nonNull)) {
            return "已完成";
        }
        if (projectExecutionSteps.stream().map(CgProjectExecutionStepVo::getStartTime).anyMatch(Objects::nonNull)) {
            return "执行中";
        }
        return "未执行";
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getQuoteType() {
        return quoteType;
    }

    public void setQuoteType(String quoteType) {
        this.quoteType = quoteType;
    }

    public String getInvalidReason() {
        return invalidReason;
    }

    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }

    public Boolean getInvalid() {
        return invalid;
    }

    public void setInvalid(Boolean invalid) {
        this.invalid = invalid;
        //this.getExecutionStatus();
    }

    public Boolean getReturnReq() {
        return returnReq;
    }

    public void setReturnReq(Boolean returnReq) {
        this.returnReq = returnReq;
    }

    public CgProjectVo getProject() {
        return project;
    }

    public void setProject(CgProjectVo project) {
        this.project = project;
    }

    public List<CgProjectExecutionStepVo> getProjectExecutionSteps() {
        return projectExecutionSteps;
    }

    public void setProjectExecutionSteps(List<CgProjectExecutionStepVo> projectExecutionSteps) {
        this.projectExecutionSteps = projectExecutionSteps;
    }

    public List<CgProjectExecutionSupVo> getProjectExecutionSups() {
        return projectExecutionSups;
    }

    public void setProjectExecutionSups(List<CgProjectExecutionSupVo> projectExecutionSups) {
        this.projectExecutionSups = projectExecutionSups;
    }

    public List<CgAttVo> getProjectExecutionAtts() {
        return projectExecutionAtts;
    }

    public void setProjectExecutionAtts(List<CgAttVo> projectExecutionAtts) {
        this.projectExecutionAtts = projectExecutionAtts;
    }

    public Object getReplyInfo() {
        return replyInfo;
    }

    public void setReplyInfo(Object replyInfo) {
        this.replyInfo = replyInfo;
    }

    @Override
    public FlowInfoVo getFlowInfo() {
        return flowInfo;
    }

    @Override
    public String getStatus() {
        return null;
    }

    public void setFlowInfo(FlowInfoVo flowInfo) {
        this.flowInfo = flowInfo;
    }

}
