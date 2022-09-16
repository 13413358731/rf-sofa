package com.realfinance.sofa.system.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class RoleGroupSmallDto implements Serializable {
    @NotNull
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleGroupSmallDto that = (RoleGroupSmallDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "RoleGroupSmallDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
