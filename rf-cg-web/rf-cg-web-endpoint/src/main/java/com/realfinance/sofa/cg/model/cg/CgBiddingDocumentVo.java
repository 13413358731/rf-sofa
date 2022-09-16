package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.flow.FlowInfoVo;
import com.realfinance.sofa.cg.model.flow.FlowableVo;
import com.realfinance.sofa.cg.model.system.DepartmentVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "采购方案执行-发标对象")
public class CgBiddingDocumentVo extends BaseVo implements FlowableVo, IdentityObject<Integer> {

    protected Integer id;

    protected DepartmentVo department;

    protected CgProjectExecutionVo projectExecution;

    private List<CgProjectExecutionStepVo> projectExecutionSteps;

    protected List<CgBiddingDocumentSectionVo> biddingDocumentSections;

    protected List<CgBiddingDocumentExaminationVo> biddingDocumentQualExaminations;

    protected List<CgBiddingDocumentExaminationVo> biddingDocumentRespExaminations;

    protected List<CgAttVo> biddingDocumentAtts;

    @Schema(description = "供应商列表")
    protected List<CgProjectExecutionSupVo> suppliers;

    @Schema(description = "选中供应商ID")
    protected Set<Integer> supplierIds;

    protected List<CgBusinessReplyVo> replies;

    @Schema(description = "发标名称，标题")
    protected String name;

    @Schema(description = "文档名称")
    protected String docName;

    /**
     * 投标截止时间
     */
    @Schema(description = "投标截止时间")
    protected LocalDateTime bidEndTime;

    /**
     * 唱标开始时间
     */
    @Schema(description = "唱标开始时间")
    protected LocalDateTime openBidStartTime;

    /**
     * 唱标截止时间
     */
    @Schema(description = "唱标截止时间")
    protected LocalDateTime openBidEndTime;

    /**
     * 需要报价
     */
    @Schema(description = "需要报价")
    protected Boolean needQuote;

    @Schema(description = "内容")
    protected String content;

    /**
     * 监督人员开启价格的时间
     */
    @Schema(description = "监督人员开启价格的时间")
    protected LocalDateTime preOpenQuoteTime;
    /**
     * 开启价格的监督人员ID
     */
    @Schema(description = "开启价格的监督人员ID")
    protected Integer preOpenQuoteUserId;
    /**
     * 开启价格时间
     */
    @Schema(description = "开启价格时间")
    protected LocalDateTime openQuoteTime;

    /**
     * 监督人员开启商务的时间
     */
    @Schema(description = "监督人员开启商务的时间")
    protected LocalDateTime preOpenBizTime;
    /**
     * 开启商务的监督人员ID
     */
    @Schema(description = "开启商务的监督人员ID")
    protected Integer preOpenBizUserId;
    /**
     * 开启商务时间
     */
    @Schema(description = "开启商务时间")
    protected LocalDateTime openBizTime;

    @Schema(description = "单据状态")
    protected String status;

    protected LocalDateTime passTime;

    private FlowInfoVo flowInfo;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public DepartmentVo getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentVo department) {
        this.department = department;
    }

    public CgProjectExecutionVo getProjectExecution() {
        return projectExecution;
    }

    public void setProjectExecution(CgProjectExecutionVo projectExecution) {
        this.projectExecution = projectExecution;
    }

    public List<CgBiddingDocumentSectionVo> getBiddingDocumentSections() {
        return biddingDocumentSections;
    }

    public void setBiddingDocumentSections(List<CgBiddingDocumentSectionVo> biddingDocumentSections) {
        this.biddingDocumentSections = biddingDocumentSections;
    }

    public List<CgBiddingDocumentExaminationVo> getBiddingDocumentQualExaminations() {
        return biddingDocumentQualExaminations;
    }

    public void setBiddingDocumentQualExaminations(List<CgBiddingDocumentExaminationVo> biddingDocumentQualExaminations) {
        this.biddingDocumentQualExaminations = biddingDocumentQualExaminations;
    }

    public List<CgBiddingDocumentExaminationVo> getBiddingDocumentRespExaminations() {
        return biddingDocumentRespExaminations;
    }

    public void setBiddingDocumentRespExaminations(List<CgBiddingDocumentExaminationVo> biddingDocumentRespExaminations) {
        this.biddingDocumentRespExaminations = biddingDocumentRespExaminations;
    }

    public List<CgAttVo> getBiddingDocumentAtts() {
        return biddingDocumentAtts;
    }

    public void setBiddingDocumentAtts(List<CgAttVo> biddingDocumentAtts) {
        this.biddingDocumentAtts = biddingDocumentAtts;
    }

    public List<CgProjectExecutionSupVo> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<CgProjectExecutionSupVo> suppliers) {
        this.suppliers = suppliers;
    }

    public Set<Integer> getSupplierIds() {
        return supplierIds;
    }

    public void setSupplierIds(Set<Integer> supplierIds) {
        this.supplierIds = supplierIds;
    }

    public List<CgBusinessReplyVo> getReplies() {
        return replies;
    }

    public void setReplies(List<CgBusinessReplyVo> replies) {
        this.replies = replies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public LocalDateTime getBidEndTime() {
        return bidEndTime;
    }

    public void setBidEndTime(LocalDateTime bidEndTime) {
        this.bidEndTime = bidEndTime;
    }

    public LocalDateTime getOpenBidStartTime() {
        return openBidStartTime;
    }

    public void setOpenBidStartTime(LocalDateTime openBidStartTime) {
        this.openBidStartTime = openBidStartTime;
    }

    public LocalDateTime getOpenBidEndTime() {
        return openBidEndTime;
    }

    public void setOpenBidEndTime(LocalDateTime openBidEndTime) {
        this.openBidEndTime = openBidEndTime;
    }

    public Boolean getNeedQuote() {
        return needQuote;
    }

    public void setNeedQuote(Boolean needQuote) {
        this.needQuote = needQuote;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getPreOpenQuoteTime() {
        return preOpenQuoteTime;
    }

    public void setPreOpenQuoteTime(LocalDateTime preOpenQuoteTime) {
        this.preOpenQuoteTime = preOpenQuoteTime;
    }

    public Integer getPreOpenQuoteUserId() {
        return preOpenQuoteUserId;
    }

    public void setPreOpenQuoteUserId(Integer preOpenQuoteUserId) {
        this.preOpenQuoteUserId = preOpenQuoteUserId;
    }

    public LocalDateTime getOpenQuoteTime() {
        return openQuoteTime;
    }

    public void setOpenQuoteTime(LocalDateTime openQuoteTime) {
        this.openQuoteTime = openQuoteTime;
    }

    public LocalDateTime getPreOpenBizTime() {
        return preOpenBizTime;
    }

    public void setPreOpenBizTime(LocalDateTime preOpenBizTime) {
        this.preOpenBizTime = preOpenBizTime;
    }

    public Integer getPreOpenBizUserId() {
        return preOpenBizUserId;
    }

    public void setPreOpenBizUserId(Integer preOpenBizUserId) {
        this.preOpenBizUserId = preOpenBizUserId;
    }

    public LocalDateTime getOpenBizTime() {
        return openBizTime;
    }

    public void setOpenBizTime(LocalDateTime openBizTime) {
        this.openBizTime = openBizTime;
    }

    @Override
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getPassTime() {
        return passTime;
    }

    public void setPassTime(LocalDateTime passTime) {
        this.passTime = passTime;
    }

    @Override
    public FlowInfoVo getFlowInfo() {
        return flowInfo;
    }

    public void setFlowInfo(FlowInfoVo flowInfo) {
        this.flowInfo = flowInfo;
    }

    public List<CgProjectExecutionStepVo> getProjectExecutionSteps() {
        return projectExecutionSteps;
    }

    public void setProjectExecutionSteps(List<CgProjectExecutionStepVo> projectExecutionSteps) {
        this.projectExecutionSteps = projectExecutionSteps;
    }
}
