package com.realfinance.sofa.cg.core.model;

public class CgDrawExpertListQueryCriteria {

    /**
     * id查询
     */
    private String id;

    /**
     * 专家名称模糊
     */
    private String nameLike;

    /**
     * 专家用户名
     */
    private String username;

    /**
     * 处理状态
     */
    private String status;

    /**
     * 处理状态
     */
    private Integer IsAttend;

    /**
     * 专家抽取
     */
    private Integer drawExpertId;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIsAttend() {
        return IsAttend;
    }

    public void setIsAttend(Integer isAttend) {
        IsAttend = isAttend;
    }

    public Integer getDrawExpertId() {
        return drawExpertId;
    }

    public void setDrawExpertId(Integer drawExpertId) {
        this.drawExpertId = drawExpertId;
    }
}
