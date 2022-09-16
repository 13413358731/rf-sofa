package com.realfinance.sofa.cg.core.domain.exec.release;

import com.realfinance.sofa.cg.core.domain.BaseEntity;
import com.realfinance.sofa.cg.core.domain.FlowStatus;
import com.realfinance.sofa.cg.core.domain.exec.ProjectExecution;
import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 发布
 */
@MappedSuperclass
public abstract class BaseMultipleRelease extends BaseEntity implements IEntity<Integer> {

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

    /**
     * 标题
     */
    @Column(nullable = false)
    protected String name;

    /**
     * 应答截止时间
     */
    @Column(nullable = false)
    protected LocalDateTime replyEndTime;

    /**
     * 唱标开始时间
     */
    @Column()
    protected LocalDateTime openStartTime;

    /**
     * 唱标截止时间
     */
    @Column()
    protected LocalDateTime openEndTime;

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

    @Column(nullable = false)
    protected FlowStatus status;

    @Column
    protected LocalDateTime passTime;

    @ManyToOne
    @JoinColumn(name = "proj_exec_id", updatable = false)
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

    public LocalDateTime getReplyEndTime() {
        return replyEndTime;
    }

    public void setReplyEndTime(LocalDateTime replyEndTime) {
        this.replyEndTime = replyEndTime;
    }

    public LocalDateTime getOpenStartTime() {
        return openStartTime;
    }

    public void setOpenStartTime(LocalDateTime openBidStartTime) {
        this.openStartTime = openBidStartTime;
    }

    public LocalDateTime getOpenEndTime() {
        return openEndTime;
    }

    public void setOpenEndTime(LocalDateTime openBidEndTime) {
        this.openEndTime = openBidEndTime;
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

    public void setContent(String content) {
        this.content = content;
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
