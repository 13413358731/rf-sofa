/*
package com.realfinance.sofa.flow.test.facade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.common.rpc.util.RpcUtils;
import com.realfinance.sofa.flow.facade.FlowFacade;
import com.realfinance.sofa.flow.facade.ModelerFacade;
import com.realfinance.sofa.flow.facade.ProcessDefFacade;
import com.realfinance.sofa.flow.model.*;
import com.realfinance.sofa.flow.service.FlowService;
import com.realfinance.sofa.flow.test.base.AbstractTestBase;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.Process;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.*;
import org.flowable.engine.impl.persistence.entity.ExecutionEntity;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.idm.api.IdmIdentityService;
import org.flowable.task.api.Task;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.LinkedMultiValueMap;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.realfinance.sofa.flow.model.ProcessVariableConstants.NEXT_NODE_KEY;
import static com.realfinance.sofa.flow.model.ProcessVariableConstants.NEXT_USER_TASK_INFO;

public class FlowFacadeTest extends AbstractTestBase {
    @SofaReference(interfaceType = ProcessDefFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private ProcessDefFacade processDefFacade;
    @SofaReference(interfaceType = ModelerFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private ModelerFacade modelerFacade;
    @SofaReference(interfaceType = FlowFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private FlowFacade flowFacade;
    @Autowired
    TaskService taskService;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    DynamicBpmnService dynamicBpmnService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    ManagementService managementService;
    @Autowired
    FlowService flowService;
    @Autowired
    IdmIdentityService idmIdentityService;

    @Test
    public void test1() {
        try {
            RpcUtils.setTenantId("SYSTEM_TENANT");
            RpcUtils.setPrincipalId(1);
            RpcUtils.setPrincipalDepartmentId(1); // 随便设置了一个
            // 新增一个模型
            BizModelSaveDto bizModelSaveDto = new BizModelSaveDto();
            bizModelSaveDto.setBiz(1);
            bizModelSaveDto.setDepartmentId(1);
            Integer id = processDefFacade.saveBizModel(bizModelSaveDto);
            Page<BizModelDto> page = processDefFacade.listBizModelByDepartmentId(1, PageRequest.of(0, 20));
            Assert.assertFalse(page.getContent().isEmpty());
            Optional<BizModelDto> saved = page.getContent().stream().filter(e -> e.getId().equals(id)).findAny();
            Assert.assertTrue(saved.isPresent());
            // 编辑模型
            String modelId = saved.get().getModelId();
            String modelUpdateJson = "{\"modeltype\":[\"model\"],\"json_xml\":[\"{\\\"modelId\\\":\\\"7fd618b3-235d-11eb-b978-005056c00008\\\",\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":1200,\\\"y\\\":1050},\\\"upperLeft\\\":{\\\"x\\\":0,\\\"y\\\":0}},\\\"properties\\\":{\\\"process_id\\\":\\\"412\\\",\\\"name\\\":\\\"3\\\",\\\"documentation\\\":\\\"\\\",\\\"process_author\\\":\\\"按不出\\\",\\\"process_version\\\":\\\"\\\",\\\"process_namespace\\\":\\\"http://www.flowable.org/processdef\\\",\\\"process_historylevel\\\":\\\"\\\",\\\"isexecutable\\\":true,\\\"dataproperties\\\":\\\"\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"eventlisteners\\\":\\\"\\\",\\\"signaldefinitions\\\":\\\"\\\",\\\"messagedefinitions\\\":\\\"\\\",\\\"escalationdefinitions\\\":\\\"\\\",\\\"process_potentialstarteruser\\\":\\\"\\\",\\\"process_potentialstartergroup\\\":\\\"\\\",\\\"iseagerexecutionfetch\\\":\\\"false\\\"},\\\"childShapes\\\":[{\\\"resourceId\\\":\\\"startEvent1\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"\\\",\\\"documentation\\\":\\\"\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"initiator\\\":\\\"\\\",\\\"formkeydefinition\\\":\\\"\\\",\\\"formreference\\\":\\\"\\\",\\\"formfieldvalidation\\\":true,\\\"formproperties\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"StartNoneEvent\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-79171E55-B86A-4158-B9F3-7D6EF4E6A7F8\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":105,\\\"y\\\":160},\\\"upperLeft\\\":{\\\"x\\\":75,\\\"y\\\":130}},\\\"dockers\\\":[]},{\\\"resourceId\\\":\\\"sid-BD6DF031-CB18-4892-9084-90F1579A9E50\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"\\\",\\\"documentation\\\":\\\"\\\",\\\"executionlisteners\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"EndNoneEvent\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":808,\\\"y\\\":69},\\\"upperLeft\\\":{\\\"x\\\":780,\\\"y\\\":41}},\\\"dockers\\\":[]},{\\\"resourceId\\\":\\\"sid-C6E2261A-5A2C-4892-B130-129E5E99EAB1\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"提交单据\\\",\\\"documentation\\\":\\\"\\\",\\\"asynchronousdefinition\\\":\\\"false\\\",\\\"exclusivedefinition\\\":\\\"false\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"multiinstance_type\\\":\\\"None\\\",\\\"multiinstance_cardinality\\\":\\\"\\\",\\\"multiinstance_collection\\\":\\\"\\\",\\\"multiinstance_variable\\\":\\\"\\\",\\\"multiinstance_condition\\\":\\\"\\\",\\\"isforcompensation\\\":\\\"false\\\",\\\"usertaskassignment\\\":{\\\"assignment\\\":{\\\"type\\\":\\\"idm\\\",\\\"idm\\\":{\\\"type\\\":\\\"user\\\",\\\"assignee\\\":{\\\"firstName\\\":\\\"realfinance\\\",\\\"lastName\\\":\\\"瑞友信息技术有限公司\\\",\\\"tenantId\\\":\\\"SYSTEM_TENANT\\\",\\\"fullName\\\":\\\"realfinance 瑞友信息技术有限公司\\\",\\\"id\\\":1,\\\"$$hashKey\\\":\\\"object:2787\\\"}}}},\\\"formkeydefinition\\\":\\\"\\\",\\\"formreference\\\":\\\"\\\",\\\"formfieldvalidation\\\":true,\\\"duedatedefinition\\\":\\\"\\\",\\\"prioritydefinition\\\":\\\"\\\",\\\"formproperties\\\":\\\"\\\",\\\"tasklisteners\\\":\\\"\\\",\\\"skipexpression\\\":\\\"\\\",\\\"categorydefinition\\\":\\\"\\\",\\\"taskidvariablename\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"UserTask\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-46CA7E32-717E-4FE6-8FD2-FF03F9CB9527\\\"},{\\\"resourceId\\\":\\\"sid-78FDA1B6-BF18-409A-B9F5-E0BCF4A3C48B\\\"},{\\\"resourceId\\\":\\\"sid-488D4C1C-2E21-4B88-91B6-807EE64F6281\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":295,\\\"y\\\":185},\\\"upperLeft\\\":{\\\"x\\\":195,\\\"y\\\":105}},\\\"dockers\\\":[]},{\\\"resourceId\\\":\\\"sid-B8966BB4-79B5-47CA-BFD6-60F1A291CB2B\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"审批\\\",\\\"documentation\\\":\\\"\\\",\\\"asynchronousdefinition\\\":\\\"false\\\",\\\"exclusivedefinition\\\":\\\"false\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"multiinstance_type\\\":\\\"None\\\",\\\"multiinstance_cardinality\\\":\\\"\\\",\\\"multiinstance_collection\\\":\\\"\\\",\\\"multiinstance_variable\\\":\\\"\\\",\\\"multiinstance_condition\\\":\\\"\\\",\\\"isforcompensation\\\":\\\"false\\\",\\\"usertaskassignment\\\":\\\"\\\",\\\"formkeydefinition\\\":\\\"\\\",\\\"formreference\\\":\\\"\\\",\\\"formfieldvalidation\\\":true,\\\"duedatedefinition\\\":\\\"\\\",\\\"prioritydefinition\\\":\\\"\\\",\\\"formproperties\\\":\\\"\\\",\\\"tasklisteners\\\":\\\"\\\",\\\"skipexpression\\\":\\\"\\\",\\\"categorydefinition\\\":\\\"\\\",\\\"taskidvariablename\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"UserTask\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-39252FC3-D044-4F50-89CC-C11D3A19B1A7\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":565,\\\"y\\\":95},\\\"upperLeft\\\":{\\\"x\\\":465,\\\"y\\\":15}},\\\"dockers\\\":[]},{\\\"resourceId\\\":\\\"sid-39252FC3-D044-4F50-89CC-C11D3A19B1A7\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"\\\",\\\"documentation\\\":\\\"\\\",\\\"conditionsequenceflow\\\":\\\"\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"defaultflow\\\":\\\"false\\\",\\\"skipexpression\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"SequenceFlow\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-BD6DF031-CB18-4892-9084-90F1579A9E50\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":779.921875,\\\"y\\\":55},\\\"upperLeft\\\":{\\\"x\\\":565.04296875,\\\"y\\\":55}},\\\"dockers\\\":[{\\\"x\\\":50,\\\"y\\\":40},{\\\"x\\\":14,\\\"y\\\":14}],\\\"target\\\":{\\\"resourceId\\\":\\\"sid-BD6DF031-CB18-4892-9084-90F1579A9E50\\\"}},{\\\"resourceId\\\":\\\"sid-79171E55-B86A-4158-B9F3-7D6EF4E6A7F8\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"\\\",\\\"documentation\\\":\\\"\\\",\\\"conditionsequenceflow\\\":\\\"\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"defaultflow\\\":\\\"false\\\",\\\"skipexpression\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"SequenceFlow\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-C6E2261A-5A2C-4892-B130-129E5E99EAB1\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":194.3515625,\\\"y\\\":145},\\\"upperLeft\\\":{\\\"x\\\":105.53125,\\\"y\\\":145}},\\\"dockers\\\":[{\\\"x\\\":15,\\\"y\\\":15},{\\\"x\\\":50,\\\"y\\\":40}],\\\"target\\\":{\\\"resourceId\\\":\\\"sid-C6E2261A-5A2C-4892-B130-129E5E99EAB1\\\"}},{\\\"resourceId\\\":\\\"sid-DD8B83FA-D018-4FEC-983D-9C556ED1AB92\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"TIJIAO\\\",\\\"name\\\":\\\"\\\",\\\"documentation\\\":\\\"\\\",\\\"asynchronousdefinition\\\":\\\"false\\\",\\\"exclusivedefinition\\\":\\\"false\\\",\\\"sequencefloworder\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"ExclusiveGateway\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-8FA9BA64-9DDF-40D5-92A8-8483082A4B68\\\"},{\\\"resourceId\\\":\\\"sid-8B59B240-EEA1-470C-A960-DDC9CEDF2EDD\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":430,\\\"y\\\":165},\\\"upperLeft\\\":{\\\"x\\\":390,\\\"y\\\":125}},\\\"dockers\\\":[]},{\\\"resourceId\\\":\\\"sid-1B60E493-D5F7-47F2-A6C3-EBBE799D5CDD\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"财务审批\\\",\\\"documentation\\\":\\\"\\\",\\\"asynchronousdefinition\\\":\\\"false\\\",\\\"exclusivedefinition\\\":\\\"false\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"multiinstance_type\\\":\\\"None\\\",\\\"multiinstance_cardinality\\\":\\\"\\\",\\\"multiinstance_collection\\\":\\\"\\\",\\\"multiinstance_variable\\\":\\\"\\\",\\\"multiinstance_condition\\\":\\\"\\\",\\\"isforcompensation\\\":\\\"false\\\",\\\"usertaskassignment\\\":\\\"\\\",\\\"formkeydefinition\\\":\\\"\\\",\\\"formreference\\\":\\\"\\\",\\\"formfieldvalidation\\\":true,\\\"duedatedefinition\\\":\\\"\\\",\\\"prioritydefinition\\\":\\\"\\\",\\\"formproperties\\\":\\\"\\\",\\\"tasklisteners\\\":\\\"\\\",\\\"skipexpression\\\":\\\"\\\",\\\"categorydefinition\\\":\\\"\\\",\\\"taskidvariablename\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"UserTask\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-C759477C-DA4A-4052-9E55-060645FFD171\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":565,\\\"y\\\":290},\\\"upperLeft\\\":{\\\"x\\\":465,\\\"y\\\":210}},\\\"dockers\\\":[]},{\\\"resourceId\\\":\\\"sid-C759477C-DA4A-4052-9E55-060645FFD171\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"\\\",\\\"documentation\\\":\\\"\\\",\\\"conditionsequenceflow\\\":\\\"\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"defaultflow\\\":\\\"false\\\",\\\"skipexpression\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"SequenceFlow\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-B8966BB4-79B5-47CA-BFD6-60F1A291CB2B\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":515,\\\"y\\\":209.390625},\\\"upperLeft\\\":{\\\"x\\\":515,\\\"y\\\":95.609375}},\\\"dockers\\\":[{\\\"x\\\":50,\\\"y\\\":40},{\\\"x\\\":50,\\\"y\\\":40}],\\\"target\\\":{\\\"resourceId\\\":\\\"sid-B8966BB4-79B5-47CA-BFD6-60F1A291CB2B\\\"}},{\\\"resourceId\\\":\\\"sid-8FA9BA64-9DDF-40D5-92A8-8483082A4B68\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"金额小于等于1000\\\",\\\"documentation\\\":\\\"\\\",\\\"conditionsequenceflow\\\":{\\\"expression\\\":{\\\"type\\\":\\\"static\\\",\\\"staticValue\\\":\\\"${amount <= 1000}\\\"}},\\\"executionlisteners\\\":\\\"\\\",\\\"defaultflow\\\":\\\"false\\\",\\\"skipexpression\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"SequenceFlow\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-B8966BB4-79B5-47CA-BFD6-60F1A291CB2B\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":464.19921875,\\\"y\\\":124.703125},\\\"upperLeft\\\":{\\\"x\\\":410.5,\\\"y\\\":55}},\\\"dockers\\\":[{\\\"x\\\":20.5,\\\"y\\\":20.5},{\\\"x\\\":410.5,\\\"y\\\":55},{\\\"x\\\":50,\\\"y\\\":40}],\\\"target\\\":{\\\"resourceId\\\":\\\"sid-B8966BB4-79B5-47CA-BFD6-60F1A291CB2B\\\"}},{\\\"resourceId\\\":\\\"sid-8B59B240-EEA1-470C-A960-DDC9CEDF2EDD\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"金额大于1000\\\",\\\"documentation\\\":\\\"\\\",\\\"conditionsequenceflow\\\":{\\\"expression\\\":{\\\"type\\\":\\\"static\\\",\\\"staticValue\\\":\\\"${amount > 1000}\\\"}},\\\"executionlisteners\\\":\\\"\\\",\\\"defaultflow\\\":\\\"false\\\",\\\"skipexpression\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"SequenceFlow\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-1B60E493-D5F7-47F2-A6C3-EBBE799D5CDD\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":464.19921875,\\\"y\\\":250},\\\"upperLeft\\\":{\\\"x\\\":410.5,\\\"y\\\":165.27734375}},\\\"dockers\\\":[{\\\"x\\\":20.5,\\\"y\\\":20.5},{\\\"x\\\":410.5,\\\"y\\\":250},{\\\"x\\\":50,\\\"y\\\":40}],\\\"target\\\":{\\\"resourceId\\\":\\\"sid-1B60E493-D5F7-47F2-A6C3-EBBE799D5CDD\\\"}},{\\\"resourceId\\\":\\\"sid-8D62D0FE-9D1D-4FB5-8D41-6D74856418DE\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"HUIQIAN\\\",\\\"name\\\":\\\"多人会签\\\",\\\"documentation\\\":\\\"\\\",\\\"asynchronousdefinition\\\":\\\"false\\\",\\\"exclusivedefinition\\\":\\\"false\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"multiinstance_type\\\":\\\"Parallel\\\",\\\"multiinstance_cardinality\\\":\\\"\\\",\\\"multiinstance_collection\\\":\\\"selectUser\\\",\\\"multiinstance_variable\\\":\\\"user\\\",\\\"multiinstance_condition\\\":\\\"${nrOfInstances <= nrOfCompletedInstances}\\\",\\\"isforcompensation\\\":\\\"false\\\",\\\"usertaskassignment\\\":{\\\"assignment\\\":{\\\"type\\\":\\\"static\\\",\\\"assignee\\\":\\\"${user}\\\"}},\\\"formkeydefinition\\\":\\\"\\\",\\\"formreference\\\":\\\"\\\",\\\"formfieldvalidation\\\":true,\\\"duedatedefinition\\\":\\\"\\\",\\\"prioritydefinition\\\":\\\"\\\",\\\"formproperties\\\":\\\"\\\",\\\"tasklisteners\\\":\\\"\\\",\\\"skipexpression\\\":\\\"\\\",\\\"categorydefinition\\\":\\\"\\\",\\\"taskidvariablename\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"UserTask\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-576765B0-9D7B-48B1-AF07-2CAFE0E88C60\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":295,\\\"y\\\":335},\\\"upperLeft\\\":{\\\"x\\\":195,\\\"y\\\":255}},\\\"dockers\\\":[]},{\\\"resourceId\\\":\\\"sid-576765B0-9D7B-48B1-AF07-2CAFE0E88C60\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"\\\",\\\"documentation\\\":\\\"\\\",\\\"conditionsequenceflow\\\":\\\"\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"defaultflow\\\":\\\"false\\\",\\\"skipexpression\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"SequenceFlow\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-BD6DF031-CB18-4892-9084-90F1579A9E50\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":794,\\\"y\\\":295},\\\"upperLeft\\\":{\\\"x\\\":295.32421875,\\\"y\\\":69.125}},\\\"dockers\\\":[{\\\"x\\\":50,\\\"y\\\":40},{\\\"x\\\":794,\\\"y\\\":295},{\\\"x\\\":14,\\\"y\\\":14}],\\\"target\\\":{\\\"resourceId\\\":\\\"sid-BD6DF031-CB18-4892-9084-90F1579A9E50\\\"}},{\\\"resourceId\\\":\\\"sid-46CA7E32-717E-4FE6-8FD2-FF03F9CB9527\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"提交\\\",\\\"documentation\\\":\\\"\\\",\\\"conditionsequenceflow\\\":{\\\"expression\\\":{\\\"type\\\":\\\"static\\\",\\\"staticValue\\\":\\\"${nextNode == 'TIJIAO'}\\\"}},\\\"executionlisteners\\\":\\\"\\\",\\\"defaultflow\\\":\\\"false\\\",\\\"skipexpression\\\":\\\"\\\",\\\"showdiamondmarker\\\":false},\\\"stencil\\\":{\\\"id\\\":\\\"SequenceFlow\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-DD8B83FA-D018-4FEC-983D-9C556ED1AB92\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":389.4589889386365,\\\"y\\\":145.43643199075117},\\\"upperLeft\\\":{\\\"x\\\":295.1328079363635,\\\"y\\\":145.15145863424883}},\\\"dockers\\\":[{\\\"x\\\":50,\\\"y\\\":40},{\\\"x\\\":20.5,\\\"y\\\":20.5}],\\\"target\\\":{\\\"resourceId\\\":\\\"sid-DD8B83FA-D018-4FEC-983D-9C556ED1AB92\\\"}},{\\\"resourceId\\\":\\\"sid-ACF047BD-DADC-42C4-8B73-1AD8606DCC00\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"HUIQIAN2\\\",\\\"name\\\":\\\"多人会签2\\\",\\\"documentation\\\":\\\"\\\",\\\"asynchronousdefinition\\\":\\\"false\\\",\\\"exclusivedefinition\\\":\\\"false\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"multiinstance_type\\\":\\\"Parallel\\\",\\\"multiinstance_cardinality\\\":\\\"2\\\",\\\"multiinstance_collection\\\":\\\"\\\",\\\"multiinstance_variable\\\":\\\"\\\",\\\"multiinstance_condition\\\":\\\"\\\",\\\"isforcompensation\\\":\\\"false\\\",\\\"usertaskassignment\\\":{\\\"assignment\\\":{\\\"type\\\":\\\"idm\\\",\\\"idm\\\":{\\\"type\\\":\\\"users\\\",\\\"candidateUsers\\\":[{\\\"id\\\":\\\"2\\\",\\\"firstName\\\":\\\"realfinance1\\\",\\\"lastName\\\":\\\"瑞友1\\\",\\\"email\\\":\\\"service@realfinance.com.cn\\\",\\\"fullName\\\":\\\"realfinance1 瑞友1\\\",\\\"tenantId\\\":\\\"SYSTEM_TENANT\\\",\\\"$$hashKey\\\":\\\"object:2492\\\"},{\\\"id\\\":\\\"3\\\",\\\"firstName\\\":\\\"realfinance2\\\",\\\"lastName\\\":\\\"瑞友2\\\",\\\"email\\\":\\\"service@realfinance.com.cn\\\",\\\"fullName\\\":\\\"realfinance2 瑞友2\\\",\\\"tenantId\\\":\\\"SYSTEM_TENANT\\\",\\\"$$hashKey\\\":\\\"object:2493\\\"}]}}},\\\"formkeydefinition\\\":\\\"\\\",\\\"formreference\\\":\\\"\\\",\\\"formfieldvalidation\\\":true,\\\"duedatedefinition\\\":\\\"\\\",\\\"prioritydefinition\\\":\\\"\\\",\\\"formproperties\\\":\\\"\\\",\\\"tasklisteners\\\":\\\"\\\",\\\"skipexpression\\\":\\\"\\\",\\\"categorydefinition\\\":\\\"\\\",\\\"taskidvariablename\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"UserTask\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-DAB1659E-B5F1-4F9E-B6EB-0E28418AE6D7\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":140,\\\"y\\\":410},\\\"upperLeft\\\":{\\\"x\\\":40,\\\"y\\\":330}},\\\"dockers\\\":[]},{\\\"resourceId\\\":\\\"sid-78FDA1B6-BF18-409A-B9F5-E0BCF4A3C48B\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"多人会签\\\",\\\"documentation\\\":\\\"\\\",\\\"conditionsequenceflow\\\":{\\\"expression\\\":{\\\"type\\\":\\\"static\\\",\\\"staticValue\\\":\\\"${nextNode == 'HUIQIAN'}\\\"}},\\\"executionlisteners\\\":\\\"\\\",\\\"defaultflow\\\":\\\"false\\\",\\\"skipexpression\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"SequenceFlow\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-8D62D0FE-9D1D-4FB5-8D41-6D74856418DE\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":245,\\\"y\\\":254.15625},\\\"upperLeft\\\":{\\\"x\\\":245,\\\"y\\\":185.84375}},\\\"dockers\\\":[{\\\"x\\\":50,\\\"y\\\":40},{\\\"x\\\":50,\\\"y\\\":40}],\\\"target\\\":{\\\"resourceId\\\":\\\"sid-8D62D0FE-9D1D-4FB5-8D41-6D74856418DE\\\"}},{\\\"resourceId\\\":\\\"sid-488D4C1C-2E21-4B88-91B6-807EE64F6281\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"\\\",\\\"documentation\\\":\\\"\\\",\\\"conditionsequenceflow\\\":{\\\"expression\\\":{\\\"type\\\":\\\"static\\\",\\\"staticValue\\\":\\\"${nextNode == 'HUIQIAN2'}\\\"}},\\\"executionlisteners\\\":\\\"\\\",\\\"defaultflow\\\":\\\"false\\\",\\\"skipexpression\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"SequenceFlow\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-ACF047BD-DADC-42C4-8B73-1AD8606DCC00\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":217.18660101374851,\\\"y\\\":329.62571114898975},\\\"upperLeft\\\":{\\\"x\\\":117.8133989862515,\\\"y\\\":185.37428885101025}},\\\"dockers\\\":[{\\\"x\\\":50,\\\"y\\\":40},{\\\"x\\\":50,\\\"y\\\":40}],\\\"target\\\":{\\\"resourceId\\\":\\\"sid-ACF047BD-DADC-42C4-8B73-1AD8606DCC00\\\"}},{\\\"resourceId\\\":\\\"sid-0A1F53EC-2C18-486E-8D96-478C1D257876\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"多人会签2后的用户活动\\\",\\\"documentation\\\":\\\"\\\",\\\"asynchronousdefinition\\\":\\\"false\\\",\\\"exclusivedefinition\\\":\\\"false\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"multiinstance_type\\\":\\\"None\\\",\\\"multiinstance_cardinality\\\":\\\"\\\",\\\"multiinstance_collection\\\":\\\"\\\",\\\"multiinstance_variable\\\":\\\"\\\",\\\"multiinstance_condition\\\":\\\"\\\",\\\"isforcompensation\\\":\\\"false\\\",\\\"usertaskassignment\\\":{\\\"assignment\\\":{\\\"type\\\":\\\"static\\\",\\\"assignee\\\":\\\"${ASSIGNEE}\\\"}},\\\"formkeydefinition\\\":\\\"\\\",\\\"formreference\\\":\\\"\\\",\\\"formfieldvalidation\\\":true,\\\"duedatedefinition\\\":\\\"\\\",\\\"prioritydefinition\\\":\\\"\\\",\\\"formproperties\\\":\\\"\\\",\\\"tasklisteners\\\":\\\"\\\",\\\"skipexpression\\\":\\\"\\\",\\\"categorydefinition\\\":\\\"\\\",\\\"taskidvariablename\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"UserTask\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-B02BB840-E2D8-431F-813A-D528B9ECDF71\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":580,\\\"y\\\":380},\\\"upperLeft\\\":{\\\"x\\\":480,\\\"y\\\":300}},\\\"dockers\\\":[]},{\\\"resourceId\\\":\\\"sid-DAB1659E-B5F1-4F9E-B6EB-0E28418AE6D7\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"\\\",\\\"documentation\\\":\\\"\\\",\\\"conditionsequenceflow\\\":\\\"\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"defaultflow\\\":\\\"false\\\",\\\"skipexpression\\\":\\\"\\\",\\\"showdiamondmarker\\\":false},\\\"stencil\\\":{\\\"id\\\":\\\"SequenceFlow\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-0A1F53EC-2C18-486E-8D96-478C1D257876\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":479.01678292594244,\\\"y\\\":367.5729843840477},\\\"upperLeft\\\":{\\\"x\\\":140.3894670740576,\\\"y\\\":351.2629531159523}},\\\"dockers\\\":[{\\\"x\\\":50,\\\"y\\\":40},{\\\"x\\\":46,\\\"y\\\":49}],\\\"target\\\":{\\\"resourceId\\\":\\\"sid-0A1F53EC-2C18-486E-8D96-478C1D257876\\\"}},{\\\"resourceId\\\":\\\"sid-B02BB840-E2D8-431F-813A-D528B9ECDF71\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"\\\",\\\"documentation\\\":\\\"\\\",\\\"conditionsequenceflow\\\":\\\"\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"defaultflow\\\":\\\"false\\\",\\\"skipexpression\\\":\\\"\\\",\\\"showdiamondmarker\\\":false},\\\"stencil\\\":{\\\"id\\\":\\\"SequenceFlow\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-A4FCF12B-12D1-4B3C-9140-37B6FC1E8A01\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":614.8652864045,\\\"y\\\":299.7305728090001},\\\"upperLeft\\\":{\\\"x\\\":550.1347135955,\\\"y\\\":170.26942719099992}},\\\"dockers\\\":[{\\\"x\\\":50,\\\"y\\\":40},{\\\"x\\\":50,\\\"y\\\":40}],\\\"target\\\":{\\\"resourceId\\\":\\\"sid-A4FCF12B-12D1-4B3C-9140-37B6FC1E8A01\\\"}},{\\\"resourceId\\\":\\\"sid-A4FCF12B-12D1-4B3C-9140-37B6FC1E8A01\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"多人会签2后的用户活动2\\\",\\\"documentation\\\":\\\"\\\",\\\"asynchronousdefinition\\\":\\\"false\\\",\\\"exclusivedefinition\\\":\\\"false\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"multiinstance_type\\\":\\\"None\\\",\\\"multiinstance_cardinality\\\":\\\"\\\",\\\"multiinstance_collection\\\":\\\"\\\",\\\"multiinstance_variable\\\":\\\"\\\",\\\"multiinstance_condition\\\":\\\"\\\",\\\"isforcompensation\\\":\\\"false\\\",\\\"usertaskassignment\\\":{\\\"assignment\\\":{\\\"type\\\":\\\"static\\\",\\\"assignee\\\":\\\"${ASSIGNEE}\\\"}},\\\"formkeydefinition\\\":\\\"\\\",\\\"formreference\\\":\\\"\\\",\\\"formfieldvalidation\\\":true,\\\"duedatedefinition\\\":\\\"\\\",\\\"prioritydefinition\\\":\\\"\\\",\\\"formproperties\\\":\\\"\\\",\\\"tasklisteners\\\":\\\"\\\",\\\"skipexpression\\\":\\\"\\\",\\\"categorydefinition\\\":\\\"\\\",\\\"taskidvariablename\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"UserTask\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-B4B71D36-8F60-459A-935C-F3AED18588EE\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":685,\\\"y\\\":170},\\\"upperLeft\\\":{\\\"x\\\":585,\\\"y\\\":90}},\\\"dockers\\\":[]},{\\\"resourceId\\\":\\\"sid-B4B71D36-8F60-459A-935C-F3AED18588EE\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"\\\",\\\"documentation\\\":\\\"\\\",\\\"conditionsequenceflow\\\":\\\"\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"defaultflow\\\":\\\"false\\\",\\\"skipexpression\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"SequenceFlow\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-BD6DF031-CB18-4892-9084-90F1579A9E50\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":780.6736934074885,\\\"y\\\":106.13588132428703},\\\"upperLeft\\\":{\\\"x\\\":685.5919315925115,\\\"y\\\":61.28599367571297}},\\\"dockers\\\":[{\\\"x\\\":50,\\\"y\\\":40},{\\\"x\\\":14,\\\"y\\\":14}],\\\"target\\\":{\\\"resourceId\\\":\\\"sid-BD6DF031-CB18-4892-9084-90F1579A9E50\\\"}}],\\\"stencil\\\":{\\\"id\\\":\\\"BPMNDiagram\\\"},\\\"stencilset\\\":{\\\"namespace\\\":\\\"http://b3mn.org/stencilset/bpmn2.0#\\\",\\\"url\\\":\\\"../editor/stencilsets/bpmn2.0/bpmn2.0.json\\\"}}\"],\"name\":[\"3\"],\"key\":[\"412\"],\"description\":[\"\"],\"newversion\":[\"false\"],\"comment\":[\"\"],\"lastUpdated\":[\"2020-11-21T20:05:41.600+0000\"]}";
            LinkedMultiValueMap map = JSON.parseObject(modelUpdateJson, LinkedMultiValueMap.class);
            String modelJSON = modelerFacade.getModelJSON(modelId);
            JSONObject model = JSONObject.parseObject(modelJSON);
            map.put("lastUpdated", Collections.singletonList(model.getString("lastUpdated")));
            modelerFacade.saveModel(modelId,map);
            // 发布
            processDefFacade.deploy(id);

            // 启动流程
            String bizKey = UUID.randomUUID().toString();
            String plan = flowFacade.startProcess("testBiz1", bizKey,null);
            String plan1 = flowFacade.startProcess("testBiz1", bizKey,null); // 重复开启流程
            Assert.assertEquals(plan,plan1); // 两次启动流程得到的流程实例ID相等

            // 查询用户
            TaskQueryCriteria queryCriteria = new TaskQueryCriteria();
            queryCriteria.setTenantId("SYSTEM_TENANT");
            //queryCriteria.setCandidateUserId(1);
            Page<TaskDto> tasks = flowFacade.listTask(queryCriteria, PageRequest.of(0, 10));
            Assert.assertFalse(tasks.getContent().isEmpty());

            String taskId = tasks.getContent().get(0).getId();
            Task task = taskService.createTaskQuery().taskId(taskId).singleResult();
            BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
            Collection<FlowElement> flowElements = bpmnModel.getProcesses().get(0).getFlowElements();
            UserTask userTask1 = (UserTask) flowElements.stream().filter(e -> e instanceof UserTask).findFirst().get();
            UserTask userTask2 = (UserTask) flowElements.stream().filter(e -> e instanceof UserTask).skip(1).findFirst().get();
            UserTask userTask3 = (UserTask) flowElements.stream().filter(e -> e instanceof UserTask).skip(2).findFirst().get();
            UserTask userTask4 = (UserTask) flowElements.stream().filter(e -> e instanceof UserTask).skip(3).findFirst().get();

//            runtimeService.setVariableLocal(task.getExecutionId(),"nextNode","LAOBANSHENPI");

            Execution execution = runtimeService.createExecutionQuery()
                    .executionId(task.getExecutionId())
                    .singleResult();
            Map<String, Object> variables1 = taskService.getVariables(task.getId());
            variables1.put(NEXT_NODE_KEY,"HUIQIAN");
            //variables.put("amount",100);
            UserTask nextUserTaskNode1 = flowService.findNextUserTaskNode((ExecutionEntity) execution, bpmnModel, variables1);
            Map<String, Object> variables2 = taskService.getVariables(task.getId());
            variables2.put(NEXT_NODE_KEY,"TIJIAO");
            variables2.put("amount",100);
            UserTask nextUserTaskNode2 = flowService.findNextUserTaskNode((ExecutionEntity) execution, bpmnModel, variables2);

            Map<String, Object> variables = taskService.getVariables(task.getId());


            NextUserTaskInfo nextUserTaskInfo = new NextUserTaskInfo();
            nextUserTaskInfo.setAssignees(Stream.of("1","2").collect(Collectors.toList()));
            nextUserTaskInfo.setDueDate(LocalDateTime.of(2021,5,13,0,0));
            nextUserTaskInfo.setPriority(100);
            try {
                taskService.createTaskCompletionBuilder()
                        .variable(NEXT_NODE_KEY,"HUIQIAN2")
                        .transientVariable(NEXT_USER_TASK_INFO,nextUserTaskInfo)
                        .variable("amount",100)
                        .taskId(task.getId())
                        .complete();
            } catch (Exception e) {
                throw e;
            }
            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId())
                    .singleResult();
            Map<String, Object> processVariables = processInstance.getProcessVariables();

            List<Task> list = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();
            System.out.println(list.size());
            int i = 1;
            for (Task t : list) {
                taskService.createTaskCompletionBuilder()
                        .transientVariable("ASSIGNEE",i ++)
                        .taskId(t.getId())
                        .complete();
            }


            List<Task> list1 = taskService.createTaskQuery().processInstanceId(task.getProcessInstanceId()).list();
            System.out.println(list1.size());

            Task task1 = list1.get(0);
            ProcessInstance processInstance1 = runtimeService.createProcessInstanceQuery().processInstanceId(task1.getProcessInstanceId()).singleResult();
            Map<String, Object> processVariables1 = processInstance1.getProcessVariables();

            taskService.complete(task1.getId());

            //Object nextNode = runtimeService.getVariableLocal(task.getExecutionId(), NEXT_NODE_KEY);
            //System.out.println(nextNode);
            //runtimeService.removeVariableLocal(task.getExecutionId(),NEXT_NODE_KEY);
//            taskService.createTaskCompletionBuilder().outcome(userTask2.getId()).taskId(task.getId()).complete();
            List<Process> processes = bpmnModel.getProcesses();
        } finally {
            RpcUtils.removeRpcContext();
        }
    }


    @Test
    public void test2() {

    }
}
*/
