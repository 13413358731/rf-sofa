package com.realfinance.sofa.cg.core.domain;



import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


/**
 * 采购结果通知
 */
@Entity
@Table(name = "CG_CORE_PURCHASE_RESULT_NOTICE")
public class PurchaseResultNotice extends BaseEntity implements IEntity<Integer> {

    /**
     * 法人ID
     */
    @Column(nullable = false)
    private String tenantId;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 采购方案执行ID
     */
    private  Integer  projectExecutionId;


    /**
     * 采购方案ID
     */
    private Integer projectId;


    /**
     * 项目编号
     */
    @Column(nullable = false)
    private String projectNo;

    /**
     * 项目名称
     */
    @Column(nullable = false)
    private String name;


    /**
     * 项目经办人
     */
    private  Integer  projectProducerId;


    /**
     * 选取的采购方式
     */
    @Column(nullable = false)
    @Enumerated
    private PurchaseMode purMode;


    /**
     * 选取的评标方法
     */
    @Column(nullable = false)
    @Enumerated
    private EvalMethod evalMethod;

    /**
     * 外部结果通知标题
     */
    private String outsideTitle;

    /**
     * 外部结果通知内容
     */
    @Column(length = 1024)
    private String outsideContent;


    /**
     * 内部结果通知标题
     */
    private String insideTitle;

    /**
     * 内部结果通知内容
     */
    @Column(length = 1024)
    private  String insideContent;

    /**
     * 合同制单人
     */
    private Integer contractProducerId;

    /**
     * 审核通过时间
     */
    private LocalDateTime passTime;

    @Column(nullable = false)
    private FlowStatus status;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "resultnotice_id")
    private List<PurchaseResultNoticeSup> resultNoticeSups;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "resultnotice_id")
    private List<PurchaseResultNoticeAttachment>  attachments;

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectExecutionId() {
        return projectExecutionId;
    }

    public void setProjectExecutionId(Integer projectExecutionId) {
        this.projectExecutionId = projectExecutionId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
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

    public Integer getProjectProducerId() {
        return projectProducerId;
    }

    public void setProjectProducerId(Integer projectProducerId) {
        this.projectProducerId = projectProducerId;
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

    public String getOutsideTitle() {
        return outsideTitle;
    }

    public void setOutsideTitle(String outsideTitle) {
        this.outsideTitle = outsideTitle;
    }

    public String getOutsideContent() {
        return outsideContent;
    }

    public void setOutsideContent(String outsideContent) {
        this.outsideContent = outsideContent;
    }

    public String getInsideTitle() {
        return insideTitle;
    }

    public void setInsideTitle(String insideTitle) {
        this.insideTitle = insideTitle;
    }

    public String getInsideContent() {
        return insideContent;
    }

    public void setInsideContent(String insideContent) {
        this.insideContent = insideContent;
    }

    public Integer getContractProducerId() {
        return contractProducerId;
    }

    public void setContractProducerId(Integer contractProducerId) {
        this.contractProducerId = contractProducerId;
    }

    public FlowStatus getStatus() {
        return status;
    }

    public void setStatus(FlowStatus status) {
        this.status = status;
    }

    public List<PurchaseResultNoticeSup> getResultNoticeSups() {
        return resultNoticeSups;
    }

    public void setResultNoticeSups(List<PurchaseResultNoticeSup> resultNoticeSups) {
        this.resultNoticeSups = resultNoticeSups;
    }

    public List<PurchaseResultNoticeAttachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<PurchaseResultNoticeAttachment> attachments) {
        this.attachments = attachments;
    }


    public LocalDateTime getPassTime() {
        return passTime;
    }

    public void setPassTime(LocalDateTime passTime) {
        this.passTime = passTime;
    }
}
