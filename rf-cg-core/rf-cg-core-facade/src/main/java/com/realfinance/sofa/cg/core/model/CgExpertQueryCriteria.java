package com.realfinance.sofa.cg.core.model;

public class CgExpertQueryCriteria {

    /**
     * id查询
     */
    private String id;

    /**
     * 方案名称模糊
     */
    private String nameLike;

    /**
     * 专家部门
     */
    private Integer expertDeptId;

    /**
     * 部门Id
     */
//    private Integer memberCode;

    /**
     * 部门Id
     */
    private Integer departmentId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public Integer getExpertDeptId() {
        return expertDeptId;
    }

    public void setExpertDeptId(Integer expertDeptId) {
        this.expertDeptId = expertDeptId;
    }

//    public Integer getMemberCode() {
//        return memberCode;
//    }
//
//    public void setMemberCode(Integer memberCode) {
//        this.memberCode = memberCode;
//    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }
}
