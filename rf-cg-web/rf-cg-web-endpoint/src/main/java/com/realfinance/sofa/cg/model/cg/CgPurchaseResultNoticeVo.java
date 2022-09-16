package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.core.model.CgAttSaveDto;
import com.realfinance.sofa.cg.core.model.CgPurchaseResultNoticeSupDto;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.flow.FlowInfoVo;
import com.realfinance.sofa.cg.model.flow.FlowableVo;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class CgPurchaseResultNoticeVo extends BaseVo implements IdentityObject<Integer>, FlowableVo {

    private Integer id;

    private  Integer projectId;

    private  Integer  projectExecutionId;

    /**
     * 项目编号
     */
    private String projectNo;

    /**
     * 项目名称
     */
    private String name;


    /**
     * 项目经办人
     */
    private UserVo projectProducer;


    /**
     * 选取的采购方式
     */

    private String purMode;


    /**
     * 选取的评标方法
     */
    private String evalMethod;

    /**
     * 外部结果通知标题
     */
    private String outsideTitle;

    /**
     * 外部结果通知内容
     */

    private String outsideContent;


    /**
     * 内部结果通知标题
     */
    private String insideTitle;

    /**
     * 内部结果通知内容
     */

    private  String insideContent;

    /**
     * 合同制单人
     */
    private UserVo contractProducer;

    private List<CgPurResultNoticeSupVo> resultNoticeSups;

    private List<CgAttVo> attachments;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setFlowInfo(FlowInfoVo flowInfo) {
        this.flowInfo = flowInfo;
    }

    @Schema(description = "处理状态")
    protected String status;

    @Schema(description = "流程任务")
    protected FlowInfoVo flowInfo;

    @Override
    public FlowInfoVo getFlowInfo() {
        return flowInfo;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPurMode() {
        return purMode;
    }

    public void setPurMode(String purMode) {
        this.purMode = purMode;
    }

    public String getEvalMethod() {
        return evalMethod;
    }

    public void setEvalMethod(String evalMethod) {
        this.evalMethod = evalMethod;
    }

    public String getOutsideTitle() {
        return outsideTitle;
    }

    public void setOutsideTitle(String outsideTitle) {
        this.outsideTitle = outsideTitle;
    }

    public String getOutsideContent() {
        return outsideContent;
    }

    public void setOutsideContent(String outsideContent) {
        this.outsideContent = outsideContent;
    }

    public String getInsideTitle() {
        return insideTitle;
    }

    public void setInsideTitle(String insideTitle) {
        this.insideTitle = insideTitle;
    }

    public String getInsideContent() {
        return insideContent;
    }

    public void setInsideContent(String insideContent) {
        this.insideContent = insideContent;
    }

    public UserVo getProjectProducer() {
        return projectProducer;
    }

    public void setProjectProducer(UserVo projectProducer) {
        this.projectProducer = projectProducer;
    }

    public UserVo getContractProducer() {
        return contractProducer;
    }

    public void setContractProducer(UserVo contractProducer) {
        this.contractProducer = contractProducer;
    }

    public List<CgAttVo> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<CgAttVo> attachments) {
        this.attachments = attachments;
    }

    public List<CgPurResultNoticeSupVo> getResultNoticeSups() {
        return resultNoticeSups;
    }

    public void setResultNoticeSups(List<CgPurResultNoticeSupVo> resultNoticeSups) {
        this.resultNoticeSups = resultNoticeSups;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProjectExecutionId() {
        return projectExecutionId;
    }

    public void setProjectExecutionId(Integer projectExecutionId) {
        this.projectExecutionId = projectExecutionId;
    }
}
