package com.realfinance.sofa.flow.impl;

import com.alipay.sofa.runtime.api.annotation.SofaService;
import com.alipay.sofa.runtime.api.annotation.SofaServiceBinding;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.realfinance.sofa.common.rpc.util.RpcUtils;
import com.realfinance.sofa.flow.facade.ModelerFacade;
import com.realfinance.sofa.flow.util.DummyRequest;
import org.apache.commons.lang3.StringUtils;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.GraphicInfo;
import org.flowable.editor.language.json.converter.BpmnJsonConverter;
import org.flowable.ui.common.model.ResultListDataRepresentation;
import org.flowable.ui.common.service.exception.BadRequestException;
import org.flowable.ui.common.service.exception.ConflictingRequestException;
import org.flowable.ui.common.service.exception.InternalServerErrorException;
import org.flowable.ui.modeler.domain.AbstractModel;
import org.flowable.ui.modeler.domain.Model;
import org.flowable.ui.modeler.domain.ModelHistory;
import org.flowable.ui.modeler.model.FormSaveRepresentation;
import org.flowable.ui.modeler.model.ModelKeyRepresentation;
import org.flowable.ui.modeler.model.ModelRepresentation;
import org.flowable.ui.modeler.model.form.FormRepresentation;
import org.flowable.ui.modeler.repository.ModelHistoryRepository;
import org.flowable.ui.modeler.repository.ModelRepository;
import org.flowable.ui.modeler.repository.ModelSort;
import org.flowable.ui.modeler.service.*;
import org.flowable.ui.modeler.serviceapi.ModelService;
import org.flowable.validation.ProcessValidator;
import org.flowable.validation.ProcessValidatorFactory;
import org.flowable.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import static com.realfinance.sofa.flow.util.ExceptionUtils.businessException;

@Service
@SofaService(interfaceType = ModelerFacade.class, uniqueId = "${service.rf-flow.id}",
        bindings = {
                @SofaServiceBinding(bindingType = "bolt"),
                @SofaServiceBinding(bindingType = "jvm")
        })
@Transactional
public class ModelerImpl implements ModelerFacade {
    private static final Logger log = LoggerFactory.getLogger(ModelerImpl.class);
    private static final String RESOLVE_ACTION_OVERWRITE = "overwrite";
    private static final String RESOLVE_ACTION_SAVE_AS = "saveAs";
    private static final String RESOLVE_ACTION_NEW_VERSION = "newVersion";

    protected final FlowableModelQueryService modelQueryService;
    protected final ModelRepository modelRepository;
    protected final ModelHistoryRepository modelHistoryRepository;
    protected final ModelService modelService;
    protected final FlowableFormService flowableFormService;
    protected final ObjectMapper objectMapper;
    protected final BpmnDisplayJsonConverter bpmnDisplayJsonConverter;
    protected final CmmnDisplayJsonConverter cmmnDisplayJsonConverter;
    protected final DmnDisplayJsonConverter dmnDisplayJsonConverter;

    public ModelerImpl(FlowableModelQueryService modelQueryService,
                       ModelRepository modelRepository,
                       ModelHistoryRepository modelHistoryRepository,
                       ModelService modelService,
                       FlowableFormService flowableFormService,
                       ObjectMapper objectMapper,
                       BpmnDisplayJsonConverter bpmnDisplayJsonConverter,
                       CmmnDisplayJsonConverter cmmnDisplayJsonConverter,
                       DmnDisplayJsonConverter dmnDisplayJsonConverter) {
        this.modelQueryService = modelQueryService;
        this.modelRepository = modelRepository;
        this.modelHistoryRepository = modelHistoryRepository;
        this.modelService = modelService;
        this.flowableFormService = flowableFormService;
        this.objectMapper = objectMapper;
        this.bpmnDisplayJsonConverter = bpmnDisplayJsonConverter;
        this.cmmnDisplayJsonConverter = cmmnDisplayJsonConverter;
        this.dmnDisplayJsonConverter = dmnDisplayJsonConverter;
    }


    @Override
    public String getModels(String filter, String sort, Integer modelType, String queryString) {
        try {
            DummyRequest dummyRequest = new DummyRequest();
            dummyRequest.setQueryString(queryString);
            ResultListDataRepresentation models = modelQueryService.getModels(filter, sort, modelType, dummyRequest);
            return objectMapper.writeValueAsString(models);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("getModels:",e);
            }
            throw businessException(e.getMessage());
        }

    }

    @Override
    public String createModel(String modelRepresentationJson) {
        try {
            ModelRepresentation modelRepresentation = objectMapper.readValue(modelRepresentationJson,ModelRepresentation.class);
            modelRepresentation.setKey(modelRepresentation.getKey().replaceAll(" ", ""));

            ModelKeyRepresentation modelKeyInfo = modelService.validateModelKey(null, modelRepresentation.getModelType(), modelRepresentation.getKey());
            if (modelKeyInfo.isKeyAlreadyExists()) {
                throw new BadRequestException("Provided model key already exists: " + modelRepresentation.getKey());
            }

            String json = modelService.createModelJson(modelRepresentation);

            Model newModel = modelService.createModel(modelRepresentation, json, RpcUtils.requirePrincipalId().toString());

            return objectMapper.writeValueAsString(new ModelRepresentation(newModel));
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("createModel:",e);
            }
            throw businessException(e.getMessage());
        }

    }

    @Override
    public String getModelRepresentation(String modelId) {
        try {
            ModelRepresentation modelRepresentation = modelService.getModelRepresentation(modelId);
            return objectMapper.writeValueAsString(modelRepresentation);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("getModelRepresentation:",e);
            }
            throw businessException(e.getMessage());
        }
    }

    @Override
    public byte[] getModelThumbnail(String modelId) {
        try {
            Model model = modelService.getModel(modelId);
            return model.getThumbnail();
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("getModelThumbnail:",e);
            }
            throw businessException(e.getMessage());
        }
    }

    @Override
    public String updateModel(String modelId, String updatedModelJson) {
        try {
            ModelRepresentation updatedModel = objectMapper.readValue(updatedModelJson,ModelRepresentation.class);
            // Get model, write-permission required if not a favorite-update
            Model model = modelService.getModel(modelId);

            ModelKeyRepresentation modelKeyInfo = modelService.validateModelKey(model, model.getModelType(), updatedModel.getKey());
            if (modelKeyInfo.isKeyAlreadyExists()) {
                throw new BadRequestException("Model with provided key already exists " + updatedModel.getKey());
            }

            try {
                updatedModel.updateModel(model);
                modelRepository.save(model);

                ModelRepresentation result = new ModelRepresentation(model);
                return objectMapper.writeValueAsString(result);

            } catch (Exception e) {
                throw new BadRequestException("Model cannot be updated: " + modelId);
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("updateModel:",e);
            }
            throw businessException(e.getMessage());
        }

    }

    @Override
    public void deleteModel(String modelId) {
        try {
            // Get model to check if it exists, read-permission required for delete
            Model model = modelService.getModel(modelId);

            try {
                modelService.deleteModel(model.getId());

            } catch (Exception e) {
                log.error("Error while deleting: ", e);
                throw new BadRequestException("Model cannot be deleted: " + modelId);
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("deleteModel:",e);
            }
            throw businessException(e.getMessage());
        }

    }

    @Override
    public String getModelJSON(String modelId) {
        try {
            Model model = modelService.getModel(modelId);
            ObjectNode modelNode = objectMapper.createObjectNode();
            modelNode.put("modelId", model.getId());
            modelNode.put("name", model.getName());
            modelNode.put("key", model.getKey());
            modelNode.put("description", model.getDescription());
            modelNode.putPOJO("lastUpdated", model.getLastUpdated());
            modelNode.put("lastUpdatedBy", model.getLastUpdatedBy());
            if (StringUtils.isNotEmpty(model.getModelEditorJson())) {
                try {
                    ObjectNode editorJsonNode = (ObjectNode) objectMapper.readTree(model.getModelEditorJson());
                    editorJsonNode.put("modelType", "model");
                    modelNode.set("model", editorJsonNode);
                } catch (Exception e) {
                    log.error("Error reading editor json {}", modelId, e);
                    throw new InternalServerErrorException("Error reading editor json " + modelId);
                }

            } else {
                ObjectNode editorJsonNode = objectMapper.createObjectNode();
                editorJsonNode.put("id", "canvas");
                editorJsonNode.put("resourceId", "canvas");
                ObjectNode stencilSetNode = objectMapper.createObjectNode();
                stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
                editorJsonNode.put("modelType", "model");
                modelNode.set("model", editorJsonNode);
            }
            return objectMapper.writeValueAsString(modelNode);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("getModelJSON:",e);
            }
            throw businessException(e.getMessage());
        }
    }

    @Override
    public String saveModel(String modelId, MultiValueMap<String, String> values) {
        try {
            // Validation: see if there was another update in the meantime
            long lastUpdated = -1L;
            String lastUpdatedString = values.getFirst("lastUpdated");
            if (lastUpdatedString == null) {
                throw new BadRequestException("Missing lastUpdated date");
            }
            try {
                Date readValue = objectMapper.getDeserializationConfig().getDateFormat().parse(lastUpdatedString);
                lastUpdated = readValue.getTime();
            } catch (ParseException e) {
                throw new BadRequestException("Invalid lastUpdated date: '" + lastUpdatedString + "'");
            }

            Model model = modelService.getModel(modelId);
            String currentUserId = RpcUtils.requirePrincipalId().toString();
            boolean currentUserIsOwner = model.getLastUpdatedBy().equals(currentUserId);
            String resolveAction = values.getFirst("conflictResolveAction");

            // If timestamps differ, there is a conflict or a conflict has been resolved by the user
            if (model.getLastUpdated().getTime() != lastUpdated) {

                if (RESOLVE_ACTION_SAVE_AS.equals(resolveAction)) {

                    String saveAs = values.getFirst("saveAs");
                    String json = values.getFirst("json_xml");
                    return objectMapper.writeValueAsString(createNewModel(saveAs, model.getDescription(), model.getModelType(), json));

                } else if (RESOLVE_ACTION_OVERWRITE.equals(resolveAction)) {
                    return objectMapper.writeValueAsString(updateModel(model, values, false));
                } else if (RESOLVE_ACTION_NEW_VERSION.equals(resolveAction)) {
                    return objectMapper.writeValueAsString(updateModel(model, values, true));
                } else {

                    // Exception case: the user is the owner and selected to create a new version
                    String isNewVersionString = values.getFirst("newversion");
                    if (currentUserIsOwner && "true".equals(isNewVersionString)) {
                        return objectMapper.writeValueAsString(updateModel(model, values, true));
                    } else {
                        // Tried everything, this is really a conflict, return 409
                        ConflictingRequestException exception = new ConflictingRequestException("Process model was updated in the meantime");
                        exception.addCustomData("userFullName", model.getLastUpdatedBy());
                        exception.addCustomData("newVersionAllowed", currentUserIsOwner);
                        throw exception;
                    }
                }
            } else {
                // Actual, regular, update
                return objectMapper.writeValueAsString(updateModel(model, values, false));
            }
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("saveModel:",e);
            }
            throw businessException(e.getMessage());
        }
    }

    @Override
    public String getModelHistoryCollection(String modelId, Boolean includeLatestVersion) {
        try {
            Model model = modelService.getModel(modelId);
            List<ModelHistory> history = modelHistoryRepository.findByModelId(model.getId());
            ResultListDataRepresentation result = new ResultListDataRepresentation();

            List<ModelRepresentation> representations = new ArrayList<>();

            // Also include the latest version of the model
            if (Boolean.TRUE.equals(includeLatestVersion)) {
                representations.add(new ModelRepresentation(model));
            }
            if (history.size() > 0) {
                for (ModelHistory modelHistory : history) {
                    representations.add(new ModelRepresentation(modelHistory));
                }
                result.setData(representations);
            }

            // Set size and total
            result.setSize(representations.size());
            result.setTotal(Long.valueOf(representations.size()));
            result.setStart(0);
            return objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("getModelHistoryCollection:",e);
            }
            throw businessException(e.getMessage());
        }
    }

    @Override
    public Map<String, Object> getProcessModelBpmn20Xml(String processModelId) {
        if (processModelId == null) {
            throw new BadRequestException("No process model id provided");
        }
        try {
            Model model = modelService.getModel(processModelId);
            Map<String,Object> result = new HashMap<>();
            if (model.getModelEditorJson() != null) {
                BpmnModel bpmnModel = modelService.getBpmnModel(model);
                byte[] xmlBytes = modelService.getBpmnXML(bpmnModel);
                result.put("xmlBytes",xmlBytes);
            }
            result.put("name",model.getName());
            return result;
        } catch (Exception e) {
            log.error("Could not generate BPMN 2.0 XML", e);
            throw businessException("Could not generate BPMN 2.0 xml");
        }
    }

    @Override
    public Map<String, Object> getHistoricProcessModelBpmn20Xml(String processModelId, String processModelHistoryId) {
        if (processModelId == null) {
            throw new BadRequestException("No process model id provided");
        }
        try {
            ModelHistory historicModel = modelService.getModelHistory(processModelId, processModelHistoryId);
            Map<String,Object> result = new HashMap<>();
            if (historicModel.getModelEditorJson() != null) {
                BpmnModel bpmnModel = modelService.getBpmnModel(historicModel);
                byte[] xmlBytes = modelService.getBpmnXML(bpmnModel);
                result.put("xmlBytes",xmlBytes);
            }
            result.put("name",historicModel.getName());
            return result;
        } catch (Exception e) {
            log.error("Could not generate BPMN 2.0 XML", e);
            throw businessException("Could not generate BPMN 2.0 xml");
        }
    }

    @Override
    public String getEditorDisplayJson(String modelId) {
        try {
            ObjectNode displayNode = objectMapper.createObjectNode();
            Model model = modelService.getModel(modelId);
            if (model.getModelType() != null && AbstractModel.MODEL_TYPE_CMMN == model.getModelType()) {
                cmmnDisplayJsonConverter.processCaseElements(model, displayNode, new org.flowable.cmmn.model.GraphicInfo());
            } else if (model.getModelType() != null && AbstractModel.MODEL_TYPE_DECISION_SERVICE == model.getModelType()) {
                dmnDisplayJsonConverter.processDefinitionElements(model, displayNode, new org.flowable.dmn.model.GraphicInfo());
            } else {
                bpmnDisplayJsonConverter.processProcessElements(model, displayNode, new GraphicInfo());
            }
            return objectMapper.writeValueAsString(displayNode);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("getEditorDisplayJson:",e);
            }
            throw businessException(e.getMessage());
        }
    }

    @Override
    public String validate(String body) {
        try {
            JsonNode jsonNode = objectMapper.readTree(body);
            BpmnModel bpmnModel = new BpmnJsonConverter().convertToBpmnModel(jsonNode);
            ProcessValidator validator = new ProcessValidatorFactory().createDefaultProcessValidator();
            List<ValidationError> errors = validator.validate(bpmnModel);
            return objectMapper.writeValueAsString(errors);
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("validate:",e);
            }
            throw businessException(e.getMessage());
        }
    }

    @Override
    public String getForms(String validFilter) {
        try {
            List<Model> models = null;
            if (validFilter != null) {
                models = modelRepository.findByModelTypeAndFilter(AbstractModel.MODEL_TYPE_FORM, validFilter, ModelSort.NAME_ASC);
            } else {
                models = modelRepository.findByModelType(AbstractModel.MODEL_TYPE_FORM, ModelSort.NAME_ASC);
            }
            List<FormRepresentation> reps = new ArrayList<>();
            for (Model model : models) {
                reps.add(new FormRepresentation(model));
            }
            Collections.sort(reps, new NameComparator());
            ResultListDataRepresentation result = new ResultListDataRepresentation(reps);
            result.setTotal(Long.valueOf(models.size()));
            return objectMapper.writeValueAsString(result);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("getForms:",e);
            }
            throw businessException(e.getMessage());
        }
    }

    @Override
    public String getFormById(String formId) {
        try {
            FormRepresentation form = flowableFormService.getForm(formId);
            return objectMapper.writeValueAsString(form);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("getFormById:",e);
            }
            throw businessException(e.getMessage());
        }
    }

    @Override
    public String getFormsByIds(String[] formIds) {
        try {
            List<FormRepresentation> forms = flowableFormService.getForms(formIds);
            return objectMapper.writeValueAsString(forms);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("getFormsByIds:",e);
            }
            throw businessException(e.getMessage());
        }
    }

    @Override
    public String getFormHistory(String formId, String formHistoryId) {
        try {
            FormRepresentation formHistory = flowableFormService.getFormHistory(formId, formHistoryId);
            return objectMapper.writeValueAsString(formHistory);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("getFormHistory:",e);
            }
            throw businessException(e.getMessage());
        }
    }

    @Override
    public String saveForm(String formId, String saveRepresentationJson) {
        try {
            FormSaveRepresentation saveRepresentation = objectMapper.readValue(saveRepresentationJson, FormSaveRepresentation.class);
            FormRepresentation formRepresentation = flowableFormService.saveForm(formId, saveRepresentation);
            return objectMapper.writeValueAsString(formRepresentation);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("saveForm:",e);
            }
            throw businessException(e.getMessage());
        }
    }

    protected ModelRepresentation updateModel(Model model, MultiValueMap<String, String> values, boolean forceNewVersion) {

//        String name = values.getFirst("name");
        String name = model.getName(); // name不变
//        String key = values.getFirst("key");
        String key = model.getKey(); // key不变

        String description = values.getFirst("description");
        String isNewVersionString = values.getFirst("newversion");
        String newVersionComment = null;

        ModelKeyRepresentation modelKeyInfo = modelService.validateModelKey(model, model.getModelType(), key);
        if (modelKeyInfo.isKeyAlreadyExists()) {
            throw new BadRequestException("Model with provided key already exists " + key);
        }

        boolean newVersion = false;
        if (forceNewVersion) {
            newVersion = true;
            newVersionComment = values.getFirst("comment");
        } else {
            if (isNewVersionString != null) {
                newVersion = "true".equals(isNewVersionString);
                newVersionComment = values.getFirst("comment");
            }
        }

        String json = values.getFirst("json_xml");

        // json 里的key和name也不变
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            ObjectNode properties = (ObjectNode) jsonNode.get("properties");
            properties.put("name",name);
            properties.put("process_id",key);
            json = objectMapper.writeValueAsString(jsonNode);
        } catch (IOException e) {
            if (log.isErrorEnabled()) {
                log.error("解析json_xml异常",e);
            }
        }

        try {
            model = modelService.saveModel(model.getId(), name, key, description, json, newVersion,
                    newVersionComment, RpcUtils.requirePrincipalId().toString());
            return new ModelRepresentation(model);

        } catch (Exception e) {
            log.error("Error saving model {}", model.getId(), e);
            throw new BadRequestException("Process model could not be saved " + model.getId());
        }
    }

    protected ModelRepresentation createNewModel(String name, String description, Integer modelType, String editorJson) {
        ModelRepresentation model = new ModelRepresentation();
        model.setName(name);
        model.setDescription(description);
        model.setModelType(modelType);
        Model newModel = modelService.createModel(model, editorJson, RpcUtils.requirePrincipalId().toString());
        return new ModelRepresentation(newModel);
    }

    class NameComparator implements Comparator<FormRepresentation> {
        @Override
        public int compare(FormRepresentation o1, FormRepresentation o2) {
            return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
        }
    }
}
