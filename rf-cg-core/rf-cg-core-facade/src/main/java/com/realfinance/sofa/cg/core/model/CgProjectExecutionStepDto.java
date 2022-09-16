package com.realfinance.sofa.cg.core.model;

import java.time.LocalDateTime;

public class CgProjectExecutionStepDto {
    private Integer id;

    private String type;

    /**
     * 环节开始时间
     */
    private LocalDateTime startTime;

    /**
     * 环节结束时间
     */
    private LocalDateTime endTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
