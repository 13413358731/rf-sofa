package com.realfinance.sofa.cg.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModifiedRecordVo {
    private String version;
    private String columnName;
    private Object beforeValue;
    private Object afterValue;
    private LocalDateTime modifiedTime;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public Object getBeforeValue() {
        return beforeValue;
    }

    public void setBeforeValue(Object beforeValue) {
        this.beforeValue = beforeValue;
    }

    public Object getAfterValue() {
        return afterValue;
    }

    public void setAfterValue(Object afterValue) {
        this.afterValue = afterValue;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }
}
