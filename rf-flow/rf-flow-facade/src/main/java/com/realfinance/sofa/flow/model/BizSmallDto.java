package com.realfinance.sofa.flow.model;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class BizSmallDto implements ReferenceDto<Integer>, Serializable {

    public BizSmallDto() {
    }

    public BizSmallDto(Integer id) {
        this.id = id;
    }

    @NotNull
    private Integer id;

    /**
     * 业务编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

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
    public String toString() {
        return "BizSmallDto{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
