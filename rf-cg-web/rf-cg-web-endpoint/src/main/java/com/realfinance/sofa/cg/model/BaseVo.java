package com.realfinance.sofa.cg.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.realfinance.sofa.cg.model.system.UserVo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serializable;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseVo implements Serializable {
    
    @JsonIgnoreProperties(value = {"createdUser","createdTime","modifiedUser","modifiedTime"})
    @Schema(description = "创建此数据的用户", accessMode = Schema.AccessMode.READ_ONLY)
    protected UserVo createdUser;
    @Schema(description = "创建此数据的时间", accessMode = Schema.AccessMode.READ_ONLY)
    protected LocalDateTime createdTime;
    @JsonIgnoreProperties(value = {"createdUser","createdTime","modifiedUser","modifiedTime"})
    @Schema(description = "最近一次修改此数据的用户", accessMode = Schema.AccessMode.READ_ONLY)
    protected UserVo modifiedUser;
    @Schema(description = "最近一次修改此数据的时间", accessMode = Schema.AccessMode.READ_ONLY)
    protected LocalDateTime modifiedTime;

    public UserVo getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(UserVo createdUser) {
        this.createdUser = createdUser;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public UserVo getModifiedUser() {
        return modifiedUser;
    }

    public void setModifiedUser(UserVo modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
