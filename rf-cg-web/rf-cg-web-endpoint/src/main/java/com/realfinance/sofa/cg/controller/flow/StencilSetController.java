package com.realfinance.sofa.cg.controller.flow;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Hidden
@RequestMapping("/flow/modeler-app")
public class StencilSetController {

    private static final Logger log = LoggerFactory.getLogger(StencilSetController.class);

    @Autowired
    protected ObjectMapper objectMapper;

    @GetMapping(value = "/rest/stencil-sets/editor", produces = "application/json")
    public ResponseEntity<?> getStencilSetForEditor() {
        try {
            JsonNode stencilNode = objectMapper.readTree(new ClassPathResource("stencilset_bpmn.json").getInputStream());
            return ResponseEntity.ok(stencilNode);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Error reading bpmn stencil set json", e);
            }
            return ResponseEntity.status(500).body("Error reading bpmn stencil set json");
        }
    }
}