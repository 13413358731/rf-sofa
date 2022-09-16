package com.realfinance.sofa.cg.core.model;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class CgContractManageDetailsSaveDto {

    private Integer id;

    /**
     * 法人ID
     */
    private String tenantId;

    /**
     * 制单人部门
     */
    private Integer departmentId;

    /**
     *合同名称
     */
    private String contractName;

    /**
     *编号
     */
    private Integer contractSerialNumber;

    /**
     *金额
     */
    private BigDecimal totalAmount;

    /**
     *合同生效日
     */
    private LocalDate startDate;

    /**
     *合同到期日
     */
    private LocalDate expireDate;

    /**
     *甲方名称
     */
    private String partyAName;

    /**
     *乙方名称
     */
    private String partyBName;

    /**
     *乙方联系人
     */
    private String partyBContacts;

    /**
     *备注
     */
    private String comment;

    /**
     *期限
     */
    private String validTime;

    /**
     *项目
     */
    private Integer project;

    /**
     * 附件
     */
    @NotEmpty
    private List<CgContractAttachmentDto> contractAttachments;


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

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public Integer getContractSerialNumber() {
        return contractSerialNumber;
    }

    public void setContractSerialNumber(Integer contractSerialNumber) {
        this.contractSerialNumber = contractSerialNumber;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(LocalDate expireDate) {
        this.expireDate = expireDate;
    }

    public String getPartyAName() {
        return partyAName;
    }

    public void setPartyAName(String partyAName) {
        this.partyAName = partyAName;
    }

    public String getPartyBName() {
        return partyBName;
    }

    public void setPartyBName(String partyBName) {
        this.partyBName = partyBName;
    }

    public String getPartyBContacts() {
        return partyBContacts;
    }

    public void setPartyBContacts(String partyBContacts) {
        this.partyBContacts = partyBContacts;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getValidTime() {
        return validTime;
    }

    public void setValidTime(String validTime) {
        this.validTime = validTime;
    }

    public Integer getProject() {
        return project;
    }

    public void setProject(Integer project) {
        this.project = project;
    }

    public List<CgContractAttachmentDto> getContractAttachments() {
        return contractAttachments;
    }

    public void setContractAttachments(List<CgContractAttachmentDto> contractAttachments) {
        this.contractAttachments = contractAttachments;
    }
}
