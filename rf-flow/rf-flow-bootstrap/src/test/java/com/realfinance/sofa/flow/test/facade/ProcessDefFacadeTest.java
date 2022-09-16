package com.realfinance.sofa.flow.test.facade;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.common.rpc.util.RpcUtils;
import com.realfinance.sofa.flow.facade.ModelerFacade;
import com.realfinance.sofa.flow.facade.ProcessDefFacade;
import com.realfinance.sofa.flow.model.BizModelDto;
import com.realfinance.sofa.flow.model.BizModelSaveDto;
import com.realfinance.sofa.flow.service.mapstruct.CustomMapper;
import com.realfinance.sofa.flow.test.base.AbstractTestBase;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.LinkedMultiValueMap;

import java.util.Collections;
import java.util.Optional;

public class ProcessDefFacadeTest extends AbstractTestBase {

    private static final Logger log = LoggerFactory.getLogger(ProcessDefFacadeTest.class);

    @SofaReference(interfaceType = ProcessDefFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private ProcessDefFacade processDefFacade;
    @SofaReference(interfaceType = ModelerFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private ModelerFacade modelerFacade;

    @Mock
    private CustomMapper customMapper;

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
            LinkedMultiValueMap map = JSON.parseObject(modelUpdateJson, LinkedMultiValueMap.class);
            String modelJSON = modelerFacade.getModelJSON(modelId);
            JSONObject model = JSONObject.parseObject(modelJSON);
            map.put("lastUpdated", Collections.singletonList(model.getString("lastUpdated")));
            modelerFacade.saveModel(modelId,map);
            // 发布
            processDefFacade.deploy(id);

        } finally {
            RpcUtils.removeRpcContext();
        }
    }




    String modelUpdateJson = "{\"modeltype\":[\"model\"],\"json_xml\":[\"{\\\"modelId\\\":\\\"7fd618b3-235d-11eb-b978-005056c00008\\\",\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":1200,\\\"y\\\":1050},\\\"upperLeft\\\":{\\\"x\\\":0,\\\"y\\\":0}},\\\"properties\\\":{\\\"process_id\\\":\\\"412\\\",\\\"name\\\":\\\"3\\\",\\\"documentation\\\":\\\"\\\",\\\"process_author\\\":\\\"按不出\\\",\\\"process_version\\\":\\\"\\\",\\\"process_namespace\\\":\\\"http://www.flowable.org/processdef\\\",\\\"process_historylevel\\\":\\\"\\\",\\\"isexecutable\\\":true,\\\"dataproperties\\\":\\\"\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"eventlisteners\\\":\\\"\\\",\\\"signaldefinitions\\\":\\\"\\\",\\\"messagedefinitions\\\":\\\"\\\",\\\"escalationdefinitions\\\":\\\"\\\",\\\"process_potentialstarteruser\\\":\\\"\\\",\\\"process_potentialstartergroup\\\":\\\"\\\",\\\"iseagerexecutionfetch\\\":\\\"false\\\"},\\\"childShapes\\\":[{\\\"resourceId\\\":\\\"startEvent1\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"\\\",\\\"documentation\\\":\\\"\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"initiator\\\":\\\"\\\",\\\"formkeydefinition\\\":\\\"\\\",\\\"formreference\\\":\\\"\\\",\\\"formfieldvalidation\\\":true,\\\"formproperties\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"StartNoneEvent\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-79171E55-B86A-4158-B9F3-7D6EF4E6A7F8\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":105,\\\"y\\\":160},\\\"upperLeft\\\":{\\\"x\\\":75,\\\"y\\\":130}},\\\"dockers\\\":[]},{\\\"resourceId\\\":\\\"sid-BD6DF031-CB18-4892-9084-90F1579A9E50\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"\\\",\\\"documentation\\\":\\\"\\\",\\\"executionlisteners\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"EndNoneEvent\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":763,\\\"y\\\":159},\\\"upperLeft\\\":{\\\"x\\\":735,\\\"y\\\":131}},\\\"dockers\\\":[]},{\\\"resourceId\\\":\\\"sid-C6E2261A-5A2C-4892-B130-129E5E99EAB1\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"提交\\\",\\\"documentation\\\":\\\"\\\",\\\"asynchronousdefinition\\\":\\\"false\\\",\\\"exclusivedefinition\\\":\\\"false\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"multiinstance_type\\\":\\\"None\\\",\\\"multiinstance_cardinality\\\":\\\"\\\",\\\"multiinstance_collection\\\":\\\"\\\",\\\"multiinstance_variable\\\":\\\"\\\",\\\"multiinstance_condition\\\":\\\"\\\",\\\"isforcompensation\\\":\\\"false\\\",\\\"usertaskassignment\\\":{\\\"assignment\\\":{\\\"type\\\":\\\"idm\\\",\\\"idm\\\":{\\\"type\\\":\\\"user\\\",\\\"assignee\\\":{\\\"firstName\\\":\\\"realfinance\\\",\\\"lastName\\\":\\\"瑞友信息技术有限公司\\\",\\\"tenantId\\\":\\\"SYSTEM_TENANT\\\",\\\"fullName\\\":\\\"realfinance 瑞友信息技术有限公司\\\",\\\"id\\\":1,\\\"$$hashKey\\\":\\\"object:2787\\\"}}}},\\\"formkeydefinition\\\":\\\"\\\",\\\"formreference\\\":\\\"\\\",\\\"formfieldvalidation\\\":true,\\\"duedatedefinition\\\":\\\"\\\",\\\"prioritydefinition\\\":\\\"\\\",\\\"formproperties\\\":\\\"\\\",\\\"tasklisteners\\\":\\\"\\\",\\\"skipexpression\\\":\\\"\\\",\\\"categorydefinition\\\":\\\"\\\",\\\"taskidvariablename\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"UserTask\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-46CA7E32-717E-4FE6-8FD2-FF03F9CB9527\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":415,\\\"y\\\":185},\\\"upperLeft\\\":{\\\"x\\\":315,\\\"y\\\":105}},\\\"dockers\\\":[]},{\\\"resourceId\\\":\\\"sid-46CA7E32-717E-4FE6-8FD2-FF03F9CB9527\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"\\\",\\\"documentation\\\":\\\"\\\",\\\"conditionsequenceflow\\\":\\\"\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"defaultflow\\\":\\\"false\\\",\\\"skipexpression\\\":\\\"\\\",\\\"showdiamondmarker\\\":false},\\\"stencil\\\":{\\\"id\\\":\\\"SequenceFlow\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-B8966BB4-79B5-47CA-BFD6-60F1A291CB2B\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":524.09375,\\\"y\\\":145},\\\"upperLeft\\\":{\\\"x\\\":415.9375,\\\"y\\\":145}},\\\"dockers\\\":[{\\\"x\\\":50,\\\"y\\\":40},{\\\"x\\\":28,\\\"y\\\":40}],\\\"target\\\":{\\\"resourceId\\\":\\\"sid-B8966BB4-79B5-47CA-BFD6-60F1A291CB2B\\\"}},{\\\"resourceId\\\":\\\"sid-B8966BB4-79B5-47CA-BFD6-60F1A291CB2B\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"审批\\\",\\\"documentation\\\":\\\"\\\",\\\"asynchronousdefinition\\\":\\\"false\\\",\\\"exclusivedefinition\\\":\\\"false\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"multiinstance_type\\\":\\\"None\\\",\\\"multiinstance_cardinality\\\":\\\"\\\",\\\"multiinstance_collection\\\":\\\"\\\",\\\"multiinstance_variable\\\":\\\"\\\",\\\"multiinstance_condition\\\":\\\"\\\",\\\"isforcompensation\\\":\\\"false\\\",\\\"usertaskassignment\\\":\\\"\\\",\\\"formkeydefinition\\\":\\\"\\\",\\\"formreference\\\":\\\"\\\",\\\"formfieldvalidation\\\":true,\\\"duedatedefinition\\\":\\\"\\\",\\\"prioritydefinition\\\":\\\"\\\",\\\"formproperties\\\":\\\"\\\",\\\"tasklisteners\\\":\\\"\\\",\\\"skipexpression\\\":\\\"\\\",\\\"categorydefinition\\\":\\\"\\\",\\\"taskidvariablename\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"UserTask\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-39252FC3-D044-4F50-89CC-C11D3A19B1A7\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":625,\\\"y\\\":185},\\\"upperLeft\\\":{\\\"x\\\":525,\\\"y\\\":105}},\\\"dockers\\\":[]},{\\\"resourceId\\\":\\\"sid-39252FC3-D044-4F50-89CC-C11D3A19B1A7\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"\\\",\\\"documentation\\\":\\\"\\\",\\\"conditionsequenceflow\\\":\\\"\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"defaultflow\\\":\\\"false\\\",\\\"skipexpression\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"SequenceFlow\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-BD6DF031-CB18-4892-9084-90F1579A9E50\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":734.40625,\\\"y\\\":145},\\\"upperLeft\\\":{\\\"x\\\":625.6171875,\\\"y\\\":145}},\\\"dockers\\\":[{\\\"x\\\":50,\\\"y\\\":40},{\\\"x\\\":14,\\\"y\\\":14}],\\\"target\\\":{\\\"resourceId\\\":\\\"sid-BD6DF031-CB18-4892-9084-90F1579A9E50\\\"}},{\\\"resourceId\\\":\\\"sid-79171E55-B86A-4158-B9F3-7D6EF4E6A7F8\\\",\\\"properties\\\":{\\\"overrideid\\\":\\\"\\\",\\\"name\\\":\\\"\\\",\\\"documentation\\\":\\\"\\\",\\\"conditionsequenceflow\\\":\\\"\\\",\\\"executionlisteners\\\":\\\"\\\",\\\"defaultflow\\\":\\\"false\\\",\\\"skipexpression\\\":\\\"\\\"},\\\"stencil\\\":{\\\"id\\\":\\\"SequenceFlow\\\"},\\\"childShapes\\\":[],\\\"outgoing\\\":[{\\\"resourceId\\\":\\\"sid-C6E2261A-5A2C-4892-B130-129E5E99EAB1\\\"}],\\\"bounds\\\":{\\\"lowerRight\\\":{\\\"x\\\":314.5859375,\\\"y\\\":145},\\\"upperLeft\\\":{\\\"x\\\":105.501953125,\\\"y\\\":145}},\\\"dockers\\\":[{\\\"x\\\":15,\\\"y\\\":15},{\\\"x\\\":50,\\\"y\\\":40}],\\\"target\\\":{\\\"resourceId\\\":\\\"sid-C6E2261A-5A2C-4892-B130-129E5E99EAB1\\\"}}],\\\"stencil\\\":{\\\"id\\\":\\\"BPMNDiagram\\\"},\\\"stencilset\\\":{\\\"namespace\\\":\\\"http://b3mn.org/stencilset/bpmn2.0#\\\",\\\"url\\\":\\\"../editor/stencilsets/bpmn2.0/bpmn2.0.json\\\"}}\"],\"name\":[\"3\"],\"key\":[\"412\"],\"description\":[\"\"],\"newversion\":[\"false\"],\"comment\":[\"\"],\"lastUpdated\":[\"2020-11-17 10:50:26\"]}";
}
