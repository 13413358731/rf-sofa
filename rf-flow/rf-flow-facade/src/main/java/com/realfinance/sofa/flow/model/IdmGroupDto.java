package com.realfinance.sofa.flow.model;

import java.io.Serializable;

public class IdmGroupDto implements Serializable {
    private String id;
    private String name;
    private String type;

    public IdmGroupDto() {
    }

    public IdmGroupDto(String id) {
        this.id = id;
    }

    public IdmGroupDto(String id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "IdmGroupDto{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
