package com.realfinance.sofa.cg.model.system;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.BaseVo;
import com.realfinance.sofa.common.model.IdentityObject;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "法人对象")
public class TenantVo extends BaseVo implements IdentityObject<String> {
    @Schema(description = "ID")
    protected String id;
    @Schema(description = "法人名称")
    protected String name;
    @Schema(description = "备注")
    protected String note;
    @Schema(description = "有效起始时间")
    protected LocalDateTime startTime;
    @Schema(description = "有效结束时间")
    protected LocalDateTime endTime;
    @Schema(description = "是否启用")
    protected Boolean enabled;

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
