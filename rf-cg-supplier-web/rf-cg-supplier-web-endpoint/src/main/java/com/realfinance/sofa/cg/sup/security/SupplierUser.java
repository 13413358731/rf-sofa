package com.realfinance.sofa.cg.sup.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class SupplierUser extends User {

    private Integer supplierId;

    public SupplierUser(String username, String password, Integer supplierId, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.supplierId = supplierId;
    }

    public SupplierUser(String username, String password, Integer supplierId, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.supplierId = supplierId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }
}
