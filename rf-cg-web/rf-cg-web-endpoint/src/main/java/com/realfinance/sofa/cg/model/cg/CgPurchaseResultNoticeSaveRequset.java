package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;
import com.realfinance.sofa.common.model.ReferenceObject;

import java.util.List;

public class CgPurchaseResultNoticeSaveRequset extends BaseVo implements IdentityObject<Integer> {

    private Integer id;

    //private  ReferenceObject<Integer> projectId;

    private Integer projectId;

    private Integer projectExecutionId;

    //private  ReferenceObject<Integer> projectExecutionId;

    /**
     * 项目编号
     */
    private String projectNo;

    /**
     * 项目名称
     */
    private String name;

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
    private  ReferenceObject<Integer> contractProducer;

    /**
     * 项目经办人
     */
    private  ReferenceObject<Integer>   projectProducer;

    private List<CgPurResultNoticeSupVo> resultNoticeSups;

    private List<CgAttVo>  attachments;

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

    public Integer getProjectExecutionId() {
        return projectExecutionId;
    }

    public void setProjectExecutionId(Integer projectExecutionId) {
        this.projectExecutionId = projectExecutionId;
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


    public ReferenceObject<Integer> getProjectProducer() {
        return projectProducer;
    }

    public void setProjectProducer(ReferenceObject<Integer> projectProducer) {
        this.projectProducer = projectProducer;
    }

    public ReferenceObject<Integer> getContractProducer() {
        return contractProducer;
    }

    public void setContractProducer(ReferenceObject<Integer> contractProducer) {
        this.contractProducer = contractProducer;
    }

    /*public List<CgPurchaseResultNoticeSupDto> getResultNoticeSups() {
        return resultNoticeSups;
    }

    public void setResultNoticeSups(List<CgPurchaseResultNoticeSupDto> resultNoticeSups) {
        this.resultNoticeSups = resultNoticeSups;
    }*/

    public List<CgPurResultNoticeSupVo> getResultNoticeSups() {
        return resultNoticeSups;
    }

    public void setResultNoticeSups(List<CgPurResultNoticeSupVo> resultNoticeSups) {
        this.resultNoticeSups = resultNoticeSups;
    }

    public List<CgAttVo> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<CgAttVo> attachments) {
        this.attachments = attachments;
    }
}
