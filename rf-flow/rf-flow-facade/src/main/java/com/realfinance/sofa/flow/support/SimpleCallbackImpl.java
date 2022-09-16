package com.realfinance.sofa.flow.support;

import com.realfinance.sofa.flow.facade.CallbackFacade;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.model.FlowCallbackResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 * 业务回调简单实现
 */
public class SimpleCallbackImpl implements CallbackFacade {

    private static final Logger log = LoggerFactory.getLogger(SimpleCallbackImpl.class);
    private final JdbcTemplate jdbcTemplate;

    private final String tableName;
    private final String primaryKeyColumnName;
    private final String bpmStatusColumnName;
    private final String bpmStatusUpdateSql;

    public SimpleCallbackImpl(JdbcTemplate jdbcTemplate,
                              String tableName,
                              String primaryKeyColumnName,
                              String bpmStatusColumnName) {
        this.jdbcTemplate = jdbcTemplate;
        this.tableName = tableName;
        this.primaryKeyColumnName = primaryKeyColumnName;
        this.bpmStatusColumnName = bpmStatusColumnName;
        this.bpmStatusUpdateSql = "UPDATE " + tableName
                + " SET " + bpmStatusColumnName + " = ? " +
                " WHERE " + primaryKeyColumnName + " = ? ";
    }

    @Override
    @Transactional
    public FlowCallbackResponse callback(FlowCallbackRequest request) {
        if (log.isTraceEnabled()) {
            log.trace("执行审批回调：{}",request);
        }
        if (request.getType() == null) {
            return FlowCallbackResponse.fail("获取不到回调请求类型");
        }
        try {
            return doCallback(request);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("回调处理异常，request：{}，exceptionMessage：{}",request,e.getMessage());
            }
            return FlowCallbackResponse.fail(e.getMessage());
        }
    }

    protected FlowCallbackResponse doCallback(FlowCallbackRequest request) {
        // TODO: 2020/11/12
        if ("审批通过".equals(request.getType())) {

            jdbcTemplate.update(bpmStatusUpdateSql,"1",request.getBusinessKey());

            return FlowCallbackResponse.ok();
        }
        return FlowCallbackResponse.fail("TODO");
    }


    public String getTableName() {
        return tableName;
    }

    public String getPrimaryKeyColumnName() {
        return primaryKeyColumnName;
    }

    public String getBpmStatusColumnName() {
        return bpmStatusColumnName;
    }
}
