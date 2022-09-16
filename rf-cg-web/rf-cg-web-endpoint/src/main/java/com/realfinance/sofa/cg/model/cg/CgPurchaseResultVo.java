package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.flow.FlowInfoVo;
import com.realfinance.sofa.cg.model.flow.FlowableVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "采购结果对象")
public class CgPurchaseResultVo extends BaseVo implements IdentityObject<Integer>, FlowableVo {

    public interface Save {
    }

    protected Integer id;

    @Schema(description = "报价类型")
    protected String quoteType;

    /**
     * 法人ID
     */
    @Schema()
    private String tenantId;

    /**
     * 部门ID
     */
    @Schema()
    private Integer departmentId;

    /**
     * 采购方案id
     */
    @Schema()
    private Integer projectId;

    /**
     * 采购方案执行id
     */
    @Schema()
    private Integer projectexeId;

    /**
     * 采购方案
     */
    @Schema()
    private CgProjectVo project;

    /**
     * 项目编号
     */
    @Schema()
    private String projectNo;

    /**
     * 项目名称
     */
    @Schema()
    private String name;

    /**
     * 选取的采购方式
     */
    @Schema()
    private String purMode;

    /**
     * 选取的评标方法
     */
    @Schema()
    private String evalMethod;

    /**
     * 采购种类
     */
    @Schema()
    private String purType;

    /**
     * 中标金额
     */
    @Schema()
    private BigDecimal biddingAmount;

    /**
     * 节省金额
     */
    @Schema()
    private BigDecimal saveAmount;

    /**
     * 预算降幅 %
     */
    @Schema()
    private Integer saveRatio;

    /**
     * 有关情况说明
     */
    @Schema()
    private String note;

    /**
     * 审批通过时间
     */
    @Schema()
    private LocalDateTime passTime;

    /**
     * 是否生效
     */
    @Schema()
    private Boolean valid;

    /**
     * 采购会议对象
     */
    private CgMeetingVo meeting;


    private CgDrawExpertVo drawExpert;
    //    /**
//     * 采购结果状态
//     */
//    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
//    @Schema(description = "处理状态", accessMode = Schema.AccessMode.READ_ONLY)
//    protected String purResultStatus;
    @Schema(description = "处理状态")
    protected String status;

    private List<CgPurResultAttVo> purResultAtts;

    private List<CgPurResultConfirmDetVo> purResultConfirmDets;

    private List<CgPurResultExpertVo> purResultExperts;

    private List<CgPurResultSupVo> purResultSups;

    private List<CgPurchaseResultRelationshipVo> relationships;

    protected FlowInfoVo flowInfo;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public CgProjectVo getProject() {
        return project;
    }

    public void setProject(CgProjectVo project) {
        this.project = project;
    }

    public String getQuoteType() {
        return quoteType;
    }

    public void setQuoteType(String quoteType) {
        this.quoteType = quoteType;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getProjectexeId() {
        return projectexeId;
    }

    public void setProjectexeId(Integer projectexeId) {
        this.projectexeId = projectexeId;
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

    public String getPurType() {
        return purType;
    }

    public void setPurType(String purType) {
        this.purType = purType;
    }

    public BigDecimal getBiddingAmount() {
        return biddingAmount;
    }

    public void setBiddingAmount(BigDecimal biddingAmount) {
        this.biddingAmount = biddingAmount;
    }

    public BigDecimal getSaveAmount() {
        return saveAmount;
    }

    public void setSaveAmount(BigDecimal saveAmount) {
        this.saveAmount = saveAmount;
    }

    public Integer getSaveRatio() {
        return saveRatio;
    }

    public void setSaveRatio(Integer saveRatio) {
        this.saveRatio = saveRatio;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<CgPurResultAttVo> getPurResultAtts() {
        return purResultAtts;
    }

    public void setPurResultAtts(List<CgPurResultAttVo> purResultAtts) {
        this.purResultAtts = purResultAtts;
    }

    public List<CgPurResultConfirmDetVo> getPurResultConfirmDets() {
        return purResultConfirmDets;
    }

    public void setPurResultConfirmDets(List<CgPurResultConfirmDetVo> purResultConfirmDets) {
        this.purResultConfirmDets = purResultConfirmDets;
    }

    public List<CgPurResultExpertVo> getPurResultExperts() {
        return purResultExperts;
    }

    public void setPurResultExperts(List<CgPurResultExpertVo> purResultExperts) {
        this.purResultExperts = purResultExperts;
    }

    public List<CgPurResultSupVo> getPurResultSups() {
        return purResultSups;
    }

    public void setPurResultSups(List<CgPurResultSupVo> purResultSups) {
        this.purResultSups = purResultSups;
    }

    @Override
    public FlowInfoVo getFlowInfo() {
        return flowInfo;
    }

    public void setFlowInfo(FlowInfoVo flowInfo) {
        this.flowInfo = flowInfo;
    }

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

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public List<CgPurchaseResultRelationshipVo> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<CgPurchaseResultRelationshipVo> relationships) {
        this.relationships = relationships;
    }

    public CgMeetingVo getMeeting() {
        return meeting;
    }

    public void setMeeting(CgMeetingVo meeting) {
        this.meeting = meeting;
    }

    public CgDrawExpertVo getDrawExpert() {
        return drawExpert;
    }

    public void setDrawExpert(CgDrawExpertVo drawExpert) {
        this.drawExpert = drawExpert;
    }


}
