package com.realfinance.sofa.cg.core.domain.exec.bid;

import com.realfinance.sofa.cg.core.domain.BaseEntity;
import com.realfinance.sofa.cg.core.domain.FlowStatus;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecution;
import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 标书
 */
@MappedSuperclass
public abstract class BaseBiddingDocument extends BaseEntity implements IEntity<Integer> {

    @Version
    protected Long v;

    /**
     * 法人ID
     */
    @Column(nullable = false)
    protected String tenantId;

    /**
     * 部门ID
     */
    @Column(nullable = false)
    protected Integer departmentId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(nullable = false)
    protected String name;

    /**
     * 生成文档名称
     */
    @Column(nullable = false)
    protected String docName;

    /**
     * 投标截止时间
     */
    @Column(nullable = false)
    protected LocalDateTime bidEndTime;

    /**
     * 唱标开始时间
     */
    @Column(nullable = false)
    protected LocalDateTime openBidStartTime;

    /**
     * 唱标截止时间
     */
    @Column(nullable = false)
    protected LocalDateTime openBidEndTime;

    /**
     * 需要报价
     */
    @Column(nullable = false)
    protected Boolean needQuote;

    /**
     * 内容
     */
    @Column(nullable = false, length = 2000)
    protected String content;

    /**
     * 监督人员开启价格的时间
     */
    @Column
    protected LocalDateTime preOpenQuoteTime;

    /**
     * 开启价格的监督人员ID
     */
    @Column
    protected Integer preOpenQuoteUserId;

    /**
     * 开启价格时间
     */
    @Column
    protected LocalDateTime openQuoteTime;

    /**
     * 监督人员开启商务的时间
     */
    @Column
    protected LocalDateTime preOpenBizTime;

    /**
     * 开启商务的监督人员ID
     */
    @Column
    protected Integer preOpenBizUserId;

    /**
     * 开启商务时间
     */
    @Column
    protected LocalDateTime openBizTime;

    @Column(nullable = false)
    protected FlowStatus status;

    @Column
    protected LocalDateTime passTime;

    @OneToOne
    @JoinColumn(name = "proj_exec_id", unique = true, nullable = false, updatable = false)
    protected ProjectExecution projectExecution;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public LocalDateTime getBidEndTime() {
        return bidEndTime;
    }

    public void setBidEndTime(LocalDateTime bidEndTime) {
        this.bidEndTime = bidEndTime;
    }

    public LocalDateTime getOpenBidStartTime() {
        return openBidStartTime;
    }

    public void setOpenBidStartTime(LocalDateTime openBidStartTime) {
        this.openBidStartTime = openBidStartTime;
    }

    public LocalDateTime getOpenBidEndTime() {
        return openBidEndTime;
    }

    public void setOpenBidEndTime(LocalDateTime openBidEndTime) {
        this.openBidEndTime = openBidEndTime;
    }

    public Boolean getNeedQuote() {
        return needQuote;
    }

    public void setNeedQuote(Boolean needQuote) {
        this.needQuote = needQuote;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String contentTemp) {
        this.content = contentTemp;
    }

    public LocalDateTime getPreOpenQuoteTime() {
        return preOpenQuoteTime;
    }

    public void setPreOpenQuoteTime(LocalDateTime preOpenQuoteTime) {
        this.preOpenQuoteTime = preOpenQuoteTime;
    }

    public Integer getPreOpenQuoteUserId() {
        return preOpenQuoteUserId;
    }

    public void setPreOpenQuoteUserId(Integer preOpenQuoteUserId) {
        this.preOpenQuoteUserId = preOpenQuoteUserId;
    }

    public LocalDateTime getOpenQuoteTime() {
        return openQuoteTime;
    }

    public void setOpenQuoteTime(LocalDateTime openQuoteTime) {
        this.openQuoteTime = openQuoteTime;
    }

    public LocalDateTime getPreOpenBizTime() {
        return preOpenBizTime;
    }

    public void setPreOpenBizTime(LocalDateTime preOpenBizTime) {
        this.preOpenBizTime = preOpenBizTime;
    }

    public Integer getPreOpenBizUserId() {
        return preOpenBizUserId;
    }

    public void setPreOpenBizUserId(Integer preOpenBizUserId) {
        this.preOpenBizUserId = preOpenBizUserId;
    }

    public LocalDateTime getOpenBizTime() {
        return openBizTime;
    }

    public void setOpenBizTime(LocalDateTime openBizTime) {
        this.openBizTime = openBizTime;
    }

    public FlowStatus getStatus() {
        return status;
    }

    public void setStatus(FlowStatus status) {
        this.status = status;
    }

    public LocalDateTime getPassTime() {
        return passTime;
    }

    public void setPassTime(LocalDateTime passTime) {
        this.passTime = passTime;
    }

    public ProjectExecution getProjectExecution() {
        return projectExecution;
    }

    public void setProjectExecution(ProjectExecution projectExecution) {
        this.projectExecution = projectExecution;
    }
}
