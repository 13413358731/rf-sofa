package com.realfinance.sofa.cg.sup.domain;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @CreatedBy
    @Column(updatable = false)
    protected Integer createdUserId;
    @CreatedDate
    @Column(updatable = false)
    protected LocalDateTime createdTime;
    @LastModifiedBy
    protected Integer modifiedUserId;
    @LastModifiedDate
    protected LocalDateTime modifiedTime;

    public Integer getCreatedUserId() {
        return createdUserId;
    }

    public void setCreatedUserId(Integer createdUserId) {
        this.createdUserId = createdUserId;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getModifiedUserId() {
        return modifiedUserId;
    }

    public void setModifiedUserId(Integer modifiedUserId) {
        this.modifiedUserId = modifiedUserId;
    }

    public LocalDateTime getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(LocalDateTime modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

}
