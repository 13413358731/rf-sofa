package com.realfinance.sofa.cg.model.flow;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.cg.model.system.DepartmentVo;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "流程业务模型对象")
public class BizModelVo extends BaseVo {
    @Schema(description = "ID")
    private Integer id;
    @Schema(description = "模型ID")
    private String modelId;
    @Schema(description = "部署ID")
    private String deploymentId;
    @Schema(description = "流程业务")
    private BizVo biz;
    @Schema(defaultValue = "流程实例名称")
    private String processInstName;
    @Schema(description = "部门")
    private DepartmentVo department;
    @Schema(description = "是否启用")
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

    public BizVo getBiz() {
        return biz;
    }

    public void setBiz(BizVo biz) {
        this.biz = biz;
    }

    public String getProcessInstName() {
        return processInstName;
    }

    public void setProcessInstName(String processInstName) {
        this.processInstName = processInstName;
    }

    public DepartmentVo getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentVo department) {
        this.department = department;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
