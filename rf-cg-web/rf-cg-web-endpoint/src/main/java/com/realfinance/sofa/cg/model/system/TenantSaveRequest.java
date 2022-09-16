package com.realfinance.sofa.cg.model.system;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Schema(description = "保存法人请求对象")
public class TenantSaveRequest {
    @NotNull
    @Schema(description = "编码（ID）")
    private String id;

    @NotNull
    @Schema(description = "法人名称")
    private String name;
    @Schema(description = "备注")
    private String note;

    @NotNull
    @Schema(description = "有效起始时间")
    private LocalDateTime startTime;

    @NotNull
    @Schema(description = "有效结束时间")
    private LocalDateTime endTime;

    @NotNull
    @Schema(description = "是否启用")
    private Boolean enabled;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
}
