package com.realfinance.sofa.cg.core.model;

import java.util.List;

public class CgPurchaseResultNoticeDto extends BaseDto{

    private Integer id;

    /**
     * 采购方案ID
     */
    private Integer projectId;

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
    private  Integer  projectProducerId;


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
    private Integer contractProducerId;


    private String status;

    private List<CgPurchaseResultNoticeSupDto> resultNoticeSups;

    private List<CgAttSaveDto>  attachments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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

    public Integer getProjectProducerId() {
        return projectProducerId;
    }

    public void setProjectProducerId(Integer projectProducerId) {
        this.projectProducerId = projectProducerId;
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

    public Integer getContractProducerId() {
        return contractProducerId;
    }

    public void setContractProducerId(Integer contractProducerId) {
        this.contractProducerId = contractProducerId;
    }

    public List<CgPurchaseResultNoticeSupDto> getResultNoticeSups() {
        return resultNoticeSups;
    }

    public void setResultNoticeSups(List<CgPurchaseResultNoticeSupDto> resultNoticeSups) {
        this.resultNoticeSups = resultNoticeSups;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<CgAttSaveDto> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<CgAttSaveDto> attachments) {
        this.attachments = attachments;
    }

    public Integer getProjectExecutionId() {
        return projectExecutionId;
    }

    public void setProjectExecutionId(Integer projectExecutionId) {
        this.projectExecutionId = projectExecutionId;
    }
}
