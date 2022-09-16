package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.core.model.CgProjectEvalDto;
import com.realfinance.sofa.cg.core.model.CgProjectOaDatumDto;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.flow.FlowInfoVo;
import com.realfinance.sofa.cg.model.flow.FlowableVo;
import com.realfinance.sofa.cg.model.system.DepartmentVo;
import com.realfinance.sofa.cg.model.system.UserVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "采购方案对象")
public class CgProjectVo extends BaseVo implements FlowableVo, IdentityObject<Integer> {

    /**
     * 部门ID
     */
    @Schema(description = "部门ID")
    private DepartmentVo department;

    private Integer id;

    /**
     * 项目编号
     */
    @Schema(description = "项目编号")
    private String projectNo;

    /**
     * 项目名称
     */
    @Schema(description = "项目名称")
    private String name;

    /**
     * 申请采购总金额
     */
    @Schema(description = "申请采购总金额")
    private BigDecimal reqTotalAmount;

    /**
     * 市场参考价/市场控制总价（总）
     */
    @Schema(description = "市场参考价/市场控制总价（总）")
    private BigDecimal marketPrice;

    /**
     * 拟成交供应商数
     */
    @Schema(description = "拟成交供应商数")
    private Integer numberOfWinSup;

    /**
     * 采购实施工时
     */
    @Schema(description = "采购实施工时")
    private String purDuration;

    /**
     * 选取的采购方式
     */
    @Schema(description = "选取的采购方式")
    private String purMode;

    /**
     * 项目特性
     */
    @Schema(description = "项目特性")
    private String projFeatures;

    /**
     * 选取的评标方法
     */
    @Schema(description = "选取的评标方法")
    private String evalMethod;

    /**
     * 项目申请部门
     */
    @Schema(description = "项目申请部门")
    private DepartmentVo reqDepartment;

    /**
     * 申请人
     */
    @Schema(description = "申请人")
    private UserVo reqUser;

    /**
     * 申请人联系电话
     */
    @Schema(description = "申请人联系电话")
    private String reqUserPhone;

    /**
     * 受理日期
     */
    @Schema(description = "受理日期")
    private LocalDateTime acceptTime;

    /**
     * 全体年度资格商
     */
    @Schema(description = "全体年度资格商")
    private CgSupplierLabelVo supLabel;

    /**
     * 采购种类
     */
    @Schema(description = "采购种类")
    private String purType;

    /**
     * 合同类别
     */
    @Schema(description = "合同类别")
    private String contractCategory;

    /**
     * 采购类别
     */
    @Schema(description = "采购类别")
    private String purCategory;

    /**
     * 采购计划
     */
    @Schema(description = "采购计划")
    private CgPurchasePlanVo purchasePlan;
    /**
     * 全新/以往采购
     */
    @Schema(description = "全新/以往采购")
    private Boolean newPurchase;

    /**
     * 项目类别
     */
    @Schema(description = "项目类别")
    private String projectCategory;

    /**
     * 采购用途
     */
    @Schema(description = "采购用途")
    private String purpose;

    /**
     * 适用范围
     */
    @Schema(description = "适用范围")
    private String applicableScope;

    /**
     * 采购需求
     */
    @Schema(description = "采购需求")
    private String purReq;

    /**
     * 采购申请
     */
    @Schema(description = "采购申请")
    private CgRequirementVo requirement;

    /**
     * 我行历史采购情况
     */
    @Schema(description = "我行历史采购情况")
    private String purHistory;

    /**
     * 供应商资质要求
     */
    @Schema(description = "供应商资质要求")
    private String supQualReq;

    /**
     * 采购小组组成
     */
    @Schema(description = "采购小组组成")
    private String purGroup;

    /**
     * 采购方式原因
     */
    @Schema(description = "采购方式原因")
    private String purModeReason;

    /**
     * 评价办法说明
     */
    @Schema(description = "评价办法说明")
    private String evalMethodReason;

    /**
     * 市场及供应商情况
     */
    @Schema(description = "市场及供应商情况")
    private String marketSupProfile;

    /**
     * 有关情况说明
     */
    @Schema(description = "有关情况说明")
    private String note;

    /**
     * 退回申请
     */
    @Schema(description = "退回申请")
    private Boolean returnReq;

    private String status;

    private LocalDateTime passTime;

    private FlowInfoVo flowInfo;


    @Schema(description = "退回申请")
    protected LocalDateTime reqCreatedTime;

    /**
     * 立项金额
     */
    @Schema(description = "立项金额")
    protected BigDecimal approvalAmount;

    /**
     * 合约有效期
     */
    @Schema(description = "合约有效期")
    protected Integer contractValidity;

    /**
     * 单一方式原因
     */
    @Schema(description = "单一方式原因")
    protected String singleReason;

    /**
     * 供应商资格要求
     */
    @Schema(description = "供应商资格要求")
    protected String supRequirements;

    /**
     * 合同制单人
     */
    @Schema(description = "合同制单人")
    private UserVo contractCreatedUser;

    /**
     * 使用部门
     */
    @Schema(description = "使用部门")
    private List<DepartmentVo> useDepartments;

    private List<CgProjectItemVo> projectItems;

    private List<CgProjectSupVo> projectSups;

    private List<CgAttVo> projectAtts;

    private List<CgProjectOaDatumDto> projectOaData;

    private List<CgProjectEvalDto> projectEvals;

//    private List<CgProjectDrawExpertRuleVo> projectDrawExpertRules;
    private List<CgProjectRelationshipVo> relationships;


    public DepartmentVo getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentVo department) {
        this.department = department;
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

    public BigDecimal getReqTotalAmount() {
        return reqTotalAmount;
    }

    public void setReqTotalAmount(BigDecimal reqTotalAmount) {
        this.reqTotalAmount = reqTotalAmount;
    }

    public BigDecimal getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }

    public Integer getNumberOfWinSup() {
        return numberOfWinSup;
    }

    public void setNumberOfWinSup(Integer numberOfWinSup) {
        this.numberOfWinSup = numberOfWinSup;
    }

    public String getPurDuration() {
        return purDuration;
    }

    public void setPurDuration(String purDuration) {
        this.purDuration = purDuration;
    }

    public String getPurMode() {
        return purMode;
    }

    public void setPurMode(String purMode) {
        this.purMode = purMode;
    }

    public String getProjFeatures() {
        return projFeatures;
    }

    public void setProjFeatures(String projFeatures) {
        this.projFeatures = projFeatures;
    }

    public String getEvalMethod() {
        return evalMethod;
    }

    public void setEvalMethod(String evalMethod) {
        this.evalMethod = evalMethod;
    }

    public DepartmentVo getReqDepartment() {
        return reqDepartment;
    }

    public void setReqDepartment(DepartmentVo reqDepartment) {
        this.reqDepartment = reqDepartment;
    }

    public UserVo getReqUser() {
        return reqUser;
    }

    public void setReqUser(UserVo reqUser) {
        this.reqUser = reqUser;
    }

    public String getReqUserPhone() {
        return reqUserPhone;
    }

    public void setReqUserPhone(String reqUserPhone) {
        this.reqUserPhone = reqUserPhone;
    }

    public LocalDateTime getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(LocalDateTime acceptTime) {
        this.acceptTime = acceptTime;
    }

    public CgSupplierLabelVo getSupLabel() {
        return supLabel;
    }

    public void setSupLabel(CgSupplierLabelVo supLabel) {
        this.supLabel = supLabel;
    }

    public String getPurType() {
        return purType;
    }

    public void setPurType(String purType) {
        this.purType = purType;
    }

    public String getContractCategory() {
        return contractCategory;
    }

    public void setContractCategory(String contractCategory) {
        this.contractCategory = contractCategory;
    }

    public String getPurCategory() {
        return purCategory;
    }

    public void setPurCategory(String purCategory) {
        this.purCategory = purCategory;
    }

    public Boolean getNewPurchase() {
        return newPurchase;
    }

    public void setNewPurchase(Boolean newPurchase) {
        this.newPurchase = newPurchase;
    }

    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getApplicableScope() {
        return applicableScope;
    }

    public void setApplicableScope(String applicableScope) {
        this.applicableScope = applicableScope;
    }

    public String getPurReq() {
        return purReq;
    }

    public void setPurReq(String purReq) {
        this.purReq = purReq;
    }

    public String getPurHistory() {
        return purHistory;
    }

    public void setPurHistory(String purHistory) {
        this.purHistory = purHistory;
    }

    public String getSupQualReq() {
        return supQualReq;
    }

    public void setSupQualReq(String supQualReq) {
        this.supQualReq = supQualReq;
    }

    public String getPurGroup() {
        return purGroup;
    }

    public void setPurGroup(String purGroup) {
        this.purGroup = purGroup;
    }

    public String getPurModeReason() {
        return purModeReason;
    }

    public void setPurModeReason(String purModeReason) {
        this.purModeReason = purModeReason;
    }

    public String getEvalMethodReason() {
        return evalMethodReason;
    }

    public void setEvalMethodReason(String evalMethodReason) {
        this.evalMethodReason = evalMethodReason;
    }

    public String getMarketSupProfile() {
        return marketSupProfile;
    }

    public void setMarketSupProfile(String marketSupProfile) {
        this.marketSupProfile = marketSupProfile;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getReturnReq() {
        return returnReq;
    }

    public void setReturnReq(Boolean returnReq) {
        this.returnReq = returnReq;
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

    public CgRequirementVo getRequirement() {
        return requirement;
    }

    public void setRequirement(CgRequirementVo requirement) {
        this.requirement = requirement;
    }

    public LocalDateTime getReqCreatedTime() {
        return reqCreatedTime;
    }

    public void setReqCreatedTime(LocalDateTime reqCreatedTime) {
        this.reqCreatedTime = reqCreatedTime;
    }

    public BigDecimal getApprovalAmount() {
        return approvalAmount;
    }

    public void setApprovalAmount(BigDecimal approvalAmount) {
        this.approvalAmount = approvalAmount;
    }

    public Integer getContractValidity() {
        return contractValidity;
    }

    public void setContractValidity(Integer contractValidity) {
        this.contractValidity = contractValidity;
    }

    public String getSingleReason() {
        return singleReason;
    }

    public void setSingleReason(String singleReason) {
        this.singleReason = singleReason;
    }

    public String getSupRequirements() {
        return supRequirements;
    }

    public void setSupRequirements(String supRequirements) {
        this.supRequirements = supRequirements;
    }

    public UserVo getContractCreatedUser() {
        return contractCreatedUser;
    }

    public void setContractCreatedUser(UserVo contractCreatedUser) {
        this.contractCreatedUser = contractCreatedUser;
    }

    public List<DepartmentVo> getUseDepartments() {
        return useDepartments;
    }

    public void setUseDepartments(List<DepartmentVo> useDepartments) {
        this.useDepartments = useDepartments;
    }

    public List<CgProjectItemVo> getProjectItems() {
        return projectItems;
    }

    public void setProjectItems(List<CgProjectItemVo> projectItems) {
        this.projectItems = projectItems;
    }

    public List<CgProjectSupVo> getProjectSups() {
        return projectSups;
    }

    public void setProjectSups(List<CgProjectSupVo> projectSups) {
        this.projectSups = projectSups;
    }

    public List<CgAttVo> getProjectAtts() {
        return projectAtts;
    }

    public void setProjectAtts(List<CgAttVo> projectAtts) {
        this.projectAtts = projectAtts;
    }

    public List<CgProjectOaDatumDto> getProjectOaData() {
        return projectOaData;
    }

    public void setProjectOaData(List<CgProjectOaDatumDto> projectOaData) {
        this.projectOaData = projectOaData;
    }

    public List<CgProjectEvalDto> getProjectEvals() {
        return projectEvals;
    }

    public void setProjectEvals(List<CgProjectEvalDto> projectEvals) {
        this.projectEvals = projectEvals;
    }

//    public List<CgProjectDrawExpertRuleVo> getProjectDrawExpertRules() {
//        return projectDrawExpertRules;
//    }
//
//    public void setProjectDrawExpertRules(List<CgProjectDrawExpertRuleVo> projectDrawExpertRules) {
//        this.projectDrawExpertRules = projectDrawExpertRules;
//    }

    public CgPurchasePlanVo getPurchasePlan() {
        return purchasePlan;
    }

    public void setPurchasePlan(CgPurchasePlanVo purchasePlan) {
        this.purchasePlan = purchasePlan;
    }

    public List<CgProjectRelationshipVo> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<CgProjectRelationshipVo> relationships) {
        this.relationships = relationships;
    }

}
