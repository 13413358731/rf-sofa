package com.realfinance.sofa.cg.model.system;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "用户详情对象")
public class UserDetailsVo extends UserVo {
    @Schema(description = "关联的角色")
    protected Set<RoleVo> roles;
    @Schema(description = "关联的角色组")
    protected Set<RoleGroupVo> roleGroups;

    public Set<RoleVo> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleVo> roles) {
        this.roles = roles;
    }

    public Set<RoleGroupVo> getRoleGroups() {
        return roleGroups;
    }

    public void setRoleGroups(Set<RoleGroupVo> roleGroups) {
        this.roleGroups = roleGroups;
    }
}
