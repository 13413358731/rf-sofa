package com.realfinance.sofa.system.model;

import java.io.Serializable;
import java.util.Set;

public class UserAuthInfoDto implements Serializable {

    private TenantSmallDto tenant;

    private UserDto user;

    private String password;

    private Set<String> roleCodes;

    private Set<String> menuCodes;

    public TenantSmallDto getTenant() {
        return tenant;
    }

    public void setTenant(TenantSmallDto tenant) {
        this.tenant = tenant;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(Set<String> roleCodes) {
        this.roleCodes = roleCodes;
    }

    public Set<String> getMenuCodes() {
        return menuCodes;
    }

    public void setMenuCodes(Set<String> menuCodes) {
        this.menuCodes = menuCodes;
    }

    @Override
    public String toString() {
        return "UserAuthInfoDto{" +
                "tenant=" + tenant +
                ", user=" + user +
                ", password='" + password + '\'' +
                ", roleCodes=" + roleCodes +
                ", menuCodes=" + menuCodes +
                '}';
    }
}
