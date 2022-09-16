package com.realfinance.sofa.cg.core.domain.contract;

import com.realfinance.sofa.cg.core.domain.BaseEntity;
import com.realfinance.sofa.cg.core.domain.ContractAttachment;
import com.realfinance.sofa.cg.core.domain.proj.Project;
import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 合同管理
 */
@Entity
@Table(name = "CG_CORE_ContractManage")
public class ContractManage extends BaseEntity implements IEntity<Integer> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 法人ID
     */
    @Column(nullable = false)
    private String tenantId;

    /**
     * 制单人部门
     */
    @Column(nullable = false)
    private Integer departmentId;

    /**
     *合同名称
     */
    @Column(nullable = false)
    private String contractName;

    /**
     *编号
     */
    @Column()
    private Integer contractSerialNumber;

    /**
     *金额
     */
    @Column()
    private BigDecimal totalAmount;

    /**
     *合同生效日
     */
    @Column()
    private LocalDate startDate;

    /**
     *合同到期日
     */
    @Column()
    private LocalDate expireDate;

    /**
     *甲方名称
     */
    @Column()
    private String partyAName;

    /**
     *乙方名称
     */
    @Column()
    private String partyBName;

    /**
     *乙方联系人
     */
    @Column()
    private String partyBContacts;

    /**
     *备注
     */
    @Column()
    private String comment;

    /**
     *期限
     */
    @Column()
    private Integer validTime;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "contract_manage_id")
    private List<ContractAttachment> contractAttachments;

    /**
     * 归档状态 0未归档 1归档
     */
    private Integer fileStatus;

    /**
     * 是否发送合同预警待办 0 否 1 是
     */
    private Integer expireStatus;

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

    public Integer getValidTime() {
        return validTime;
    }

    public void setValidTime(Integer validTime) {
        this.validTime = validTime;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<ContractAttachment> getContractAttachments() {
        return contractAttachments;
    }

    public void setContractAttachments(List<ContractAttachment> contractAttachments) {
        this.contractAttachments = contractAttachments;
    }

    public Integer getFileStatus() {
        return fileStatus;
    }

    public void setFileStatus(Integer fileStatus) {
        this.fileStatus = fileStatus;
    }

    public Integer getExpireStatus() {
        return expireStatus;
    }

    public void setExpireStatus(Integer expireStatus) {
        this.expireStatus = expireStatus;
    }
}
