package com.realfinance.sofa.flow.flowable.idm;

import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.idm.api.User;
import org.flowable.idm.engine.impl.UserQueryImpl;
import org.flowable.idm.engine.impl.util.CommandContextUtil;

import java.util.List;

public class CustomUserQuery extends UserQueryImpl {

    @Override
    public long executeCount(CommandContext commandContext) {
        return CommandContextUtil.getUserEntityManager(commandContext).findUserCountByQueryCriteria(this);
    }

    @Override
    public List<User> executeList(CommandContext commandContext) {
        return CommandContextUtil.getUserEntityManager(commandContext).findUserByQueryCriteria(this);
    }
}
