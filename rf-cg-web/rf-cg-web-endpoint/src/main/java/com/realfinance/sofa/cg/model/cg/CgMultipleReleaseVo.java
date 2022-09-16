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
@Schema(description = "采购方案执行-发布对象")
public class CgMultipleReleaseVo extends BaseVo implements FlowableVo, IdentityObject<Integer> {

    protected Integer id;

    protected DepartmentVo department;

    protected CgProjectExecutionVo projectExecution;

    protected List<CgAttVo> multipleReleaseAtts;

    @Schema(description = "供应商列表")
    protected List<CgProjectExecutionSupVo> suppliers;

    @Schema(description = "选中供应商ID")
    protected Set<Integer> supplierIds;

    protected List<CgBusinessReplyVo> replies;

    @Schema(description = "发标名称，标题")
    protected String name;

    /**
     * 投标截止时间
     */
    @Schema(description = "应答截止时间")
    protected LocalDateTime replyEndTime;

    /**
     * 唱标开始时间
     */
    @Schema(description = "唱标开始时间")
    protected LocalDateTime openStartTime;

    /**
     * 唱标截止时间
     */
    @Schema(description = "唱标截止时间")
    protected LocalDateTime openEndTime;

    /**
     * 需要报价
     */
    @Schema(description = "需要报价")
    protected Boolean needQuote;

    @Schema(description = "内容")
    protected String content;

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

    public List<CgAttVo> getMultipleReleaseAtts() {
        return multipleReleaseAtts;
    }

    public void setMultipleReleaseAtts(List<CgAttVo> multipleReleaseAtts) {
        this.multipleReleaseAtts = multipleReleaseAtts;
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

    public LocalDateTime getReplyEndTime() {
        return replyEndTime;
    }

    public void setReplyEndTime(LocalDateTime replyEndTime) {
        this.replyEndTime = replyEndTime;
    }

    public LocalDateTime getOpenStartTime() {
        return openStartTime;
    }

    public void setOpenStartTime(LocalDateTime openStartTime) {
        this.openStartTime = openStartTime;
    }

    public LocalDateTime getOpenEndTime() {
        return openEndTime;
    }

    public void setOpenEndTime(LocalDateTime openEndTime) {
        this.openEndTime = openEndTime;
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
}
