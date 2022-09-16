package com.realfinance.sofa.flow.flowable.cmd;

import com.fasterxml.jackson.databind.JsonNode;
import com.realfinance.sofa.common.datascope.DataScopeUtils;
import com.realfinance.sofa.common.util.SpringContextHolder;
import com.realfinance.sofa.flow.domain.Biz;
import com.realfinance.sofa.flow.domain.BizModel;
import com.realfinance.sofa.flow.flowable.FlowConstants;
import com.realfinance.sofa.flow.flowable.listener.ProcessStartListener;
import com.realfinance.sofa.flow.model.FlowCallbackRequest;
import com.realfinance.sofa.flow.model.FlowCallbackResponse;
import com.realfinance.sofa.flow.repository.BizModelRepository;
import com.realfinance.sofa.flow.util.CommandContextUtils;
import org.flowable.common.engine.api.FlowableException;
import org.flowable.common.engine.api.FlowableObjectNotFoundException;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.common.engine.impl.interceptor.Command;
import org.flowable.common.engine.impl.interceptor.CommandContext;
import org.flowable.engine.form.FormProperty;
import org.flowable.engine.form.StartFormData;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.flowable.engine.impl.util.CommandContextUtil;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.engine.runtime.ProcessInstanceBuilder;
import org.flowable.identitylink.api.IdentityLinkInfo;
import org.flowable.identitylink.service.impl.persistence.entity.IdentityLinkEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 启动流程
 * 根据业务编码和业务主键判断，如果流程实例已存在则直接返回而不是再次创建
 */
public class StartProcessCmd implements Command<ProcessInstance>, Serializable {

    private static final Logger log = LoggerFactory.getLogger(StartProcessCmd.class);

    private static final ExpressionParser EXPRESSION_PARSER = new SpelExpressionParser();


    private BizModel bizModel;
    private String businessKey;
    private Map<String, String> formData;
    private String tenantId;
    //流程名称自定义名称
    private String name;
    //使用默认法人，用于后端直接执行
    private boolean isDefault;

    public StartProcessCmd(BizModel bizModel, String businessKey, Map<String, String> formData, String name) {
        this.bizModel = bizModel;
        this.businessKey = businessKey;
        this.formData = formData;
        this.tenantId = DataScopeUtils.loadTenantId();
        this.name = name;
    }

    public StartProcessCmd(BizModel bizModel, String businessKey, Map<String, String> formData, String name,boolean isDefault) {
        this.bizModel = bizModel;
        this.businessKey = businessKey;
        this.formData = formData;
        if(isDefault){
            this.tenantId = "01";
        }else{
            this.tenantId = DataScopeUtils.loadTenantId();
        }
        this.name = name;
        this.isDefault = isDefault;
    }

    @Override
    public ProcessInstance execute(CommandContext commandContext) {
        // 加个锁，防止产生多条数据
        SpringContextHolder.getBean(BizModelRepository.class).lockBizModel(bizModel.getId());
        ProcessEngineConfigurationImpl processEngineConfiguration = CommandContextUtil.getProcessEngineConfiguration(commandContext);
        ProcessInstance processInstance = processEngineConfiguration.getRuntimeService()
                .createProcessInstanceQuery()
                .processInstanceBusinessKey(businessKey)
                .processInstanceTenantId(tenantId)
                .processInstanceReferenceType(FlowConstants.PROCESS_INSTANCE_REFERENCE_TYPE)
                .processInstanceReferenceId(bizModel.getBiz().getCode())
                .singleResult();
        if (processInstance == null) {
            processInstance = startProcessInstance(commandContext);
        }
        commandContext.addCloseListener(new ProcessStartListener(processInstance,isDefault));
        return processInstance;
    }

    /**
     * 启动一个流程
     * @param commandContext
     * @return
     */
    protected ProcessInstance startProcessInstance(CommandContext commandContext) {
        ProcessInstance processInstance;
        ProcessEngineConfigurationImpl processEngineConfiguration = CommandContextUtil.getProcessEngineConfiguration(commandContext);
        String processDefinitionKey = bizModel.getModelKey();
        ProcessDefinitionEntity processDefinition = CommandContextUtil.getProcessDefinitionEntityManager(commandContext)
                .findLatestProcessDefinitionByKeyAndTenantId(processDefinitionKey,tenantId);
        if (processDefinition == null) {
            throw new FlowableObjectNotFoundException("Cannot find process definition with key " + processDefinitionKey, ProcessDefinition.class);
        }

        checkPotentialStarter(processDefinition,commandContext);

        Biz biz = bizModel.getBiz();
        StartFormData startFormData = processEngineConfiguration.getFormService().getStartFormData(processDefinition.getId());
        List<FormProperty> formProperties = startFormData.getFormProperties();

        ProcessInstanceBuilder builder = processEngineConfiguration.getRuntimeService().createProcessInstanceBuilder()
                .tenantId(tenantId)
                .processDefinitionKey(bizModel.getModelKey())
                .name(name==null?getProcessInstanceName(commandContext):getProcessInstanceName(commandContext)+"("+name+")")
                .businessKey(businessKey)
                .callbackType(biz.getCallbackType().name())
                .callbackId(biz.getCallbackUrl())
                .referenceType(FlowConstants.PROCESS_INSTANCE_REFERENCE_TYPE)
                .referenceId(biz.getCode());

        if (!formProperties.isEmpty()) {
            //builder.startFormVariables(fillFormData(formProperties, formData));
            //启动流程动态选人方法
            builder.variables(fillFormData(formProperties, formData));
        }
        processInstance = builder.start();
        return processInstance;
    }

    /**
     * 获取流程名称
     * @param commandContext
     * @return
     */
    private String getProcessInstanceName(CommandContext commandContext) {
        try {
            String processInstName = bizModel.getProcessInstName();
            Expression expression = EXPRESSION_PARSER.parseExpression(processInstName, new TemplateParserContext());
            if (expression instanceof LiteralExpression) {
                return processInstName;
            } else {
                FlowCallbackRequest request = new FlowCallbackRequest();
                request.setTenantId(DataScopeUtils.loadTenantId());
                request.setBusinessKey(businessKey);
                request.setBusinessCode(bizModel.getBiz().getCode());
                request.setType(FlowCallbackRequest.TYPE_LOAD_DATA);
                FlowCallbackResponse response = bizModel.getBiz().getCallbackType().sendCallbackRequest(bizModel.getBiz().getCallbackUrl(), request);
                JsonNode data = commandContext.getObjectMapper().convertValue(response.getData(), JsonNode.class);
                StandardEvaluationContext context = new StandardEvaluationContext(data);
                return expression.getValue(context, String.class);
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                if (e.getCause() != null) {
                    log.error("获取流程名称失败",e.getCause());
                }
                log.error("获取流程名称失败，processInstName：{}，businessKey：{}，businessCode：{}",
                        bizModel.getProcessInstName(),businessKey,bizModel.getBiz().getCode());
            }
            return bizModel.getProcessInstName();
        }
    }

    /**
     * 检查是否流程潜在启动者
     * @param processDefinition
     * @param commandContext
     */
    protected void checkPotentialStarter(ProcessDefinitionEntity processDefinition, CommandContext commandContext) {
        List<IdentityLinkEntity> identityLinks = processDefinition.getIdentityLinks();
        if (identityLinks == null || identityLinks.isEmpty()) {
            return;
        }
        String authenticatedUserId = Authentication.getAuthenticatedUserId();
        List<String> userIds = identityLinks.stream()
                .filter(IdentityLinkEntity::isUser)
                .map(IdentityLinkInfo::getUserId)
                .distinct()
                .collect(Collectors.toList());

        if (!userIds.isEmpty() && authenticatedUserId != null) {
            if (userIds.contains(authenticatedUserId)) {
                return;
            }
        }

        List<String> groupIds = identityLinks.stream()
                .filter(IdentityLinkEntity::isGroup)
                .map(IdentityLinkInfo::getGroupId)
                .distinct()
                .collect(Collectors.toList());

        if (!groupIds.isEmpty() && authenticatedUserId != null) {
            if (CommandContextUtils.checkUserInGroup(commandContext,authenticatedUserId,groupIds)) {
                return;
            }
        }

        throw new FlowableException("无权限启动此流程");
    }

    protected Map<String, Object> fillFormData(List<FormProperty> formProperties, Map<String, String> formData) {
        assert !formProperties.isEmpty();
        if (formData == null) {
            formData = new HashMap<>();
        }
        for (FormProperty formProperty : formProperties) {
            if (!formData.containsKey(formProperty.getId())) {
                formData.put(formProperty.getId(),null);
            }
        }
        return new HashMap<>(formData);
    }
}
