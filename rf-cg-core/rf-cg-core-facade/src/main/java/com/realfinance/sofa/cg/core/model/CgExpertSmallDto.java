package com.realfinance.sofa.cg.core.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class CgExpertSmallDto implements ReferenceDto<Integer>, Serializable {

    @NotNull
    private Integer id;


    private String name;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CgExpertSmallDto that = (CgExpertSmallDto) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
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

}
