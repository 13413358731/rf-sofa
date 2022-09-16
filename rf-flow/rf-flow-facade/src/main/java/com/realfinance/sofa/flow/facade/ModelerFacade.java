package com.realfinance.sofa.flow.facade;

import org.springframework.util.MultiValueMap;

import java.util.Map;

public interface ModelerFacade {
    // --org.flowable.ui.modeler.rest.app.ModelsResource--
    // ---------->
    String getModels(String filter, String sort, Integer modelType, String queryString);

    String createModel(String modelRepresentationJson);
    // <----------

    // --org.flowable.ui.modeler.rest.api.ApiModelResource--
    // TODO: 2020/11/10 这些接口要修改为org.flowable.ui.modeler.rest.app.ModelResource上的
    // ---------->
    String getModelRepresentation(String modelId);

    byte[] getModelThumbnail(String modelId);

    String updateModel(String modelId, String updatedModelJson);

    void deleteModel(String modelId);

    String getModelJSON(String modelId);

    String saveModel(String modelId, MultiValueMap<String, String> values);
    // <----------

    // --org.flowable.ui.modeler.rest.app.ModelHistoryResource--
    String getModelHistoryCollection(String modelId, Boolean includeLatestVersion);
    // ---------->

    // --org.flowable.ui.modeler.rest.app.ModelBpmnResource--
    Map<String, Object> getProcessModelBpmn20Xml(String processModelId);

    Map<String, Object> getHistoricProcessModelBpmn20Xml(String processModelId, String processModelHistoryId);
    // ---------->

    // --org.flowable.ui.modeler.rest.app.EditorDisplayJsonClientResource--
    // ---------->
    String getEditorDisplayJson(String modelId);
    // <----------

    // --org.flowable.ui.modeler.rest.app.EditorDisplayJsonClientResource--
    // ---------->
    String validate(String body);

    // <----------


    // --org.flowable.ui.modeler.rest.app.FormsResource--
    // ---------->
    String getForms(String validFilter);
    // <----------

    // --org.flowable.ui.modeler.rest.app.FormResource--
    // ---------->

    String getFormById(String formId);

    String getFormsByIds(String[] formIds);

    String getFormHistory(String formId, String formHistoryId);

    String saveForm(String formId, String saveRepresentationJson);
    // <----------
}
