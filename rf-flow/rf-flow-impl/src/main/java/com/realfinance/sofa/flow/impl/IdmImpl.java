package com.realfinance.sofa.flow.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.flow.facade.IdmFacade;
import com.realfinance.sofa.flow.model.IdmGroupDto;
import com.realfinance.sofa.flow.model.IdmUserDto;
import org.apache.commons.lang3.StringUtils;
import org.flowable.engine.IdentityService;
import org.flowable.idm.api.Group;
import org.flowable.idm.api.GroupQuery;
import org.flowable.idm.api.User;
import org.flowable.idm.api.UserQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@SofaService(interfaceType = IdmFacade.class, uniqueId = "${service.rf-flow.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional
public class IdmImpl implements IdmFacade {

    private final IdentityService identityService;

    public IdmImpl(IdentityService identityService) {
        this.identityService = identityService;
    }

    @Override
    public Page<IdmUserDto> queryUsers(String filter, Pageable pageable) {
        UserQuery query = identityService.createUserQuery()
                .tenantId(DataScopeUtils.loadTenantId());
        if (StringUtils.isNotEmpty(filter)) {
            query.userFullNameLikeIgnoreCase("%" + filter + "%");
        }
        long total = query.count();
        if (total > 0) {
            List<User> groups = query.listPage((int) pageable.getOffset(), pageable.getPageSize());
            return new PageImpl<>(groups,pageable,total)
                    .map(e -> new IdmUserDto(e.getId(),
                            e.getFirstName(),
                            e.getLastName(),
                            e.getEmail(),
                            (e.getFirstName() != null ? e.getFirstName() : "") + " " + (e.getLastName() != null ? e.getLastName() : ""),
                            e.getTenantId()));
        } else {
            return Page.empty(pageable);
        }
    }

    @Override
    public Page<IdmUserDto> queryUsers(String filter, Set<String> userScope, Set<String> groupScope, @NotNull Pageable pageable) {
        Objects.requireNonNull(pageable);
        UserQuery userQuery = identityService.createUserQuery();
        // TODO: 2020/12/24 这里同时有用户范围和组范围的条件时是 AND 连接，如果要用 OR 连接需要写native sql
        if (userScope != null && !userScope.isEmpty()) {
            userQuery.userIds(new ArrayList<>(userScope));
        }
        if (groupScope != null && !groupScope.isEmpty()) {
            userQuery.memberOfGroups(new ArrayList<>(groupScope));
        }
        if (!DataScopeUtils.loadSkipValidateTenantId()) {
            userQuery.tenantId(DataScopeUtils.loadTenantId());
        }
        if (StringUtils.isNotEmpty(filter)) {
            userQuery.userFullNameLikeIgnoreCase("%" + filter + "%");
        }
        long total = userQuery.count();
        if (total > 0) {
            List<User> users = userQuery.listPage((int) pageable.getOffset(), pageable.getPageSize());
            return new PageImpl<>(users,pageable,total)
                    .map(e -> new IdmUserDto(e.getId(),
                            e.getFirstName(),
                            e.getLastName(),
                            e.getEmail(),
                            (e.getFirstName() != null ? e.getFirstName() : "") + " " + (e.getLastName() != null ? e.getLastName() : ""),
                            e.getTenantId()));
        } else {
            return Page.empty(pageable);
        }

    }

    @Override
    public Page<IdmGroupDto> queryGroups(String filter, Pageable pageable) {
        GroupQuery query = identityService.createGroupQuery()
                .groupType(DataScopeUtils.loadTenantId());
        if (StringUtils.isNotEmpty(filter)) {
            query.groupNameLike("%" + filter + "%");
        }

        long total = query.count();
        if (total > 0) {
            List<Group> groups = query.orderByGroupName().asc()
                    .listPage((int) pageable.getOffset(), pageable.getPageSize());
            return new PageImpl<>(groups,pageable,total)
                    .map(e -> new IdmGroupDto(e.getId(),e.getName(),e.getType()));
        } else {
            return Page.empty(pageable);
        }
    }
}
