package com.realfinance.sofa.cg.controller.flow;

import com.alibaba.fastjson.JSONObject;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.flow.facade.ModelerFacade;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Map;

@RestController
@Tag(name = "工作流模型")
@Hidden
@RequestMapping("/flow/modeler-app")
public class ModelController {

    private static final Logger log = LoggerFactory.getLogger(ModelController.class);

    @SofaReference(interfaceType = ModelerFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private ModelerFacade modelerFacade;

    @GetMapping(value = "/rest/models", produces = "application/json")
    public Object getModels(@RequestParam(required = false) String filter, @RequestParam(required = false) String sort, @RequestParam(required = false) Integer modelType,
                            HttpServletRequest request) {
        return modelerFacade.getModels(filter, sort, modelType, request.getQueryString());
    }

    @PostMapping(value = "/rest/models", produces = "application/json")
    public Object createModel(@RequestBody JSONObject modelRepresentation) {
        return modelerFacade.createModel(modelRepresentation.toJSONString());
    }

    @GetMapping(value = "/rest/models/{modelId}", produces = "application/json")
    public Object getModel(@PathVariable String modelId) {
        return modelerFacade.getModelRepresentation(modelId);
    }

    @GetMapping(value = "/rest/models/{modelId}/thumbnail", produces = MediaType.IMAGE_PNG_VALUE)
    public Object getModelThumbnail(@PathVariable String modelId) {
        return modelerFacade.getModelThumbnail(modelId);
    }

    @PutMapping(value = "/rest/models/{modelId}")
    public Object updateModel(@PathVariable String modelId, @RequestBody JSONObject updatedModel) {
        return modelerFacade.updateModel(modelId,updatedModel.toJSONString());
    }

    @ResponseStatus(value = HttpStatus.OK)
    @DeleteMapping(value = "/rest/models/{modelId}")
    public void deleteModel(@PathVariable String modelId) {
        modelerFacade.deleteModel(modelId);
    }

    @GetMapping(value = "/rest/models/{modelId}/editor/json", produces = "application/json")
    public Object getModelJSON(@PathVariable String modelId) {
        return modelerFacade.getModelJSON(modelId);
    }

    @PostMapping(value = "/rest/models/{modelId}/editor/json")
    public Object saveModel(@PathVariable String modelId, @RequestBody MultiValueMap<String, String> values) {
        return modelerFacade.saveModel(modelId,values);
    }

    @PostMapping(value = "/rest/model/validate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Object validate(@RequestBody JSONObject body){
        if (body != null && body.containsKey("stencilset")) {
            JSONObject stencilset = body.getJSONObject("stencilset");
            if (stencilset.containsKey("namespace")) {
                String namespace = stencilset.getString("namespace");
                if (namespace.endsWith("bpmn2.0#")) {
                    return modelerFacade.validate(body.toJSONString());
                }
            }
        }
        return Collections.emptyList();
    }


    @GetMapping(value = "/rest/models/{modelId}/history", produces = "application/json")
    public Object getModelHistoryCollection(@PathVariable String modelId, @RequestParam(value = "includeLatestVersion", required = false) Boolean includeLatestVersion) {
        return modelerFacade.getModelHistoryCollection(modelId,includeLatestVersion);
    }

    @GetMapping(value = "/rest/models/{processModelId}/bpmn20")
    public void getProcessModelBpmn20Xml(HttpServletResponse response, @PathVariable String processModelId) throws IOException {
        Map<String, Object> result = modelerFacade.getProcessModelBpmn20Xml(processModelId);
        generateBpmn20Xml(response, result);
    }

    @GetMapping(value = "/rest/models/{processModelId}/history/{processModelHistoryId}/bpmn20")
    public void getHistoricProcessModelBpmn20Xml(HttpServletResponse response, @PathVariable String processModelId, @PathVariable String processModelHistoryId) throws IOException {
        Map<String,Object> result = modelerFacade.getHistoricProcessModelBpmn20Xml(processModelId, processModelHistoryId);
        generateBpmn20Xml(response, result);
    }

    private void generateBpmn20Xml(HttpServletResponse response, Map<String, Object> result) throws IOException {
        byte[] xmlBytes = (byte[]) result.get("xmlBytes");
        if (xmlBytes != null) {
            String name = ((String) result.get("name")).replaceAll(" ", "_") + ".bpmn20.xml";
            String encodedName = null;
            try {
                encodedName = "UTF-8''" + URLEncoder.encode(name, "UTF-8");
            } catch (Exception e) {
                log.warn("Failed to encode name {}", name);
            }
            String contentDispositionValue = "attachment; filename=" + name;
            if (encodedName != null) {
                contentDispositionValue += "; filename*=" + encodedName;
            }
            response.setHeader("Content-Disposition", contentDispositionValue);
            ServletOutputStream servletOutputStream = response.getOutputStream();
            response.setContentType("application/xml");
            BufferedInputStream in = new BufferedInputStream(new ByteArrayInputStream(xmlBytes));
            byte[] buffer = new byte[8096];
            while (true) {
                int count = in.read(buffer);
                if (count == -1) {
                    break;
                }
                servletOutputStream.write(buffer, 0, count);
            }
            // Flush and close stream
            servletOutputStream.flush();
            servletOutputStream.close();
        }
    }

}
