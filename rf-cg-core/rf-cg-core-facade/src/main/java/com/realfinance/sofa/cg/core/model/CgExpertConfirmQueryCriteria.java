package com.realfinance.sofa.cg.core.model;

public class CgExpertConfirmQueryCriteria {

    /**
     * id查询
     */
    private Integer id;

    /**
     * 抽取事由模糊查询
     */
    private String eventLike;

    /**
     * 抽取事由模糊查询
     */
    private Integer expertUserId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEventLike() {
        return eventLike;
    }

    public void setEventLike(String eventLike) {
        this.eventLike = eventLike;
    }

    public Integer getExpertUserId() {
        return expertUserId;
    }

    public void setExpertUserId(Integer expertUserId) {
        this.expertUserId = expertUserId;
    }
}

