package com.realfinance.sofa.cg.core.model;

import java.io.Serializable;

public class CgExpertSaveDto implements Serializable {

    protected Integer id;

    protected String expertCode;

    protected Boolean isInternalExpert;

    protected Integer memberCode;

    protected String name;

    protected Integer expertDepartment;

    protected String phone;

    protected String email;

    protected Boolean expertSource;

    protected String expertType;

    protected Boolean valid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExpertCode() {
        return expertCode;
    }

    public void setExpertCode(String expertCode) {
        this.expertCode = expertCode;
    }

    public Boolean getInternalExpert() {
        return isInternalExpert;
    }

    public void setInternalExpert(Boolean internalExpert) {
        isInternalExpert = internalExpert;
    }

    public Integer getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(Integer memberCode) {
        this.memberCode = memberCode;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getExpertSource() {
        return expertSource;
    }

    public void setExpertSource(Boolean expertSource) {
        this.expertSource = expertSource;
    }

    public String getExpertType() {
        return expertType;
    }

    public void setExpertType(String expertType) {
        this.expertType = expertType;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }
}
