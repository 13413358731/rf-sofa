package com.realfinance.sofa.flow.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class BizModelSaveDto implements Serializable {

    private Integer id;

    /**
     * 业务ID
     */
    @NotNull
    private Integer biz;

    /**
     * 流程实例名称
     */
    private String processInstName;

    /**
     * 部门ID
     */
    @NotNull
    private Integer departmentId;

    /**
     * 是否可用
     */
    @NotNull
    private Boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBiz() {
        return biz;
    }

    public void setBiz(Integer biz) {
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
}
