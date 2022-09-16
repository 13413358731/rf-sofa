package com.realfinance.sofa.cg.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 登录用户信息
 */
public class AuthInfo implements UserDetails, CredentialsContainer {
    // 登录系统时选择的法人
    private final Tenant tenant;
    private final User user;
    private String password;

    @JsonIgnore
    private final List<GrantedAuthority> authorities;

    public AuthInfo(Tenant tenant, User user, String password, List<GrantedAuthority> authorities) {
        if (tenant == null
                || tenant.getId() == null
                || user == null
                || ((user.getId() == null))
                || ((user.getUsername() == null) || "".equals(user.getUsername()))
                || (password == null)) {
            throw new IllegalArgumentException(
                    "Cannot pass null or empty values to constructor");
        }
        this.tenant = tenant;
        this.user = user;
        this.password = password;
        this.authorities = authorities;
    }

    @JsonIgnore
    public String getTenantId() {
        return tenant.getId();
    }

    public List<String> getRoleCodes() {
        List<String> result = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(e -> e.startsWith("ROLE_"))
                .map(e -> e.substring("ROLE_".length()))
                .collect(Collectors.toList());
        return result;
    }

    public List<String> getMenuCodes() {
        List<String> result = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .filter(e -> !e.startsWith("ROLE_"))
                .collect(Collectors.toList());
        return result;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return user.getId().toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    @Override
    public String toString() {
        return "AuthInfo{" +
                "tenant=" + tenant +
                ", user=" + user +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }

    @Override
    public boolean equals(Object rhs) {
        if (rhs instanceof AuthInfo) {
            return user.id.equals(((AuthInfo) rhs).user.id);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.id);
    }
}
