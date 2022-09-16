package com.realfinance.sofa.cg.model.system;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "角色组对象")
public class RoleGroupVo extends BaseVo {
    @Schema(description = "ID")
    protected Integer id;
    @Schema(description = "法人")
    protected TenantVo tenant;
    @Schema(description = "角色组编码")
    protected String code;
    @Schema(description = "角色组名称")
    protected String name;
    @Schema(description = "备注")
    protected String note;
    @Schema(description = "是否启用")
    protected Boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TenantVo getTenant() {
        return tenant;
    }

    public void setTenant(TenantVo tenant) {
        this.tenant = tenant;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
