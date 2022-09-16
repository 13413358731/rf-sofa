package com.realfinance.sofa.flow.util;

import com.realfinance.sofa.system.facade.SystemQueryFacade;
import com.realfinance.sofa.system.model.UserDto;
import com.realfinance.sofa.system.model.UserQueryCriteria;
import com.realfinance.sofa.system.model.UserSmallDto;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.impl.persistence.entity.ExecutionEntityManager;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.idm.api.User;
import org.flowable.idm.engine.impl.persistence.entity.UserEntity;
import org.flowable.idm.engine.impl.persistence.entity.UserEntityImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class CommandContextUtils {

    private static final Logger log = LoggerFactory.getLogger(CommandContextUtils.class);

    /**
     * 查询用户
     * @param commandContext
     * @param userId
     * @return
     */
    public static UserEntity findUser(CommandContext commandContext, String userId) {
        UserEntity user = null;
        String cacheKey = "cache_user_" + userId;
        if (userId != null) {
            if (commandContext != null) {
                UserEntity userCache = (UserEntity) commandContext.getAttribute(cacheKey);
                if (userCache != null) {
                    return userCache;
                }
            }
            UserSmallDto userSmallDto = SystemQuery.get().queryUserSmallDto(Integer.parseInt(userId));
            if (userSmallDto != null) {
                user = new UserEntityImpl();
                user.setId(userSmallDto.getId().toString());
                user.setFirstName(userSmallDto.getUsername());
                user.setLastName(userSmallDto.getRealname());
                user.setDisplayName(String.format("%s(%s)", userSmallDto.getRealname(), userSmallDto.getUsername()));
                if (commandContext != null) {
                    commandContext.addAttribute(cacheKey,user);
                }
            }
        }
        if (user == null) {
            throw new FlowableObjectNotFoundException("Cannot find user with id " + userId, User.class);
        }
        return user;
    }

    /**
     * 查询用户
     * @param commandContext
     * @param userIds
     * @return
     */
    public static List<User> findUser(CommandContext commandContext, List<String> userIds) {
        if (userIds == null || userIds.isEmpty()) {
            return new ArrayList<>();
        }
        UserQueryCriteria queryCriteria = new UserQueryCriteria();
        Set<Integer> ids = userIds.stream().map(Integer::parseInt).collect(Collectors.toSet());
        queryCriteria.setIdIn(ids);
        Page<UserDto> userDtos = SystemQuery.get().queryUserRefer(queryCriteria, PageRequest.of(0, ids.size()));
        if (userDtos.getContent().isEmpty()) {
            return new ArrayList<>();
        }
        return userDtos.getContent().stream().map(e -> {
            UserEntity user = new UserEntityImpl();
            user.setId(e.getId().toString());
            user.setFirstName(e.getUsername());
            user.setLastName(e.getRealname());
            user.setDisplayName(String.format("%s(%s)", e.getRealname(), e.getUsername()));
            return user;
        }).collect(Collectors.toList());
    }

    /**
     * 判断用户是否在组里
     * @param commandContext
     * @param userId
     * @param groupIds
     * @return
     */
    public static boolean checkUserInGroup(CommandContext commandContext, String userId, List<String> groupIds) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(groupIds);
        if (groupIds.isEmpty()) {
            return true;
        }
        boolean result = false;
        SystemQueryFacade systemQueryFacade = SystemQuery.get();
        Set<Integer> departmentIds = groupIds.stream()
                .filter(e -> e.startsWith("D_"))
                .map(e -> e.substring(2))
                .map(Integer::parseInt).collect(Collectors.toSet());
        if (!departmentIds.isEmpty()) {
            result = systemQueryFacade.checkUserInDepartment(Integer.parseInt(userId),departmentIds);
        }
        if (!result) {
            Set<Integer> roleIds = groupIds.stream()
                    .filter(e -> e.startsWith("R_"))
                    .map(e -> e.substring(2))
                    .map(Integer::parseInt).collect(Collectors.toSet());
            if (!roleIds.isEmpty()) {
                result = systemQueryFacade.checkUserInRole(Integer.parseInt(userId),roleIds);
            }
        }
        return result;
    }

    /**
     * 判断流程是否结束
     * @param commandContext
     * @param processInstanceId
     * @return
     */
    public static boolean processIsEnd(CommandContext commandContext, String processInstanceId) {
        ExecutionEntityManager executionEntityManager = CommandContextUtil
                .getExecutionEntityManager(commandContext);
        ExecutionEntity entity = executionEntityManager.findById(processInstanceId);
        boolean isEnd = (entity == null || entity.isDeleted());
        if (log.isTraceEnabled()) {
            log.trace("流程[{}]{}结束", processInstanceId,isEnd ? "已" : "未");
        }
        return isEnd;
    }
}
