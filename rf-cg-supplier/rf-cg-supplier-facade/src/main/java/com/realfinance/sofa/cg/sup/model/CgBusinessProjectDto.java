package com.realfinance.sofa.cg.sup.model;

import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;

public class CgBusinessProjectDto extends BaseDto {
    public CgBusinessProjectDto(Integer id) {
        this.id = id;
    }

    /**
     * 主键
     */
    protected Integer id;

    /**
     * 项目编号
     */
    private String projectNo;

    /**
     * 项目名称
     */
    protected String projectName;

    /**
     * 项目状态
     */
    protected String projectStatus;

    @Version
    private Long v;

    /**
     * 法人ID
     */
    private String tenantId;


    /**
     * 方案ID
     */
    protected String projectId;

    /**
     * 项目完成时间
     */
    protected LocalDateTime projectCompletionTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public LocalDateTime getProjectCompletionTime() {
        return projectCompletionTime;
    }

    public void setProjectCompletionTime(LocalDateTime projectCompletionTime) {
        this.projectCompletionTime = projectCompletionTime;
    }
}
