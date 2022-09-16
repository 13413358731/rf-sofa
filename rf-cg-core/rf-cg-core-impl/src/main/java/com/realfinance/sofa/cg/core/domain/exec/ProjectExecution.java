package com.realfinance.sofa.cg.core.domain.exec;

import com.realfinance.sofa.cg.core.domain.BaseEntity;
import com.realfinance.sofa.cg.core.domain.proj.Project;
import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 采购方案执行
 */
@Entity
@Table(name = "CG_CORE_PROJ_EXEC")
public class ProjectExecution extends BaseEntity implements IEntity<Integer> {

    /**
     * 报价类别
     */
    public enum QuoteType {
        /**
         * 总金额式
         */
        TOTAL,
        /**
         * 目录单价式
         */
        UNIT
    }

    @Version
    private Long v;

    /**
     * 法人ID
     */
    @Column(nullable = false)
    private String tenantId;

    /**
     * 部门ID
     */
    @Column(nullable = false)
    private Integer departmentId;

    @Enumerated
    @Column
    private QuoteType quoteType;

    /**
     * 废标原因
     */
    @Column
    private String invalidReason;

    /**
     * 是否废弃
     */
    @Column(nullable = false)
    private Boolean invalid;

    /**
     * 处理意见
     */
    @Column(length = 2000)
    private String reason;

    /**
     * 是否退回到采购申请
     */
    @Column(nullable = false)
    private Boolean returnReq;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "proj_id", updatable = false, unique = true)
    private Project project;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "proj_exec_id")
    private List<ProjectExecutionStep> projectExecutionSteps;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "proj_exec_id")
    private List<ProjectExecutionSup> projectExecutionSups;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "proj_exec_id")
    private List<ProjectExecutionAtt> projectExecutionAtts;

    public ProjectExecution() {
        projectExecutionSteps = new ArrayList<>();
        projectExecutionSups = new ArrayList<>();
        projectExecutionAtts = new ArrayList<>();
    }

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
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

    public QuoteType getQuoteType() {
        return quoteType;
    }

    public void setQuoteType(QuoteType quoteType) {
        this.quoteType = quoteType;
    }

    public String getInvalidReason() {
        return invalidReason;
    }

    public void setInvalidReason(String invalidReason) {
        this.invalidReason = invalidReason;
    }

    public Boolean getInvalid() {
        return invalid;
    }

    public void setInvalid(Boolean invalid) {
        this.invalid = invalid;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public List<ProjectExecutionStep> getProjectExecutionSteps() {
        return projectExecutionSteps;
    }

    public void setProjectExecutionSteps(List<ProjectExecutionStep> projectExecutionSteps) {
        this.projectExecutionSteps = projectExecutionSteps;
    }

    public List<ProjectExecutionSup> getProjectExecutionSups() {
        return projectExecutionSups;
    }

    public void setProjectExecutionSups(List<ProjectExecutionSup> projectExecutionSups) {
        this.projectExecutionSups = projectExecutionSups;
    }

    public List<ProjectExecutionAtt> getProjectExecutionAtts() {
        return projectExecutionAtts;
    }

    public void setProjectExecutionAtts(List<ProjectExecutionAtt> projectExecutionAtts) {
        this.projectExecutionAtts = projectExecutionAtts;
    }

    public Boolean getReturnReq() {
        return returnReq;
    }

    public void setReturnReq(Boolean returnReq) {
        this.returnReq = returnReq;
    }
}
