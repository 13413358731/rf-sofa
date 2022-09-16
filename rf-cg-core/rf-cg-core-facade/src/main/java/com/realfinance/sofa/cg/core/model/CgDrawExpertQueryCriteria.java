package com.realfinance.sofa.cg.core.model;

public class CgDrawExpertQueryCriteria {

    /**
     * id查询
     */
    private String id;

    /**
     * 抽取事由模糊查询
     */
    private String eventLike;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEventLike() {
        return eventLike;
    }

    public void setEventLike(String eventLike) {
        this.eventLike = eventLike;
    }
}
