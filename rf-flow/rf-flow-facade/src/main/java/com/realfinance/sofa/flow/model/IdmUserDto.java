package com.realfinance.sofa.flow.model;

import java.io.Serializable;

public class IdmUserDto implements Serializable {
    protected String id;
    protected String firstName;
    protected String lastName;
    protected String email;
    protected String fullName;
    protected String tenantId;

    public IdmUserDto() {
    }

    public IdmUserDto(String id) {
        this.id = id;
    }

    public IdmUserDto(String id, String firstName, String lastName, String email, String fullName, String tenantId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.fullName = fullName;
        this.tenantId = tenantId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String toString() {
        return "IdmUserDto{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + fullName + '\'' +
                ", tenantId='" + tenantId + '\'' +
                '}';
    }
}
