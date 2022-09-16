package com.realfinance.sofa.cg.model.system;

import com.realfinance.sofa.common.model.ReferenceObject;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "保存角色请求对象")
public class RoleSaveRequest {

    @Schema(description = "ID")
    private Integer id;
    @NotNull
    @Schema(description = "法人")
    private ReferenceObject<String> tenant;
    @NotBlank
    @Schema(description = "角色编码")
    private String code;
    @NotBlank
    @Schema(description = "角色名称")
    private String name;
//    @NotBlank
    @Schema(description = "备注")
    private String note;
    @NotNull
    @Schema(description = "是否启用")
    private Boolean enabled;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ReferenceObject<String> getTenant() {
        return tenant;
    }

    public void setTenant(ReferenceObject<String> tenant) {
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
