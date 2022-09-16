package com.realfinance.sofa.cg.core.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class CgPurchaseResultDetailsDto extends BaseDto {
    public CgPurchaseResultDetailsDto(Integer id) {
        this.id = id;
    }

    private Integer id;

    /**
     * 法人ID
     */
    private String tenantId;

    /**
     * 部门ID
     */
    private Integer departmentId;

    /**
     * 采购方案id
     */
    private Integer projectId;

    /**
     * 采购方案执行id
     */
    private Integer projectexeId;

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
     * 采购种类
     */
    private String purType;

    /**
     * 中标金额
     */
    private BigDecimal biddingAmount;

    /**
     * 节省金额
     */
    private BigDecimal saveAmount;

    /**
     * 预算降幅 %
     */
    private Integer saveRatio;

    /**
     * 有关情况说明
     */
    private String note;

    /**
     * 审批通过时间
     */
    private LocalDateTime passTime;

    /**
     * 是否生效
     */
    private Boolean valid;

    /**
     * 采购结果状态
     */
//    private String purResultStatus;
    /**
     * 采购结果状态
     */
    private String status;

    private List<CgPurResultConfirmDetDto> purResultConfirmDets;

    private List<CgPurResultAttDto> purResultAtts;

    private List<CgPurResultExpertDto> purResultExperts;

    private List<CgPurResultSupDto> purResultSups;

    private List<CgPurResultResultRelationshipDto> relationships;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public List<CgPurResultConfirmDetDto> getPurResultConfirmDets() {
        return purResultConfirmDets;
    }

    public void setPurResultConfirmDets(List<CgPurResultConfirmDetDto> purResultConfirmDets) {
        this.purResultConfirmDets = purResultConfirmDets;
    }

    public List<CgPurResultAttDto> getPurResultAtts() {
        return purResultAtts;
    }

    public void setPurResultAtts(List<CgPurResultAttDto> purResultAtts) {
        this.purResultAtts = purResultAtts;
    }

    public List<CgPurResultExpertDto> getPurResultExperts() {
        return purResultExperts;
    }

    public void setPurResultExperts(List<CgPurResultExpertDto> purResultExperts) {
        this.purResultExperts = purResultExperts;
    }

    public List<CgPurResultSupDto> getPurResultSups() {
        return purResultSups;
    }

    public void setPurResultSups(List<CgPurResultSupDto> purResultSups) {
        this.purResultSups = purResultSups;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public LocalDateTime getPassTime() {
        return passTime;
    }

    public void setPassTime(LocalDateTime passTime) {
        this.passTime = passTime;
    }

    public List<CgPurResultResultRelationshipDto> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<CgPurResultResultRelationshipDto> relationships) {
        this.relationships = relationships;
    }
}
