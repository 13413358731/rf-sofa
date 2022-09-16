package com.realfinance.sofa.cg.model.flow;

import com.realfinance.sofa.common.model.ReferenceObject;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;

@Schema(description = "保存流程业务模型请求对象")
public class BizModelSaveRequest {
    @Schema(description = "ID")
    private Integer id;
    @NotNull
    @Schema(description = "流程业务")
    private ReferenceObject<Integer> biz;
//    @NotNull
    @Schema(description = "实例名称")
    private String processInstName;
    @NotNull
    @Schema(description = "部门")
    private ReferenceObject<Integer> department;
    @NotNull
    @Schema(description = "是否可用")
    private Boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ReferenceObject<Integer> getBiz() {
        return biz;
    }

    public void setBiz(ReferenceObject<Integer> biz) {
        this.biz = biz;
    }

    public String getProcessInstName() {
        return processInstName;
    }

    public void setProcessInstName(String processInstName) {
        this.processInstName = processInstName;
    }

    public ReferenceObject<Integer> getDepartment() {
        return department;
    }

    public void setDepartment(ReferenceObject<Integer> department) {
        this.department = department;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
