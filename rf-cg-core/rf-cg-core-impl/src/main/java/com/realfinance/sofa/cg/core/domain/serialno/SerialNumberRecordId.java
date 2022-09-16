package com.realfinance.sofa.cg.core.domain.serialno;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class SerialNumberRecordId implements Serializable {
    private String type;
    private String tenantId;

    public SerialNumberRecordId() {
    }

    public SerialNumberRecordId(String type, String tenantId) {
        this.type = type;
        this.tenantId = tenantId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public static SerialNumberRecordId of(String type, String tenantId) {
        return new SerialNumberRecordId(type, tenantId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SerialNumberRecordId that = (SerialNumberRecordId) o;
        return type.equals(that.type) &&
                tenantId.equals(that.tenantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, tenantId);
    }

    @Override
    public String toString() {
        return "SerialNumberRecordId{" +
                "type='" + type + '\'' +
                ", tenantId='" + tenantId + '\'' +
                '}';
    }
}
