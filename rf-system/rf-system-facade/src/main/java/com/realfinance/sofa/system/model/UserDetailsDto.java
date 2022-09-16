package com.realfinance.sofa.system.model;

import java.io.Serializable;
import java.util.Set;

public class UserDetailsDto extends UserDto implements Serializable {

    protected Set<RoleSmallDto> roles;
    protected Set<RoleGroupSmallDto> roleGroups;

    public Set<RoleSmallDto> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleSmallDto> roles) {
        this.roles = roles;
    }

    public Set<RoleGroupSmallDto> getRoleGroups() {
        return roleGroups;
    }

    public void setRoleGroups(Set<RoleGroupSmallDto> roleGroups) {
        this.roleGroups = roleGroups;
    }


}
