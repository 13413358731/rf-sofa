package com.realfinance.sofa.cg.sup.security;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.cg.sup.facade.CgSupplierAccountFacade;
import com.realfinance.sofa.cg.sup.model.CgSupplierAccountAuthInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;

public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    @SofaReference(interfaceType = CgSupplierAccountFacade.class, uniqueId = "${service.rf-cg-sup.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private CgSupplierAccountFacade cgSupplierAccountFacade;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (log.isTraceEnabled()) {
            log.trace("loadUserByUsername: {}", username);
        }
        try {
//            String tenantId = obtainTenantId();
            String tenantId="01";
            CgSupplierAccountAuthInfoDto account = cgSupplierAccountFacade.getAuthInfoDto(tenantId,username);
            if (account == null) {
                throw new UsernameNotFoundException(String.format("找不到用户: %s", username));
            }
            if (log.isDebugEnabled()) {
                log.debug("getByUsername，account：{}", account);
            }
            Collection<GrantedAuthority> authorities = getGrantedAuthorities(account);
            return new SupplierUser(username, account.getPassword(),account.getSupplierId(),
                    account.getEnabled(),true,true,true,authorities);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("loadUserByUsername 异常", e);
                log.error(e.getMessage());
            }
            if (e instanceof UsernameNotFoundException) {
                throw e;
            }
            throw new AuthenticationServiceException(String.format("查询用户失败: %s", e.getMessage()));
        }
    }

    private Collection<GrantedAuthority> getGrantedAuthorities(CgSupplierAccountAuthInfoDto account) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (account.getAuthorities() != null) {
            for (String authority : account.getAuthorities()) {
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        }
        return authorities;
    }

    /**
     * 从请求中获取租户ID
     * @return
     */
    private String obtainTenantId() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            if (log.isWarnEnabled()) {
                log.warn("RequestAttributes is null");
            }
            throw new RuntimeException("RequestAttributes is null");
        }
        try {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            String tenantId = request.getParameter("tenant");
            if (log.isTraceEnabled()) {
                log.trace("从请求中获取租户ID：{}",tenantId);
            }
            if (tenantId == null) {
                throw new RuntimeException("Tenant is missing");
            }
            return tenantId;
        } catch (Exception e) {
            log.error("获取租户异常",e);
            throw new RuntimeException("Tenant is missing");
        }
    }
}
