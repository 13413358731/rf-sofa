package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.flow.FlowInfoVo;
import com.realfinance.sofa.cg.model.flow.FlowableVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "采购方案对象")
public class CgContractManageVo extends BaseVo implements FlowableVo,IdentityObject<Integer> {

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
    private Integer validTime;

    /**
     *项目
     */
    private CgProjectVo project;

    private Integer fileStatus;

    /**
     * 是否发送合同预警待办 0 否 1 是
     */
    private Integer expireStatus;

    @Schema(description = "合同管理附件表")
    protected List<CgContractAttachmentVo> contractAttachments;

    private String status;

    protected FlowInfoVo flowInfo;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
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

    public Integer getValidTime() {
        return validTime;
    }

    public void setValidTime(Integer validTime) {
        this.validTime = validTime;
    }

    public CgProjectVo getProject() {
        return project;
    }

    public void setProject(CgProjectVo project) {
        this.project = project;
    }

    public List<CgContractAttachmentVo> getContractAttachments() {
        return contractAttachments;
    }

    public void setContractAttachments(List<CgContractAttachmentVo> contractAttachments) {
        this.contractAttachments = contractAttachments;
    }

    public Integer getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(Integer fileStatus) {
        this.fileStatus = fileStatus;
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

    public Integer getExpireStatus() {
        return expireStatus;
    }

    public void setExpireStatus(Integer expireStatus) {
        this.expireStatus = expireStatus;
    }
}
