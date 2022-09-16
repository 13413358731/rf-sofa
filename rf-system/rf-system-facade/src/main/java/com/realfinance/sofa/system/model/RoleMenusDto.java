package com.realfinance.sofa.system.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

public class RoleMenusDto implements Serializable {
    @NotNull
    private Integer id; // 角色ID
    private Set<MenuSmallDto> menus; // 角色拥有的菜单

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleMenusDto that = (RoleMenusDto) o;
        return Objects.equals(id, that.id);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<MenuSmallDto> getMenus() {
        return menus;
    }

    public void setMenus(Set<MenuSmallDto> menus) {
        this.menus = menus;
    }

    @Override
    public String toString() {
        return "RoleMenusDto{" +
                "id=" + id +
                ", menus=" + menus +
                '}';
    }
}
