package com.realfinance.sofa.cg.sup.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 商业项目
 */
@Entity
@Table(name = "CG_SUP_BIZ_PROJ", indexes = {
        @Index(name = "idx_pid_tenant",columnList = "projectId,tenantId", unique = true)
})
public class BusinessProject extends BaseEntity implements IEntity<Integer> {

    @Version
    private Long v;

    /**
     * 法人ID
     */
    @Column(nullable = false)
    private String tenantId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 方案ID
     */
    @Column(nullable = false)
    protected String projectId;

    /**
     * 项目编号
     */
    @Column(nullable = false)
    protected String projectNo;

    /**
     * 项目名称
     */
    @Column(nullable = false)
    protected String projectName;

    /**
     * 项目状态
     */
    @Column(nullable = false)
    protected String projectStatus;

    /**
     * 项目完成时间
     */
    @Column
    protected LocalDateTime projectCompletionTime;

    /**
     * 商务应答
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "biz_proj_id")
    protected List<BusinessReply> replies;

    public BusinessProject() {
        replies = new ArrayList<>();
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(String projectStatus) {
        this.projectStatus = projectStatus;
    }

    public LocalDateTime getProjectCompletionTime() {
        return projectCompletionTime;
    }

    public void setProjectCompletionTime(LocalDateTime projectCompletionTime) {
        this.projectCompletionTime = projectCompletionTime;
    }

    public List<BusinessReply> getReplies() {
        return replies;
    }

    public void setReplies(List<BusinessReply> replies) {
        this.replies = replies;
    }
}
