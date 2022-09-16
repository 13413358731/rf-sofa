package com.realfinance.sofa.flow.model;

import java.io.Serializable;

public class BizModelDto extends BaseDto implements Serializable {

    private Integer id;

    /**
     * 模型ID
     */
    private String modelId;

    /**
     * 部署ID
     */
    private String deploymentId;

    /**
     * 业务ID
     */
    private BizDto biz;

    private String processInstName;

    /**
     * 部门ID
     */
    private Integer departmentId;

    private Boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getModelId() {
        return modelId;
    }

    public void setModelId(String modelId) {
        this.modelId = modelId;
    }

    public String getDeploymentId() {
        return deploymentId;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    public BizDto getBiz() {
        return biz;
    }

    public void setBiz(BizDto biz) {
        this.biz = biz;
    }

    public String getProcessInstName() {
        return processInstName;
    }

    public void setProcessInstName(String processInstName) {
        this.processInstName = processInstName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "BizModelDto{" +
                "id=" + id +
                ", modelId='" + modelId + '\'' +
                ", deploymentId='" + deploymentId + '\'' +
                ", biz=" + biz +
                ", processInstName='" + processInstName + '\'' +
                ", departmentId=" + departmentId +
                ", enabled=" + enabled +
                ", createdUserId=" + createdUserId +
                ", createdTime=" + createdTime +
                ", modifiedUserId=" + modifiedUserId +
                ", modifiedTime=" + modifiedTime +
                '}';
    }
}
