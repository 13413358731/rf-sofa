package com.realfinance.sofa.system.domain;

import com.realfinance.sofa.common.model.IEntity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 租户
 */
@Entity
@Table(name = "SYSTEM_Tenant")
public class Tenant extends BaseEntity implements IEntity<String> {

    @Version
    private Long v;

    @Id
    private String id;

    /**
     * 名称
     */
    @Column(nullable = false)
    private String name;

    /**
     * 描述
     */
    @Column(nullable = false)
    private String note;

    /**
     * 开始时间
     */
    @Column(nullable = false)
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @Column(nullable = false)
    private LocalDateTime endTime;

    /**
     * 是否可用
     */
    @Column(nullable = false)
    private Boolean enabled;

    @OneToMany(mappedBy = "tenant")
    private Set<User> users;
    @OneToMany(mappedBy = "tenant")
    private Set<Department> departments;
    @OneToMany(mappedBy = "tenant")
    private Set<Role> roles;
    @OneToMany(mappedBy = "tenant")
    private Set<RoleGroup> roleGroups;

    /**
     * 有效的租户
     * 有效指在时间上有效并且是可用的
     * @return
     */
    public boolean isValid() {
        LocalDateTime now = LocalDateTime.now();
        return getEnabled() && now.isAfter(getStartTime()) && now.isBefore(getEndTime());
    }

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

    public Long getV() {
        return v;
    }

    public void setV(Long v) {
        this.v = v;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Set<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(Set<Department> departments) {
        this.departments = departments;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<RoleGroup> getRoleGroups() {
        return roleGroups;
    }

    public void setRoleGroups(Set<RoleGroup> roleGroups) {
        this.roleGroups = roleGroups;
    }
}
