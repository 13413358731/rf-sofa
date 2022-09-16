package com.realfinance.sofa.cg.core.domain.purresult;

import com.realfinance.sofa.cg.core.domain.*;
import com.realfinance.sofa.cg.core.domain.req.RequirementRelationship;
import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 采购结果
 */
@Entity
@Table(name = "CG_CORE_PUR_RESULT")
public class PurchaseResult extends BaseEntity implements IEntity<Integer> {

    @Version
    private Long v;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 法人ID
     */
    @Column()
    private String tenantId;

    /**
     * 部门ID
     */
    @Column()
    private Integer departmentId;

    /**
     * 采购方案id
     */
    @Column()
    private Integer projectId;

    /**
     * 采购方案执行id
     */
    @Column()
    private Integer projectexeId;

    /**
     * 项目编号
     */
    @Column()
    private String projectNo;

    /**
     * 项目名称
     */
    @Column()
    private String name;

    /**
     * 选取的采购方式
     */
    @Column()
    @Enumerated
    private PurchaseMode purMode;

    /**
     * 选取的评标方法
     */
    @Column()
    @Enumerated
    private EvalMethod evalMethod;

    /**
     * 采购种类
     */
    @Column(nullable = false)
    @Enumerated
    private PurchaseType purType;

    /**
     * 中标金额
     */
    @Column()
    private BigDecimal biddingAmount;

    /**
     * 节省金额
     */
    @Column()
    private BigDecimal saveAmount;

    /**
     * 预算降幅 %
     */
    @Column()
    private Integer saveRatio;

    /**
     * 有关情况说明
     */
    @Column(length = 1000)
    private String note;

    /**
     * 审批通过时间
     */
    @Column
    private LocalDateTime passTime;

    /**
     * 是否生效
     */
    @Column(nullable = false)
    private Boolean valid;

//    /**
//     * 采购结果状态
//     */
//    @Enumerated
//    @Column(nullable = false)
//    private FlowStatus purResultStatus;

    /**
     * 处理状态
     */
    @Enumerated
    @Column(nullable = false)
    private FlowStatus status;

//    @OneToOne(cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "proj_id", updatable = false, unique = true)
//    private Project project;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pur_result_id")
    private List<PurResultExpert> purResultExperts;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pur_result_id")
    private List<PurResultAtt> purResultAtts;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pur_result_id")
    private List<PurResultSupplier> purResultSups;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pur_result_id")
    private List<PurResultConfirmDet> purResultConfirmDets;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pur_result_id")
    private List<PurResultRelationship> relationships;

//    public PurchaseResult() {
//        projectExecutionSteps = new ArrayList<>();
//        projectExecutionSups = new ArrayList<>();
//        projectExecutionAtts = new ArrayList<>();
//    }

    public PurchaseResult() {
        purResultExperts = new ArrayList<>();
        purResultAtts = new ArrayList<>();
        purResultSups = new ArrayList<>();
        purResultConfirmDets = new ArrayList<>();
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

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
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

    public PurchaseMode getPurMode() {
        return purMode;
    }

    public void setPurMode(PurchaseMode purMode) {
        this.purMode = purMode;
    }

    public EvalMethod getEvalMethod() {
        return evalMethod;
    }

    public void setEvalMethod(EvalMethod evalMethod) {
        this.evalMethod = evalMethod;
    }

    public PurchaseType getPurType() {
        return purType;
    }

    public void setPurType(PurchaseType purType) {
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

    public FlowStatus getStatus() {
        return status;
    }

    public void setStatus(FlowStatus status) {
        this.status = status;
    }

    public List<PurResultExpert> getPurResultExperts() {
        return purResultExperts;
    }

    public void setPurResultExperts(List<PurResultExpert> purResultExperts) {
        this.purResultExperts = purResultExperts;
    }

    public List<PurResultAtt> getPurResultAtts() {
        return purResultAtts;
    }

    public void setPurResultAtts(List<PurResultAtt> purResultAtts) {
        this.purResultAtts = purResultAtts;
    }

    public List<PurResultSupplier> getPurResultSups() {
        return purResultSups;
    }

    public void setPurResultSups(List<PurResultSupplier> purResultSups) {
        this.purResultSups = purResultSups;
    }

    public List<PurResultConfirmDet> getPurResultConfirmDets() {
        return purResultConfirmDets;
    }

    public void setPurResultConfirmDets(List<PurResultConfirmDet> purResultConfirmDets) {
        this.purResultConfirmDets = purResultConfirmDets;
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

    public List<PurResultRelationship> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<PurResultRelationship> relationships) {
        this.relationships = relationships;
    }
}
