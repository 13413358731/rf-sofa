package com.realfinance.sofa.cg.controller.flow;

import com.alibaba.fastjson.JSONObject;
import com.alipay.sofa.runtime.api.annotation.SofaReference;
import com.alipay.sofa.runtime.api.annotation.SofaReferenceBinding;
import com.realfinance.sofa.flow.facade.ModelerFacade;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@Tag(name = "表单")
@Hidden
@RequestMapping("/flow/modeler-app")
public class FormController {

    @SofaReference(interfaceType = ModelerFacade.class, uniqueId = "${service.rf-flow.id}", binding = @SofaReferenceBinding(bindingType = "bolt"))
    private ModelerFacade modelerFacade;


    @GetMapping(value = "/rest/form-models", produces = "application/json")
    public Object getForms(@RequestParam(required = false) String filter, HttpServletRequest request) {
        String validFilter = makeValidFilterText(filter);
        return modelerFacade.getForms(validFilter);
    }

    @GetMapping(value = "/rest/form-models/{formId}", produces = "application/json")
    public Object getForm(@PathVariable String formId) {
        return modelerFacade.getFormById(formId);
    }

    @GetMapping(value = "/rest/form-models/values", produces = "application/json")
    public Object getForms(HttpServletRequest request) {
        String[] formIds = request.getParameterValues("formId");
        return modelerFacade.getFormsByIds(formIds);
    }

    @GetMapping(value = "/rest/form-models/{formId}/history/{formHistoryId}", produces = "application/json")
    public Object getFormHistory(@PathVariable String formId, @PathVariable String formHistoryId) {
        return modelerFacade.getFormHistory(formId, formHistoryId);
    }

    @PutMapping(value = "/rest/form-models/{formId}", produces = "application/json")
    public Object saveForm(@PathVariable String formId, @RequestBody JSONObject saveRepresentation) {
        return modelerFacade.saveForm(formId, saveRepresentation.toJSONString());
    }

    protected String makeValidFilterText(String filterText) {
        String validFilter = null;

        if (filterText != null) {
            String trimmed = StringUtils.trim(filterText);
            if (trimmed.length() >= 2) {
                validFilter = "%" + trimmed.toLowerCase() + "%";
            }
        }
        return validFilter;
    }

}
