package com.realfinance.sofa.cg.model.cg;

import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.flow.FlowInfoVo;
import com.realfinance.sofa.cg.model.flow.FlowableVo;
import com.realfinance.sofa.cg.model.system.DepartmentVo;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "专家对象")
public class CgDrawExpertVo extends BaseVo implements FlowableVo,IdentityObject<Integer> {

    public interface Save {
    }

    @Schema(description = "ID")
    protected Integer id;

    @Schema(description = "单据编号")
    protected String receipt;

    @Schema(description = "专家抽取事由")
    protected String event;

    @Schema(description = "会议类别")
    protected String sort;

    @Schema(description = "参会人数")
    protected Integer meetingNumber;

    @Schema(description = "抽取人数")
    protected Integer drawNumber;

    @Schema(description = "制单人")
    protected UserVo drawer;

    @Schema(description = "制单人公司")
    protected String tenantId;

    @Schema(description = "制单人部门")
    protected DepartmentVo departmentId;

    @Schema(description = "抽取日期")
    protected LocalDateTime drawTime;

    @Schema(description = "制单人联系电话")
    protected String phone;

    @Schema(description = "专家组成描述")
    protected String description;

    @Schema(description = "通知专家")
    protected String noticeExpert;

    @Schema(description = "状态")
    protected Boolean validStatus;

    @Schema(description = "采购方案")
    protected CgProjectVo projectId;

    @Schema(description = "确认人数")
    protected Integer confirmNumber;

    @Schema(description = "抽取专家子表")
    protected List<CgDrawExpertListVo> drawExpertLists;

    @Schema(description = "抽取专家规则")
    protected List<CgDrawExpertRuleVo> drawExpertRules;

    @Schema(description = "完成状态（是否已关联会议）")
    private Boolean finishStatus;

    @Schema(description = "评标时间")
    private LocalDateTime biddingTime;

    @Schema(description = "结束时间")
    private LocalDateTime endTime;

    @Schema(description = "处理状态")
    protected String status;

    @Schema(description = "流程任务")

    protected FlowInfoVo flowInfo;


    public void setFlowInfo(FlowInfoVo flowInfo) {
        this.flowInfo = flowInfo;
    }

    @Override
    public FlowInfoVo getFlowInfo() {
        return flowInfo;
    }

    @Override
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public Integer getMeetingNumber() {
        return meetingNumber;
    }

    public void setMeetingNumber(Integer meetingNumber) {
        this.meetingNumber = meetingNumber;
    }

    public Integer getDrawNumber() {
        return drawNumber;
    }

    public void setDrawNumber(Integer drawNumber) {
        this.drawNumber = drawNumber;
    }

    public UserVo getDrawer() {
        return drawer;
    }

    public void setDrawer(UserVo drawer) {
        this.drawer = drawer;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public DepartmentVo getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(DepartmentVo departmentId) {
        this.departmentId = departmentId;
    }

    public LocalDateTime getDrawTime() {
        return drawTime;
    }

    public void setDrawTime(LocalDateTime drawTime) {
        this.drawTime = drawTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNoticeExpert() {
        return noticeExpert;
    }

    public void setNoticeExpert(String noticeExpert) {
        this.noticeExpert = noticeExpert;
    }

    public Boolean getValidStatus() {
        return validStatus;
    }

    public void setValidStatus(Boolean validStatus) {
        this.validStatus = validStatus;
    }

    public List<CgDrawExpertListVo> getDrawExpertLists() {
        return drawExpertLists;
    }

    public void setDrawExpertLists(List<CgDrawExpertListVo> drawExpertLists) {
        this.drawExpertLists = drawExpertLists;
    }

    public CgProjectVo getProjectId() {
        return projectId;
    }

    public void setProjectId(CgProjectVo projectId) {
        this.projectId = projectId;
    }

    public Integer getConfirmNumber() {
        return confirmNumber;
    }

    public void setConfirmNumber(Integer confirmNumber) {
        this.confirmNumber = confirmNumber;
    }

    public List<CgDrawExpertRuleVo> getDrawExpertRules() {
        return drawExpertRules;
    }

    public void setDrawExpertRules(List<CgDrawExpertRuleVo> drawExpertRules) {
        this.drawExpertRules = drawExpertRules;
    }

    public Boolean getFinishStatus() {
        return finishStatus;
    }

    public void setFinishStatus(Boolean finishStatus) {
        this.finishStatus = finishStatus;
    }

    public LocalDateTime getBiddingTime() {
        return biddingTime;
    }

    public void setBiddingTime(LocalDateTime biddingTime) {
        this.biddingTime = biddingTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
