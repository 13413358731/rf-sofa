package com.realfinance.sofa.cg.core.model;

public class CgPurResultExpertDto {

    private Integer id;

    private String name;

    private Integer expertDepartment;

    private Boolean expertSource;

    private String note;

    private String drawWay;

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

    public Integer getExpertDepartment() {
        return expertDepartment;
    }

    public void setExpertDepartment(Integer expertDepartment) {
        this.expertDepartment = expertDepartment;
    }

    public Boolean getExpertSource() {
        return expertSource;
    }

    public void setExpertSource(Boolean expertSource) {
        this.expertSource = expertSource;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDrawWay() {
        return drawWay;
    }

    public void setDrawWay(String drawWay) {
        this.drawWay = drawWay;
    }
}
