package com.realfinance.sofa.flow.test.bpmn;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realfinance.sofa.common.rpc.util.RpcUtils;
import com.realfinance.sofa.flow.test.base.AbstractTestBase;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.CustomProperty;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.impl.identity.Authentication;
import org.flowable.engine.FormService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.form.FormProperty;
import org.flowable.engine.form.TaskFormData;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.Execution;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.model.ModelRepresentation;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SomeTest extends AbstractTestBase {

    @Autowired
    RuntimeService runtimeService;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    TaskService taskService;
    @Autowired
    FormService formService;

    @Test
    public void test1() throws IOException {
        InputStream inputStream = new ClassPathResource("/bpmn/供应商审核.bpmn20.xml").getInputStream();
        Deployment deployment = repositoryService.createDeployment().addInputStream("供应商审核.bpmn20.xml",inputStream)
                .key("供应商审核")
                .deploy();

        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .singleResult();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());

        List<Task> list = taskService.createTaskQuery().processInstanceId(processInstance.getId())
                .list();

        Task task = list.get(0);
        taskService.complete(task.getId());

        List<Task> list1 = taskService.createTaskQuery().processInstanceId(processInstance.getId())
                .list();
        Task task1 = list1.get(0);
        TaskFormData taskFormData = formService.getTaskFormData(task1.getId());
        Object renderedTaskForm = formService.getRenderedTaskForm(task1.getId());
        taskService.complete(task1.getId());

        System.out.println("------");

    }

    @Resource
    ModelService modelService;

    @Test
    public void test2() throws IOException {
        RpcUtils.setTenantId("test");
       /* InputStream inputStream = new ClassPathResource("/bpmn/123.bpmn20.xml").getInputStream();
        Deployment deployment = repositoryService.createDeployment()
                .addInputStream("123.bpmn20.xml",inputStream)
                .key("test")
                .deploy();*/
        ModelRepresentation modelRepresentation = new ModelRepresentation();
        modelRepresentation.setKey("123");
        modelRepresentation.setName("name123");

        String json = "{\"modelId\":\"d4e6c676-3f41-11eb-a714-005056c00001\",\"bounds\":{\"lowerRight\":{\"x\":1200,\"y\":1050},\"upperLeft\":{\"x\":0,\"y\":0}},\"properties\":{\"process_id\":\"BM_2_1\",\"name\":\"供应商审核\",\"documentation\":\"test\",\"process_author\":\"111\",\"process_version\":\"\",\"process_namespace\":\"http://www.flowable.org/processdef\",\"process_historylevel\":\"\",\"isexecutable\":true,\"dataproperties\":\"{\\\"items\\\":[{\\\"dataproperty_id\\\":\\\"new_data_object_1\\\",\\\"dataproperty_name\\\":\\\"金额\\\",\\\"dataproperty_type\\\":\\\"int\\\",\\\"readable\\\":true,\\\"writable\\\":true,\\\"dataproperty_value\\\":\\\"0\\\",\\\"$$hashKey\\\":\\\"uiGrid-000V\\\"}]}\",\"executionlisteners\":\"\",\"eventlisteners\":\"{\\\"eventListeners\\\":[{\\\"event\\\":\\\"PROCESS_CANCELLED\\\",\\\"implementation\\\":\\\"Rethrow as message 11111111111\\\",\\\"className\\\":\\\"\\\",\\\"delegateExpression\\\":\\\"\\\",\\\"retrowEvent\\\":false,\\\"$$hashKey\\\":\\\"uiGrid-001H\\\",\\\"fields\\\":[],\\\"events\\\":[{\\\"event\\\":\\\"PROCESS_CANCELLED\\\",\\\"$$hashKey\\\":\\\"object:513\\\"}],\\\"rethrowEvent\\\":true,\\\"rethrowType\\\":\\\"message\\\",\\\"messagename\\\":\\\"11111111111\\\"}]}\",\"signaldefinitions\":\"[{\\\"id\\\":\\\"aa\\\",\\\"name\\\":\\\"aaaaaaaaaa\\\",\\\"scope\\\":\\\"processInstance\\\",\\\"$$hashKey\\\":\\\"uiGrid-003C\\\"}]\",\"messagedefinitions\":\"\",\"escalationdefinitions\":\"\",\"process_potentialstarteruser\":\"AA\",\"process_potentialstartergroup\":\"\",\"iseagerexecutionfetch\":\"false\"},\"childShapes\":[{\"resourceId\":\"startEvent1\",\"properties\":{\"overrideid\":\"\",\"name\":\"\",\"documentation\":\"\",\"executionlisteners\":\"\",\"initiator\":\"\",\"formkeydefinition\":\"\",\"formreference\":\"\",\"formfieldvalidation\":true,\"formproperties\":\"\"},\"stencil\":{\"id\":\"StartNoneEvent\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-1DACD0B6-1703-4714-941D-262B685DA38E\"}],\"bounds\":{\"lowerRight\":{\"x\":120,\"y\":180},\"upperLeft\":{\"x\":90,\"y\":150}},\"dockers\":[]},{\"resourceId\":\"sid-B941C66E-929D-48A7-B78B-D1960F12AD9F\",\"properties\":{\"overrideid\":\"\",\"name\":\"提交\",\"documentation\":\"\",\"asynchronousdefinition\":\"false\",\"exclusivedefinition\":\"false\",\"executionlisteners\":\"\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_variable\":\"\",\"multiinstance_condition\":\"\",\"isforcompensation\":\"false\",\"usertaskassignment\":{\"assignment\":{\"type\":\"static\"}},\"formkeydefinition\":\"\",\"formreference\":\"\",\"formfieldvalidation\":true,\"duedatedefinition\":\"\",\"prioritydefinition\":\"\",\"formproperties\":{\"formProperties\":[{\"id\":\"HQ_USER\",\"name\":\"会签人员\",\"type\":\"users\",\"readable\":true,\"writable\":true,\"maxSize\":\"3\",\"userScope\":\"\",\"variable\":\"\",\"groupScope\":\"采购部\",\"$$hashKey\":\"uiGrid-000E\"},{\"id\":\"LD_USER\",\"name\":\"领导审批\",\"type\":\"users\",\"readable\":true,\"writable\":true,\"maxSize\":1,\"userScope\":\"ld1,ld2\",\"$$hashKey\":\"uiGrid-000G\"},{\"id\":\"new_property_1\",\"name\":\"枚举\",\"type\":\"enum\",\"readable\":true,\"writable\":true,\"$$hashKey\":\"uiGrid-000Z\",\"enumValues\":[{\"id\":\"value1\",\"name\":\"Value 1\",\"$$hashKey\":\"uiGrid-0017\"},{\"id\":\"value2\",\"name\":\"Value 2\",\"$$hashKey\":\"uiGrid-0019\"}]}]},\"tasklisteners\":\"\",\"skipexpression\":\"\",\"categorydefinition\":\"\",\"taskidvariablename\":\"\"},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-727215FD-2710-4710-887B-E6EAB32DF54A\"}],\"bounds\":{\"lowerRight\":{\"x\":250,\"y\":200},\"upperLeft\":{\"x\":150,\"y\":120}},\"dockers\":[]},{\"resourceId\":\"sid-1DACD0B6-1703-4714-941D-262B685DA38E\",\"properties\":{\"overrideid\":\"\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"executionlisteners\":\"\",\"defaultflow\":\"false\",\"skipexpression\":\"\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-B941C66E-929D-48A7-B78B-D1960F12AD9F\"}],\"bounds\":{\"lowerRight\":{\"x\":149.27481967066748,\"y\":164.16619116687724},\"upperLeft\":{\"x\":120.84236782933252,\"y\":162.66974633312276}},\"dockers\":[{\"x\":15,\"y\":15},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"sid-B941C66E-929D-48A7-B78B-D1960F12AD9F\"}},{\"resourceId\":\"sid-63B78DC7-7052-4D99-861C-3F9A5F0F39C4\",\"properties\":{\"overrideid\":\"\",\"name\":\"审批\",\"documentation\":\"\",\"asynchronousdefinition\":\"false\",\"exclusivedefinition\":\"false\",\"executionlisteners\":\"\",\"multiinstance_type\":\"Parallel\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"HQ_USER\",\"multiinstance_variable\":\"hq\",\"multiinstance_condition\":\"\",\"isforcompensation\":\"false\",\"usertaskassignment\":{\"assignment\":{\"type\":\"static\",\"assignee\":\"${hq}\"}},\"formkeydefinition\":\"\",\"formreference\":\"\",\"formfieldvalidation\":true,\"duedatedefinition\":\"\",\"prioritydefinition\":\"\",\"formproperties\":{\"formProperties\":[{\"id\":\"HQ_PASS\",\"name\":\"是否通过\",\"type\":\"boolean\",\"readable\":true,\"writable\":true,\"$$hashKey\":\"uiGrid-0082\",\"required\":true}]},\"tasklisteners\":\"\",\"skipexpression\":\"\",\"categorydefinition\":\"\",\"taskidvariablename\":\"\"},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-C951ECC3-F798-42EF-A894-7D3088793BBA\"}],\"bounds\":{\"lowerRight\":{\"x\":400,\"y\":205},\"upperLeft\":{\"x\":300,\"y\":125}},\"dockers\":[]},{\"resourceId\":\"sid-727215FD-2710-4710-887B-E6EAB32DF54A\",\"properties\":{\"overrideid\":\"\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"executionlisteners\":\"\",\"defaultflow\":\"false\",\"skipexpression\":\"\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-63B78DC7-7052-4D99-861C-3F9A5F0F39C4\"}],\"bounds\":{\"lowerRight\":{\"x\":299.78125,\"y\":165},\"upperLeft\":{\"x\":250.21875,\"y\":160}},\"dockers\":[{\"x\":50,\"y\":40},{\"x\":275,\"y\":160},{\"x\":275,\"y\":165},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"sid-63B78DC7-7052-4D99-861C-3F9A5F0F39C4\"}},{\"resourceId\":\"sid-B7339A59-2C9F-42DE-8B1C-C460CE169879\",\"properties\":{\"overrideid\":\"\",\"name\":\"审批2\\n\",\"documentation\":\"\",\"asynchronousdefinition\":\"false\",\"exclusivedefinition\":\"false\",\"executionlisteners\":\"\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_variable\":\"\",\"multiinstance_condition\":\"\",\"isforcompensation\":\"false\",\"usertaskassignment\":{\"assignment\":{\"type\":\"static\",\"assignee\":\"${LD_USER}\"}},\"formkeydefinition\":\"\",\"formreference\":\"\",\"formfieldvalidation\":true,\"duedatedefinition\":\"\",\"prioritydefinition\":\"\",\"formproperties\":\"\",\"tasklisteners\":\"\",\"skipexpression\":\"\",\"categorydefinition\":\"\",\"taskidvariablename\":\"\"},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-21AC4FA6-2100-49B2-A59A-90FF6DCE2C4D\"}],\"bounds\":{\"lowerRight\":{\"x\":535,\"y\":200},\"upperLeft\":{\"x\":435,\"y\":120}},\"dockers\":[]},{\"resourceId\":\"sid-C951ECC3-F798-42EF-A894-7D3088793BBA\",\"properties\":{\"overrideid\":\"\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"executionlisteners\":\"\",\"defaultflow\":\"false\",\"skipexpression\":\"\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-B7339A59-2C9F-42DE-8B1C-C460CE169879\"}],\"bounds\":{\"lowerRight\":{\"x\":434.4303726662332,\"y\":163.12705083949012},\"upperLeft\":{\"x\":400.5696273337668,\"y\":161.87294916050988}},\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"sid-B7339A59-2C9F-42DE-8B1C-C460CE169879\"}},{\"resourceId\":\"sid-30BDE863-C86C-48E8-9ED6-709F6B76D2D2\",\"properties\":{\"overrideid\":\"\",\"name\":\"\",\"documentation\":\"\",\"executionlisteners\":\"\"},\"stencil\":{\"id\":\"EndNoneEvent\"},\"childShapes\":[],\"outgoing\":[],\"bounds\":{\"lowerRight\":{\"x\":618,\"y\":179},\"upperLeft\":{\"x\":590,\"y\":151}},\"dockers\":[]},{\"resourceId\":\"sid-21AC4FA6-2100-49B2-A59A-90FF6DCE2C4D\",\"properties\":{\"overrideid\":\"\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"executionlisteners\":\"\",\"defaultflow\":\"false\",\"skipexpression\":\"\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-30BDE863-C86C-48E8-9ED6-709F6B76D2D2\"}],\"bounds\":{\"lowerRight\":{\"x\":589.3828125,\"y\":165},\"upperLeft\":{\"x\":535.6484375,\"y\":160}},\"dockers\":[{\"x\":50,\"y\":40},{\"x\":562.5,\"y\":160},{\"x\":562.5,\"y\":165},{\"x\":14,\"y\":14}],\"target\":{\"resourceId\":\"sid-30BDE863-C86C-48E8-9ED6-709F6B76D2D2\"}}],\"stencil\":{\"id\":\"BPMNDiagram\"},\"stencilset\":{\"namespace\":\"http://b3mn.org/stencilset/bpmn2.0#\",\"url\":\"../editor/stencilsets/bpmn2.0/bpmn2.0.json\"}}";

        Model model = modelService
                .createModel(modelRepresentation, json, "lrj");

        BpmnModel bpmnModel = modelService.getBpmnModel(model);
        byte[] xmlBytes = modelService.getBpmnXML(bpmnModel);

        Deployment deployment = repositoryService.createDeployment()
                .addBpmnModel("123.bpmn20.xml",bpmnModel)
                .key("123")
                .tenantId("test")
                .deploy();

        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .processDefinitionTenantId("test")
                .singleResult();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId());

        List<Task> list = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .list();

        Task task = list.get(0);
     /*   UserTask flowElement = (UserTask) bpmnModel.getFlowElement("");
        List<org.flowable.bpmn.model.FormProperty> formProperties1 = flowElement.getFormProperties();
        System.out.println();*/

        Task xxx = taskService.createTaskBuilder()
                .assignee("XXX")
                .parentTaskId(task.getId())
                .create();

        TaskFormData taskFormData1 = formService.getTaskFormData(task.getId());
        ObjectMapper objectMapper = new ObjectMapper();
        List<FormProperty> formProperties = taskFormData1.getFormProperties();
        for (FormProperty formProperty : formProperties) {
            System.out.println(objectMapper.writeValueAsString(formProperty));
        }

//        FormInfo taskFormModel = taskService.getTaskFormModel(task.getId());

//        System.out.println(objectMapper.writeValueAsString(taskFormModel));

        Object renderedTaskForm1 = formService.getRenderedTaskForm(task.getId());

        HashMap<String, String> formData = new HashMap<>();
        formData.put(taskFormData1.getFormProperties().get(0).getId(),"A,B,C");
        formData.put(taskFormData1.getFormProperties().get(1).getId(),null); //
        formService.submitTaskFormData(task.getId(), formData);
//        taskService.complete(task.getId());
        List<Task> list1 = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .list();
        int i = 0;
        for (Task task1 : list1) {
            TaskFormData taskFormData = formService.getTaskFormData(task1.getId());
            Object renderedTaskForm = formService.getRenderedTaskForm(task1.getId());
            Map<String,String> formData2 =  new HashMap<>();
            formData2.put("HQ_PASS",String.valueOf(i ++ > 0));


           /* if (i == 1) {
                runtimeService.createChangeActivityStateBuilder()
                        .processInstanceId(processInstance.getId())
                        .moveActivityIdTo(task1.getTaskDefinitionKey(),task.getTaskDefinitionKey())
                        .changeState();
                break;
            }*/

            formService.submitTaskFormData(task1.getId(),formData2);
        }

        List<Execution> execution = runtimeService.createExecutionQuery()
                .processInstanceId(processInstance.getId()).list();

        Map<String, Object> variables = runtimeService.getVariables(execution.get(0).getId());


        List<Task> list2 = taskService.createTaskQuery()
                .processInstanceId(processInstance.getId())
                .list();

        Task task1 = list2.get(0);
        TaskFormData taskFormData2 = formService.getTaskFormData(task1.getId());

        taskService.complete(task1.getId());
        System.out.println("------");
    }


    @Test
    public void test3() {
        RpcUtils.setTenantId("test");
       /* InputStream inputStream = new ClassPathResource("/bpmn/123.bpmn20.xml").getInputStream();
        Deployment deployment = repositoryService.createDeployment()
                .addInputStream("123.bpmn20.xml",inputStream)
                .key("test")
                .deploy();*/
        ModelRepresentation modelRepresentation = new ModelRepresentation();
        modelRepresentation.setKey("123");
        modelRepresentation.setName("name123");

        String json = "{\"modelId\":\"d4e6c676-3f41-11eb-a714-005056c00001\",\"bounds\":{\"lowerRight\":{\"x\":1200,\"y\":1050},\"upperLeft\":{\"x\":0,\"y\":0}},\"properties\":{\"process_id\":\"BM_2_1\",\"name\":\"供应商审核\",\"documentation\":\"test\",\"process_author\":\"111\",\"process_version\":\"\",\"process_namespace\":\"http://www.flowable.org/processdef\",\"process_historylevel\":\"\",\"isexecutable\":true,\"dataproperties\":\"{\\\"items\\\":[{\\\"dataproperty_id\\\":\\\"new_data_object_1\\\",\\\"dataproperty_name\\\":\\\"金额\\\",\\\"dataproperty_type\\\":\\\"int\\\",\\\"readable\\\":true,\\\"writable\\\":true,\\\"dataproperty_value\\\":\\\"0\\\",\\\"$$hashKey\\\":\\\"uiGrid-000V\\\"}]}\",\"executionlisteners\":\"\",\"eventlisteners\":\"{\\\"eventListeners\\\":[{\\\"event\\\":\\\"PROCESS_CANCELLED\\\",\\\"implementation\\\":\\\"Rethrow as message 11111111111\\\",\\\"className\\\":\\\"\\\",\\\"delegateExpression\\\":\\\"\\\",\\\"retrowEvent\\\":false,\\\"$$hashKey\\\":\\\"uiGrid-001H\\\",\\\"fields\\\":[],\\\"events\\\":[{\\\"event\\\":\\\"PROCESS_CANCELLED\\\",\\\"$$hashKey\\\":\\\"object:513\\\"}],\\\"rethrowEvent\\\":true,\\\"rethrowType\\\":\\\"message\\\",\\\"messagename\\\":\\\"11111111111\\\"}]}\",\"signaldefinitions\":\"[{\\\"id\\\":\\\"aa\\\",\\\"name\\\":\\\"aaaaaaaaaa\\\",\\\"scope\\\":\\\"processInstance\\\",\\\"$$hashKey\\\":\\\"uiGrid-003C\\\"}]\",\"messagedefinitions\":\"\",\"escalationdefinitions\":\"\",\"process_potentialstarteruser\":\"AA\",\"process_potentialstartergroup\":\"\",\"iseagerexecutionfetch\":\"false\"},\"childShapes\":[{\"resourceId\":\"startEvent1\",\"properties\":{\"overrideid\":\"\",\"name\":\"\",\"documentation\":\"\",\"executionlisteners\":\"\",\"initiator\":\"\",\"formkeydefinition\":\"\",\"formreference\":\"\",\"formfieldvalidation\":true,\"formproperties\":\"\"},\"stencil\":{\"id\":\"StartNoneEvent\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-1DACD0B6-1703-4714-941D-262B685DA38E\"}],\"bounds\":{\"lowerRight\":{\"x\":120,\"y\":180},\"upperLeft\":{\"x\":90,\"y\":150}},\"dockers\":[]},{\"resourceId\":\"sid-B941C66E-929D-48A7-B78B-D1960F12AD9F\",\"properties\":{\"overrideid\":\"\",\"name\":\"提交\",\"documentation\":\"\",\"asynchronousdefinition\":\"false\",\"exclusivedefinition\":\"false\",\"executionlisteners\":\"\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_variable\":\"\",\"multiinstance_condition\":\"\",\"isforcompensation\":\"false\",\"usertaskassignment\":{\"assignment\":{\"type\":\"static\"}},\"formkeydefinition\":\"\",\"formreference\":\"\",\"formfieldvalidation\":true,\"duedatedefinition\":\"\",\"prioritydefinition\":\"\",\"formproperties\":{\"formProperties\":[{\"id\":\"HQ_USER\",\"name\":\"会签人员\",\"type\":\"users\",\"readable\":true,\"writable\":true,\"maxSize\":\"3\",\"userScope\":\"\",\"variable\":\"\",\"groupScope\":\"采购部\",\"$$hashKey\":\"uiGrid-000E\"},{\"id\":\"LD_USER\",\"name\":\"领导审批\",\"type\":\"users\",\"readable\":true,\"writable\":true,\"maxSize\":1,\"userScope\":\"ld1,ld2\",\"$$hashKey\":\"uiGrid-000G\"},{\"id\":\"new_property_1\",\"name\":\"枚举\",\"type\":\"enum\",\"readable\":true,\"writable\":true,\"$$hashKey\":\"uiGrid-000Z\",\"enumValues\":[{\"id\":\"value1\",\"name\":\"Value 1\",\"$$hashKey\":\"uiGrid-0017\"},{\"id\":\"value2\",\"name\":\"Value 2\",\"$$hashKey\":\"uiGrid-0019\"}]}]},\"tasklisteners\":\"\",\"skipexpression\":\"\",\"categorydefinition\":\"\",\"taskidvariablename\":\"\"},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-727215FD-2710-4710-887B-E6EAB32DF54A\"}],\"bounds\":{\"lowerRight\":{\"x\":250,\"y\":200},\"upperLeft\":{\"x\":150,\"y\":120}},\"dockers\":[]},{\"resourceId\":\"sid-1DACD0B6-1703-4714-941D-262B685DA38E\",\"properties\":{\"overrideid\":\"\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"executionlisteners\":\"\",\"defaultflow\":\"false\",\"skipexpression\":\"\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-B941C66E-929D-48A7-B78B-D1960F12AD9F\"}],\"bounds\":{\"lowerRight\":{\"x\":149.27481967066748,\"y\":164.16619116687724},\"upperLeft\":{\"x\":120.84236782933252,\"y\":162.66974633312276}},\"dockers\":[{\"x\":15,\"y\":15},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"sid-B941C66E-929D-48A7-B78B-D1960F12AD9F\"}},{\"resourceId\":\"sid-63B78DC7-7052-4D99-861C-3F9A5F0F39C4\",\"properties\":{\"overrideid\":\"\",\"name\":\"审批\",\"documentation\":\"\",\"asynchronousdefinition\":\"false\",\"exclusivedefinition\":\"false\",\"executionlisteners\":\"\",\"multiinstance_type\":\"Parallel\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"HQ_USER\",\"multiinstance_variable\":\"hq\",\"multiinstance_condition\":\"\",\"isforcompensation\":\"false\",\"usertaskassignment\":{\"assignment\":{\"type\":\"static\",\"assignee\":\"${hq}\"}},\"formkeydefinition\":\"\",\"formreference\":\"\",\"formfieldvalidation\":true,\"duedatedefinition\":\"\",\"prioritydefinition\":\"\",\"formproperties\":{\"formProperties\":[{\"id\":\"HQ_PASS\",\"name\":\"是否通过\",\"type\":\"boolean\",\"readable\":true,\"writable\":true,\"$$hashKey\":\"uiGrid-0082\",\"required\":true}]},\"tasklisteners\":\"\",\"skipexpression\":\"\",\"categorydefinition\":\"\",\"taskidvariablename\":\"\"},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-C951ECC3-F798-42EF-A894-7D3088793BBA\"}],\"bounds\":{\"lowerRight\":{\"x\":400,\"y\":205},\"upperLeft\":{\"x\":300,\"y\":125}},\"dockers\":[]},{\"resourceId\":\"sid-727215FD-2710-4710-887B-E6EAB32DF54A\",\"properties\":{\"overrideid\":\"\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"executionlisteners\":\"\",\"defaultflow\":\"false\",\"skipexpression\":\"\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-63B78DC7-7052-4D99-861C-3F9A5F0F39C4\"}],\"bounds\":{\"lowerRight\":{\"x\":299.78125,\"y\":165},\"upperLeft\":{\"x\":250.21875,\"y\":160}},\"dockers\":[{\"x\":50,\"y\":40},{\"x\":275,\"y\":160},{\"x\":275,\"y\":165},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"sid-63B78DC7-7052-4D99-861C-3F9A5F0F39C4\"}},{\"resourceId\":\"sid-B7339A59-2C9F-42DE-8B1C-C460CE169879\",\"properties\":{\"overrideid\":\"\",\"name\":\"审批2\\n\",\"documentation\":\"\",\"asynchronousdefinition\":\"false\",\"exclusivedefinition\":\"false\",\"executionlisteners\":\"\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_variable\":\"\",\"multiinstance_condition\":\"\",\"isforcompensation\":\"false\",\"usertaskassignment\":{\"assignment\":{\"type\":\"static\",\"assignee\":\"${LD_USER}\"}},\"formkeydefinition\":\"\",\"formreference\":\"\",\"formfieldvalidation\":true,\"duedatedefinition\":\"\",\"prioritydefinition\":\"\",\"formproperties\":\"\",\"tasklisteners\":\"\",\"skipexpression\":\"\",\"categorydefinition\":\"\",\"taskidvariablename\":\"\"},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-21AC4FA6-2100-49B2-A59A-90FF6DCE2C4D\"}],\"bounds\":{\"lowerRight\":{\"x\":535,\"y\":200},\"upperLeft\":{\"x\":435,\"y\":120}},\"dockers\":[]},{\"resourceId\":\"sid-C951ECC3-F798-42EF-A894-7D3088793BBA\",\"properties\":{\"overrideid\":\"\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"executionlisteners\":\"\",\"defaultflow\":\"false\",\"skipexpression\":\"\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-B7339A59-2C9F-42DE-8B1C-C460CE169879\"}],\"bounds\":{\"lowerRight\":{\"x\":434.4303726662332,\"y\":163.12705083949012},\"upperLeft\":{\"x\":400.5696273337668,\"y\":161.87294916050988}},\"dockers\":[{\"x\":50,\"y\":40},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"sid-B7339A59-2C9F-42DE-8B1C-C460CE169879\"}},{\"resourceId\":\"sid-30BDE863-C86C-48E8-9ED6-709F6B76D2D2\",\"properties\":{\"overrideid\":\"\",\"name\":\"\",\"documentation\":\"\",\"executionlisteners\":\"\"},\"stencil\":{\"id\":\"EndNoneEvent\"},\"childShapes\":[],\"outgoing\":[],\"bounds\":{\"lowerRight\":{\"x\":618,\"y\":179},\"upperLeft\":{\"x\":590,\"y\":151}},\"dockers\":[]},{\"resourceId\":\"sid-21AC4FA6-2100-49B2-A59A-90FF6DCE2C4D\",\"properties\":{\"overrideid\":\"\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"executionlisteners\":\"\",\"defaultflow\":\"false\",\"skipexpression\":\"\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-30BDE863-C86C-48E8-9ED6-709F6B76D2D2\"}],\"bounds\":{\"lowerRight\":{\"x\":589.3828125,\"y\":165},\"upperLeft\":{\"x\":535.6484375,\"y\":160}},\"dockers\":[{\"x\":50,\"y\":40},{\"x\":562.5,\"y\":160},{\"x\":562.5,\"y\":165},{\"x\":14,\"y\":14}],\"target\":{\"resourceId\":\"sid-30BDE863-C86C-48E8-9ED6-709F6B76D2D2\"}}],\"stencil\":{\"id\":\"BPMNDiagram\"},\"stencilset\":{\"namespace\":\"http://b3mn.org/stencilset/bpmn2.0#\",\"url\":\"../editor/stencilsets/bpmn2.0/bpmn2.0.json\"}}";

        Model model = modelService
                .createModel(modelRepresentation, json, "lrj");

        BpmnModel bpmnModel = modelService.getBpmnModel(model);
        byte[] xmlBytes = modelService.getBpmnXML(bpmnModel);

        Deployment deployment = repositoryService.createDeployment()
                .addBpmnModel("123.bpmn20.xml",bpmnModel)
                .key("123")
                .tenantId("test")
                .deploy();

        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .processDefinitionTenantId("test")
                .singleResult();

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(),"业务ID");

        processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(),"业务ID");

        List<ProcessInstance> 业务ID = runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey("业务ID")
                .list();

        System.out.println("第一次启动");
        System.out.println(processInstance.getId());

        runtimeService.deleteProcessInstance(processDefinition.getId(),"取消流程");

        ProcessInstance processInstance2 = runtimeService.startProcessInstanceById(processDefinition.getId(),"业务ID");

    }


    @Test
    public void test4() {
        String json = "{\"modelId\":\"89b59b5d-44e2-11eb-ad9e-005056c00001\",\"bounds\":{\"lowerRight\":{\"x\":1200,\"y\":1050},\"upperLeft\":{\"x\":0,\"y\":0}},\"properties\":{\"process_id\":\"BM_2_10\",\"name\":\"供应商审核\",\"documentation\":\"beizhu\",\"process_author\":\"\",\"process_version\":\"\",\"process_namespace\":\"http://www.flowable.org/processdef\",\"process_historylevel\":\"\",\"isexecutable\":true,\"dataproperties\":\"\",\"executionlisteners\":\"\",\"eventlisteners\":\"\",\"signaldefinitions\":\"\",\"messagedefinitions\":\"\",\"escalationdefinitions\":\"\",\"process_potentialstarteruser\":\"\",\"process_potentialstartergroup\":\"\",\"iseagerexecutionfetch\":\"false\"},\"childShapes\":[{\"resourceId\":\"startEvent1\",\"properties\":{\"overrideid\":\"\",\"name\":\"\",\"documentation\":\"\",\"executionlisteners\":\"\",\"initiator\":\"INITIATOR\",\"formkeydefinition\":\"\",\"formreference\":\"\",\"formfieldvalidation\":true,\"formproperties\":\"\"},\"stencil\":{\"id\":\"StartNoneEvent\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-68DD75B7-BDE7-4BED-8695-E21EBF184D3E\"}],\"bounds\":{\"lowerRight\":{\"x\":130,\"y\":193},\"upperLeft\":{\"x\":100,\"y\":163}},\"dockers\":[]},{\"resourceId\":\"sid-D2A6B2C3-FCBC-4D24-9FA1-FB2ED02C7D08\",\"properties\":{\"overrideid\":\"\",\"name\":\"测试\",\"documentation\":\"\",\"asynchronousdefinition\":\"false\",\"exclusivedefinition\":\"false\",\"executionlisteners\":\"\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_variable\":\"\",\"multiinstance_condition\":\"\",\"isforcompensation\":\"false\",\"usertaskassignment\":{\"assignment\":{\"type\":\"static\",\"initiatorCanCompleteTask\":true,\"assignee\":\"${INITIATOR}\"}},\"formkeydefinition\":\"\",\"formreference\":\"\",\"formfieldvalidation\":true,\"duedatedefinition\":\"\",\"prioritydefinition\":\"\",\"formproperties\":\"\",\"tasklisteners\":\"\",\"skipexpression\":\"\",\"categorydefinition\":\"\",\"taskidvariablename\":\"\"},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-E7F0912B-8247-4165-BA06-5186DC27DA50\"}],\"bounds\":{\"lowerRight\":{\"x\":275,\"y\":218},\"upperLeft\":{\"x\":175,\"y\":138}},\"dockers\":[]},{\"resourceId\":\"sid-68DD75B7-BDE7-4BED-8695-E21EBF184D3E\",\"properties\":{\"overrideid\":\"\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"executionlisteners\":\"\",\"defaultflow\":\"false\",\"skipexpression\":\"\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-D2A6B2C3-FCBC-4D24-9FA1-FB2ED02C7D08\"}],\"bounds\":{\"lowerRight\":{\"x\":174.15625,\"y\":178},\"upperLeft\":{\"x\":130.609375,\"y\":178}},\"dockers\":[{\"x\":15,\"y\":15},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"sid-D2A6B2C3-FCBC-4D24-9FA1-FB2ED02C7D08\"}},{\"resourceId\":\"sid-74B2A0A4-B076-4527-8AA8-2AEFAF1FAB78\",\"properties\":{\"overrideid\":\"\",\"name\":\"\",\"documentation\":\"\",\"executionlisteners\":\"\"},\"stencil\":{\"id\":\"EndNoneEvent\"},\"childShapes\":[],\"outgoing\":[],\"bounds\":{\"lowerRight\":{\"x\":348,\"y\":192},\"upperLeft\":{\"x\":320,\"y\":164}},\"dockers\":[]},{\"resourceId\":\"sid-E7F0912B-8247-4165-BA06-5186DC27DA50\",\"properties\":{\"overrideid\":\"\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"executionlisteners\":\"\",\"defaultflow\":\"false\",\"skipexpression\":\"\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-74B2A0A4-B076-4527-8AA8-2AEFAF1FAB78\"}],\"bounds\":{\"lowerRight\":{\"x\":319.375,\"y\":178},\"upperLeft\":{\"x\":275.390625,\"y\":178}},\"dockers\":[{\"x\":50,\"y\":40},{\"x\":14,\"y\":14}],\"target\":{\"resourceId\":\"sid-74B2A0A4-B076-4527-8AA8-2AEFAF1FAB78\"}}],\"stencil\":{\"id\":\"BPMNDiagram\"},\"stencilset\":{\"namespace\":\"http://b3mn.org/stencilset/bpmn2.0#\",\"url\":\"../editor/stencilsets/bpmn2.0/bpmn2.0.json\"}}";
        RpcUtils.setTenantId("test");
       /* InputStream inputStream = new ClassPathResource("/bpmn/123.bpmn20.xml").getInputStream();
        Deployment deployment = repositoryService.createDeployment()
                .addInputStream("123.bpmn20.xml",inputStream)
                .key("test")
                .deploy();*/
        ModelRepresentation modelRepresentation = new ModelRepresentation();
        modelRepresentation.setKey("123");
        modelRepresentation.setName("name123");

        Model model = modelService
                .createModel(modelRepresentation, json, "lrj");

        BpmnModel bpmnModel = modelService.getBpmnModel(model);
        Deployment deployment = repositoryService.createDeployment()
                .addBpmnModel("123.bpmn20.xml",bpmnModel)
                .key("123")
                .tenantId("test")
                .deploy();

        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .processDefinitionTenantId("test")
                .singleResult();
        Authentication.setAuthenticatedUserId("4");
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), "bk");
        Authentication.setAuthenticatedUserId(null);
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();

        System.out.println(task.getAssignee());
    }

    @Test
    public void test5() {
        String json = "{\"modelId\":\"85307f08-4791-11eb-8b0c-005056c00008\",\"bounds\":{\"lowerRight\":{\"x\":1200,\"y\":1050},\"upperLeft\":{\"x\":0,\"y\":0}},\"properties\":{\"process_id\":\"BM_2_10\",\"name\":\"1231\",\"documentation\":\"12321\",\"process_author\":\"\",\"process_version\":\"\",\"process_namespace\":\"http://www.flowable.org/processdef\",\"process_historylevel\":\"\",\"isexecutable\":true,\"dataproperties\":\"\",\"executionlisteners\":\"\",\"eventlisteners\":\"\",\"signaldefinitions\":\"\",\"messagedefinitions\":\"\",\"escalationdefinitions\":\"\",\"process_potentialstarteruser\":\"\",\"process_potentialstartergroup\":\"\",\"iseagerexecutionfetch\":\"false\"},\"childShapes\":[{\"resourceId\":\"startEvent1\",\"properties\":{\"overrideid\":\"\",\"name\":\"\",\"documentation\":\"\",\"executionlisteners\":\"\",\"initiator\":\"\",\"formkeydefinition\":\"\",\"formreference\":\"\",\"formfieldvalidation\":true,\"formproperties\":\"\"},\"stencil\":{\"id\":\"StartNoneEvent\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-86071841-1B22-4B94-8968-10BEA041C4C2\"}],\"bounds\":{\"lowerRight\":{\"x\":130,\"y\":193},\"upperLeft\":{\"x\":100,\"y\":163}},\"dockers\":[]},{\"resourceId\":\"sid-DEE274B8-C815-48A5-B20A-CA4266F559E2\",\"properties\":{\"overrideid\":\"\",\"name\":\"ASD\",\"documentation\":\"\",\"asynchronousdefinition\":\"false\",\"exclusivedefinition\":\"false\",\"executionlisteners\":\"\",\"multiinstance_type\":\"None\",\"multiinstance_cardinality\":\"\",\"multiinstance_collection\":\"\",\"multiinstance_variable\":\"\",\"multiinstance_condition\":\"\",\"isforcompensation\":\"false\",\"usertaskassignment\":\"\",\"formkeydefinition\":\"\",\"formreference\":\"\",\"formfieldvalidation\":true,\"duedatedefinition\":\"\",\"prioritydefinition\":\"\",\"formproperties\":\"\",\"tasklisteners\":\"\",\"skipexpression\":\"\",\"categorydefinition\":\"\",\"taskidvariablename\":\"\",\"businessdataeditable\":true},\"stencil\":{\"id\":\"UserTask\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-D2A2024B-28CD-470D-A755-AAE8585C184D\"}],\"bounds\":{\"lowerRight\":{\"x\":275,\"y\":218},\"upperLeft\":{\"x\":175,\"y\":138}},\"dockers\":[]},{\"resourceId\":\"sid-86071841-1B22-4B94-8968-10BEA041C4C2\",\"properties\":{\"overrideid\":\"\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"executionlisteners\":\"\",\"defaultflow\":\"false\",\"skipexpression\":\"\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-DEE274B8-C815-48A5-B20A-CA4266F559E2\"}],\"bounds\":{\"lowerRight\":{\"x\":174.15625,\"y\":178},\"upperLeft\":{\"x\":130.609375,\"y\":178}},\"dockers\":[{\"x\":15,\"y\":15},{\"x\":50,\"y\":40}],\"target\":{\"resourceId\":\"sid-DEE274B8-C815-48A5-B20A-CA4266F559E2\"}},{\"resourceId\":\"sid-C5D60F81-2918-46F3-A3DD-4EC175386F6D\",\"properties\":{\"overrideid\":\"\",\"name\":\"\",\"documentation\":\"\",\"executionlisteners\":\"\"},\"stencil\":{\"id\":\"EndNoneEvent\"},\"childShapes\":[],\"outgoing\":[],\"bounds\":{\"lowerRight\":{\"x\":348,\"y\":192},\"upperLeft\":{\"x\":320,\"y\":164}},\"dockers\":[]},{\"resourceId\":\"sid-D2A2024B-28CD-470D-A755-AAE8585C184D\",\"properties\":{\"overrideid\":\"\",\"name\":\"\",\"documentation\":\"\",\"conditionsequenceflow\":\"\",\"executionlisteners\":\"\",\"defaultflow\":\"false\",\"skipexpression\":\"\"},\"stencil\":{\"id\":\"SequenceFlow\"},\"childShapes\":[],\"outgoing\":[{\"resourceId\":\"sid-C5D60F81-2918-46F3-A3DD-4EC175386F6D\"}],\"bounds\":{\"lowerRight\":{\"x\":319.375,\"y\":178},\"upperLeft\":{\"x\":275.390625,\"y\":178}},\"dockers\":[{\"x\":50,\"y\":40},{\"x\":14,\"y\":14}],\"target\":{\"resourceId\":\"sid-C5D60F81-2918-46F3-A3DD-4EC175386F6D\"}}],\"stencil\":{\"id\":\"BPMNDiagram\"},\"stencilset\":{\"namespace\":\"http://b3mn.org/stencilset/bpmn2.0#\",\"url\":\"../editor/stencilsets/bpmn2.0/bpmn2.0.json\"}}";
        RpcUtils.setTenantId("test");
        ModelRepresentation modelRepresentation = new ModelRepresentation();
        modelRepresentation.setKey("123");
        modelRepresentation.setName("name123");

        Model model = modelService
                .createModel(modelRepresentation, json, "lrj");

        byte[] bytes = modelService.getBpmnXML(model);

        Deployment deployment = repositoryService.createDeployment()
                .addBytes("123.bpmn20.xml",bytes)
                .key("123")
                .tenantId("test")
                .deploy();

        ProcessDefinition processDefinition = repositoryService
                .createProcessDefinitionQuery()
                .deploymentId(deployment.getId())
                .processDefinitionTenantId("test")
                .singleResult();
        Authentication.setAuthenticatedUserId("4");
        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinition.getId(), "bk");
        BpmnModel bpmnModel1 = repositoryService.getBpmnModel(processInstance.getProcessDefinitionId());
        Task task = taskService.createTaskQuery().processInstanceId(processInstance.getId()).singleResult();
        UserTask flowElement = (UserTask) bpmnModel1.getFlowElement(task.getTaskDefinitionKey());
        List<CustomProperty> customProperties = flowElement.getCustomProperties();
        System.out.println(customProperties);

    }
}
