package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.system.model.DepartmentSmallDto;
import io.swagger.v3.oas.annotations.media.Schema;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "采购方案执行-供应商对象")
public class CgPurResultExpertVo {
    protected Integer id;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "部门")
    private Integer expertDepartment;

    private DepartmentSmallDto department;

    @Schema(description = "专家来源")
    private Boolean expertSource;

    @Schema(description = "外部专家")
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

    public DepartmentSmallDto getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentSmallDto department) {
        this.department = department;
    }
}
