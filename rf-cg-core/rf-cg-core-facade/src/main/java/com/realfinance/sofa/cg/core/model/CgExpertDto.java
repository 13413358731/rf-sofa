package com.realfinance.sofa.cg.core.model;

public class CgExpertDto extends BaseDto {
    public CgExpertDto(Integer id) {
        this.id = id;
    }

    private Integer id;

    /**
     * 专家编码
     */
    private String expertCode;

    /**
     * 是否内部专家
     */
    private Boolean isInternalExpert;

    /**
     * 人员编码
     */
    private Integer memberCode;

    /**
     * 姓名
     */
    private String name;

    /**
     * 工作部门
     */
    protected Integer expertDepartment;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 专家来源
     */
    private Boolean expertSource;

    /**
     * 专家类别
     */
    private String expertType;

    /**
     * 专家状态
     */
    private String expertStatus;

    /**
     * 是否生效
     */
    private Boolean valid;

    private String tenantId;

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

    public String getExpertStatus() {
        return expertStatus;
    }

    public void setExpertStatus(String expertStatus) {
        this.expertStatus = expertStatus;
    }

    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
