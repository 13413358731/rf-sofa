package com.realfinance.sofa.system.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class DepartmentSmallDto implements Serializable {
    @NotNull
    private Integer id;
    private String code;
    private String name;

    public DepartmentSmallDto() {
    }

    public DepartmentSmallDto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepartmentSmallDto that = (DepartmentSmallDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "DepartmentSmallDto{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
