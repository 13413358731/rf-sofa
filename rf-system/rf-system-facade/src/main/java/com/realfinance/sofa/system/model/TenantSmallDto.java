package com.realfinance.sofa.system.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class TenantSmallDto implements Serializable {

    @NotNull
    private String id;

    private String name;

    public TenantSmallDto() {
    }

    public TenantSmallDto(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TenantSmallDto that = (TenantSmallDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TenantSmallDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
