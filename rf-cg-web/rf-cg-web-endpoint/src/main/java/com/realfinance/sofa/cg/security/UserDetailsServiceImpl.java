package com.realfinance.sofa.cg.security;

import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.system.facade.UserAuthInfoFacade;
import com.realfinance.sofa.system.model.UserAuthInfoDto;
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
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.realfinance.sofa.system.model.SystemConstants.SYSTEM_TENANT_ID;

public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger log = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private static final String DEFAULT_TENANT_ID = SYSTEM_TENANT_ID;

    @SofaReference(interfaceType = UserAuthInfoFacade.class, uniqueId = "${service.rf-system.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private UserAuthInfoFacade userAuthInfoFacade;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (log.isTraceEnabled()) {
            log.trace("loadUserByUsername: {}", username);
        }
        try {
            String tenantId = username.substring(0, 2);
            UserAuthInfoDto userAuthInfoDto = userAuthInfoFacade.getUserAuthInfo(tenantId, username);
            if (log.isDebugEnabled()) {
                log.debug("getUserAuthInfo，tenantId：{}，username：{}，userAuthInfo：{}",
                        tenantId, username, userAuthInfoDto);
            }
            if (userAuthInfoDto == null) {
                throw new UsernameNotFoundException(String.format("找不到用户: %s", username));
            }
            return createAuthInfo(userAuthInfoDto);
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

    /**
     * 从用户授权信息提取授权信息并且转换为Spring security的授权集合
     *
     * @param userAuthInfoDto
     * @return
     */
    private List<GrantedAuthority> mapToGrantedAuthorities(UserAuthInfoDto userAuthInfoDto) {
        Stream<String> roleCodes = userAuthInfoDto.getRoleCodes() == null ?
                Stream.empty() : userAuthInfoDto.getRoleCodes().stream().map(e -> "ROLE_" + e);
        Stream<String> menuCodes = userAuthInfoDto.getMenuCodes() == null ?
                Stream.empty() : userAuthInfoDto.getMenuCodes().stream();
        Set<String> authorities = Stream.concat(roleCodes, menuCodes).collect(Collectors.toSet());
        if (log.isTraceEnabled()) {
            log.trace("从用户授权信息中获取GrantedAuthority => userAuthInfoDto: {}, authorities: {}",
                    userAuthInfoDto.getUser(), authorities);
        }
        return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    /**
     * 从请求中获取租户ID
     *
     * @return
     */
    private String obtainTenantId() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            if (log.isTraceEnabled()) {
                log.trace("RequestAttributes is null");
            }
            return DEFAULT_TENANT_ID;
        }
        try {
            HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();
            String tenantId = request.getParameter("tenant");
            if (log.isTraceEnabled()) {
                log.trace("从请求中获取租户ID：{}", tenantId);
            }
            return tenantId == null ? DEFAULT_TENANT_ID : tenantId;
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("获取租户异常", e);
            }
            return DEFAULT_TENANT_ID;
        }
    }


    private UserDetails createAuthInfo(UserAuthInfoDto userAuthInfoDto) {
        Tenant tenant = new Tenant();
        tenant.setId(userAuthInfoDto.getTenant().getId());
        tenant.setName(userAuthInfoDto.getTenant().getName());

        User user = new User();
        user.setId(userAuthInfoDto.getUser().getId());
        user.setUsername(userAuthInfoDto.getUser().getUsername());
        user.setRealname(userAuthInfoDto.getUser().getRealname());
        user.setEmail(userAuthInfoDto.getUser().getEmail());
        user.setMobile(userAuthInfoDto.getUser().getMobile());
        user.setEnabled(userAuthInfoDto.getUser().getEnabled());
        user.setClassification(userAuthInfoDto.getUser().getClassification());
        if (userAuthInfoDto.getUser().getDepartment() != null) {
            Department department = new Department();
            department.setId(userAuthInfoDto.getUser().getDepartment().getId());
            department.setCode(userAuthInfoDto.getUser().getDepartment().getCode());
            department.setName(userAuthInfoDto.getUser().getDepartment().getName());
            user.setDepartment(department);
        }
        return new AuthInfo(tenant, user, userAuthInfoDto.getPassword(),
                mapToGrantedAuthorities(userAuthInfoDto));
    }
}
