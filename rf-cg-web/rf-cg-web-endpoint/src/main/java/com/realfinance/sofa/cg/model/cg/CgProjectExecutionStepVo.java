package com.realfinance.sofa.cg.model.cg;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "采购方案执行-环节对象")
public class CgProjectExecutionStepVo {
    private Integer id;

    @Schema(description = "环节类型")
    private String type;

    @Schema(description = "环节开始时间")
    private LocalDateTime startTime;

    @Schema(description = "环节结束时间")
    private LocalDateTime endTime;

    public String getExecutionStepStatus() {
        if (startTime == null && endTime == null) {
            return "未执行";
        }
        if (endTime == null) {
            return "执行中";
        }
        return "已完成";
    }

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
